package com.erajayaapi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@MappedSuperclass
public class Common {

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "created_date", nullable = false)
   private Date createdDate;

   @Column(name = "created_by", nullable = false)
   private String createdBy;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "modified_date")
   private Date modifiedDate;

   @Column(name = "modified_by")
   private String modifiedBy;

   private Boolean status;
}
