package $package$
package services
import hammock.marshalling._
import models._
import effects._
import hammock._
import hammock.circe.implicits._
import io.circe.generic.auto._

object ServerInfoService {
  def info(implicit RW: ReaderWriterOps[Program],
           Http: HttpRequestC[Program],
           Marshall: MarshallC[Program]): ProgramF[Either[String, ServerInfo]] =
    for {
      config <- RW.ask
      _ <- RW.info("fetching server info from admin endpoint")
      resp <- Http.get(config.adminUri / "admin/server_info", Map())
      bodyOrError <- if (resp.status == Status.OK)
        Marshall.unmarshall[ServerInfo](resp.entity).map(Right(_))
      else
        RW.error("fetch error")
          .map(_ => Left("Server Info Service Error:" + resp.status.description))
    } yield bodyOrError
}
