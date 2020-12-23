package com.erajayaapi.service.impl;

import com.erajayaapi.model.Common;
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
        return orderRepository.findAll().stream().filter(Common::getStatus)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Order> getAllOrderWIthPagination(Pageable page) {
        return orderRepository.findAll(page);
    }

    @Override
    public Optional<Order> getDataOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order saveDataOrder(Order order) {
        order.setCreatedBy("XKX-009");
        order.setCreatedDate(new Date());
        order.setModifiedBy("XKX-010");
        order.setModifiedDate(new Date());
        order.setStatus(true);

        return orderRepository.save(order);
    }

    @Override
    public void updateDataOrder(Order order) {

        order.setModifiedBy("sherlock");
        order.setModifiedDate(new Date());
        order.setInvoiceNumber(order.getInvoiceNumber());
        order.setOrderDescription(order.getOrderDescription());
        order.setOrderName(order.getOrderName());

        orderRepository.save(order);
    }

    @Override
    public void deleteDataOrder(Long orderId) {
        Order orderToDelete = orderRepository.findById(orderId).get();
        orderToDelete.setStatus(false);

        orderRepository.save(orderToDelete);
    }
}
