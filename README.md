# Shopping Cart



# endpoints

* GET /products
    * paginated collection of products
    * query parameters `available=true` - to display only those products which are still available
    * `available` can be transformed even to a more complex one, by adding a few elements about the stock ; if there is sufficient stock, or if there are only a few elements to present this information

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
    "items": [
        {
            "id" : "334s",
            "name": "TV",
            "description": "something here",
            "price": "56",
            "currency": "EUR",
            "available": "true",
            "link": {
                "rel": "self",
                "href": "/products/334s"
            }
        },
        {
            "id" : "335s",
            "name": "TV",
            "description": "something here",
            "price": "77",
            "currency": "EUR",
            "available": "false",
            "link": {
                "rel": "self",
                "href": "/products/334s"
            }
        }
    ]
}

```



* GET /products/:id
    * product by id
```javascript
{
    "id" : "334s",
    "name": "TV",
    "description": "something here",
    "price": "56",
    "currency": "EUR",
    "available": "true",
    "link": {
        "rel": "self",
        "href": "/products/334s"
    }
}
```

* POST /shoppingbaskets
    * adding an element to the cart. Everything is based on a session id
    * a cart is added/available only for session id (!)  //TODO: future implementation
    * Returns 201 + Location Header with the location of the resource
    * a cart can be in multiple statuses
        * PENDING - just created (waits for modifications)
        * DELETED - removed from the system (soon the cart will disappear)
        * FINALIZED - user finalized the cart and wants the goods added inside to be delivered
            * this step can be done only through PATCH /carts/:id with additional data about the payment & owner of the command
            
```javascript
{
    "items": [
        {
            "product": {
                "id": "334s"
            },
            "capacity": 2  <- how many the user wants to buy
        }
    ]
}
```


* GET /shoppingbaskets/:id
    * shows the content of the
    
```javascript
{
  "id": "2c6cfe7e-1c5b-4c1a-887b-5c1efa529b58",
  "items": [
    {
      "id": "d1bb93e8-85a8-4ad3-816f-329a7a23966b",
      "product": {
        "id": "3dce5964-66d4-4385-8de3-57dc54fe8fd2",
        "name": "37aS2EkQCY",
        "description": "pSGsOnLgrotl2E1fUjeqXGiCSZ9kbu0s0No4301bFPZP1ARHF4",
        "price": 75,
        "currency": "EUR",
        "link": {
          "rel": "self",
          "href": "http://localhost:9000/products/3dce5964-66d4-4385-8de3-57dc54fe8fd2"
        }
      },
      "addedDt": 1456069739532,
      "capacity": 2,
      "link": {
        "rel": "self",
        "href": "http://localhost:9000/shoppingbaskets/3dce5964-66d4-4385-8de3-57dc54fe8fd2/items"
      }
    }
  ],
  "link": {
    "rel": "self",
    "href": "http://localhost:9000/shoppingbaskets/2c6cfe7e-1c5b-4c1a-887b-5c1efa529b58"
  }
}
```

* POST /shoppingbaskets/d83jjdf939/items
    * adds a new element to the basket
    
```javascript
{
    "product": {
        "id": "334s"
    },
    "capacity": 2  <- how many the user wants to buy
}

```

* GET /shoppingbaskets/d83jjdf939/items/d83jjdaffd

```javascript
    {
        "id": "d83jjdaffd"
        "link": {
           "rel": "self",
           "href": "/shoppingbaskets/d83jjdf939/items/d83jjdaffd"
        },
        "product": {
           "id" : "334s",
           "name": "TV",
           "description": "something here",
           "price": "56",
           "currency": "EUR",
           "available": "true",
           "link": {
               "rel": "self",
               "href": "/products/334s"
           }
        },
        "capacity": 2
    }
```

* DELETE /shoppingbaskets/d83jjdf939/items/d83jjdaffd

* DELETE /shoppingbaskets/:id