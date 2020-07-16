package com.erajayaapi.dto;

import com.erajayaapi.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseAllPaging {
   private String apiVersion;
   private String organization;
   private Integer statusCode;
   private List<Order> data;
   private Integer itemPerPage;
   private Integer totalItems;
   private Integer pageIndex;
   private Integer totalPages;
}
