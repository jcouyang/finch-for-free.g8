package $package$
package effects

import models._

import cats.InjectK
import cats.free.Free

sealed trait ReaderWriter[A]
case class Ask() extends ReaderWriter[Config]
case class Info(name: String) extends ReaderWriter[Unit]
case class Error(name: String) extends ReaderWriter[Unit]

class ReaderWriterOps[F[_]](implicit I: InjectK[ReaderWriter, F]) {
  def ask: Free[F, Config] = Free.inject[ReaderWriter, F](Ask())
  def info(log: String): Free[F, Unit] =
    Free.inject[ReaderWriter, F](Info(log))
  def error(log: String): Free[F, Unit] =
    Free.inject[ReaderWriter, F](Error(log))
}

object ReaderWriterOps {
  implicit def reader[F[_]](implicit I: InjectK[ReaderWriter, F]): ReaderWriterOps[F] =
    new ReaderWriterOps[F]
}
