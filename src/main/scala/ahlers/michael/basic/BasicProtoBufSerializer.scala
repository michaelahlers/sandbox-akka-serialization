package ahlers.michael.basic

import ahlers.michael.basic.BasicActor._
import akka.serialization.SerializerWithStringManifest

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

  override def manifest(o: AnyRef): String =
    o match {
      case _: Command => CommandManifestV1
      case _: Event => EventManifestV1
      case _: State => StateManifestV1
    }

  override def toBinary(o: AnyRef): Array[Byte] =
    o match {

      case Command(message) =>
        V1.Command.newBuilder
          .setData(message)
          .build
          .toByteArray

      case Event(message) =>
        V1.Event.newBuilder
          .setData(message)
          .build
          .toByteArray

      case State(messages) =>
        V1.State.newBuilder
          .addAllDatas(messages)
          .build
          .toByteArray

    }

  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef =
    manifest match {

      case CommandManifestV1 =>
        val command = V1.Command.parseFrom(bytes)
        Command(command.getData)

      case EventManifestV1 =>
        val event = V1.Event.parseFrom(bytes)
        Event(event.getData)

      case StateManifestV1 =>
        val state = V1.State.parseFrom(bytes)
        State(state.getDatasList.toList)

    }

}
