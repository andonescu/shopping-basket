package ro.andonescu.shoppingbasket.controllers

import com.google.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import ro.andonescu.shoppingbasket.controllers.mappers.{ProductViewMapper, ProductCollectionMappers}
import ro.andonescu.shoppingbasket.services.ProductService
import ro.andonescu.shoppingbasket.controllers.views.formatters._

import scala.concurrent.ExecutionContext

/**
  * Created by andonescu on 20.02.2016.
  */

class ProductController @Inject()(val productService: ProductService, val messagesApi: MessagesApi)
                                 (implicit ec: ExecutionContext) extends Controller with I18nSupport {

  def products(page: Int, pageSize: Int, available: Option[Boolean] = None) = Action.async { implicit request =>
    productService.products(page, pageSize, available).map { data =>
      import ProductCollectionViewFormatter._
      import CollectionViewFormatter._

      Ok(Json.toJson(new ProductCollectionMappers(request).toView(data)))
    }
  }

  def product(id: String) = Action.async { implicit request =>
    productService.product(id).map { productOpt =>
      import ProductCollectionViewFormatter._

      productOpt.fold(NotFound("")){
        product => Ok(Json.toJson(new ProductViewMapper(request).toView(product)))
      }
    }
  }
}
