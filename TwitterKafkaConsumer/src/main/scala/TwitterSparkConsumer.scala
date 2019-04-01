import java.util.HashMap
import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.kafka.KafkaUtils

object twitterSparkConsumer {
  def main(args: Array[String]) {

    // set topic name to match tweet producer class
    val topicName = "FFXIVTweets"

    //other stuff
    val batchTimer = Seconds(1)
    val threads = 1
    val zookeeper = "localhost:2181"
    val groupName = "consumergroupTwitter"

    //set Spark config
    val conf = new SparkConf()
    conf.setAppName(topicName).setMaster("local[*]")

    //start spark context, which creates RDDs
    val ssc = new StreamingContext(conf, batchTimer)
    val sc = ssc.sparkContext

    //turn off extra log information
    sc.setLogLevel("OFF")


    //map the tweets
    val topicRDD= topicName.split(",").map((_,threads.toInt)).toMap

    val tweetsRDD = KafkaUtils.createStream(ssc, zookeeper,groupName, topicRDD).map(_._2)

    val tweeetsTransRDD = tweetsRDD.map(lines => lines.toLowerCase())

    //  val englishTweets = tweets.filter(_.getLang() == "en")
    tweeetsTransRDD.print()
    ssc.start()
    ssc.awaitTermination()
  }
}