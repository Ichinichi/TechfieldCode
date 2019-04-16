import org.apache.spark.SparkConf
import org.apache.spark.sql.{SparkSession, types}
import org.apache.spark.sql.types.{ArrayType, DoubleType, LongType, StringType, StructField, StructType}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object DarkSkyConsumer {

  def main(args: Array[String]): Unit = {

    //setup the parameters for this consumer's connection to kafka(only used for DirectStream)
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092, localhost:9093",
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "group.id" -> "weatherGroup"
    )

    //Set the topic that this consumer will be pulling from
    val topic = "weather"
    val topics = Array("weather")

    //setup spark streaming, spark context, and spark SQL Context
    val conf = new SparkConf().setMaster("local[*]").setAppName(topic)
    val ssc = new StreamingContext(conf, Seconds(1))
    val sc = ssc.sparkContext
    val spark = SparkSession.builder.master("local[*]").appName("weatherfeed").getOrCreate()
    sc.setLogLevel("OFF")

    // create a schema to be applied essentially already clean RDD
    val streamSchema = new StructType(Array(
      StructField("summary",StringType),
      StructField("icon",StringType),
      StructField("data",ArrayType(
        StructType(List(
          StructField("time", LongType),
          StructField("temperature", DoubleType)
        ))
      ))
    ))

    // section that was made to get in with Dstream
    //creates an RDDs out of the stream
    val stream = KafkaUtils.createDirectStream(ssc,LocationStrategies.PreferConsistent,ConsumerStrategies.Subscribe[String, String](topics, kafkaParams))

    val streamRDD = stream.map(lines => ( lines.value))

    streamRDD.print(10)

    streamRDD.foreachRDD{ RDD =>

      val streamDF = spark.read.schema(streamSchema).json(RDD)

      //this output section is only for JSON
      val streamDF2 = streamDF.select("summary","icon","data.time","data.temperature")

      streamDF2.show()
    }

    ssc.start()
    ssc.awaitTerminationOrTimeout(40000)
    System.out.println("code reached end")
    ssc.stop(true)
  }

}
