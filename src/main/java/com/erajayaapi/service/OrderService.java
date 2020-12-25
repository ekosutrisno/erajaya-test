package com.erajayaapi.service;

import com.erajayaapi.model.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order service.
 */
public interface OrderService {
   /**
    * Gets all order.
    *
    * @return the all order
    */
   List<OrderEntity> getAllOrder();

   /**
    * Gets all order w ith pagination.
    *
    * @param page the page
    * @return the all order w ith pagination
    */
   Page<OrderEntity> getAllOrderWIthPagination(Pageable page);

   /**
    * Gets data order by id.
    *
    * @param id the id
    * @return the data order by id
    */
   Optional<OrderEntity> getDataOrderById(String id);

   /**
    * Save data order order entity.
    *
    * @param order the order
    * @return the order entity
    */
   OrderEntity saveDataOrder(OrderEntity order);

   /**
    * Update data order.
    *
    * @param order the order
    */
   void updateDataOrder(OrderEntity order);

   /**
    * Delete data order.
    *
    * @param orderId the order id
    */
   void deleteDataOrder(String orderId);
}
