package ro.andonescu.shoppingbasket.controllers.views.formatters

import play.api.libs.json.Json

import ro.andonescu.shoppingbasket.controllers.views.{ShoppingBasketDisplayView, ProductDisplayView, ShoppingBasketItemDisplayView}

/**
  * Created by andonescu on 21.02.2016.
  */
object ShoppingBasketDisplayFormatter {
  import ro.andonescu.shoppingbasket.controllers.views.formatters.LinkFormatter._

  implicit val jsonProductDisplayView = Json.writes[ProductDisplayView]
  implicit val jsonShoppingBasketItemDisplayView = Json.writes[ShoppingBasketItemDisplayView]
  implicit val jsonShoppingBasketDisplayView = Json.writes[ShoppingBasketDisplayView]
}
