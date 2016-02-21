package ro.andonescu.shoppingbasket.services.items

/**
  * Created by andonescu on 21.02.2016.
  */
case class ShoppingBasketCreateProductItem(id: String)

case class ShoppingBasketCreateItem(product: ShoppingBasketCreateProductItem, capacity: Int)

case class ShoppingBasketCreate(items: Seq[ShoppingBasketCreateItem]) extends ServiceDto