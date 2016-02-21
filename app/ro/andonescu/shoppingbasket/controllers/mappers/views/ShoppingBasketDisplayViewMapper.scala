package ro.andonescu.shoppingbasket.controllers.mappers.views

import play.api.mvc.Request

import ro.andonescu.shoppingbasket.controllers.mappers.ViewMappers
import ro.andonescu.shoppingbasket.controllers.views.{Link, ProductDisplayView, ShoppingBasketDisplayView, ShoppingBasketItemDisplayView}
import ro.andonescu.shoppingbasket.services.items.ShoppingBasketDisplay

/**
  * Created by andonescu on 21.02.2016.
  */
final class ShoppingBasketDisplayViewMapper(req: Request[_]) extends ViewMappers[ShoppingBasketDisplay, ShoppingBasketDisplayView] {
  override def toView(obj: ShoppingBasketDisplay): ShoppingBasketDisplayView =
    ShoppingBasketDisplayView(
      obj.id,
      obj.items.map {
        i => ShoppingBasketItemDisplayView(
          i.id,
          ProductDisplayView(
            i.product.id,
            i.product.name,
            i.product.description,
            i.product.price,
            i.product.currency,
            Link.self(ro.andonescu.shoppingbasket.controllers.routes.ProductController.product(i.product.id), req)
          ),
          i.addedDt,
          i.capacity,
          Link.self(ro.andonescu.shoppingbasket.controllers.routes.ProductController.product(i.product.id), req)
        )
      },
      Link.self(ro.andonescu.shoppingbasket.controllers.routes.BasketController.get(obj.id), req)
    )
}
