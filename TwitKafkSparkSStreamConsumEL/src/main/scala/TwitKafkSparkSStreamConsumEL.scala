import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql._
import org.elasticsearch.hadoop.cfg.ConfigurationOptions





object TwitKafkSparkSStreamConsumEL {

  def main(args: Array[String]): Unit = {

     //Sets the topic that this consumer will be pulling from
    val topics = Array("GameTweets")
    val topic = "GameTweets"

//    //setup spark streaming, spark context, and spark SQL Context
//    val conf = new SparkConf().setMaster("local[*]").setAppName(topic)
//    val ssc = new StreamingContext(conf, Seconds(1))
//    val sc = ssc.sparkContext


    // NOTE: of all this setup, only and only spark session is needed for structured streaming
//    val spark = SparkSession.builder.master("local").appName("sparkkafkatweets").getOrCreate()

    val spark = SparkSession.builder()
      .config(ConfigurationOptions.ES_NODES, "127.0.0.1")
      .config(ConfigurationOptions.ES_PORT, "9200")
      .master("local[*]")
      .appName("GameTweetsAppName")
      .getOrCreate()


//    sc.setLogLevel("OFF")


    // alternate way to create schema, used by the structured streaming(streaming directly to DF)
    val streamSchema2 = new StructType()
      .add("user",new StructType()
        .add("created_at", types.StringType)
        .add("screen_name",types.StringType)
        .add("followers_count", types.LongType)
        .add("friends_count", types.LongType)
        .add("location",types.StringType)
      )
      .add("text", types.StringType)

    val streamSchema3 = new StructType()
      .add("text", types.StringType)
      .add("user",StructType(Array(
        StructField("created_at", types.StringType),
        StructField("screen_name",types.StringType),
        StructField("followers_count",types.LongType),
        StructField("friends_count", types.LongType),
        StructField("location",types.StringType)
      )))

    //This is streaming directly into DFs
    val streamDF = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092, localhost:9093")
      .option("subscribe", topic)
      .load()

    val streamProperDF = streamDF.select(from_json(col("value").cast("string"), streamSchema2).alias("tweet"))

    val streamnextDF = streamProperDF.select("tweet.user.created_at",
      "tweet.text",
      "tweet.user.screen_name",
      "tweet.user.followers_count",
      "tweet.user.friends_count",
      "tweet.user.location")

    //writes the stream to elasticsearch. be sure to use lower case letters for the args in start()
    //https://github.com/elastic/elasticsearch-hadoop/issues/1059

    streamnextDF
      .writeStream
      .outputMode("append")
      .format("org.elasticsearch.spark.sql")
      .option("checkpointLocation","/home/ichinichi/Documents/kibbi")
      .start("gametwitter/json")
      .awaitTermination()

//    streamnextDF
//      .writeStream
//      .format("console")
//      .start()
//      .awaitTermination()


  }

}


