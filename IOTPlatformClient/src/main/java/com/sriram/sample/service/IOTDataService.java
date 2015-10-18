package com.sriram.sample.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sriram.sample.data.Customer;
import com.sriram.sample.data.CustomerInfo;
import com.sriram.sample.data.Device;
import com.sriram.sample.repository.CustomerRepository;
import com.sriram.sample.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by srirkumar on 10/18/2015.
 */
@RestController
public class IOTDataService {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @RequestMapping(value = "data/customer", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public String createCustomer(@RequestBody String customerOnboardingRequest) {
        String customerResponseJSON = null;
        if (customerOnboardingRequest != null) {
            try {

                CustomerInfo customerInfoFromJson = objectMapper.readValue(customerOnboardingRequest, CustomerInfo.class);
                Customer customerEntity = customerInfoFromJson.getCustomer();
                customerRepository.save(customerEntity);

                Device deviceEntity = customerInfoFromJson.getDevice();
                deviceEntity.setCustomerid(customerEntity.getCustomerId());
                deviceRepository.save(deviceEntity);

                CustomerInfo customerResponse = buildResponse(customerEntity, deviceEntity);
                customerResponseJSON = objectMapper.writeValueAsString(customerResponse);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(customerResponseJSON);
        return customerResponseJSON;
    }

    private CustomerInfo buildResponse(Customer customerEntity, Device deviceEntity) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustomer(customerEntity);
        customerInfo.setDevice(deviceEntity);
        return customerInfo;
    }

    @RequestMapping(value="data/customer/macid/{macId}", method = RequestMethod.GET)
    @ResponseBody
    public String getCustomerByMacId(@PathVariable String macId) {
        String customerResponseJSON = null;
        Device deviceEntity = deviceRepository.findByMacid(macId);
        if (deviceEntity != null) {
            Customer customerEntity = customerRepository.findByCustomerId(deviceEntity.getCustomerid());
            CustomerInfo customerResponse = buildResponse(customerEntity, deviceEntity);

            try {
                customerResponseJSON = objectMapper.writeValueAsString(customerResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return customerResponseJSON;
    }

    @RequestMapping(value="data/customer/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public String getCustomerByCustomerId(@PathVariable String customerId) {
        String customerResponseJSON = null;
        Customer customerEntity = customerRepository.findByCustomerId(Long.valueOf(customerId));
        if (customerEntity != null) {
            Device deviceEntity = deviceRepository.findByCustomerid(customerEntity.getCustomerId());
            if (deviceEntity != null) {
                CustomerInfo customerResponse = buildResponse(customerEntity, deviceEntity);

                try {
                    customerResponseJSON = objectMapper.writeValueAsString(customerResponse);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        return customerResponseJSON;
    }
}
