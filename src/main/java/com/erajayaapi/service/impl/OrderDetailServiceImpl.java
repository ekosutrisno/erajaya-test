package com.erajayaapi.service.impl;

import com.erajayaapi.model.OrderDetail;
import com.erajayaapi.repository.OrderDetailRepository;
import com.erajayaapi.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
   @Autowired
   private OrderDetailRepository orderDetailRepository;

   @Override
   public List<OrderDetail> getALlOrderDetail() {
      return orderDetailRepository.findAll();
   }

   @Override
   public Page<OrderDetail> getAllOrderDetailWithPagination(Pageable page) {
      return orderDetailRepository.findAll(page);
   }

   @Override
   public OrderDetail getOrderDetailById(Long id) {
      return orderDetailRepository.findById(id).get();
   }

   @Override
   public List<OrderDetail> saveOrderDetail(List<OrderDetail> orderDetail) {
      return orderDetailRepository.saveAll(orderDetail);
   }

   @Override
   public OrderDetail updateOrderDetail(Long id, OrderDetail orderDetail) {
      OrderDetail orderDetailToEdit = orderDetailRepository.findById(id).get();
      double totalPrice = orderDetail.getOrderDetailItemPrice() * orderDetail.getOrderDetailItemPrice();

      orderDetailToEdit.setOrderDetailItem(orderDetail.getOrderDetailItem());
      orderDetailToEdit.setOrderDetailItemPrice(totalPrice);
      orderDetailToEdit.setOrderDetailItemQuantity(orderDetail.getOrderDetailItemQuantity());
      orderDetailToEdit.setOrderDetailMerchant(orderDetail.getOrderDetailMerchant());

      return orderDetailRepository.save(orderDetailToEdit);
   }

   @Override
   public void deleteOrderDetail(Long id) {
      OrderDetail orderDetailToDelete = orderDetailRepository.findById(id).get();
      orderDetailRepository.delete(orderDetailToDelete);
   }
}
