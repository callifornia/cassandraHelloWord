name := "cassandraHelloWord"
version := "0.1"
scalaVersion := "2.12.4"

libraryDependencies ++= Seq (
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.3.0"
)