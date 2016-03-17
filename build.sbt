name := """shopping cart"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.mockito"                 %  "mockito-all"                % "1.10.19"   % Test,
  "com.typesafe.play"           %% "play-specs2"                % "2.5.0"   % Test,
  "com.typesafe.play"           %% "play-test"                  % "2.5.0"   % Test,
  "com.typesafe.akka"           %% "akka-testkit"               % "2.4.2"  % Test,
  specs2 % Test
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := false