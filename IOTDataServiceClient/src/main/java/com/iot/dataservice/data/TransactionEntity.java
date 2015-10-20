package com.iot.dataservice.data;

import lombok.Data;
import javax.persistence.*;

/**
 * Created by awmishra on 10/19/2015.
 */
@Data
@Table(name = "transaction" , schema = "iot")
public class TransactionEntity {

    /**
     * Transaction Id
     */
    @Id
    @GeneratedValue
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;

    /**
     * Transaction Type
     * ORDER or PAYMENT or VOID
     */
    @Column(name = "TRANSACTION_TYPE",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 50)
    private String transactionType;

    /**
     * Merchant Id
     */
    @Column(name = "MERCHANT_ID",
            nullable = true,
            insertable = true,
            updatable = false)
    private Long merchantId;

    /**
     * Customer Id
     */
    @Column(name = "CUSTOMER_ID",
            nullable = true,
            insertable = true,
            updatable = false)
    private Long customerId;

    @Column(name = "ITEM_UPC",
            nullable = false,
            insertable = true,
            updatable = true,
            length = 255)
    private String itemUpc;

    /**
     * Transaction Amount
     */
    @Column(name = "AMOUNT")
    private Double amount;

    /**
     *  Transaction Status
     *  IN-PROGRESS or COMPLETE
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * Session_id
     * Session Id is unique for one complete flow
     */
    @Column(name = "SESSION_ID")
    private String sessionId;



}
