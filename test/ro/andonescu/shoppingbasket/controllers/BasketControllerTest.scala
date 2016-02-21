package ro.andonescu.shoppingbasket.controllers

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.api.libs.json.Json
import play.api.test.{FakeHeaders, FakeRequest, PlaySpecification, WithApplication}

/**
  * Created by andonescu on 21.02.2016.
  */
@RunWith(classOf[JUnitRunner])
class BasketControllerTest extends PlaySpecification {


  "BasketController#post" should {

    "return not create a shopping basket for no json" in new WithApplication {
      val response = route(FakeRequest(POST, "/shoppingbaskets"))


      response must beSome.which(status(_) == BAD_REQUEST)
    }



    "return create a shopping basket request with a single available item" in new WithApplication {

      //TODO: override the id from the json
      val jsonString =
        """ {
          | "items": [
          |        {
          |            "product": {
          |                "id": "334s"
          |            },
          |            "capacity": 2
          |        }
          |    ]
          | }
        """.stripMargin


      val response = route(
        FakeRequest(
          POST,
          "/shoppingbaskets",
          FakeHeaders(Seq(("Content-Type", "application/json"))),
          Json.parse(jsonString))
      )


      response must beSome.which(status(_) == CREATED)
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


      val response = route(
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


      val response = route(
        FakeRequest(
          POST,
          "/shoppingbaskets",
          FakeHeaders(Seq(("Content-Type", "application/json"))),
          Json.parse(jsonString))
      )


      response must beSome.which(status(_) == BAD_REQUEST)
    }

    "return return an error if the product is not available" in new WithApplication {

      //TODO: override the id from the json
      val jsonString =
        """ {
          | "items": [
          |        {
          |            "product": {
          |                "id": "334s"
          |            },
          |            "capacity": 2
          |        }
          |    ]
          | }
        """.stripMargin


      val response = route(
        FakeRequest(
          POST,
          "/shoppingbaskets",
          FakeHeaders(Seq(("Content-Type", "application/json"))),
          Json.parse(jsonString))
      )


      response must beSome.which(status(_) == BAD_REQUEST)
    }
  }

}
