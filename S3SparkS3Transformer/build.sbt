name := "S3SparkS3Transformer"

version := "0.1"

scalaVersion := "2.11.8"

lazy val sparkVersion = "2.3.0"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion

libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion

libraryDependencies += "com.amazonaws" % "amazon-kinesis-client" % "1.10.0"

libraryDependencies += "jp.co.bizreach" %% "aws-s3-scala" % "0.0.14"







