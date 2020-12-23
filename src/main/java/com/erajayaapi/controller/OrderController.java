package com.erajayaapi.controller;

import com.erajayaapi.dto.OrderRequest;
import com.erajayaapi.dto.ResponseAllPaging;
import com.erajayaapi.dto.ResponseOrder;
import com.erajayaapi.exception.ApiRequestException;
import com.erajayaapi.model.Order;
import com.erajayaapi.model.OrderDetail;
import com.erajayaapi.service.OrderDetailService;
import com.erajayaapi.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"Order"}, description = "All Implementation CRUD Order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/all-order")
    @ApiOperation(value = "Get All Order without Details", tags = {"Order"})
    public List<Order> getAllOrder() {
        return orderService.getAllOrder();
    }

    @GetMapping("/page-order")
    @ApiOperation(value = "Get All Order with Details", tags = {"Order"})
    public ResponseEntity<ResponseAllPaging> getAllOrderWithPagination(Pageable page) {
        Page<Order> orderPage = orderService.getAllOrderWIthPagination(page);
        List<ResponseOrder> responseOrders = new ArrayList<>();

        for (Order order : orderPage.getContent()) {
            if (order.getStatus())
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
    @ApiOperation(value = "Get Single Order with Details", tags = {"Order"})
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
        throw new ApiRequestException("Data not found with Id [" + id + "].");
    }

    @PostMapping("/add-order")
    @ApiOperation(value = "Add order", notes = "orderId and orderDetailId not required, will generated automatic")
    public ResponseEntity<ResponseOrder> createOrder(@RequestBody OrderRequest request) {
        var order = new Order(
                request.getOrderName(),
                request.getInvoiceNumber(),
                request.getOrderDescription()
        );

        //Saving order
        Order resOrder = orderService.saveDataOrder(order);

        List<OrderDetail> resOrderDetail;
        if (request.getOrderDetail().size() == 0) {
            resOrderDetail = request.getOrderDetail();
        } else {
            request.getOrderDetail().forEach(detail -> {
                detail.setOrderId(resOrder.getOrderId());
                detail.setOrderDetailItemPrice(detail.getOrderDetailItemPrice() * detail.getOrderDetailItemQuantity());
            });

            //Save Order details
            resOrderDetail = orderDetailService.saveOrderDetail(request.getOrderDetail());
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
    @ApiOperation(value = "Update order", notes = "orderId and orderDetailId not required, will generated automatic")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id, @RequestBody OrderRequest request) {
        Optional<Order> orderToUpdateFromDB = orderService.getDataOrderById(id);

        if (orderToUpdateFromDB.isPresent()) {
            Order orderToUpdate = orderToUpdateFromDB.get();

            orderToUpdate.setOrderName(request.getOrderName());
            orderToUpdate.setInvoiceNumber(request.getInvoiceNumber());
            orderToUpdate.setOrderDescription(request.getOrderDescription());

            Map<String, String> response = new HashMap<>();

            //Save Updated Order
            orderService.updateDataOrder(orderToUpdate);

            if (request.getOrderDetail().size() == 0) {
                response.put("Status", "Success");
                response.put("Message", "Order Updated");
                response.put("OrderDetail", "No Order detail item updated");
            } else {
                request.getOrderDetail().forEach(oDetail -> {
                    Optional<OrderDetail> optOrderDetail = orderDetailService.findByOrderIdAndOrderDetailItem(orderToUpdate.getOrderId(), oDetail.getOrderDetailItem());

                    if (optOrderDetail.isPresent()) {
                        int quantityUpdate = optOrderDetail.get().getOrderDetailItemQuantity() + oDetail.getOrderDetailItemQuantity();
                        double newPrice = oDetail.getOrderDetailItemPrice() * oDetail.getOrderDetailItemQuantity();
                        double priceUpdate = optOrderDetail.get().getOrderDetailItemPrice() + newPrice;

                        optOrderDetail.get().setOrderDetailItemQuantity(quantityUpdate);
                        optOrderDetail.get().setOrderDetailItemPrice(priceUpdate);

                        //Save each order details updated
                        orderDetailService.updateOrderDetail(optOrderDetail.get().getOrderDetailId(), optOrderDetail.get());
                    } else {
                        oDetail.setOrderId(orderToUpdate.getOrderId());
                        oDetail.setOrderDetailItemPrice(oDetail.getOrderDetailItemPrice() * oDetail.getOrderDetailItemQuantity());

                        //Add each order details new
                        List<OrderDetail> orderDetails = Collections.singletonList(oDetail);
                        orderDetailService.saveOrderDetail(orderDetails);
                    }
                });

                response.put("Status", "Success");
                response.put("Message", "Order Updated");
            }
            return new ResponseEntity<>(response, OK);
        }

        throw new ApiRequestException("Data not found with Id [" + id + "]. and update Failed!");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Order", notes = "Change Order Status")
    public ResponseEntity<?> deleteOrderStatus(@PathVariable("id") Long id) {
        Optional<Order> orderToDelete = orderService.getDataOrderById(id);

        if (orderToDelete.isPresent()) {
            //Change status order from active to non active
            orderService.deleteDataOrder(id);

            Map<String, String> response = new HashMap<>();
            response.put("Message", "Status changed and not available to Get");
            response.put("Status", "Success");
            return new ResponseEntity<>(response, OK);
        }

        throw new ApiRequestException("Data not found with Id [" + id + "]. and status Changed Failed!");
    }

    @DeleteMapping("/delete-item")
    @ApiOperation(value = "Change Order Detail Status", tags = {"Order"})
    public ResponseEntity<?> deleteOrderDetailItems(@RequestBody OrderDetail orderDetail) {
        Optional<OrderDetail> orderDetail1ToDelete = orderDetailService
                .findByOrderIdAndOrderDetailItem(orderDetail.getOrderId(), orderDetail.getOrderDetailItem());

        if (orderDetail1ToDelete.isPresent()) {
            //Delete order detail item
            orderDetailService.deleteOrderDetail(orderDetail1ToDelete.get().getOrderDetailId());

            Map<String, String> response = new HashMap<>();
            response.put("Message", "Order detail item deleted.");
            response.put("Status", "Success");
            return new ResponseEntity<>(response, OK);
        }

        throw new ApiRequestException("Data Not found, and delete item Failed!");
    }

}
