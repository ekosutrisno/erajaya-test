package com.erajayaapi.service;

import com.erajayaapi.model.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderDetailService {
   List<OrderDetail> getALlOrderDetail();

   Page<OrderDetail> getAllOrderDetailWithPagination(Pageable page);

   OrderDetail getOrderDetailById(Long id);

   List<OrderDetail> saveOrderDetail(List<OrderDetail> orderDetail);

   OrderDetail updateOrderDetail(Long id, OrderDetail orderDetail);

   void deleteOrderDetail(Long id);
}
