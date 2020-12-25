package com.erajayaapi.service.impl;

import com.erajayaapi.model.OrderEntity;
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

/**
 * The type Order service.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;

    /**
     * Instantiates a new Order service.
     *
     * @param orderRepository the order repository
     */
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderEntity> getAllOrder() {
        return orderRepository.findAll().stream().filter(OrderEntity::getStatus)
                .collect(Collectors.toList());
    }

    @Override
    public Page<OrderEntity> getAllOrderWIthPagination(Pageable page) {
        return orderRepository.findAllOrderWithPagination(page);
    }

    @Override
    public Optional<OrderEntity> getDataOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public OrderEntity saveDataOrder(OrderEntity order) {
        order.setCreatedBy("XKX-009");
        order.setCreatedDate(new Date());
        order.setModifiedBy("XKX-010");
        order.setModifiedDate(new Date());
        order.setStatus(true);

        return orderRepository.save(order);
    }

    @Override
    public void updateDataOrder(OrderEntity order) {

        order.setModifiedBy("sherlock");
        order.setModifiedDate(new Date());
        order.setInvoiceNumber(order.getInvoiceNumber());
        order.setOrderDescription(order.getOrderDescription());
        order.setOrderName(order.getOrderName());

        orderRepository.save(order);
    }

    @Override
    public void deleteDataOrder(Long orderId) {
        OrderEntity orderToDelete = orderRepository.findById(orderId).get();
        orderToDelete.setStatus(false);

        orderRepository.save(orderToDelete);
    }
}
