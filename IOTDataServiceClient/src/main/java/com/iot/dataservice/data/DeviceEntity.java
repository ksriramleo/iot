package com.iot.dataservice.data;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by srirkumar on 10/18/2015.
 */
@Entity
@Data
@Table(name = "device", schema = "iot")
public class DeviceEntity {
    @Id
    @GeneratedValue
    @Column(name = "DEVICE_ID")
    private Long deviceid;

    /**
     * MAC ID
     */
    @Column(name = "MAC_ID",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String macid;

    /**
     * Item Id
     */
    @Column(name = "ITEM_ID",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 11)
    private Long itemid;

    /**
     * CustomerId
     */
    @Column(name = "BT_CUSTOMER_ID",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 255)
    private String btCustomerId;

    /**
     * Quantity of Item
     */
    @Column(name = "BT_CUSTOMER_ID",
            nullable = true,
            insertable = true,
            updatable = false,
            length = 11)
    private Long quantity;

}
