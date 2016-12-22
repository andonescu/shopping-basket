package ro.andonescu.shoppingbasket.controllers.mappers.views

import play.api.mvc.Request
import ro.andonescu.shoppingbasket.controllers.mappers.ViewMappers
import ro.andonescu.shoppingbasket.controllers.routes
import ro.andonescu.shoppingbasket.controllers.views.{Link, ProductDisplayView, ShoppingBasketDisplayView, ShoppingBasketItemDisplayView}
import ro.andonescu.shoppingbasket.services.items.{ShoppingBasketDisplay, ShoppingBasketItemDisplay}

/**
  * Created by andonescu on 21.02.2016.
  */
final class ShoppingBasketDisplayViewMapper(req: Request[_]) extends ViewMappers[ShoppingBasketDisplay, ShoppingBasketDisplayView] {
  override def toView(obj: ShoppingBasketDisplay): ShoppingBasketDisplayView =
    ShoppingBasketDisplayView(
      obj.id,
      obj.items.map {
        i => new ShoppingBasketItemDisplayViewMapper(obj.id)(req).toView(i)
      },
      Link.self(routes.BasketController.get(obj.id), req)
    )
}


final class ShoppingBasketItemDisplayViewMapper(basketId: String)(req: Request[_]) extends ViewMappers[ShoppingBasketItemDisplay, ShoppingBasketItemDisplayView] {
  override def toView(obj: ShoppingBasketItemDisplay): ShoppingBasketItemDisplayView = ShoppingBasketItemDisplayView(
    obj.id,
    ProductDisplayView(
      obj.product.id,
      obj.product.name,
      obj.product.description,
      obj.product.price,
      obj.product.currency,
      Link.self(routes.ProductController.product(obj.product.id), req)
    ),
    obj.addedDt,
    obj.capacity,
    Link.self(routes.BasketItemController.itemByBasket(basketId, obj.id), req)
  )
}