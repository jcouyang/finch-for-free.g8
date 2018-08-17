package $organization$

import cats.data.EitherK
import cats.free.Free
import $package$.effects._
import hammock.HttpF
import hammock.marshalling.MarshallF

package object $name;format="camel"$ {
  // \$COVERAGE-OFF\$
  val APP_NAME_SPACE = "$package$.$name;format="normalize"$"
  // \$COVERAGE-ON\$
  type Hammock[A] = EitherK[HttpF, MarshallF, A]
  type Program[A] = EitherK[ReaderWriter, Hammock, A]
  type ProgramF[A] = Free[Program, A]
}
