package ro.andonescu.shoppingbasket.dal

import com.google.inject.Singleton
import ro.andonescu.shoppingbasket.dal.entities.Product
import ro.andonescu.shoppingbasket.dal.entities.gen.ProductGen

import scala.concurrent.Future

/**
  * Created by andonescu on 20.02.2016.
  */
@Singleton
class ProductRepository {

  /**
    * Default data; is var because it receives updates
    *
    * If a [[scala.concurrent.Future]] like in a live scenario
    */
  def collection: Future[Seq[Product]] = Future.successful(ProductGen.getProducts)

  /**
    * Retrieve a product from the list
    *
    */
  def item(id: String): Future[Option[Product]] = Future.successful(ProductGen.getProducts.find(_.id == id))
}
