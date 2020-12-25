package com.erajayaapi.service;

import com.erajayaapi.model.OrderDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order detail service.
 */
public interface OrderDetailService {
    /**
     * Gets a ll order detail.
     *
     * @return the a ll order detail
     */
    List<OrderDetailEntity> getALlOrderDetail();

    /**
     * Gets all order detail with pagination.
     *
     * @param page the page
     * @return the all order detail with pagination
     */
    Page<OrderDetailEntity> getAllOrderDetailWithPagination(Pageable page);

    /**
     * Gets order detail by id.
     *
     * @param id the id
     * @return the order detail by id
     */
    OrderDetailEntity getOrderDetailById(String id);

    /**
     * Save order detail list.
     *
     * @param orderDetail the order detail
     * @return the list
     */
    List<OrderDetailEntity> saveOrderDetail(List<OrderDetailEntity> orderDetail);

    /**
     * Find order detail by order id list.
     *
     * @param orderId the order id
     * @return the list
     */
    List<OrderDetailEntity> findOrderDetailByOrderId(String orderId);

    /**
     * Find by order id and order detail item optional.
     *
     * @param orderId  the order id
     * @param itemName the item name
     * @return the optional
     */
    Optional<OrderDetailEntity> findByOrderIdAndOrderDetailItem(String orderId, String itemName);

    /**
     * Update order detail.
     *
     * @param id          the id
     * @param orderDetail the order detail
     */
    void updateOrderDetail(String id, OrderDetailEntity orderDetail);

    /**
     * Delete order detail.
     *
     * @param id the id
     */
    void deleteOrderDetail(String id);
}
