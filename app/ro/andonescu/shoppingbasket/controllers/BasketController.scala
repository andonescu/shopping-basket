package ro.andonescu.shoppingbasket.controllers

import com.google.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.JsError
import play.api.mvc.{Action, Controller}
import ro.andonescu.shoppingbasket.controllers.forms.PostBasketForms
import ro.andonescu.shoppingbasket.controllers.forms.formatters.BasketFormsFormatters._

import scala.concurrent.{Future, ExecutionContext}

/**
  * Created by andonescu on 21.02.2016.
  */
class BasketController @Inject()(val messagesApi: MessagesApi)
                                (implicit ec: ExecutionContext) extends Controller with I18nSupport {


  /**
    * Handles the creation of a new shopping basket
    */
  def post() = Action.async { implicit request =>
    request.body.asJson.map {
      json =>
        json.validate[PostBasketForms].map {
          form =>


            ???
        }.recoverTotal(e => Future.successful(BadRequest("Detected error: " + JsError.toJson(e))))
    }.getOrElse {
      Future.successful(BadRequest("Expecting Json Data"))
    }
  }
}
