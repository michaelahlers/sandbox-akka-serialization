scalaVersion := "2.12.1"

/**
 * Strict settings to avoid common bugs. Class files are limited in length to support builds on Windows.
 */
scalacOptions ++=
  "-target:jvm-1.8" ::
    "-feature" ::
    "-unchecked" ::
    "-deprecation" ::
    "-Xfatal-warnings" ::
    // "-Ywarn-dead-code" ::
    "-Ywarn-unused-import" ::
    "-Xmax-classfile-name" :: "150" ::
    Nil