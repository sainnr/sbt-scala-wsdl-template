name := """sbt-scala-wsdl-template"""
organization := "github.sainnr"
scalaVersion := "2.12.8"
version := "1.0-SNAPSHOT"

lazy val publishSettings = Seq(
  // fill in with registry of your choice
  credentials += Credentials(Path.userHome / ".sbt" / "nexus_creds"),
  publishTo := {
    val nexus = "https://nexus.localhost/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "repository/maven-snapshots")
    else
      Some("releases" at nexus + "repository/maven-releases")
  }
)

lazy val wsdl = (project in file("wsdl"))
  .settings (
    publishSettings,
    sources in (Compile, doc) := Seq.empty
  )

lazy val root = (project in file("."))
  .aggregate(wsdl)
  .dependsOn(wsdl)
  .settings (
    publishSettings,
    inThisBuild(Seq(
      trackInternalDependencies := TrackLevel.TrackIfMissing,
      exportJars := true
    )),
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    ),
    // we rely on JAXB but run without a container,
    // so we need to provide a native Java package from rt.jar
    unmanagedJars in Test += Attributed.blank(
      file(System.getenv("JAVA_HOME") + "/jre/lib")
    ),
    // very subjective, feel free to adjust to your needs
    scalacOptions := Seq(
      "-deprecation",
      "-feature",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-Ybackend-parallelism", "8"
    )
)
