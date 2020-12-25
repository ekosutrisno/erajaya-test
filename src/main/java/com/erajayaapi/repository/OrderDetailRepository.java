package com.erajayaapi.repository;

import com.erajayaapi.model.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order detail repository.
 */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

   /**
    * Find by order id list.
    *
    * @param orderId the order id
    * @return the list
    */
   List<OrderDetailEntity> findByOrderId(Long orderId);

   /**
    * Find by order id and order detail item optional.
    *
    * @param orderId  the order id
    * @param itemName the item name
    * @return the optional
    */
   Optional<OrderDetailEntity> findByOrderIdAndOrderDetailItem(Long orderId, String itemName);
}
