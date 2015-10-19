package com.iot.dataservice.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by awmishra on 10/18/2015.
 */
@Data
@Entity
@Table(name = "merchant", schema = "world")
public class MerchantEntity {

    /**
     * Merchant Id
     */
    @Id
    @GeneratedValue
    @Column(name = "MERCHANT_ID")
    private Long merchant_id;

    /**
     * Business Name
     */
    @Column(name = "BUSINESS_NAME",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String businessName;

    /**
     * Merchant Account Id
     */
    @Column(name = "MERCH_ACCT_ID",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String merchantAccountNumber;

}
