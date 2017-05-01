import com.typesafe.sbt.SbtAspectj.{ Aspectj, aspectjSettings, compiledClasses }
import com.typesafe.sbt.SbtAspectj.AspectjKeys.{ inputs, weave }

name := "SoftwareDevelopment"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka"     %% "akka-actor"   % "2.4.17",
  "com.xebialabs.restito" % "restito"       % "0.9.0",
  "com.typesafe.akka"     %% "akka-http"    % "10.0.4",
  "org.scalatest"         %% "scalatest"    % "3.0.1" % "test",
  "org.aspectj"           % "aspectjweaver" % "1.8.9",
  "org.aspectj"           % "aspectjrt"     % "1.8.9"
)

scalacOptions ++= Seq("-deprecation", "-feature", "-Xexperimental", "-language:postfixOps")
aspectjSettings


inputs in Aspectj <+= compiledClasses

// use the results of aspectj weaving
products in Compile <<= products in Aspectj
products in Runtime <<= products in Compile

