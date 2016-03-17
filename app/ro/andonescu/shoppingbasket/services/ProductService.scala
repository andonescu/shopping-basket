package ro.andonescu.shoppingbasket.services

import com.google.inject.{Inject, Singleton}
import play.api.Logger

import ro.andonescu.shoppingbasket.dao.ProductRepository
import ro.andonescu.shoppingbasket.dao.entities.Product
import ro.andonescu.shoppingbasket.services.items.{Item, PaginationItem}

import scala.concurrent.{ExecutionContext, Future}

/**
  * This service has capabilities to access [[ro.andonescu.shoppingbasket.dao.ProductRepository]] but only in read mode.
  *
  * Any data modification is done by the [[ro.andonescu.shoppingbasket.services.akka.ShoppingBasketActor]]
  *
  * Created by andonescu on 20.02.2016.
  */
@Singleton
class ProductService @Inject()(productRepo: ProductRepository) {


  /**
    *
    * Get all products from the system
    *
    * @param page      who needs to retrieved, default 1
    * @param pageSize  dimension of the extract data, default 25
    * @param available if the requested data needs to be available
    * @return a [[Item]] of [[ro.andonescu.shoppingbasket.dao.entities.Product]] as [[scala.concurrent.Future]]
    *         with the extract data
    */
  def products(
      page: Int = 1,
      pageSize: Int = 50,
      available: Option[Boolean] = None)(implicit ec: ExecutionContext): Future[Item[Product]] = {

    Logger.debug(s"entry data: $page - $pageSize - $available")

    // get all data based on the given parameter `available`
    val productsFuture = productRepo.collection.map {
      _.filter(product => available.fold(true)(_ == product.isAvailable))
    }

    productsFuture.map {
      products =>
        Logger.debug(s"retrieved data - $products")
        val sliceStartPosition = (page - 1) * pageSize
        // get just a slice of those elements
        val returnedElements = products.slice(sliceStartPosition, sliceStartPosition + pageSize)


        Item[Product](PaginationItem(products.size, page, returnedElements.size), returnedElements)
    }
  }

  /**
    * Get a product from the system
    */
  def product(id: String)(implicit ec: ExecutionContext): Future[Option[Product]] =
    productRepo.collection.map(_.find(_.id == id))

}