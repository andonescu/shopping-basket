package ro.andonescu.shoppingbasket.controllers.mappers

import ro.andonescu.shoppingbasket.services.items.ServiceDto

/**
  * Created by andonescu on 21.02.2016.
  */
trait ServiceMappers[T, V <: ServiceDto] {
  def toServiceObj(obj: T): V
}
