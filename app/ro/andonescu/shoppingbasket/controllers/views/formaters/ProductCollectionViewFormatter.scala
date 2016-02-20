package ro.andonescu.shoppingbasket.controllers.views.formaters

import play.api.libs.json.Json
import ro.andonescu.shoppingbasket.controllers.views.{ProductCollectionView, Link}

/**
  * Created by ionut on 20.02.2016.
  */
object ProductCollectionViewFormatter {
  import ro.andonescu.shoppingbasket.controllers.views.formaters.LinkFormatter._

  implicit val jsonProductCollectionView = Json.writes[ProductCollectionView]
}
