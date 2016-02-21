package ro.andonescu.shoppingbasket.controllers.views.formatters

import play.api.libs.json.{Json, Writes}
import ro.andonescu.shoppingbasket.controllers.views.Link

/**
  * Created by andonescu on 20.02.2016.
  */
object LinkFormatter {

  implicit def jsonLinkWrites: Writes[Link] =
    new Writes[Link] {
      def writes(c: Link) = Json.obj(
        "rel" -> c.rel,
        "href" -> c.href
      )
    }
}
