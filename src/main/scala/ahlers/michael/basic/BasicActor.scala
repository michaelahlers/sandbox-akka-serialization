package ahlers.michael.basic

import java.util.UUID
import java.util.UUID.randomUUID

import ahlers.michael.basic.BasicActor._
import akka.actor.Props
import akka.event.Logging
import akka.persistence._

object BasicActor {

  sealed trait Message

  case object Fetch extends Message

  case class Command(data: Seq[Byte]) extends Message

  case class Event(data: Seq[Byte]) extends Message

  case class State(datas: List[Seq[Byte]] = Nil) {
    def updated(event: Event): State = copy(datas :+ event.data)
  }

  def props(id: UUID = randomUUID): Props = Props(new BasicActor(id))

}

class BasicActor(id: UUID) extends PersistentActor {

  val log = Logging(context.system, this)

  override def persistenceId = s"example-$id"

  var state = State()

  val receiveRecover: Receive = {

    case event: Event => state =
      state.updated(event)
      Thread.sleep(100)

    case SnapshotOffer(_, snapshot: State) =>
      state = snapshot

    case message =>
      log.warning("""Recovery got unhandled message: {}.""", message)

  }

  val receiveCommand: Receive = {

    case Command(data) =>
      persist(Event(data)) { event =>
        state = state.updated(event)
      }

    case Fetch => sender ! state

  }

}