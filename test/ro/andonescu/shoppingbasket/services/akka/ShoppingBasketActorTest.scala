package ro.andonescu.shoppingbasket.services.akka

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.testkit.TestActorRef
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import org.junit.runner.RunWith
import org.specs2.concurrent.ExecutionEnv
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

import ro.andonescu.shoppingbasket.dao.ProductRepository
import ro.andonescu.shoppingbasket.services.items.{ServiceErrors, ShoppingBasketCreate, ShoppingBasketCreateItem, ShoppingBasketCreateProductItem}
import ro.andonescu.shoppingbasket.util.ProductGenerators._

import scala.concurrent.{Await, Future}

/**
  * Created by andonescu on 21.02.2016.
  */
@RunWith(classOf[JUnitRunner])
class ShoppingBasketActorTest extends Specification with Mockito {

  import scala.concurrent.duration._

  implicit val timeout: Timeout = 5.seconds

  "ShoppingBasketActor#handleShoppingBasketCreateRequest" should {
    "do nothing if the given sequence is empty" in { implicit ee: ExecutionEnv =>

      val (actorRef, productMock) = serviceWithMock(ee)

      val products = generateProducts()
      (productMock.collection).returns(Future.successful(products))

      val responseAsFuture = (actorRef ? ShoppingBasketCreate(
        Seq(ShoppingBasketCreateItem(ShoppingBasketCreateProductItem(products(0).id), 2))
      )).mapTo[Future[Either[ServiceErrors, String]]].flatMap(identity)

      val response = Await.result(responseAsFuture, Duration.Inf)

      response must beLike[Either[ServiceErrors, String]] {
        case Right(id) =>
          id must not beEmpty
      }
    }

    "create a new shopping basket if a valid product is given" in { implicit ee: ExecutionEnv =>

      val (actorRef, productMock) = serviceWithMock(ee)

      val products = generateProducts()
      (productMock.collection).returns(Future.successful(products))

      val responseAsFuture = (actorRef ? ShoppingBasketCreate(
        Seq(ShoppingBasketCreateItem(ShoppingBasketCreateProductItem(products(0).id), 2))
      )).mapTo[Future[Either[ServiceErrors, String]]].flatMap(identity)

      val response = Await.result(responseAsFuture, Duration.Inf)

      response must beLike[Either[ServiceErrors, String]] {
        case Right(id) =>
          id must not beEmpty
      }
    }
  }


  implicit val system = ActorSystem("testsystem", ConfigFactory.parseString(
    """
        akka.loggers = ["akka.testkit.TestEventListener"]
    """
  ))

  def serviceWithMock(implicit ee: ExecutionEnv) = {
    val mockProductRepository = mock[ProductRepository]
    (TestActorRef(new ShoppingBasketActor(mockProductRepository)), mockProductRepository)
  }

}
