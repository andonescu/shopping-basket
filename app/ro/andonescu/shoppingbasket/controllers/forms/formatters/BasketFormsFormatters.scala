package ro.andonescu.shoppingbasket.controllers.forms.formatters

import play.api.libs.json.Json
import ro.andonescu.shoppingbasket.controllers.forms.{PostBasketForms, PostBasketItemForm, PostBasketProductForm}

/**
  * Created by andonescu on 21.02.2016.
  */
object BasketFormsFormatters {
  implicit val postBasketProductFormFormat = Json.format[PostBasketProductForm]
  implicit val postBasketItemFormFormat = Json.format[PostBasketItemForm]
  implicit val postBasketFormsFormat = Json.format[PostBasketForms]
}
