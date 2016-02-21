package ro.andonescu.shoppingbasket.controllers

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.Inject
import com.google.inject.name.Named
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import ro.andonescu.shoppingbasket.controllers.mappers.views.ShoppingBasketItemDisplayViewMapper
import ro.andonescu.shoppingbasket.services.items.{ShoppingBasketItemDisplay, ShoppingBasketItemView}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by andonescu on 21.02.2016.
  */
class BasketItemController @Inject()(val messagesApi: MessagesApi,
                                     @Named("shopping-basket-actor") shoppingBasketActor: ActorRef)
                                    (implicit ec: ExecutionContext) extends Controller with I18nSupport {

  import scala.concurrent.duration._

  implicit val timeout: Timeout = 5.seconds

  /**
    * Handler for GET /shoppingbaskets/:id/items/:id
    */
  def itemByBasket(basketId: String, itemId: String) = Action.async { implicit request =>
    //TODO: refactor these long lines
    (shoppingBasketActor ? ShoppingBasketItemView(basketId, itemId)).mapTo[Future[Option[ShoppingBasketItemDisplay]]].flatMap(identity).map {
      case Some(display) =>
        import ro.andonescu.shoppingbasket.controllers.views.formatters.ShoppingBasketDisplayFormatter._
        Ok(Json.toJson(new ShoppingBasketItemDisplayViewMapper(basketId)(request).toView(display)))
      case None => NotFound
    }
  }

}
