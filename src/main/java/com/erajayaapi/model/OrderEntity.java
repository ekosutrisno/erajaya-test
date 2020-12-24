package com.erajayaapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = OrderEntity.TABLE_NAME)
public class OrderEntity {
    static final String TABLE_NAME = "t_order";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String invoiceNumber;
    private String orderName;
    private String orderDescription;

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

    public OrderEntity(String invoiceNumber, String orderName, String orderDescription) {
        this.invoiceNumber = invoiceNumber;
        this.orderName = orderName;
        this.orderDescription = orderDescription;
    }
}
