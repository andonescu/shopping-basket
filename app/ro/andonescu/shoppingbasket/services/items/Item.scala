package ro.andonescu.shoppingbasket.services.items

/**
  * Created by andonescu on 20.02.2016.
  */
case class Item[T](pagination: PaginationItem, data: Seq[T])