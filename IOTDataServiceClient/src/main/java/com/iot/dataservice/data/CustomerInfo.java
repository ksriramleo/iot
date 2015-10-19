package com.iot.dataservice.data;

/**
 * Created by srirkumar on 10/18/2015.
 */
public class CustomerInfo {
    private CustomerEntity customer;

    public DeviceEntity getDevice() {
        return device;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    private DeviceEntity device;
}
