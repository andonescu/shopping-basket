package ro.andonescu.shoppingbasket.services.items

import java.util.UUID

import org.joda.time.DateTime

import ro.andonescu.shoppingbasket.dao.entities.Product

/**
  * Created by andonescu on 21.02.2016.
  */
case class ShoppingBasketCreateProductItem(id: String)

case class ShoppingBasketCreateItem(product: ShoppingBasketCreateProductItem, capacity: Int)

case class ShoppingBasketCreate(items: Seq[ShoppingBasketCreateItem]) extends ServiceDto

case class ShoppingBasketView(id: String) extends ServiceDto

case class ShoppingBasketItemView(basketId: String, itemId: String) extends ServiceDto

case class ShoppingBasketItemDelete(basketId: String, itemId: String) extends ServiceDto

case class ShoppingBasketCreateItemSingle(basketId: String, item: ShoppingBasketCreateItem) extends ServiceDto

// ShoppingBasket items

case class ShoppingBasketItem(id: String, product: ShoppingBasketCreateProductItem, addedDt: DateTime, capacity: Int)

object ShoppingBasketItem {
  def apply(id: UUID, basketItem: ShoppingBasketCreateItem): ShoppingBasketItem =
    ShoppingBasketItem(id.toString, basketItem.product, DateTime.now(), basketItem.capacity)
}

case class ShoppingBasket(id: String, items: Seq[ShoppingBasketItem])


//  dtos used for display


case class ProductDisplay(id: String, name: String, description: String, price: BigDecimal, currency: String)

object ProductDisplay {

  def apply(product: Product): ProductDisplay =
    ProductDisplay(product.id, product.name, product.description, product.price, product.currency)
}

case class ShoppingBasketItemDisplay(id: String, product: ProductDisplay,  addedDt: DateTime, capacity: Int)


case class ShoppingBasketDisplay(id: String, items: Seq[ShoppingBasketItemDisplay])