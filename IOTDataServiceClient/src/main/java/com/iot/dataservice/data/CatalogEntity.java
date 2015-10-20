package com.iot.dataservice.data;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by awmishra on 10/19/2015.
 */
@Data
@Entity
@Table(name = "catalog", schema = "iot")
public class CatalogEntity {

    /**
     * Merchant Id
     */
    @Id
    @GeneratedValue
    @Column(name = "CATALOG_ID")
    private Long catalogId;

    /**
     * Item UPC
     */
    @Column(name = "ITEM_UPC",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String itemUpc;

    /**
     * Merchant Account Id
     */
    @Column(name = "MERCHANT_ID",
            nullable = true,
            insertable = true,
            updatable = false)
    private Long merchantId;

    /**
     * Price
     */
    @Column(name = "PRICE")
    private Double price;

    /**
     * Quantity
     */
    @Column(name = "QUANTITY")
    private Long quantity;

    /**
     * Availability
     */
    @Column(name = "AVAILABILITY")
    private Boolean availability;



}
