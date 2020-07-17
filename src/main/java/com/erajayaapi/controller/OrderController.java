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
   public ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
      Optional<Order> order = orderService.getDataOrderById(id);
      if (order.isPresent() && order.get().getStatus()) {
         List<OrderDetail> orderDetail = orderDetailService.findOrderDetailByOrderId(order.get().getOrderId());

         ResponseOrder response = new ResponseOrder(
                 order.get().getOrderId(),
                 order.get().getInvoiceNumber(),
                 order.get().getOrderName(),
                 orderDetail,
                 order.get().getOrderDescription(),
                 order.get().getCreatedDate(),
                 order.get().getCreatedBy(),
                 order.get().getModifiedDate(),
                 order.get().getModifiedBy()
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
         List<OrderDetail> orderDetails = new ArrayList<>();
         for (OrderDetail detail : request.getOrderList()) {
            detail.setOrderId(resOrder.getOrderId());
            detail.setOrderDetailItemPrice(detail.getOrderDetailItemPrice() * detail.getOrderDetailItemQuantity());
         }

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
      Order orderToUpdate = orderService.getDataOrderById(id).get();
      orderToUpdate.setOrderName(request.getOrderName());
      orderToUpdate.setInvoiceNumber(request.getInvoiceNumber());
      orderToUpdate.setOrderDescription(request.getOrderDescription());

      Map<String, String> response = new HashMap<>();

      //Save Updated Order
      orderService.updateDataOrder(orderToUpdate);

      if (request.getOrderList().size() == 0) {
         response.put("Status", "Success");
         response.put("Message", "Order Updated");
         response.put("OrderDetail", "No Order detail item updated");
      } else {
         OrderDetail oDetail = request.getOrderList().get(0);
         Optional<OrderDetail> optOrderDetail = orderDetailService.findByOrderIdAndOrderDetailItem(orderToUpdate.getOrderId(), oDetail.getOrderDetailItem());

         if (optOrderDetail.isPresent()) {
            int quantityUpdate = optOrderDetail.get().getOrderDetailItemQuantity() + oDetail.getOrderDetailItemQuantity();
            double newPrice = oDetail.getOrderDetailItemPrice() * oDetail.getOrderDetailItemQuantity();
            double priceUpdate = optOrderDetail.get().getOrderDetailItemPrice() + newPrice;

            optOrderDetail.get().setOrderDetailItemQuantity(quantityUpdate);
            optOrderDetail.get().setOrderDetailItemPrice(priceUpdate);

            //Save setiap order details updated
            orderDetailService.updateOrderDetail(optOrderDetail.get().getOrderDetailId(), optOrderDetail.get());
         } else {
            oDetail.setOrderId(orderToUpdate.getOrderId());
            oDetail.setOrderDetailItemPrice(oDetail.getOrderDetailItemPrice() * oDetail.getOrderDetailItemQuantity());

            //Add setiap order details new
            List<OrderDetail> orderDetails = Arrays.asList(oDetail);
            orderDetailService.saveOrderDetail(orderDetails);
         }
         response.put("Status", "Success");
         response.put("Message", "Order Updated");
      }
      return new ResponseEntity<>(response, OK);
   }
}
