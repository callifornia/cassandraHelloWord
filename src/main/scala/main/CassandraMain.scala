package main

import util.CassandraExecuteQueries._
import util.{CassandraConnectionURI, Helper}
import util.ImplicitExtensions._
object CassandraMain {

  lazy val session = {
    val connectionURI = CassandraConnectionURI("cassandra://localhost:9042/r1")
    Helper.createSessionAndInitKeyspace(connectionURI)
  }

  def main(args: Array[String]): Unit = {

    executeQuery { session =>
      session
        .executeAs("select * from t2;")
        .foreach(println)
    }

    executeQuery2(session) { session =>
      session
        .executeAs("select * from t2;")
        .foreach(println)
    }

    println("Finished")
  }
}
