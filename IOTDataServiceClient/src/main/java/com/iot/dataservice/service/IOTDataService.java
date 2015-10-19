package com.iot.dataservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.dataservice.data.CustomerEntity;
import com.iot.dataservice.data.CustomerInfo;
import com.iot.dataservice.data.DeviceEntity;
import com.iot.dataservice.data.MerchantEntity;
import com.iot.dataservice.datatype.Device;
import com.iot.dataservice.datatype.Merchant;
import com.iot.dataservice.datatype.Patch;
import com.iot.dataservice.repository.CustomerRepository;
import com.iot.dataservice.repository.DeviceRepository;
import com.iot.dataservice.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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

    @Autowired
    private MerchantRepository merchantRepository;

    @RequestMapping(value = "data/customer", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public String createCustomer(@RequestBody String customerOnboardingRequest) {
        String customerResponseJSON = null;
        if (customerOnboardingRequest != null) {
            try {

                CustomerInfo customerInfoFromJson = objectMapper.readValue(customerOnboardingRequest, CustomerInfo.class);
                CustomerEntity customerEntity = customerInfoFromJson.getCustomer();
                customerRepository.save(customerEntity);

                DeviceEntity deviceEntity = customerInfoFromJson.getDevice();
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

    private CustomerInfo buildResponse(CustomerEntity customerEntity, DeviceEntity deviceEntity) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustomer(customerEntity);
        customerInfo.setDevice(deviceEntity);
        return customerInfo;
    }

    @RequestMapping(value="data/customer/macid/{macId}", method = RequestMethod.GET)
    @ResponseBody
    public String getCustomerByMacId(@PathVariable String macId) {
        String customerResponseJSON = null;
        DeviceEntity deviceEntity = deviceRepository.findByMacid(macId);
        if (deviceEntity != null) {
            CustomerEntity customerEntity = customerRepository.findByCustomerId(deviceEntity.getCustomerid());
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
        CustomerEntity customerEntity = customerRepository.findByCustomerId(Long.valueOf(customerId));
        if (customerEntity != null) {
            DeviceEntity deviceEntity = deviceRepository.findByCustomerid(customerEntity.getCustomerId());
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

    /**
     * This method archives the merchant information
     * @param request Merchant Request
     */
    @RequestMapping(value = "/iot_data_service/onboarding/merchant", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public void merchantOnboardingService(@RequestBody String request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Merchant merchant = objectMapper.readValue(request, Merchant.class);
            MerchantEntity merchantEntity = new MerchantEntity();
            merchantEntity.setBusinessName(merchant.getBusinessName());
            merchantEntity.setMerchantAccountNumber(merchant.getMerchantAccountId());
            merchantRepository.save(merchantEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will be used to update the device details
     */
    @RequestMapping(value = "/iot_data_service/onboarding/device", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public void deviceOnboardingService(@RequestBody String request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Device device = objectMapper.readValue(request, Device.class);
            DeviceEntity deviceEntity = new DeviceEntity();
            deviceEntity.setCustomerid(Long.valueOf(device.getCustomerId()));
            deviceEntity.setItemid(Long.valueOf(device.getItemId()));
            deviceEntity.setMacid(device.getMacId());
            deviceEntity.setQuantity(Long.valueOf(device.getQuantity()));
            deviceRepository.save(deviceEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will be used to update the device details
     */
    @RequestMapping(value = "/iot_data_service/onboarding/device/{device_id}", produces = "application/json", method = RequestMethod.PATCH)
    @ResponseBody
    public void udpateDeviceDetails(@RequestBody String request, @PathParam("device_id") String device_id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Patch devicePatch = objectMapper.readValue(request, Patch.class);
            if(devicePatch.getOp().toString().equals("REMOVE")) {
                deviceRepository.delete(Long.valueOf(device_id));
            } else if (devicePatch.getOp().toString().equals("UPDATE")) {
                DeviceEntity device = deviceRepository.findOne(Long.valueOf(device_id));
                deviceRepository.updateDeviceDetails(devicePatch.getItemId(), devicePatch.getQuantity(), device_id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
