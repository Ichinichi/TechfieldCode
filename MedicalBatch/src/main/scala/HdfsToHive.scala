import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StructType, StructField, StringType}

object HdfsToHive {

  val mortpath = "hdfs://sandbox.hortonworks.com:8020/user/maria_dev/badhospital/mortality.csv"
  val eventpath = "hdfs://sandbox.hortonworks.com:8020/user/maria_dev/badhospital/events.csv"

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.master("local").appName("sparkToHive").enableHiveSupport().getOrCreate()

    val eventSchema = StructType(List(
          StructField("patientid",StringType,true),
          StructField("eventid",StringType,true),
          StructField("eventdescription",StringType,true),
          StructField("timestamp",StringType,true),
          StructField("value",StringType, true)
        ))

        val mortalitySchema = StructType(List(
          StructField("patientId",StringType,false),
          StructField("timestamp",StringType,false),
          StructField("label",StringType,false)
        ))

    val eventdf = spark.read
        .format("csv")
        .schema(eventSchema)
        .load(eventpath)
      /*.withColumnRenamed("_c0","patientId")
      .withColumnRenamed("_c1","eventId")
      .withColumnRenamed("_c2","eventDescription")
      .withColumnRenamed("_c3","timeStamp")
      .withColumnRenamed("_c4","value")*/


    val mortdf = spark.read
        .format("csv")
        .schema(mortalitySchema)
        .load(mortpath)
     /* .withColumnRenamed("_c0","patientId")
      .withColumnRenamed("_c1","timeStamp")
      .withColumnRenamed("_c2","label")*/


    eventdf.toDF().createOrReplaceTempView("bad_full_events")
    spark.sql("DROP TABLE IF EXISTS bad_hospital_full_table")
    spark.sql("CREATE TABLE bad_hospital_full_table AS SELECT * FROM bad_full_events")

    mortdf.toDF().createOrReplaceTempView("bad_dead_events")
    spark.sql("DROP TABLE if exists bad_hospital_dead_table")
    spark.sql("CREATE TABLE bad_hospital_dead_table AS SELECT * FROM bad_dead_events")

  }
}

