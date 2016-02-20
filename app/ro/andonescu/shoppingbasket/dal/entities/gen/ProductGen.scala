package ro.andonescu.shoppingbasket.dal.entities.gen

import java.util.UUID

import ro.andonescu.shoppingbasket.dal.entities.Product

import scala.collection.mutable
import scala.util.Random

/**
  * Utility class to create starting data
  *
  * Created by andonescu on 20.02.2016.
  */
object ProductGen {

  val intRandomNumber = new Random(2)

  def getNewProduct: Product = Product(
    id = UUID.randomUUID().toString,
    name = Random.alphanumeric.take(10).mkString(""),
    description = Random.alphanumeric.take(50).mkString(""),
    price = BigDecimal(getRandom(2, 80)),
    currency = "EUR",
    stock = getRandom(1, 10)
  )

  var getProducts: scala.collection.mutable.Seq[Product]= {
    //TODO : get a random number of products
    (1 to getRandom(10, 50)).map(i => getNewProduct)(collection.breakOut)
  }


  private def getRandom(from: Int, to: Int): Int = {
    if (from < to)
      from + new Random().nextInt(Math.abs(to - from))
    else from - new Random().nextInt(Math.abs(to - from))
  }
}
