package $package$
package syntax
import cats._
object ProgramOps {
  implicit class ProgramSyntax[A](program: ProgramF[A]) {
    def runProgram[B[_]: Monad](implicit interp: Program ~> B) = program.foldMap(interp)
  }
}
