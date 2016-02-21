package ro.andonescu.shoppingbasket.controllers.mappers.services

import ro.andonescu.shoppingbasket.controllers.forms.PostBasketForms
import ro.andonescu.shoppingbasket.controllers.mappers.ServiceMappers
import ro.andonescu.shoppingbasket.services.items.{ShoppingBasketCreate, ShoppingBasketCreateItem, ShoppingBasketCreateProductItem}

/**
  * Created by andonescu on 21.02.2016.
  */
object BasketCreationMapper extends ServiceMappers[PostBasketForms, ShoppingBasketCreate] {
  override def toServiceObj(obj: PostBasketForms): ShoppingBasketCreate = ShoppingBasketCreate(
    items = obj.items.map{
      item =>
        ShoppingBasketCreateItem(ShoppingBasketCreateProductItem(item.product.id), item.capacity)
    }
  )
}
