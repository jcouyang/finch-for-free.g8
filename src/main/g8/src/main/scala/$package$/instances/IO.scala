package $package$.instances

import com.twitter.util.{Promise, Future}
import io.finch.syntax.ToTwitterFuture
import cats.effect.{IO => CatsIO}

object IO {
  implicit val ioToTTF: ToTwitterFuture[CatsIO] = new ToTwitterFuture[CatsIO] {
    def apply[A](io: CatsIO[A]): Future[A] = {
      val promise = new Promise[A]()
      io unsafeRunAsync (_.fold(promise.setException, promise.setValue))
      promise
    }
  }
}
