package main

import util.{CassandraConnectionURI, Helper}
import util.ImplicitExtensions.SessionExtension


object CassandraMain {

  def main(args: Array[String]): Unit = {

    val connectionURI = CassandraConnectionURI("cassandra://localhost:9042/r1")
    val session = Helper.createSessionAndInitKeyspace(connectionURI)

    session.executeAs("select * from t2").foreach(println)
    println("Finished")

  }
}
