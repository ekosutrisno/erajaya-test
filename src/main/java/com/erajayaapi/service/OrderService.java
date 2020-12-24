package com.erajayaapi.service;

import com.erajayaapi.model.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
   List<OrderEntity> getAllOrder();

   Page<OrderEntity> getAllOrderWIthPagination(Pageable page);

   Optional<OrderEntity> getDataOrderById(Long id);

   OrderEntity saveDataOrder(OrderEntity order);

   void updateDataOrder(OrderEntity order);

   void deleteDataOrder(Long orderId);
}
