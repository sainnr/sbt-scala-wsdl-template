name := """sbt-scala-wsdl-template-wsdlgen"""
organization := "github.sainnr"
scalaVersion := "2.12.8"
version := "1.0-SNAPSHOT"

lazy val wsdlImport = TaskKey[Unit]("wsdlImport", "Generates Java classes from WSDL")

wsdlImport := {
  val wsdlSources = "./wsdl/src/main/java"
  val d = file(wsdlSources)
  if (d.isDirectory) {
    // don't forget to rename to your fav one in line with WSDL generating sh
    val gen = file(s"$wsdlSources/github/sainnr/wsdl")
    if (!gen.exists() || gen.listFiles().isEmpty) {
      import sys.process._

      println("[wsdl_import] Importing Java beans from WSDL...")
      "./wsdl/bin/wsdl_import.sh" !
    } else
      println("[wsdl_import] Looks like WSDL is already imported, skipping.")
  } else
    println(s"[wsdl_import] Make sure the directory ${d.absolutePath} exists.")
}

(compile in Compile) := ((compile in Compile) dependsOn wsdlImport).value
