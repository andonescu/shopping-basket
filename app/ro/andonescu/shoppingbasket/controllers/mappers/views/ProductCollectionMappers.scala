package ro.andonescu.shoppingbasket.controllers.mappers.views

import play.api.mvc.Request

import ro.andonescu.shoppingbasket.controllers.mappers.ViewMappers
import ro.andonescu.shoppingbasket.controllers.views.{CollectionView, Link, ProductCollectionView}
import ro.andonescu.shoppingbasket.dao.entities.Product
import ro.andonescu.shoppingbasket.services.items.Item

/**
  * Created by andonescu on 20.02.2016.
  */
final class ProductViewMapper(req: Request[_]) extends ViewMappers[Product, ProductCollectionView] {

  override def toView(obj: Product): ProductCollectionView =
    ProductCollectionView(
      obj.id,
      obj.name,
      obj.description,
      obj.price,
      obj.currency,
      obj.isAvailable,
      Link.self(ro.andonescu.shoppingbasket.controllers.routes.ProductController.product(obj.id), req)
    )

}

final class ProductCollectionMappers(req: Request[_]) extends ViewMappers[Item[Product], CollectionView[ProductCollectionView]] {

  override def toView(obj: Item[Product]): CollectionView[ProductCollectionView] =
    CollectionView[ProductCollectionView](
      pageInfo = PageInfoMapper.toView(obj.pagination),
      data = obj.data.map(new ProductViewMapper(req).toView)
    )
}
