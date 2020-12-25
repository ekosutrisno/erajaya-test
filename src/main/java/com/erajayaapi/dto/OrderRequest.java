package com.erajayaapi.dto;

import com.erajayaapi.model.OrderDetailEntity;
import lombok.Data;

import java.util.List;

/**
 * The type Order request.
 */
@Data
public class OrderRequest {
   private String invoiceNumber;
   private String orderName;
   private List<OrderDetailEntity> orderDetail;
   private String orderDescription;
}
