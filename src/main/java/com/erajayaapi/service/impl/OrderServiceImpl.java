package com.erajayaapi.service.impl;

import com.erajayaapi.model.Order;
import com.erajayaapi.repository.OrderRepository;
import com.erajayaapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
   @Autowired
   private OrderRepository orderRepository;

   @Override
   public List<Order> getAllOrder() {
      List<Order> ordersFiltered = orderRepository.findAll().stream().filter(order -> order.getStatus())
              .collect(Collectors.toList());

      return ordersFiltered;
   }

   @Override
   public Page<Order> getAllOrderWIthPagination(Pageable page) {
      return orderRepository.findAll(page);
   }

   @Override
   public Order getDataOrderById(Long id) {
      Optional<Order> dataOrder = orderRepository.findById(id);
      return dataOrder.orElse(null);
   }

   @Override
   public Order saveDataOrder(Order order) {
      order.setCreatedBy("sherlock");
      order.setCreatedDate(new Date());
      order.setStatus(true);

      return orderRepository.save(order);
   }

   @Override
   public Order updateDataOrder(Long orderId, Order order) {
      Order orderToEdit = orderRepository.findById(orderId).get();

      orderToEdit.setModifiedBy("sherlock");
      orderToEdit.setModifiedDate(new Date());
      orderToEdit.setInvoiceNumber(order.getInvoiceNumber());
      orderToEdit.setOrderDescription(order.getOrderDescription());
      orderToEdit.setOrderName(order.getOrderName());

      return orderRepository.save(orderToEdit);
   }

   @Override
   public void deleteDataOrder(Long orderId) {
      Order orderToDelete = orderRepository.findById(orderId).get();
      orderToDelete.setStatus(false);

      orderRepository.save(orderToDelete);
   }
}
