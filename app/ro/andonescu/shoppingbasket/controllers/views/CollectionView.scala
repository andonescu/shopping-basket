package ro.andonescu.shoppingbasket.controllers.views

import play.api.libs.json.{Writes, Json}
import play.api.mvc.{Request, Call}

/**
  * Created by andonescu on 20.02.2016.
  */


case class PageInfoView(page: Int, pageSize: Int, total: Int) extends View

object PageInfoView {
  implicit val jsonPageInfoView = Json.format[PageInfoView]
}

case class CollectionView[T](pageInfo: PageInfoView, data: Seq[T]) extends View

object CollectionView {

  import PageInfoView.jsonPageInfoView

  implicit def jsonCollectionViewWrites[T](implicit innerWrites: Writes[T]): Writes[CollectionView[T]] =
    new Writes[CollectionView[T]] {
      def writes(c: CollectionView[T]) = Json.obj(
        "pageInfo" -> c.pageInfo,
        "data" -> c.data
      )
    }
}

case class Link(rel: String, href: String)

object Link {
  implicit def jsonLinkWrites: Writes[Link] =
    new Writes[Link] {
      def writes(c: Link) = Json.obj(
        "rel" -> c.rel,
        "href" -> c.href
      )
    }


  private val selfKey = "self"
  private val lastKey = "last"
  private val previousKey = "prev"
  private val firstKey = "first"

  def self(call: Call, request: Request[_]): Link =
    apply(selfKey, call, request)

  def last(call: Call, request: Request[_]): Link =
    apply(lastKey, call, request)

  def previous(call: Call, request: Request[_]): Link =
    apply(previousKey, call, request)

  def first(call: Call, request: Request[_]): Link =
    apply(firstKey, call, request)

  def apply(rel: String, call: Call, request: Request[_]): Link =
    Link(rel, call.absoluteURL(request.secure)(request).stripSuffix("/").trim)
}