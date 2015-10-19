package com.iot.dataservice.data;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by srirkumar on 10/18/2015.
 */
@Entity
@Data
@Table(name = "device", schema = "world")
public class DeviceEntity {
    @Id
    @GeneratedValue
    @Column(name = "DEVICE_ID")
    private Long deviceid;

    /**
     * MAC ID
     */
    @Column(name = "MAC_ID",
            nullable = false,
            insertable = true,
            updatable = true,
            length = 255)
    private String macid;

    /**
     * Item Id
     */
    @Column(name = "ITEM_ID",
            nullable = false,
            insertable = true,
            updatable = true)
    private Long itemid;

    /**
     * CustomerId
     */
    @Column(name = "CUSTOMER_ID",
            nullable = false,
            insertable = true,
            updatable = true)
    private Long customerid;

    /**
     * Quantity of Item
     */
    @Column(name = "QUANTITY",
            nullable = false,
            insertable = true,
            updatable = true)
    private Long quantity;

}
