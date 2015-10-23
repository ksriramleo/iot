package com.iot.dataservice.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by awmishra on 10/18/2015.
 */
@Data
@Entity
@Table(name = "merchant", schema = "iot")
public class MerchantEntity {

    /**
     * Merchant Id
     */
    @Id
    @GeneratedValue
    @Column(name = "MERCHANT_ID")
    private Long merchantId;

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
    @Column(name = "SUB_MERCHANT_ID",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String subMerchantId;

}
