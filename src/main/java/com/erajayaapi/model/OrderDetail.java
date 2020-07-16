package com.erajayaapi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = OrderDetail.TABLE_NAME)
public class OrderDetail {
   static final String TABLE_NAME = "t_order_detail";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long orderDetailId;

   @Column(name = "order_id", nullable = false)
   private Long OrderId;

   private String orderDetailItem;
   private Integer orderDetailItemQuantity;
   private Double orderDetailItemPrice;
   private String orderDetailMerchant;
}
