package $package$

import com.twitter.finagle.Http
import com.twitter.finagle.Http
import com.twitter.logging.{Level, Logger, LoggerFactory}
import com.twitter.util.{Await}
import com.twitter.server.TwitterServer
import io.circe.generic.auto._
import com.twitter.app.Flag
import io.finch.Application
import io.finch.circe.encodeCirce
import interpreters.ProgramInterpreter._
import cats.effect.IO
import instances.IO._
import instances.Encoder._

object Main extends TwitterServer {
  val port: Flag[Int] = flag("port", 8081, "TCP port for HTTP server")

  val logLevel = flag("log.level", "INFO", "Log Level")

  def main(): Unit = {
    LoggerFactory(
      node = APP_NAME_SPACE,
      level = Level.parse(logLevel())
    ).apply()

    Logger
      .get(APP_NAME_SPACE)
      .info(s"Server started at http://localhost:\${port()}")

    val api = new Routes[IO].all
    val server = Http.server
      .serve(s":\${port()}", api.toServiceAs[Application.Json])

    onExit { server.close() }

    Await.ready(adminHttpServer)
  }
}
