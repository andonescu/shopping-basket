package ro.andonescu.shoppingbasket.services.akka

import akka.actor.Actor
import com.google.inject.Inject
import play.api.Configuration

import ro.andonescu.shoppingbasket.services.items.ShoppingBasketCreate


class ShoppingBasketActor @Inject()(configuration: Configuration) extends Actor {


  def receive = {
    case ShoppingBasketCreate(items) =>
      sender() ! Right("sakdakjdajdaskj")
  }
}