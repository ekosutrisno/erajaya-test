package com.erajayaapi.dto;

import com.erajayaapi.model.OrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
   private String invoiceNumber;
   private String orderName;
   private List<OrderDetail> orderList;
   private String orderDescription;
}
