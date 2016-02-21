package ro.andonescu.shoppingbasket.util

import ro.andonescu.shoppingbasket.dao.entities.Product
import ro.andonescu.shoppingbasket.dao.entities.gen.ProductGen

/**
  * Created by andonescu on 21.02.2016.
  */
object ProductGenerators {
  def generateProducts(numOfProducts: Int = 5): scala.collection.mutable.Seq[Product] =
    (1 to numOfProducts).map(i => ProductGen.getNewProduct)(collection.breakOut)
}
