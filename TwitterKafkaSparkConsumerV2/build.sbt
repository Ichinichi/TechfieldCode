name := "TwitterKafkaSparkConsumerV2"

version := "0.1"

scalaVersion := "2.11.8"

/************************************************
  * Twitter Dependencies
  ************************************************/

libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "3.0.3"


/************************************************
  * Kafka Dependencies ver 2.0
  ************************************************/

lazy val kakfaVersion = "2.1.0"

libraryDependencies += "org.apache.kafka" %% "kafka" % kakfaVersion
libraryDependencies += "org.apache.kafka" % "kafka-clients" % kakfaVersion


/************************************************
  * Spark Dependencies ver 2.0.0
  ************************************************/

lazy val sparkVersion = "2.0.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-streaming" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion

/************************************************
  * Other Dependencies
  ************************************************/

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.7"

resolvers += "hortonworks" at "http://repo.hortonworks.com/content/groups/public/"
libraryDependencies += "com.hortonworks" % "shc-core" % "1.1.1-2.1-s_2.11"

lazy val hbaseVersion = "1.1.2"

libraryDependencies += "org.apache.hbase" % "hbase-client" % hbaseVersion
libraryDependencies += "org.apache.hbase" % "hbase" % hbaseVersion pomOnly()
libraryDependencies += "org.apache.hbase" % "hbase-common" % hbaseVersion
libraryDependencies += "org.apache.hbase" % "hbase-server" % hbaseVersion
libraryDependencies += "org.apache.hbase" % "hbase-hadoop-compat" % hbaseVersion