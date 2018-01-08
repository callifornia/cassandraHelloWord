package util

import com.datastax.driver.core.{Row, Session}
import scala.collection.JavaConverters._

object ImplicitExtensions {
  type ColumnType = String
  type ColumnName = String
  type ColumnValue = Any

  implicit class SessionExtension(session: Session) {

    def initCassandra = {
      session.createKeyspaces.createColumnFamily.insertDataIntoTable
    }

    def createKeyspaces = {
      session.execute("drop keyspace r1;")
      session.execute(
        "create keyspace if not exists r1 with replication = {'class':'SimpleStrategy','replication_factor': 1};")
      session
    }

    def createColumnFamily = {
      session.execute("USE r1;")
      session.execute("drop table if exists t2;")
      session.execute(""" create table if not exists t2(
          |   id int,
          |   age int,
          |   name text,
          |   lastName text,
          |   PRIMARY KEY(id, age, name))
          |   WITH CLUSTERING ORDER BY(age ASC);
        """.stripMargin)
      session
    }

    def insertDataIntoTable = {
      for (i <- 0 to 5) {
        session.execute(
          s"INSERT INTO t2(id, age, name, lastName) VALUES($i, $i, 'name_$i', 'lastName_$i')")
      }
      for (i <- 0 to 5) {
        session.execute(
          s"INSERT INTO t2(id, age, name, lastName) VALUES($i, ${i + 1}, 'name_${i + 1}', 'lastName_${i + 1}')")
      }
      for (i <- 0 to 5) {
        session.execute(
          s"INSERT INTO t2(id, age, name, lastName) VALUES($i, ${i + 2}, 'name_${i + 2}', 'lastName_${i + 2}')")
      }
      session
    }

    def executeAs(query: String): List[Row] = {
      session.execute(query).all().asScala.toList
    }
  }
}
