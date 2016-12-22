package ro.andonescu.shoppingbasket.controllers

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.api.libs.json.Json
import play.api.test.{FakeHeaders, FakeRequest, PlaySpecification, WithApplication}
import ro.andonescu.shoppingbasket.dao.entities.gen.ProductGen

/**
  * Created by andonescu on 21.02.2016.
  */
@RunWith(classOf[JUnitRunner])
class BasketControllerTest extends PlaySpecification {


  "BasketController#post" should {

    "return not create a shopping basket for no json" in new WithApplication {
      val response = route(app, FakeRequest(POST, "/shoppingbaskets"))


      response must beSome.which(status(_) == BAD_REQUEST)
    }


    "return create the shopping basket request with a single available item" in new WithApplication {

      //TODO: improve this test; create a mock with the data; or inject a different Repo into the akka

      val firstProduct = ProductGen.getProducts(0)
      // make sure that this product is still available
      ProductGen.getProducts(0) = firstProduct.copy(stock = 10)

      val jsonString =
        s""" {
           | "items": [
           |        {
           |            "product": {
           |                "id": "${firstProduct.id}"
           |            },
           |            "capacity": 2
           |        }
           |    ]
           | }
        """.stripMargin


      val response = route(app,
        FakeRequest(
          POST,
          "/shoppingbaskets",
          FakeHeaders(Seq(("Content-Type", "application/json"))),
          Json.parse(jsonString))
      )


      response must beSome.which(status(_) == CREATED)
    }

    "return an error if the product is not available" in new WithApplication {

      //TODO: improve this test; create a mock with the data; or inject a different Repo into the akka

      val firstProduct = ProductGen.getProducts(0)
      // make sure that this product is still available
      ProductGen.getProducts(0) = firstProduct.copy(stock = 0)

      val jsonString =
        s""" {
           | "items": [
           |        {
           |            "product": {
           |                "id": "${firstProduct.id}"
           |            },
           |            "capacity": 2
           |        }
           |    ]
           | }
        """.stripMargin


      val response = route(app,
        FakeRequest(
          POST,
          "/shoppingbaskets",
          FakeHeaders(Seq(("Content-Type", "application/json"))),
          Json.parse(jsonString))
      )


      response must beSome.which(status(_) == BAD_REQUEST)
    }


    "return an error if no product is available in the request" in new WithApplication {

      val jsonString =
        """ {
          | "items": [
          |        {
          |            "capacity": 2
          |        }
          |    ]
          | }
        """.stripMargin


      val response = route(app,
        FakeRequest(
          POST,
          "/shoppingbaskets",
          FakeHeaders(Seq(("Content-Type", "application/json"))),
          Json.parse(jsonString))
      )


      response must beSome.which(status(_) == BAD_REQUEST)
    }

    "return an error if the product does not exists" in new WithApplication {

      val jsonString =
        """ {
          | "items": [
          |        {
          |            "product": {
          |                "id": "334s-ando-product-ha"
          |            },
          |            "capacity": 2
          |        }
          |    ]
          | }
        """.stripMargin


      val response = route(app,
        FakeRequest(
          POST,
          "/shoppingbaskets",
          FakeHeaders(Seq(("Content-Type", "application/json"))),
          Json.parse(jsonString))
      )


      response must beSome.which(status(_) == BAD_REQUEST)
    }
  }

  "BasketController#delete" should {
    "delete an entire basket with product number updated" in new WithApplication {
      val basketId = "1111-EXISTS"

      val response = route(app, FakeRequest(DELETE, s"/shoppingbaskets/$basketId"))

      response must beSome.which(status(_) == OK)
    }

    "response with `NOT_FOUND` when delete is performed on a basket which doesn't exists" in new WithApplication {

      val basketId = "1111-NOT_EXISTS"

      val response = route(app, FakeRequest(DELETE, s"/shoppingbaskets/$basketId"))

      response must beSome.which(status(_) == NOT_FOUND)
    }

    "delete an empty basket" in new WithApplication {
      val basketId = "1111-EMPTY"

      val response = route(app, FakeRequest(DELETE, s"/shoppingbaskets/$basketId"))

      response must beSome.which(status(_) == OK)
    }
  }
}
