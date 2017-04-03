package ahlers.michael.basic

import ahlers.michael.basic.BasicActor._
import akka.serialization.SerializerWithStringManifest
import com.google.protobuf.ByteString

import scala.collection.convert.ImplicitConversionsToJava._
import scala.collection.convert.ImplicitConversionsToScala._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object BasicProtoBufSerializer extends SerializerWithStringManifest {

  override def identifier: Int = 1001

  final val CommandManifestV1 = classOf[V1.Command].getName
  final val EventManifestV1 = classOf[V1.Event].getName
  final val StateManifestV1 = classOf[V1.State].getName

  final val CommandManifestV2 = classOf[V2.Command].getName
  final val EventManifestV2 = classOf[V2.Event].getName
  final val StateManifestV2 = classOf[V2.State].getName

  override def manifest(o: AnyRef): String =
    o match {

      /* Version 1 is deprecated; it's not longer written. */
      //case _: Command => CommandManifestV1
      //case _: Event => EventManifestV1
      //case _: State => StateManifestV1

      case _: Command => CommandManifestV2
      case _: Event => EventManifestV2
      case _: State => StateManifestV2

    }

  override def toBinary(o: AnyRef): Array[Byte] =
    o match {

      /* Version 1 is deprecated; it's not longer written. */
      //case Command(message) =>
      //  V1.Command.newBuilder
      //    .setData(message)
      //    .build
      //    .toByteArray
      //
      //case Event(message) =>
      //  V1.Event.newBuilder
      //    .setData(message)
      //    .build
      //    .toByteArray
      //
      //case State(messages) =>
      //  V1.State.newBuilder
      //    .addAllDatas(messages)
      //    .build
      //    .toByteArray

      case Command(data) =>
        V2.Command.newBuilder
          .setData(ByteString.copyFrom(data.toArray))
          .build
          .toByteArray

      case Event(data) =>
        V2.Event.newBuilder
          .setData(ByteString.copyFrom(data.toArray))
          .build
          .toByteArray

      case State(datas) =>
        V2.State.newBuilder
          .addAllDatas(datas map { data => ByteString.copyFrom(data.toArray) })
          .build
          .toByteArray

    }


  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef =
    manifest match {

      case CommandManifestV1 =>
        val command = V1.Command.parseFrom(bytes)
        Command(command.getData.getBytes)

      case EventManifestV1 =>
        val event = V1.Event.parseFrom(bytes)
        Event(event.getData.getBytes)

      case StateManifestV1 =>
        val state = V1.State.parseFrom(bytes)
        State(state.getDatasList.toList map {
          _.getBytes.toSeq
        })

      case CommandManifestV2 =>
        val command = V2.Command.parseFrom(bytes)
        Command(command.getData.toByteArray.toSeq)

      case EventManifestV2 =>
        val event = V2.Event.parseFrom(bytes)
        Event(event.getData.toByteArray.toSeq)

      case StateManifestV2 =>
        val state = V2.State.parseFrom(bytes)
        State(state.getDatasList.toList map {
          _.toByteArray.toSeq
        })

      case _ =>
        throw new IllegalArgumentException(s"""Don't know how to consume serialization with manifest "$manifest".""")

    }

}
