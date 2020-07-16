package com.erajayaapi.controller;

import com.erajayaapi.dto.OrderRequest;
import com.erajayaapi.dto.ResponseAllPaging;
import com.erajayaapi.dto.ResponseOrder;
import com.erajayaapi.exception.ApiRequestException;
import com.erajayaapi.model.Order;
import com.erajayaapi.model.OrderDetail;
import com.erajayaapi.service.OrderDetailService;
import com.erajayaapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api/v1/order", produces = "application/json")
public class OrderController {

   @Autowired
   private OrderService orderService;

   @Autowired
   private OrderDetailService orderDetailService;

   @GetMapping("/all-order")
   public List<Order> getAllOrder() {
      return orderService.getAllOrder();
   }

   @GetMapping("/page-order")
   public ResponseEntity<ResponseAllPaging> getAllOrderWithPagination(Pageable page) {
      Page<Order> orderPage = orderService.getAllOrderWIthPagination(page);


      var response = new ResponseAllPaging(
              "api.v1.0.0",
              "Erajaya",
              OK.value(),
              orderPage.getContent(),
              orderPage.getSize(),
              orderPage.getNumberOfElements(),
              orderPage.getNumber(),
              orderPage.getTotalPages()
      );

      return new ResponseEntity<>(response, OK);
   }

   @GetMapping("/{id}")
   public ResponseEntity<ResponseOrder> getOrderById(@PathVariable("id") Long id) {
      Order order = orderService.getDataOrderById(id);

      if (order != null) {
         ResponseOrder response = new ResponseOrder(
                 order.getOrderId(),
                 order.getInvoiceNumber(),
                 order.getOrderName(),
                 null,
                 order.getOrderDescription(),
                 order.getCreatedDate(),
                 order.getCreatedBy(),
                 order.getModifiedDate(),
                 order.getModifiedBy()
         );
         return new ResponseEntity<>(response, OK);

      }

      throw new ApiRequestException("Data tidak ditemukan dengan Id [" + id + "].");
   }

   @PostMapping("/add-order")
   public ResponseEntity<ResponseOrder> createOrder(@RequestBody OrderRequest request) {
      var order = new Order(
              request.getOrderName(),
              request.getInvoiceNumber(),
              request.getOrderDescription()
      );
      Order resOrder = orderService.saveDataOrder(order);

      List<OrderDetail> orderDetails = new ArrayList<>();
      for (OrderDetail detail : request.getOrderList()) {
         detail.setOrderId(resOrder.getOrderId());
         detail.setOrderDetailItemPrice(detail.getOrderDetailItemPrice() * detail.getOrderDetailItemQuantity());
         orderDetails.add(detail);
      }

      List<OrderDetail> resOrderDetail = orderDetailService.saveOrderDetail(orderDetails);

      return new ResponseEntity<>(
              new ResponseOrder(
                      resOrder.getOrderId(),
                      resOrder.getInvoiceNumber(),
                      resOrder.getOrderName(),
                      resOrderDetail,
                      resOrder.getOrderDescription(),
                      resOrder.getCreatedDate(),
                      resOrder.getCreatedBy(),
                      resOrder.getModifiedDate(),
                      resOrder.getModifiedBy()
              )
              , OK);
   }

   @PutMapping("/{id}")
   public ResponseEntity<ResponseOrder> updateOrder(@PathVariable("id") Long id, @RequestBody OrderRequest request) {
      return null;
   }


}
