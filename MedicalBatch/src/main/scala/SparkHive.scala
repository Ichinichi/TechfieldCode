import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StructType,StructField,StringType}

object sparkhive{
  def main(args: Array[String]): Unit = {
    val inputpath = "/home/Documents"
    val spark = SparkSession.builder.master("local").appName("sparkhive").enableHiveSupport().getOrCreate()

    val schema = StructType(List(
      StructField("patientid",StringType,false),
      StructField("eventid",StringType,false),
      StructField("eventdescription",StringType,true),
      StructField("timestamp",StringType,true),
      StructField("value",StringType,true)
    ))

    val df = spark.read.format("csv").schema(schema).load(inputpath)
    df.toDF().createOrReplaceTempView("maple")
    spark.sqlContext.sql("DROP TABLE IF EXISTS spark_events")
    spark.sqlContext.sql("CREATE TABLE spark_events AS SELECT * FROM maple")

  }
}
