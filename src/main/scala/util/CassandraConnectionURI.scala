package util

import java.net.URI

import com.datastax.driver.core.{
  Cluster,
  ConsistencyLevel,
  QueryOptions,
  Session
}

case class CassandraConnectionURI(connectionString: String) {
  private val uri = new URI(connectionString)
  val host = uri.getHost
  val hosts = Seq(uri.getHost)
  val port = uri.getPort
  val keyspace = uri.getPath.substring(1)
}

object Helper {

  import util.ImplicitExtensions.SessionExtension

  def createSessionAndInitKeyspace(
      uri: CassandraConnectionURI,
      defaultConsistencyLevel: ConsistencyLevel =
        QueryOptions.DEFAULT_CONSISTENCY_LEVEL): Session = {

    val session = createSession(uri, defaultConsistencyLevel)
    session.initCassandra
  }

  def createSession(uri: CassandraConnectionURI,
                    defaultConsistencyLevel: ConsistencyLevel =
                      QueryOptions.DEFAULT_CONSISTENCY_LEVEL) = {

    buildCluster(uri, defaultConsistencyLevel).connect()
  }

  def buildCluster(uri: CassandraConnectionURI,
                   defaultConsistencyLevel: ConsistencyLevel =
                     QueryOptions.DEFAULT_CONSISTENCY_LEVEL) = {

    new Cluster.Builder()
      .addContactPoints(uri.hosts: _*)
      .withPort(uri.port)
      .withQueryOptions(
        new QueryOptions().setConsistencyLevel(defaultConsistencyLevel))
      .build
  }
}
