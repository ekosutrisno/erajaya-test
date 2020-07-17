package com.erajayaapi.repository;

import com.erajayaapi.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

   List<OrderDetail> findByOrderId(Long orderId);

   Optional<OrderDetail> findByOrderIdAndOrderDetailItem(Long orderId, String itemName);
}
