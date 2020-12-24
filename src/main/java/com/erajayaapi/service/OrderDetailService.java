package com.erajayaapi.service;

import com.erajayaapi.model.OrderDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
   List<OrderDetailEntity> getALlOrderDetail();

   Page<OrderDetailEntity> getAllOrderDetailWithPagination(Pageable page);

   OrderDetailEntity getOrderDetailById(Long id);

   List<OrderDetailEntity> saveOrderDetail(List<OrderDetailEntity> orderDetail);

   List<OrderDetailEntity> findOrderDetailByOrderId(Long orderId);

   Optional<OrderDetailEntity> findByOrderIdAndOrderDetailItem(Long orderId, String itemName);


   OrderDetailEntity updateOrderDetail(Long id, OrderDetailEntity orderDetail);

   void deleteOrderDetail(Long id);
}
