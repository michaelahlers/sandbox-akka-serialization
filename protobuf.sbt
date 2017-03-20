import sbtprotobuf.ProtobufPlugin

ProtobufPlugin.protobufSettings

ProtobufPlugin.runProtoc in ProtobufPlugin.protobufConfig := { as => com.github.os72.protocjar.Protoc.runProtoc("-v300" +: as.toArray) }

val versions = new {
  val ProtobufJava = "3.0.2"
}

/* Used only for Google's primitive wrappers. */
libraryDependencies ++=
  "com.google.protobuf" % "protobuf-java" % versions.ProtobufJava ::
    "com.google.protobuf" % "protobuf-java" % versions.ProtobufJava % ProtobufPlugin.protobufConfig.name ::
    Nil
