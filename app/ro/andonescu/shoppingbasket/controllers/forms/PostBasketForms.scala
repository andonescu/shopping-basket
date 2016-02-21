package ro.andonescu.shoppingbasket.controllers.forms

/**
  * Created by andonescu on 21.02.2016.
  */
case class PostBasketProductForm(id : String)
case class PostBasketItemForm(product: PostBasketProductForm, capacity: Int)
case class PostBasketForms(items: Seq[PostBasketItemForm])