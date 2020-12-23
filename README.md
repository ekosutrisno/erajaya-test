### Interview Test Erajaya Group

- Create endpint GetOrder from order microservice with response below
   * Get All Order
   * Get Order By Id 

```json
[
  {
    "apiVersion" : "1.0.0",
    "organization" : "Erajaya",
    "statusCode" : 0,
    "data" : [
      {
        "orderId" : "d52a0843-2b2e-4ba8-8f59-ce97739eedd4",
        "invoceNumber" : "INV/001",
        "orderName" : "Order 001",
        "orderDetail" : [
          {
            "orderDetailId" : "d52a0843-2b2e-4ba8-8f59-ce97739eedd4",
            "orderDetailItem" : "iPhone",
            "orderDetailItemQuantity" : 1,
            "orderDetailItemPrice" : 14000000,
            "orderDetailMerchant" : "iBox"
          },
          {
            "orderDetailId" : "d52a0843-2b2e-4ba8-8f59-ce97739eedd4",
            "orderDetailItem" : "Charger WiFi Universel",
            "orderDetailItemQuantity" : 1,
            "orderDetailItemPrice" : 1500000,
            "orderDetailMerchant" : "Erafone"
          }
        ],
        "orderDescription" : "Order 001",
        "createdBy" : "sherlock",
        "CreatedDate" : "2020-06-26T06:56:43.714Z",
        "ModifiedBy" : "sherlock",
        "ModifiedDate" : "2020-06-26T06:56:43.714Z"
      }
    ],
    "itemPerPage" : 2,
    "totalItems" : 1,
    "pageIndex" : 1,
    "totalPages" : 1
  }
]
```

- Create endpoint CreateOrder from order microservice with same response 
- Create endpoint UpdateOrder from order microservice with same response 
- Create endpoint UpdateOrderItem from order microservice with same response 
- Create Database ERD 

notes:
* Plus if use API Documentation (Swagger)
* Demo when interview
* Language: Can be anything
* Database: Use RDBMS and don’t use No SQL