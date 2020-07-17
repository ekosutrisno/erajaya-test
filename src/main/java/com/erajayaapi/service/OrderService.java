package com.erajayaapi.service;

import com.erajayaapi.dto.OrderRequest;
import com.erajayaapi.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
   List<Order> getAllOrder();

   Page<Order> getAllOrderWIthPagination(Pageable page);

   Optional<Order> getDataOrderById(Long id);

   Order saveDataOrder(Order order);

   Order updateDataOrder(Order order);

   void deleteDataOrder(Long orderId);
}
