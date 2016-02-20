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
    "id": "d83jjdf939",
    "creationDt": "2016-02-19T03:26:07Z',
    "items": [
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
            "capacity": 2,
            "addedDt": "2016-02-19T03:26:07Z'
        }
    ],
    "link": {
        "rel": "self",
        "href": "/shoppingbaskets/d83jjdf939"
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