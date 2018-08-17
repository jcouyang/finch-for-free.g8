package $package$
package interpreters
import com.twitter.logging.Logger
import models._
import effects._
import cats.effect.IO
import cats.{~>}

object ReaderWriterInterp {
  val logger = Logger.get(APP_NAME_SPACE)
  def runReader(env: Config) = new (ReaderWriter ~> IO) {
    def apply[A](r: ReaderWriter[A]) = r match {
      case Ask() =>
        IO(env)
      case Info(log) =>
        IO(logger.info(log))
      case Error(log) =>
        IO(logger.error(log))
    }
  }
}
