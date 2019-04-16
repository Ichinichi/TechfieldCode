name := "KinesisProducerScala"

version := "0.1"

scalaVersion := "2.11.8"

// https://mvnrepository.com/artifact/com.amazonaws/amazon-kinesis-client
libraryDependencies += "com.amazonaws" % "amazon-kinesis-client" % "1.10.0"

libraryDependencies += "com.amazonaws" % "amazon-kinesis-producer" % "0.12.11"

// https://mvnrepository.com/artifact/com.twitter/hbc-core
libraryDependencies += "com.twitter" % "hbc-core" % "2.2.0"