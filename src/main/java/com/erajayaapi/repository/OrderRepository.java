package com.erajayaapi.repository;

import com.erajayaapi.model.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The interface Order repository.
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    /**
     * Find all order with pagination page.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Query("select o from OrderEntity o where o.status = true")
    Page<OrderEntity> findAllOrderWithPagination(Pageable pageable);
}
