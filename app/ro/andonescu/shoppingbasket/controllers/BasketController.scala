package ro.andonescu.shoppingbasket.controllers

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.Inject
import com.google.inject.name.Named
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, Controller, Request}
import ro.andonescu.shoppingbasket.controllers.forms.PostBasketForms
import ro.andonescu.shoppingbasket.controllers.forms.formatters.BasketFormsFormatters._
import ro.andonescu.shoppingbasket.controllers.mappers.services.BasketCreationMapper
import ro.andonescu.shoppingbasket.controllers.mappers.views.ShoppingBasketDisplayViewMapper
import ro.andonescu.shoppingbasket.services.items._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by andonescu on 21.02.2016.
  */
class BasketController @Inject()(val messagesApi: MessagesApi,
                                 @Named("shopping-basket-actor") shoppingBasketActor: ActorRef)
                                (implicit ec: ExecutionContext) extends Controller with I18nSupport {

  import scala.concurrent.duration._

  implicit val timeout: Timeout = 5.seconds

  /**
    * Handles the creation of a new shopping basket
    */
  def post() = Action.async { implicit request =>
    request.body.asJson.map {
      json =>
        json.validate[PostBasketForms].map {
          form =>

            // we can have an intermediary validator

            (shoppingBasketActor ? BasketCreationMapper.toServiceObj(form)).mapTo[Either[ServiceErrors, String]].map {
              case Right(id) => Created.withHeaders((LOCATION, basketLocationURl(id)))
              case Left(errors) => BadRequest(errors.toString)
            }

        }.recoverTotal(e => Future.successful(BadRequest("Detected error: " + JsError.toJson(e))))
    }.getOrElse {
      Future.successful(BadRequest("Expecting Json Data"))
    }
  }

  /**
    * Retrieve a basket by id
    */
  def get(id: String) = Action.async { implicit request =>

    (shoppingBasketActor ? ShoppingBasketDelete(id)).mapTo[Option[ShoppingBasketDisplay]].map {
      case Some(display) =>
        import ro.andonescu.shoppingbasket.controllers.views.formatters.ShoppingBasketDisplayFormatter._
        Ok(Json.toJson(new ShoppingBasketDisplayViewMapper(request).toView(display)))
      case None => NotFound
    }
  }

  /**
    * Delete an entire shopping list
    */
  def delete(id: String) = Action.async(implicit request =>
    (shoppingBasketActor ? ShoppingBasketDelete(id)).mapTo[Option[ShoppingBasketDeleted]].map {
      case Some(_) =>
        Ok
      case None =>
        NotFound
    }
  )

  private def basketLocationURl(id: String)(implicit req: Request[_]) =
    ro.andonescu.shoppingbasket.controllers.routes.BasketController.get(id).absoluteURL()(req).stripSuffix("/").trim
}
