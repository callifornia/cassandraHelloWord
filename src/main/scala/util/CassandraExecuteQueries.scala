package util

import com.datastax.driver.core.Session

object CassandraExecuteQueries {

  lazy val session = {
    val connectionURI = CassandraConnectionURI("cassandra://localhost:9042/r1")
    Helper.createSessionAndInitKeyspace(connectionURI)
  }

  def executeQuery(handle: Session => Unit): Unit = {
    try {
      handle(session)
    } finally {
      session.close()
    }
  }

  def executeQuery2(session: Session)(handle: Session => Unit): Unit = {
    try {
      handle(session)
    } finally {
      session.close()
    }
  }
}
