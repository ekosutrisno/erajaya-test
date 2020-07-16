package com.erajayaapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Order.TABLE_NAME)
public class Order extends Common {
   static final String TABLE_NAME = "t_order";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long orderId;
   private String invoiceNumber;
   private String orderName;
   private String orderDescription;

   public Order(String invoiceNumber, String orderName, String orderDescription) {
      this.invoiceNumber = invoiceNumber;
      this.orderName = orderName;
      this.orderDescription = orderDescription;
   }
}
