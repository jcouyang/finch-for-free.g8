package $package$

import cats._
import cats.effect.IO
import $package$.models._
import hammock._
import hammock.marshalling._
import $package$.interpreters.ReaderWriterInterp._
import io.finch.Input
import org.scalatest.{FlatSpec, Matchers}
import instances.IO._

class RoutesSpec extends FlatSpec with Matchers {
  behavior of "info"
  lazy val serverInfo = """
{
  "build_branch_name" : "unknown",
  "name" : "util-core",
  "start_time" : "Thu May 17 00:07:18 UTC 2018",
  "build_revision" : "5f5dee4791a41fd814f6c6a2f574a6fb77e811d0",
  "build" : "20180306-103656",
  "version" : "18.3.0",
  "build_last_few_commits" : [
    "unknown"
  ],
  "scm_repository" : "unknown",
  "merge_base" : "unknown",
  "uptime" : 2043254,
  "merge_base_commit_date" : "unknown"
}
"""
  def interpOf(runHttp: (HttpF ~> IO)): Program ~> IO =
    runReader(Config(uri"http://admin.uri", "8000")) or (runHttp or marshallNT[IO])

  it should "return server information" in {
    implicit val interp = interpOf(new (HttpF ~> IO) {
      def apply[A](a: HttpF[A]): IO[A] = a match {
        case Get(HttpRequest(url, _, _)) if url == uri"http://admin.uri/admin/server_info" =>
          IO(HttpResponse(Status.Statuses(200), Map(), Entity.StringEntity(serverInfo)))
        case r => fail("unexpected request:" + r)
      }
    })
    new Routes[IO].all(Input.get("/info")).awaitValueUnsafe() shouldBe a[Some[_]]
  }

  it should "return 500" in {
    implicit val interp = interpOf(new (HttpF ~> IO) {
      def apply[A](a: HttpF[A]): IO[A] = a match {
        case Get(HttpRequest(url, _, _)) if url == uri"http://admin.uri/admin/server_info" =>
          IO(HttpResponse(Status.Statuses(500), Map(), Entity.StringEntity("")))
        case r => fail("unexpected request:" + r)
      }
    })
    the[Exception] thrownBy {
      new Routes[IO].all(Input.get("/info")).awaitValueUnsafe() shouldBe a[Some[_]]
    } should have message "Server Info Service Error:There was an internal server error."
  }
}
