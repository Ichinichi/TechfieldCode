import java.nio.ByteBuffer
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import com.amazonaws.services.kinesis.producer.{KinesisProducer, KinesisProducerConfiguration}
import com.google.common.collect.Lists
import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.{Client, Constants}
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.auth.{Authentication, OAuth1}

object TwitterKinesisProducer {

  def main(args: Array[String]): Unit = {

    //THis sets up the a queue for tweets to be shoved into. The endpoint lists the topics we are interested in
    val queue: BlockingQueue[String] = new LinkedBlockingQueue[String](100000)
    val endpoint: StatusesFilterEndpoint = new StatusesFilterEndpoint()
    endpoint.trackTerms(Lists.newArrayList("FFXIV", "#ESports", "#CLG", "TSM", "#C9",
      "#games", "#gamer", "#FFXIV", "#LeagueOfLegends", "#Overwatch", "#DarkSouls", "#Sekiro"))

    //this is just all of my Twitter credentias
    val consumerKey = TwitterKeyHolder.CONSUMER_KEY_KEY
    val consumerSecret = TwitterKeyHolder.CONSUMER_SECRET_KEY
    val accessToken = TwitterKeyHolder.ACCESS_TOKEN_KEY
    val accessTokenSecret = TwitterKeyHolder.ACCESS_TOKEN_SECRET_KEY

    //this is the twitter client
    val auth = new OAuth1(consumerKey, consumerSecret, accessToken, accessTokenSecret)
    val client = new ClientBuilder().hosts(Constants.STREAM_HOST).endpoint(endpoint).authentication(auth).processor(new StringDelimitedProcessor(queue)).build
    client.connect()

    //starting a kinesis producer
    val regionName ="us-west-2"

   //setting the configs for the kinesis producer
    val KinProducerConfig = new KinesisProducerConfiguration()
      .setRecordMaxBufferedTime(3000)
      .setMaxConnections(1)
      .setRequestTimeout(60000)
      .setRegion(regionName)

    //instantiating the kinesis producer with the configs
    val kinesis = new KinesisProducer(KinProducerConfig)

    //putting in i number of records
    var i =0
    for (i <- 0 to 10000){

      val record = queue.take()
      //this is only prtinting to console for viewing pleasure
      println(i + ": " + record.toString)

      //the result has to be in a byte buffer form for the producer to actually accept the information
      val recordByteBuffer = ByteBuffer.wrap(record.getBytes("UTF-8"))

      //PUTTING IT IN
      kinesis.addUserRecord("TwitKinesisStreamName","1", recordByteBuffer)
    }

    client.stop()
    System.exit(0)

  }

}
