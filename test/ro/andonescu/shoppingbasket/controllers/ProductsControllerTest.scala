package ro.andonescu.shoppingbasket.controllers

import org.specs2.control.Ok
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
  * Created by andonescu on 20.02.2016.
  */
@RunWith(classOf[JUnitRunner])
class ProductsControllerTest extends Specification {


  "ProductsController#products" should {

    "return the products page" in new WithApplication {
      val response = route(FakeRequest(GET, "/products"))

      response must beSome.which(status(_) == Ok)
    }
  }
}
