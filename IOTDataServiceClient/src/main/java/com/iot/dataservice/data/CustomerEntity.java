package com.iot.dataservice.data;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by srirkumar on 10/18/2015.
 */
@Entity
@Data
@Table(name = "customer", schema = "iot")
public class CustomerEntity {

    @Id
    @GeneratedValue
    @Column(name = "customerId")
    private Long customerId;

    /**
     * Customer First Name
     */
    @Column(name = "FIRST_NAME",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String firstname;

    /**
     * Customer Last Name
     */
    @Column(name = "LAST_NAME",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String lastname;

    /**
     * Customer Email
     */
    @Column(name = "EMAIL",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String email;

    /**
     * Customer First Name
     */
    @Column(name = "PHONE_NUMBER",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 15)
    private String phonenumber;

    /**
     * Brain Tree Customer Id
     */
    @Column(name = "BT_CUSTOMER_ID",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String btCustomerId;

    /**
     * Payment Method Token
     */
    @Column(name = "PAYMENT_METHOD_TOKEN",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String paymentMethodToken;

    /**
     * Customer Address Line 1
     */
    @Column(name = "ADDRESS_LINE1",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String addressLine1;

    /**
     * Customer address locality
     */
    @Column(name = "LOCALITY",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String locality;

    /**
     * Customer Address Region
     */
    @Column(name = "REGION",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String region;

    /**
     * Customer Address Postal Code
     */
    @Column(name = "POSTAL_CODE",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String postalCode;
}
