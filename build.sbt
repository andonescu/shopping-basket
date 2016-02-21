name := """shopping cart"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.mockito"                 %  "mockito-all"                % "1.9.5"   % Test,
  "com.typesafe.play"           %% "play-specs2"                % "2.4.6"   % Test,
  "com.typesafe.play"           %% "play-test"                  % "2.4.6"   % Test,
  specs2 % Test
)     

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := false