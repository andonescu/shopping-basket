package ro.andonescu.shoppingbasket.services.items

/**
  * Created by andonescu on 21.02.2016.
  */
case class ServiceError(field: String, message: String) extends ServiceDto

case class ServiceErrors(list: Seq[ServiceError])

