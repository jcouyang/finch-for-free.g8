package $package$
package interpreters
import scala.util.Properties._

import cats._
import cats.data.Validated._
import cats.syntax.validated._
import models._
import cats.effect.IO
import cats.syntax.apply._
import hammock.marshalling._
import hammock._
import cats.syntax.either._
import cats.instances.string._
import cats.syntax.show._
object ProgramInterpreter {
  val validatedConfig = (
    Uri
      .fromString(envOrElse("ADMIN_URI", "http://localhost:9990"))
      .toValidated,
    envOrElse("PORT", "8081").valid
  ).mapN(Config.apply)

  implicit val idInterp: Program ~> IO = {
    validatedConfig match {
      case Valid(config) =>
        ReaderWriterInterp.runReader(config) or ((hammock.jvm
          .Interpreter[IO]
          .trans) or marshallNT[IO])
      case Invalid(error) => throw new Exception(error.show)
    }
  }
}
