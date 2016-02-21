# Shopping Cart

# endpoints
https://github.com/andonescu/shopping-basket/blob/master/conf/routes

```config
# Product
GET         /products                                       ro.andonescu.shoppingbasket.controllers.ProductController.products(page: Int ?= 1, pageSize: Int ?= 50, available: Option[Boolean])
GET         /products/:id                                   ro.andonescu.shoppingbasket.controllers.ProductController.product(id: String)

# Shopping Basket
POST        /shoppingbaskets                                ro.andonescu.shoppingbasket.controllers.BasketController.post()
GET         /shoppingbaskets/:id                            ro.andonescu.shoppingbasket.controllers.BasketController.get(id: String)

# Shopping Basket Item Operations
GET         /shoppingbaskets/:basketId/items/:itemId        ro.andonescu.shoppingbasket.controllers.BasketItemController.itemByBasket(basketId: String, itemId: String)
DELETE      /shoppingbaskets/:basketId/items/:itemId        ro.andonescu.shoppingbasket.controllers.BasketItemController.deleteItemFromBasket(basketId: String, itemId: String)
POST        /shoppingbaskets/:basketId/items                ro.andonescu.shoppingbasket.controllers.BasketItemController.addItemToBasket(basketId)
```


# description

* GET /products?page,pageSize,available
    * (x) paginated collection of products (x)
    * (x) query parameters `available=true` - to display only those products which are still available
    * (x) `available` can be transformed even to a more complex one, by adding a few elements about the stock ; if there is sufficient stock, or if there are only a few elements to present this information
        * `links` object has not been implemented

```javascript
{
    "pageInfo" : {
        "page": 1,
        "pageSize": 2,
        "total": 7
    },
    "links": [
        {
            "rel": "next",
            "href": "/products?page=2&pageSize=2"
        },
        {
            "rel": "last",
            "href": "/products?page=5&pageSize=3"
        }
    ],
    "data": [
        {
          "id": "ca28b673-7406-4e28-a653-23a477f1a381",
          "name": "EKABVavxG6",
          "description": "XX2jkNvrRCJrg4isHAnqtXBn86rh4XEgNSWLdnALxsE1FX3hrC",
          "price": 21,
          "currency": "EUR",
          "available": true,
          "link": {
            "rel": "self",
            "href": "/products/ca28b673-7406-4e28-a653-23a477f1a381"
          }
        },
        {
          "id": "4a0fe443-aff7-43e0-8a19-39374e9b7439",
          "name": "CiAlauOTAq",
          "description": "LHyZScAKSblB6vziDOKg2hVidmQCaDQ9aCrODge6dB6YocEc6D",
          "price": 14,
          "currency": "EUR",
          "available": true,
          "link": {
            "rel": "self",
            "href": "/products/4a0fe443-aff7-43e0-8a19-39374e9b7439"
          }
        },
        {
          "id": "6053795e-4de1-4d5e-af44-c8d1a6d5f6c9",
          "name": "jQflqxgPyQ",
          "description": "Rhp2VT09ggsJ6nhnjVR5mmBPFlai0BJv1EmcMjHSIxxvapJaPU",
          "price": 60,
          "currency": "EUR",
          "available": true,
          "link": {
            "rel": "self",
            "href": "/products/6053795e-4de1-4d5e-af44-c8d1a6d5f6c9"
          }
        }
    ]
}

```



* GET /products/:id
    * product by id
```javascript
{
  "id": "ca28b673-7406-4e28-a653-23a477f1a381",
  "name": "EKABVavxG6",
  "description": "XX2jkNvrRCJrg4isHAnqtXBn86rh4XEgNSWLdnALxsE1FX3hrC",
  "price": 21,
  "currency": "EUR",
  "available": true,
  "link": {
    "rel": "self",
    "href": "/products/ca28b673-7406-4e28-a653-23a477f1a381"
  }
}
```

* POST /shoppingbaskets
    * (x) creates a shopping basket
        * (x)  a new request to this endpoint creates a new basket
    * (x)  Returns 201 + Location Header with the location of the resource
    * (!) a cart can be in multiple statuses  <- future enhancements
        * PENDING - just created (waits for modifications)
        * DELETED - removed from the system (soon the cart will disappear)
        * FINALIZED - user finalized the cart and wants the goods added inside to be delivered
            * this step can be done only through PATCH /carts/:id with additional data about the payment & owner of the command
            
```javascript
{
    "items": [
        {
            "product": {
                "id": "f624f049-8283-42db-8b39-d45553444845"
            },
            "capacity": 1
        },
        {
            "product": {
                "id": "62fc21fc-8773-4b18-93ad-2ec4ef782703"
            },
            "capacity": 3
        }
    ]
}
```


* GET /shoppingbaskets/:id
    * shows the content of the
    
```javascript
{
  "id": "436f0c1b-5878-49a4-ac01-223de24058bd",
  "items": [
    {
      "id": "ecd2f5ca-da23-4b63-a1b4-22b23b0a115a",
      "product": {
        "id": "f624f049-8283-42db-8b39-d45553444845",
        "name": "sbKEIPOYHw",
        "description": "7Yvoa4hsngn5PtP1c25d8WZfpdH8QCKWX7SkjbIwiDZFWIUvUb",
        "price": 77,
        "currency": "EUR",
        "link": {
          "rel": "self",
          "href": "http://localhost:9000/products/f624f049-8283-42db-8b39-d45553444845"
        }
      },
      "addedDt": 1456088336892,
      "capacity": 1,
      "link": {
        "rel": "self",
        "href": "http://localhost:9000/shoppingbaskets/436f0c1b-5878-49a4-ac01-223de24058bd/items/ecd2f5ca-da23-4b63-a1b4-22b23b0a115a"
      }
    },
    {
      "id": "7ed2516a-0b1b-4088-a827-e1fb289c28c3",
      "product": {
        "id": "62fc21fc-8773-4b18-93ad-2ec4ef782703",
        "name": "BT7kXvuxvN",
        "description": "6abwqeKUclXNZ5YRAy15Zvr87diQqtU5xqxWMj9ZgNC5lsjBDk",
        "price": 27,
        "currency": "EUR",
        "link": {
          "rel": "self",
          "href": "http://localhost:9000/products/62fc21fc-8773-4b18-93ad-2ec4ef782703"
        }
      },
      "addedDt": 1456088336892,
      "capacity": 3,
      "link": {
        "rel": "self",
        "href": "http://localhost:9000/shoppingbaskets/436f0c1b-5878-49a4-ac01-223de24058bd/items/7ed2516a-0b1b-4088-a827-e1fb289c28c3"
      }
    }
  ],
  "link": {
    "rel": "self",
    "href": "http://localhost:9000/shoppingbaskets/436f0c1b-5878-49a4-ac01-223de24058bd"
  }
}
```

* `POST /shoppingbaskets/436f0c1b-5878-49a4-ac01-223de24058bd/items`
    * adds a new element to the basket
    
```javascript
{
    "product": {
        "id": "e87b5f02-1676-4b3c-9216-98f3d514c2e5"
    },
    "capacity": 1
}
```

     * After the POST, the new element will be visible in `GET /shoppingbaskets/436f0c1b-5878-49a4-ac01-223de24058bd`
     
```javascript
{
  "id": "436f0c1b-5878-49a4-ac01-223de24058bd",
  "items": [
    {
      "id": "ecd2f5ca-da23-4b63-a1b4-22b23b0a115a",
      "product": {
        "id": "f624f049-8283-42db-8b39-d45553444845",
        "name": "sbKEIPOYHw",
        "description": "7Yvoa4hsngn5PtP1c25d8WZfpdH8QCKWX7SkjbIwiDZFWIUvUb",
        "price": 77,
        "currency": "EUR",
        "link": {
          "rel": "self",
          "href": "http://localhost:9000/products/f624f049-8283-42db-8b39-d45553444845"
        }
      },
      "addedDt": 1456088336892,
      "capacity": 1,
      "link": {
        "rel": "self",
        "href": "http://localhost:9000/shoppingbaskets/436f0c1b-5878-49a4-ac01-223de24058bd/items/ecd2f5ca-da23-4b63-a1b4-22b23b0a115a"
      }
    },
    {
      "id": "7ed2516a-0b1b-4088-a827-e1fb289c28c3",
      "product": {
        "id": "62fc21fc-8773-4b18-93ad-2ec4ef782703",
        "name": "BT7kXvuxvN",
        "description": "6abwqeKUclXNZ5YRAy15Zvr87diQqtU5xqxWMj9ZgNC5lsjBDk",
        "price": 27,
        "currency": "EUR",
        "link": {
          "rel": "self",
          "href": "http://localhost:9000/products/62fc21fc-8773-4b18-93ad-2ec4ef782703"
        }
      },
      "addedDt": 1456088336892,
      "capacity": 3,
      "link": {
        "rel": "self",
        "href": "http://localhost:9000/shoppingbaskets/436f0c1b-5878-49a4-ac01-223de24058bd/items/7ed2516a-0b1b-4088-a827-e1fb289c28c3"
      }
    },
    {
      "id": "378e1414-810d-4227-a67f-5982b83a4123",
      "product": {
        "id": "e87b5f02-1676-4b3c-9216-98f3d514c2e5",
        "name": "ZXcE7FvRC9",
        "description": "QcHZCYcKCeb5lQyfkg5ob0RobtfE58vQt0sBcq4LrzmEr7PFEp",
        "price": 74,
        "currency": "EUR",
        "link": {
          "rel": "self",
          "href": "http://localhost:9000/products/e87b5f02-1676-4b3c-9216-98f3d514c2e5"
        }
      },
      "addedDt": 1456088478059,
      "capacity": 1,
      "link": {
        "rel": "self",
        "href": "http://localhost:9000/shoppingbaskets/436f0c1b-5878-49a4-ac01-223de24058bd/items/378e1414-810d-4227-a67f-5982b83a4123"
      }
    }
  ],
  "link": {
    "rel": "self",
    "href": "http://localhost:9000/shoppingbaskets/436f0c1b-5878-49a4-ac01-223de24058bd"
  }
}
```
    
* GET /shoppingbaskets/436f0c1b-5878-49a4-ac01-223de24058bd/items/ecd2f5ca-da23-4b63-a1b4-22b23b0a115a

```javascript
{
  "id": "ecd2f5ca-da23-4b63-a1b4-22b23b0a115a",
  "product": {
    "id": "f624f049-8283-42db-8b39-d45553444845",
    "name": "sbKEIPOYHw",
    "description": "7Yvoa4hsngn5PtP1c25d8WZfpdH8QCKWX7SkjbIwiDZFWIUvUb",
    "price": 77,
    "currency": "EUR",
    "link": {
      "rel": "self",
      "href": "http://localhost:9000/products/f624f049-8283-42db-8b39-d45553444845"
    }
  },
  "addedDt": 1456088336892,
  "capacity": 1,
  "link": {
    "rel": "self",
    "href": "http://localhost:9000/shoppingbaskets/436f0c1b-5878-49a4-ac01-223de24058bd/items/ecd2f5ca-da23-4b63-a1b4-22b23b0a115a"
  }
}
```

* DELETE /shoppingbaskets/d83jjdf939/items/d83jjdaffd
    * Deletes the given item from the basket

* DELETE /shoppingbaskets/:id (!) not implemented