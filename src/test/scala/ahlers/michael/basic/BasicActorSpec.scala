package ahlers.michael.basic

import java.util.UUID.randomUUID

import ahlers.michael.basic.BasicActor._
import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class BasicActorSpec extends TestKit(ActorSystem(classOf[BasicActorSpec].getSimpleName)) with ImplicitSender
  with FlatSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    super.afterAll
    TestKit.shutdownActorSystem(system)
  }

  val id = randomUUID

  val datas = "foo" :: "bear" :: Nil

  it must "accept commands" in {
    val actor = system.actorOf(BasicActor.props(id))
    datas map Command foreach {
      actor ! _
    }

    actor ! Fetch
    expectMsg(State(datas))
  }

  it must "restore state" in {
    val actor = system.actorOf(BasicActor.props(id))

    actor ! Fetch
    expectMsg(State(datas))
  }


}
