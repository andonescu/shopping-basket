package ro.andonescu.shoppingbasket.controllers.mappers

import ro.andonescu.shoppingbasket.controllers.views.View

/**
  * Created by andonescu on 20.02.2016.
  */
trait Mapper[T, V <: View] {
  def toView(obj: T): V
}
