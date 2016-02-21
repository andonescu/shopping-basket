package ro.andonescu.shoppingbasket.services.akka

import akka.actor.Actor
import com.google.inject.Inject

import ro.andonescu.shoppingbasket.dao.ProductRepository
import ro.andonescu.shoppingbasket.services.items.{ServiceErrors, ShoppingBasketCreate}

import scala.concurrent.ExecutionContext


class ShoppingBasketActor @Inject()(productRepo: ProductRepository) (implicit ec: ExecutionContext)  extends Actor {


  def receive = {
    case ShoppingBasketCreate(items) =>

      val itemsIdsSeq = items.map(_.product.id)


      val response = productRepo.collection.map{
        products =>

          val retrievedProducts = products.filter( p => itemsIdsSeq.contains(p.id))

          if (retrievedProducts.size != items.size) {
            // we have an issue; a few items are not in database
            // abort the request
            Left(ServiceErrors("/items", "elements not discovered in dababase"))
          } else {
            // continue to


            Right("adadas")
          }

      }




      sender() ! response
  }
}