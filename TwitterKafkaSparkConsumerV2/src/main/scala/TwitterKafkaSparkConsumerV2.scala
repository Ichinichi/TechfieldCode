import org.apache.spark.sql._
import org.apache.spark.sql.types._
//import org.apache.spark.sql.functions._
import org.apache.spark.streaming._
import org.apache.spark._
import org.apache.spark.streaming.kafka010._
//import org.apache.spark.sql.kafka010.KafkaSourceProvider
import org.apache.spark.sql.execution.datasources.hbase.{HBaseTableCatalog}
//import org.apache.kafka.clients.consumer.ConsumerConfig
//import org.apache.kafka.common.serialization.StringDeserializer




object TwitterKafkaSparkConsumerV2 {

  def main(args: Array[String]): Unit = {

    //setup the parameters for this consumer's connection to kafka(only used for DirectStream)
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "sandbox.hortonworks.com:6667",
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "group.id" -> "twocarTweetsGroup"
      //   "auto.offset.reset" -> "latest",
      //   "enable.auto.commit" -> (false :java.lang.Boolean)
    )

    //Sets the topic that this consumer will be pulling from
    val topics = Array("twocar")
    val topic = "twocar"

    //setup spark streaming, spark context, and spark SQL Context
    val conf = new SparkConf().setMaster("local[*]").setAppName(topic)
    val ssc = new StreamingContext(conf, Seconds(1))
    val sc = ssc.sparkContext
    // NOTE: of all this setup, only and only spark session is needed for structured streaming
    //val spark = SparkSession.builder.master("local").appName("sparkkafkatweets").getOrCreate()
    sc.setLogLevel("OFF")
  //  val config = new HBaseConfiguration()
   // val hbaseContext = new HBaseContext(sc, config)


    // create a schema to be applied to the JSON RDDs
    val streamSchema = new StructType(Array(
      StructField("user", StructType(List(
        StructField("created_at", types.StringType,true),
        StructField("screen_name",types.StringType,true),
        StructField("followers_count",types.LongType,true),
        StructField("friends_count",types.LongType,true),
        StructField("location",types.StringType,true)
      )),true),
      StructField("text",types.StringType,true)
    ))

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

    // section that was made to get in with Dstream
    //creates an RDDs out of the stream
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )

    //This makes refined RDDs  with value column, where the tweet information is located
    val streamRDD = stream.map(lines => lines.value)

    var timer = 0
    streamRDD.foreachRDD( RDD => {
      if (timer>400) {
        ssc.stop()
        System.exit(0)
      }
      timer+=1

        val spark = SparkSession.builder.master("local[*]").appName("sparkkafkatweets").getOrCreate()
        val streamDF = spark.read.schema(streamSchema3).json(RDD)
        //this output section is only for JSON
        val streamDF2 = streamDF.select("user.created_at",
          "text",
          "user.screen_name",
          "user.followers_count",
          "user.friends_count",
          "user.location")
        // streamDF.show()

        //this catalog is for HBase output(inside or outside foreach loop?)
        def catalog =
          s"""{
             |"table":{"namespace":"default", "name":"TwitterHBaseTable"},
             |"rowkey":"screen_name",
             |"columns":{
             |"screen_name":{"cf":"rowkey", "col":"screen_name", "type":"string"},
             |"created_at":{"cf":"Tweet", "col":"created_at", "type":"string"},
             |"text":{"cf":"Tweet", "col":"text", "type":"string"},
             |"followers_count":{"cf":"User", "col":"followers_count", "type":"string"},
             |"friends_count":{"cf":"User", "col":"friends_count", "type":"string"},
             |"location":{"cf":"User", "col":"location", "type":"string"}
             |}
             |}""".stripMargin


        //need to prun the RDD
        val stringedStreamDF = streamDF2.withColumn("followers_count", streamDF2.col("followers_count").cast(StringType)).drop("followers_count")
          .withColumn("friends_count", streamDF2.col("friends_count").cast(StringType)).drop("friends_count")
        val cleanStreamDF = stringedStreamDF.na.fill(" ")
        cleanStreamDF.show()


        System.out.println("right before trying to save the Hbase")
        //This should create an HBase table/catalog based on the schema
        val catalogDF = cleanStreamDF.write.options(
          Map(HBaseTableCatalog.tableCatalog -> catalog, HBaseTableCatalog.newTable -> "5"))
          .format("org.apache.spark.sql.execution.datasources.hbase")
          .save()

        System.out.println("right before trying to show the Hbase")
        //trying to show the HBase catalog in the console
        /* def withCatalog(cat: String): DataFrame = {
        spark
          .read
          .options(Map(HBaseTableCatalog.tableCatalog->cat))
          .format("org.apache.spark.sql.execution.datasources.hbase")
          .load()
      }
      val streamDFFromCatalog = withCatalog(catalog)

      streamDFFromCatalog.show()*/

        System.out.println("right before trying to SAVE the Hbase")



    })

    //This is waiting for a separate part of Spark context to properly go
    ssc.start()
    ssc.awaitTermination()
    System.out.println("code reached end")
    //end of section trying to get it working with RDDs first

    /*//This is streaming directly into DFs
     val streamDF = spark
       .readStream
       .format("kafka")
       .option("kafka.bootstrap.servers", "localhost:9092, localhost:9093")
       .option("subscribe", topic)
       .load()

     val streamProperDF = streamDF.select(from_json(col("value").cast("string"), streamSchema2).alias("tweet"))

     val streamnextDF = streamProperDF.select($"tweet.user.created_at",
       $"tweet.text",
       $"tweet.user.screen_name",
       $"tweet.user.followers_count",
       $"tweet.user.friends_count",
       $"tweet.user.location")

     /*streamnextDF
       .writeStream
       .format("console")
       .start()
       .awaitTermination()*/

     streamnextDF
       .writeStream
       .format("json")
       .option("checkpointLocation","/home/ichinichi/Documents/SparkCheckpoint")
       .option("path", "/home/ichinichi/Documents/sparkjson")
       .start()
       .awaitTermination()
     //This is end of the section  streaming directly into DFs*/


  }

}

