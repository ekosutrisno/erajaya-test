package com.erajayaapi.service;

import com.erajayaapi.dto.OrderRequest;
import com.erajayaapi.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
   List<Order> getAllOrder();

   Page<Order> getAllOrderWIthPagination(Pageable page);

   Order getDataOrderById(Long id);

   Order saveDataOrder(Order order);

   Order updateDataOrder(Order order);

   void deleteDataOrder(Long orderId);
}
