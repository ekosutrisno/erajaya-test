package com.erajayaapi.service.impl;

import com.erajayaapi.model.OrderDetailEntity;
import com.erajayaapi.repository.OrderDetailRepository;
import com.erajayaapi.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Order detail service.
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetailEntity> getALlOrderDetail() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Page<OrderDetailEntity> getAllOrderDetailWithPagination(Pageable page) {
        return orderDetailRepository.findAll(page);
    }

    @Override
    public OrderDetailEntity getOrderDetailById(String id) {
        return orderDetailRepository.findById(id).get();
    }

    @Override
    public List<OrderDetailEntity> findOrderDetailByOrderId(String orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public Optional<OrderDetailEntity> findByOrderIdAndOrderDetailItem(String orderId, String itemName) {
        return orderDetailRepository.findByOrderIdAndOrderDetailItem(orderId, itemName);
    }

    @Override
    public List<OrderDetailEntity> saveOrderDetail(List<OrderDetailEntity> orderDetail) {
        return orderDetailRepository.saveAll(orderDetail);
    }

    @Override
    public void updateOrderDetail(String id, OrderDetailEntity orderDetail) {
        Optional<OrderDetailEntity> orderDetailToEdit = orderDetailRepository.findById(id);
        if (orderDetailToEdit.isPresent()) {
            OrderDetailEntity orderDetailData = orderDetailToEdit.get();
            orderDetailData.setOrderDetailItem(orderDetail.getOrderDetailItem());
            orderDetailData.setOrderDetailItemPrice(orderDetail.getOrderDetailItemPrice());
            orderDetailData.setOrderDetailItemQuantity(orderDetail.getOrderDetailItemQuantity());
            orderDetailData.setOrderDetailMerchant(orderDetail.getOrderDetailMerchant());

            orderDetailRepository.save(orderDetailData);

        }
    }

    @Override
    public void deleteOrderDetail(String id) {
        OrderDetailEntity orderDetailToDelete = orderDetailRepository.findById(id).get();
        orderDetailRepository.delete(orderDetailToDelete);
    }
}
