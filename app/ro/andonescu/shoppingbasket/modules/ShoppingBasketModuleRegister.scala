package ro.andonescu.shoppingbasket.modules

import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

import ro.andonescu.shoppingbasket.services.akka.ShoppingBasketActor

/**
  * Created by andonescu on 21.02.2016.
  */
class ShoppingBasketModuleRegister extends AbstractModule with AkkaGuiceSupport {
  def configure = {
    bindActor[ShoppingBasketActor](ShoppingBasketModuleRegister.ShoppingBasketRegisterName)
  }
}

object ShoppingBasketModuleRegister {
  val ShoppingBasketRegisterName = "shopping-basket-actor"
}