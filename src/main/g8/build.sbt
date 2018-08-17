name := "$name;format="lower,hyphen"$"
version := "0.0.1-SNAPSHOT"

scalaVersion in ThisBuild  := "2.12.6"

scalacOptions in ThisBuild ++= Seq(
  "-encoding", "UTF-8",   // source files are in UTF-8
  "-deprecation",         // warn about use of deprecated APIs
  "-unchecked",           // warn about unchecked type parameters
  "-feature",             // warn about misused language features
  "-language:higherKinds",// allow higher kinded types without `import scala.language.higherKinds`
  "-Xlint",               // enable handy linter warnings
  "-Xfatal-warnings",     // turn compiler warnings into errors
  "-language:implicitConversions",
  "-Ypartial-unification" // allow the compiler to unify type constructors of different arities
)

lazy val circeVersion = "0.9.1"
lazy val finchVersion = "0.18.1"
lazy val hammockVersion = "0.8.1"
libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "1.0.0-RC",
  "org.typelevel" %% "cats-core" % "1.0.1",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "com.pepegar" %% "hammock-core" % hammockVersion,
  "com.pepegar" %% "hammock-circe" % hammockVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.github.finagle" %% "finch-core" % finchVersion,
  "com.github.finagle" %% "finch-circe" % finchVersion,
  "com.twitter" %% "twitter-server" % "18.3.0",
  "org.scalatest" %% "scalatest" % "3.0.4" % Test
)

scalafmtOnCompile in ThisBuild := true

coverageMinimum in ThisBuild := 100
coverageFailOnMinimum in ThisBuild := true
coverageExcludedPackages := "$package$.Main;$package$.interpreters.*"

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")
