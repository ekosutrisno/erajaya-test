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

import java.util.*;

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
      List<ResponseOrder> responseOrders = new ArrayList<>();

      for (Order order : orderPage.getContent()) {
         responseOrders.add(new ResponseOrder(
                 order.getOrderId(),
                 order.getInvoiceNumber(),
                 order.getOrderName(),
                 orderDetailService.findOrderDetailByOrderId(order.getOrderId()),
                 order.getOrderDescription(),
                 order.getCreatedDate(),
                 order.getCreatedBy(),
                 order.getModifiedDate(),
                 order.getModifiedBy()
         ));
      }

      var response = new ResponseAllPaging(
              "api.v1.0.0",
              "Erajaya",
              OK.value(),
              responseOrders,
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
      List<OrderDetail> orderDetail = orderDetailService.findOrderDetailByOrderId(order.getOrderId());

      if (order != null) {
         ResponseOrder response = new ResponseOrder(
                 order.getOrderId(),
                 order.getInvoiceNumber(),
                 order.getOrderName(),
                 orderDetail,
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

      //Saving order
      Order resOrder = orderService.saveDataOrder(order);

      List<OrderDetail> resOrderDetail;
      if (request.getOrderList().isEmpty()) {
         resOrderDetail = request.getOrderList();
      } else {
         request.getOrderList().forEach(detail -> {
            detail.setOrderId(resOrder.getOrderId());
            detail.setOrderDetailItemPrice(detail.getOrderDetailItemPrice() * detail.getOrderDetailItemQuantity());
         });
         //Save Order details
         resOrderDetail = orderDetailService.saveOrderDetail(request.getOrderList());
      }

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
   public ResponseEntity<?> updateOrder(@PathVariable("id") Long id, @RequestBody OrderRequest request) {
      Order orderToUpdate = orderService.getDataOrderById(id);
      orderToUpdate.setOrderName(request.getOrderName());
      orderToUpdate.setInvoiceNumber(request.getInvoiceNumber());
      orderToUpdate.setOrderDescription(request.getOrderDescription());

      Map<String, String> response = new HashMap<>();
      orderService.updateDataOrder(orderToUpdate);
      response.put("Message", "Order Updated");

      if (request.getOrderList().size() == 0) {
         response.put("Message", "No Order detail item updated");
      } else {
         OrderDetail oDetail = request.getOrderList().get(0);
         Optional<OrderDetail> optOrderDetail = orderDetailService.findByOrderIdAndOrderDetailItem(orderToUpdate.getOrderId(), oDetail.getOrderDetailItem());

         int quantityUpdate = optOrderDetail.get().getOrderDetailItemQuantity() + oDetail.getOrderDetailItemQuantity();
         double newPrice = oDetail.getOrderDetailItemPrice() * oDetail.getOrderDetailItemQuantity();
         double priceUpdate = optOrderDetail.get().getOrderDetailItemPrice() + newPrice;

         if (optOrderDetail.isPresent()) {
            optOrderDetail.get().setOrderDetailItemQuantity(quantityUpdate);
            optOrderDetail.get().setOrderDetailItemPrice(priceUpdate);

            //Save setiap order details
            List<OrderDetail> orderDetails = Arrays.asList(optOrderDetail.get());
            orderDetailService.saveOrderDetail(orderDetails);
            response.put("Message", "Order detail updated");
         } else {
            oDetail.setOrderId(orderToUpdate.getOrderId());
            oDetail.setOrderDetailItemPrice(oDetail.getOrderDetailItemPrice() * oDetail.getOrderDetailItemQuantity());
            //Save setiap order details
            List<OrderDetail> orderDetails = Arrays.asList(oDetail);
            orderDetailService.saveOrderDetail(orderDetails);
            response.put("Message", "Order detail Added");
         }
         response.put("Status", "Success");
      }

      return new ResponseEntity<>(response, OK);
   }
}
