package ro.andonescu.shoppingbasket.controllers.mappers

import ro.andonescu.shoppingbasket.controllers.views.PageInfoView
import ro.andonescu.shoppingbasket.services.items.PaginationItem

/**
  * Created by andonescu on 20.02.2016.
  */
object PageInfoMapper extends Mapper[PaginationItem, PageInfoView] {
  override def toView(obj: PaginationItem): PageInfoView = PageInfoView(obj.size, obj.page, obj.pageSize)
}
