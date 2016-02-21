package ro.andonescu.shoppingbasket.controllers.views

import org.joda.time.DateTime

/**
  * Created by andonescu on 21.02.2016.
  */


case class ProductDisplayView(id: String, name: String, description: String, price: BigDecimal, currency: String, link: Link)

case class ShoppingBasketItemDisplayView(id: String, product: ProductDisplayView, addedDt: DateTime, capacity: Int, link: Link) extends View

case class ShoppingBasketDisplayView(id: String, items: Seq[ShoppingBasketItemDisplayView], link: Link) extends View
