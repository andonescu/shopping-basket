package ro.andonescu.shoppingbasket.controllers.mappers.services

import ro.andonescu.shoppingbasket.controllers.forms.PostBasketItemForm
import ro.andonescu.shoppingbasket.controllers.mappers.ServiceMappers
import ro.andonescu.shoppingbasket.services.items.{ShoppingBasketCreateItem, ShoppingBasketCreateItemSingle, ShoppingBasketCreateProductItem}

/**
  * Created by andonescu on 21.02.2016.
  */
class ShoppingBasketCreateItemSingleMapper(basketId: String) extends ServiceMappers[PostBasketItemForm, ShoppingBasketCreateItemSingle] {
  override def toServiceObj(obj: PostBasketItemForm): ShoppingBasketCreateItemSingle =
    ShoppingBasketCreateItemSingle(basketId, ShoppingBasketCreateItem(ShoppingBasketCreateProductItem(obj.product.id), obj.capacity))
}
