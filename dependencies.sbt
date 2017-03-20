resolvers += Resolver.jcenterRepo

libraryDependencies ++=
  "com.github.dnvriend" %% "akka-persistence-inmemory" % "1.3.16" ::
    "com.typesafe.akka" %% "akka-persistence" % Versions.Akka ::
    "com.typesafe.akka" %% "akka-testkit" % Versions.Akka ::
    "org.scalactic" %% "scalactic" % Versions.ScalaTest ::
    Nil

libraryDependencies ++=
  "org.scalatest" %% "scalatest" % Versions.ScalaTest % Test ::
    Nil