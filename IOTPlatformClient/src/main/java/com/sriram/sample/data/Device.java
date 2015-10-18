package com.sriram.sample.data;

import javax.persistence.*;

/**
 * Created by srirkumar on 10/18/2015.
 */
@Entity
@Table(name = "device", schema = "world")
public class Device {
    @Id
    @GeneratedValue
    @Column(name = "deviceid")
    private Long deviceid;

    private String macid;

    private Long itemid;

    private Long customerid;

    private Long quantity;

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Long deviceid) {
        this.deviceid = deviceid;
    }

    public String getMacid() {
        return macid;
    }

    public void setMacid(String macid) {
        this.macid = macid;
    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" ItemId: " + this.itemid)
                .append(" CustomerID: " + this.customerid)
                .append(" MacId: " + this.macid)
                .append(" DeviceID: " + this.macid)
                .append(" Quantity: " + this.quantity);
        return sb.toString();
    }
}
