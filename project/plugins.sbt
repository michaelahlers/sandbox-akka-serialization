logLevel := Level.Warn

addSbtPlugin("com.github.gseitz" % "sbt-protobuf" % "0.5.5")

libraryDependencies ++=
  "com.github.os72" % "protoc-jar" % "3.0.0" ::
    Nil