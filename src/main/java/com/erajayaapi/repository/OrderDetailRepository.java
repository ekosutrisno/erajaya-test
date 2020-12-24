package com.erajayaapi.repository;

import com.erajayaapi.model.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

   List<OrderDetailEntity> findByOrderId(Long orderId);

   Optional<OrderDetailEntity> findByOrderIdAndOrderDetailItem(Long orderId, String itemName);
}
