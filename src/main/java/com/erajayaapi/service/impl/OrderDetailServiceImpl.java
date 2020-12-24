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
    public OrderDetailEntity getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id).get();
    }

    @Override
    public List<OrderDetailEntity> findOrderDetailByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public Optional<OrderDetailEntity> findByOrderIdAndOrderDetailItem(Long orderId, String itemName) {
        return orderDetailRepository.findByOrderIdAndOrderDetailItem(orderId, itemName);
    }

    @Override
    public List<OrderDetailEntity> saveOrderDetail(List<OrderDetailEntity> orderDetail) {
        return orderDetailRepository.saveAll(orderDetail);
    }

    @Override
    public OrderDetailEntity updateOrderDetail(Long id, OrderDetailEntity orderDetail) {
        Optional<OrderDetailEntity> orderDetailToEdit = orderDetailRepository.findById(id);
        if (orderDetailToEdit.isPresent()) {
            OrderDetailEntity orderDetailData = orderDetailToEdit.get();
            orderDetailData.setOrderDetailItem(orderDetail.getOrderDetailItem());
            orderDetailData.setOrderDetailItemPrice(orderDetail.getOrderDetailItemPrice());
            orderDetailData.setOrderDetailItemQuantity(orderDetail.getOrderDetailItemQuantity());
            orderDetailData.setOrderDetailMerchant(orderDetail.getOrderDetailMerchant());

            return orderDetailRepository.save(orderDetailData);

        }
        return null;
    }

    @Override
    public void deleteOrderDetail(Long id) {
        OrderDetailEntity orderDetailToDelete = orderDetailRepository.findById(id).get();
        orderDetailRepository.delete(orderDetailToDelete);
    }
}
