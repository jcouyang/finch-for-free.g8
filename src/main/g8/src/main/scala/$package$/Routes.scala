package $package$

import cats.Monad
import io.finch._
import io.finch.syntax._
import controllers._
import syntax.ProgramOps._
import cats.~>

class Routes[M[_]: ToTwitterFuture: Monad](implicit interp: Program ~> M) {
  val getVersion = get("info") {
    ServerInfoController.get.runProgram[M]
  }

  val all = getVersion
}
