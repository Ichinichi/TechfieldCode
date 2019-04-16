name := "TwitKafkSparkSStreamConsumEL"

version := "0.1"

scalaVersion := "2.11.8"

/************************************************
  * Spark Dependencies ver 2.2.0
  ************************************************/

lazy val sparkVersion = "2.2.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-streaming" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion


libraryDependencies += "org.elasticsearch" % "elasticsearch-hadoop" % "6.4.1"

