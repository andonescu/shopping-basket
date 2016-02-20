package ro.andonescu.shoppingbasket.controllers.views

import play.api.libs.json.Json

/**
  * Created by andonescu on 20.02.2016.
  */
case class ProductCollectionView(id: String, name: String, description: String, price: BigDecimal, currency: String, available: Boolean, link: Link) extends View

object ProductViews {
  import Link.jsonLinkWrites

  implicit val jsonProductCollectionView = Json.writes[ProductCollectionView]
}