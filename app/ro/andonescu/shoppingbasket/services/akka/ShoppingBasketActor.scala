package ro.andonescu.shoppingbasket.services.akka

import java.util.UUID

import akka.actor.Actor
import com.google.inject.Inject

import ro.andonescu.shoppingbasket.dao.ProductRepository
import ro.andonescu.shoppingbasket.dao.entities.Product
import ro.andonescu.shoppingbasket.dao.entities.gen.ProductGen
import ro.andonescu.shoppingbasket.services.items._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class ShoppingBasketActor @Inject()(productRepo: ProductRepository)(implicit ec: ExecutionContext) extends Actor {

  def receive = {
    case ShoppingBasketCreate(items) =>
      // POST /shoppingbaskets
      sender() ! handleShoppingBasketCreateRequest(items)
    case ShoppingBasketView(id) =>
      // GET /shoppingbaskets/:id
      sender() ! handleBasketView(id)
    case ShoppingBasketItemView(basketId, itemId) =>
      // GET /shoppingbaskets/:id/items
      sender() ! handleBasketItems(basketId, itemId)
    case ShoppingBasketItemDelete(basketId, itemId) =>
      sender() ! handleBasketItemDelete(basketId, itemId)
    case createItemInfo: ShoppingBasketCreateItemSingle =>
      sender() ! handleShoppingBasketCreateItemSimple(createItemInfo)
    case _ => ???
  }

  def handleShoppingBasketCreateItemSimple(createItemInfo: ShoppingBasketCreateItemSingle): Future[Option[Either[ServiceErrors, String]]] =
    Future.successful(shoppingBasketSeq.find(b => b.id == createItemInfo.basketId).map {
      basket =>
        updateItems(Seq(createItemInfo.item)) match {
          case Success(_) =>
            // we can store the new list
            val shoppingListWithTheCurrentBasket = shoppingBasketSeq diff (Seq(basket))

            val newItem = ShoppingBasketItem(UUID.randomUUID(), createItemInfo.item)

            shoppingBasketSeq = shoppingListWithTheCurrentBasket :+ basket.copy(items = basket.items :+ newItem)

            Right(newItem.id)
          case Failure(errors) =>
            Left(ServiceErrors("/items", errors.toString()))
        }
    })


  def handleBasketItemDelete(basketId: String, itemId: String) = {
    Future.successful(shoppingBasketSeq.find(b => b.id == basketId).map {
      basket =>

        val elementToBeRemoved = basket.items.find(_.id == itemId)

        elementToBeRemoved.map { elem =>
          val shoppingListWithTheCurrentBasket = shoppingBasketSeq diff (Seq(basket))
          val itemsWithoutTheRemovedItem = basket.items.filterNot(_.id == itemId)

          shoppingBasketSeq = shoppingListWithTheCurrentBasket :+ basket.copy(items = itemsWithoutTheRemovedItem)
          updateItems(elem.product.id, elem.capacity, ShoppingCartOperations.add)
        }
      //TODO: put back what has been removed
    }.flatten)
  }


  /**
    * Retrieve a single basket element
    */
  def handleBasketItems(basketId: String, itemId: String): Future[Option[ShoppingBasketItemDisplay]] =
    handleBasketView(basketId).map { basketOpt =>
      basketOpt.map { basket =>
        basket.items.find(_.id == itemId)
      }.flatten
      // we need to flatten so that we can transform from Option[Option to just a single Option
    }

  private def handleBasketView(id: String): Future[Option[ShoppingBasketDisplay]] = {
    val shoppingBasket = shoppingBasketSeq.find(_.id == id).map {
      basket =>
        // get all the items from the basket (unique items)
        val basketItems = basket.items.map(_.product.id).toSet

        // get all these items from storage
        val retrievedProducts = productRepo.collection.map(_.filter(p => basketItems.contains(p.id)))



        retrievedProducts.map {
          products =>

            // from the original basket list we will assemble the display list
            val items = basket.items.map {
              item =>

                // we should not have problems here ; products.find(p => p.id == item.product.id).get should exist all the time
                // we can add a Try[] to prevent this issue
                ShoppingBasketItemDisplay(
                  item.id,
                  ProductDisplay(products.find(p => p.id == item.product.id).get),
                  item.addedDt,
                  item.capacity
                )

            }

            ShoppingBasketDisplay(basket.id, items)

        }
    }

    shoppingBasket match {
      case None => Future.successful(None)
      case Some(basket) => basket.map(Some(_))
    }

  }

  /**
    * Handle a [[ro.andonescu.shoppingbasket.services.items.ShoppingBasketCreate]] request
    */
  private[akka] def handleShoppingBasketCreateRequest(items: Seq[ShoppingBasketCreateItem]): Future[Either[ServiceErrors, String]] = {
    val response = productRepo.collection.map {
      products =>

        val itemsIdsSeq = items.map(_.product.id)

        val retrievedProducts = products.filter(p => itemsIdsSeq.contains(p.id))

        if (retrievedProducts.size != items.size) {
          // we have an issue; a few items are not in database
          // abort the request
          Left(ServiceErrors("/items", "elements not discovered in dababase"))
        } else {
          // continue to second type of validation //availability of the item


          val unavailableErrorProducts = retrievedProducts
            .filterNot(p => checkProductAvailability(p, requestedProductById(p.id, items)))
            .map(p => ServiceErrors("/items", s"there are not sufficient ${p.name}").errors)
            .flatten

          if (!unavailableErrorProducts.isEmpty) {
            // it looks like we have a few products which are not available
            Left(ServiceErrors(unavailableErrorProducts))
          } else {
            // this thing usually should be in a transaction

            updateItems(items) match {
              case Success(_) =>
                // we can store the new list
                val newShoppingBasket = ShoppingBasket(
                  UUID.randomUUID().toString,
                  items.map(i => ShoppingBasketItem(UUID.randomUUID(), i))
                )

                shoppingBasketSeq = shoppingBasketSeq :+ newShoppingBasket

                Right(newShoppingBasket.id)
              case Failure(errors) =>
                Left(ServiceErrors("/items", errors.toString()))
            }
          }
        }
    }
    response
  }

  //TODO: this should be moved from here into a singleton
  private [akka]  var shoppingBasketSeq: scala.collection.mutable.Seq[ShoppingBasket] = scala.collection.mutable.Seq.empty[ShoppingBasket]

  private def requestedProductById(id: String, itemsFromBasket: Seq[ShoppingBasketCreateItem]) =
    itemsFromBasket.find(_.product.id == id)

  /**
    * check if the given product is available / or it is ok to block n elements from it
    */
  private def checkProductAvailability(p: Product, itemFromBasket: Option[ShoppingBasketCreateItem]) =
    p.isAvailable && itemFromBasket.isDefined && p.stock - itemFromBasket.get.capacity >= 0


  /**
    * TODO: this needs some refactor;
    */
  private[akka] def updateItems(id: String, numberToBlock: Long, op: (Long, Long) => Long = ShoppingCartOperations.decrease) =
    Try(synchronized {
      import scala.util.control.Breaks._

      breakable {
        for (i <- 0 until ProductGen.getProducts.size) {
          val product = ProductGen.getProducts(i)

          if (product.id == id)
            if (product.stock > numberToBlock) {
              ProductGen.getProducts(i) = product.copy(stock = op(product.stock, numberToBlock))
              break
            }
        }
      }
    })

  object ShoppingCartOperations {
    def add(x: Long, y: Long) = x + y

    def decrease(x: Long, y: Long) = x - y
  }


  /**
    * TODO: this should be handled in a different way
    */
  private[akka] def updateItems(items: Seq[ShoppingBasketCreateItem]): Try[Unit] =
    Try(items.map(f => updateItems(f.product.id, f.capacity)))
}