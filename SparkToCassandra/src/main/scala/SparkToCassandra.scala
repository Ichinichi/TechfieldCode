import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.hadoop.conf.Configuration

object SparkToCassandra {

  val schema = StructType(List(
    StructField("user_id",IntegerType,false),
    StructField("age",IntegerType,true),
    StructField("gender",StringType,true),
    StructField("occupation",StringType,true),
    StructField("zip",StringType,true)
  ))

  //create a spark session
  val spark = SparkSession.builder.appName("CassandraIntegration")
    .config("spark.cassandra.connection.host", "localhost")
    .config("spark.cassandra.connection.port","9042")
    .master("local[*]")
    .getOrCreate()


  def main (args : Array[String]) : Unit = {

    //get the raw data
   val lines = spark.sparkContext.textFile("/home/ichinichi/Downloads/u.user")

  // convert to an RDD of Rows with stuff

    val users = lines.map(_.split("\\|")).map(R => Row(R(0).toInt,R(1).toInt,R(2),R(3),R(4)))

    //convert to dataframe

    val usersDataset = spark.createDataFrame(users, schema)

    //write to cass
    usersDataset.write
    .format("org.apache.spark.sql.cassandra")
    .mode("append")
      .options(Map("table" -> "users", "keyspace" -> "movielens"))
    .save()

    // read back from cass

    val readUsers = spark.read
    .format("org.apache.spark.sql.cassandra")
    .options(Map("table" -> "users", "keyspace" -> "movielens"))
    .load()

    readUsers.createOrReplaceTempView("users")

    val sqlDF = spark.sql("SELECT * FROM users WHERE age < 20")
    sqlDF.show()

//    Stop the session
    spark.stop()
  }

}
