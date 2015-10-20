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
    @Column(name = "deviceid")
    private Long deviceid;

    /**
     * MAC ID
     */
    private String macid;

    /**
     * Item Id
     */
    private Long itemid;

    /**
     * CustomerId
     */
    private Long customerid;

    /**
     * Quantity of Item
     */
    private Long quantity;

}
