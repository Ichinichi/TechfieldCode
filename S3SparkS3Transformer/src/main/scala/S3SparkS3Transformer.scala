import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import org.apache.spark.sql.SparkSession
import jp.co.bizreach.s3scala.S3
import awscala.s3._
import awscala.{Region, s3}

object S3SparkS3Transformer {

  def main(args: Array[String]): Unit = {

    val credentials = new DefaultAWSCredentialsProviderChain().getCredentials()
    require(credentials != null,
      "No AWS credentials found. See http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/credentials.html")


    //from bizreach-setting the region
    implicit val region = Region.US_WEST_2

    //from bizreach, setting the credentials to be able to access the buckets
    implicit val s3 = S3(accessKeyId = "", secretAccessKey = "")

    //from me, setting spark to read from the buckets
    val spark = SparkSession.builder.appName("S3SparkS3")
      .master("local[*]")
      .getOrCreate()

    //should theoretically not work since s3 buckests save objects, not files. need to change
    //bucket path into actual string json first
  //  val rawBucketjson = spark.read.textFile("s3a://twitterkinesisbucket6969/2019/04/11/16/TwitterKinesisFirehose-1-2019-04-11-16-24-04-41f2b6d3-2cf1-4f28-a556-eb40bd253882")

    //from bizreach
    val bucket: Bucket = s3.createBucket("s3sparks3outputbucket")
    bucket.put("sample.txt", new java.io.File("sample.txt"))

 //   rawBucketjson.writeStream.format("/home/ichinichi/Documents/S3SparkBucketData/output")
  //    .start()
 //     .awaitTermination()

  }
}
