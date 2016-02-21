package ro.andonescu.shoppingbasket.services.items

/**
  * Created by andonescu on 21.02.2016.
  */
case class ServiceError(field: String, message: String) extends ServiceDto

case class ServiceErrors(errors: Seq[ServiceError])

object ServiceErrors {

  def apply(field: String, message: String): ServiceErrors =
    ServiceErrors(Seq(ServiceError(field, message)))
}