package com.erajayaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * The type Response all paging.
 */
@Data
@AllArgsConstructor
public class ResponseAllPaging {
   private String apiVersion;
   private String organization;
   private Integer statusCode;
   private List<ResponseOrder> data;
   private Integer itemPerPage;
   private Integer totalItems;
   private Integer pageIndex;
   private Integer totalPages;
}
