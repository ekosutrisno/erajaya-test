package com.erajayaapi.dto;

import com.erajayaapi.model.OrderDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ResponseOrder {
   private String orderId;
   private String invoiceNumber;
   private String orderName;
   private List<OrderDetailEntity> orderDetail;
   private String orderDescription;
   private Date createdDate;
   private String createdBy;
   private Date modifiedDate;
   private String modifiedBy;
}
