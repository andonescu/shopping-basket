package ro.andonescu.shoppingbasket.dal.entities

/**
  * Created by andonescu on 20.02.2016.
  */
/**
  * Product class
  *
  * For i18n support - name / description must represent some only some keys and their value should be extracted from an internationalized file
  *
  * @param id Id of the product from the system (is string so that we can use any type/ structure of ids we want
  * @param name the name of product
  * @param description description of the product
  * @param price how much this product cost at this moment
  * @param currency
  * @param stock
  */
case class Product(id: String, name: String, description: String, price: BigDecimal, currency: String, stock: Long) {

  def isAvailable = stock > 0
}