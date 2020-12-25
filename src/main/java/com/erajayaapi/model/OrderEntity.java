package com.erajayaapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


/**
 * The type Order entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = OrderEntity.TABLE_NAME)
public class OrderEntity {
    /**
     * The Table name.
     */
    static final String TABLE_NAME = "t_order";

    @Id
    private String orderId = UUID.randomUUID().toString();
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

    /**
     * Instantiates a new Order entity.
     *
     * @param invoiceNumber    the invoice number
     * @param orderName        the order name
     * @param orderDescription the order description
     */
    public OrderEntity(String invoiceNumber, String orderName, String orderDescription) {
        this.invoiceNumber = invoiceNumber;
        this.orderName = orderName;
        this.orderDescription = orderDescription;
    }
}
