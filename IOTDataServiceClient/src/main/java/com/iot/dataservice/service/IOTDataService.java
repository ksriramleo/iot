package com.iot.dataservice.service;

import com.braintreegateway.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.dataservice.data.CatalogEntity;
import com.iot.dataservice.data.CustomerEntity;
import com.iot.dataservice.data.CustomerInfo;
import com.iot.dataservice.data.DeviceEntity;
import com.iot.dataservice.data.ItemEntity;
import com.iot.dataservice.data.MerchantEntity;
import com.iot.dataservice.data.TransactionEntity;
import com.iot.dataservice.datatype.Catalog;
import com.iot.dataservice.datatype.Device;
import com.iot.dataservice.datatype.Item;
import com.iot.dataservice.datatype.MerchantOnboarding;
import com.iot.dataservice.datatype.Patch;
import com.iot.dataservice.datatype.Payment;
import com.iot.dataservice.datatype.TransactionInfo;
import com.iot.dataservice.repository.CatalogRepository;
import com.iot.dataservice.repository.CustomerRepository;
import com.iot.dataservice.repository.DeviceRepository;
import com.iot.dataservice.repository.ItemRepository;
import com.iot.dataservice.repository.MerchantRepository;
import com.iot.dataservice.repository.TransactionRepository;
import com.iot.dataservice.data.*;
import com.iot.dataservice.datatype.*;
import com.iot.dataservice.datatype.Transaction;
import com.iot.dataservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private static final BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX, "j7qppkys6zg4qqdr", "dwkmcjgk7xwt6trz", "f5500775a53383cef86a16c8c092f566");

//    private static final BraintreeGateway gateway = new BraintreeGateway(
//            Environment.SANDBOX,
//            "3gjxkqn9j4gvkwv8",
//            "cbx62tfstsdnjr45",
//            "7e4a4983f78893d6cd49db5aa0763b8a"
//            );

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
            CustomerEntity customerEntity = customerRepository.findByBTCustomerId(deviceEntity.getBtCustomerId());
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

    @RequestMapping(value = "data/merchant/{merchantId}", method = RequestMethod.GET)
    @ResponseBody
    public String getMerchantByMerchantId(@PathVariable String merchantId) {
        String merchantResponseJSON = null;
        MerchantEntity merchantEntity = merchantRepository.findByMerchantId(Long.valueOf(merchantId));
        if (merchantEntity != null) {
            MerchantOnboarding merchantOnboarding = new MerchantOnboarding();
            Business merchantBusiness = new Business();
            merchantBusiness.setLegalName(merchantEntity.getBusinessName());
            merchantOnboarding.setId(merchantEntity.getSubMerchantId());
            merchantOnboarding.setBusiness(merchantBusiness);
            try {
                merchantResponseJSON = objectMapper.writeValueAsString(merchantOnboarding);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return merchantResponseJSON;
    }

    /**
     * This method will be used for merchant onboarding and saving the information
     */
    @RequestMapping(value = "merchant/onboarding", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public String merchantOnboardingService(@RequestBody String request) {
        ObjectMapper objectMapper = new ObjectMapper();
        String merchantOnboardingResponseJSON = null;
        try {
            MerchantOnboarding merchantOnboardingRequest = objectMapper.readValue(request, MerchantOnboarding.class);
            MerchantAccountRequest merchantAccountRequest = new MerchantAccountRequest()
                    .individual()
                        .firstName(merchantOnboardingRequest.getIndividual().getFirstName())
                        .lastName(merchantOnboardingRequest.getIndividual().getLastName())
                        .email(merchantOnboardingRequest.getIndividual().getEmail())
                        .phone(merchantOnboardingRequest.getIndividual().getPhoneNumber())
                        .dateOfBirth(merchantOnboardingRequest.getIndividual().getDateOfBirth())
                        .ssn(merchantOnboardingRequest.getIndividual().getSsn())
                        .address()
                            .streetAddress(merchantOnboardingRequest.getIndividual().getStreetAddress())
                            .locality(merchantOnboardingRequest.getIndividual().getLocality())
                            .region(merchantOnboardingRequest.getIndividual().getRegion())
                            .postalCode(merchantOnboardingRequest.getIndividual().getPostalCode())
                        .done()
                    .done()
                    .business()
                        .legalName(merchantOnboardingRequest.getBusiness().getLegalName())
                        .dbaName(merchantOnboardingRequest.getBusiness().getDbaName())
                        .taxId(merchantOnboardingRequest.getBusiness().getTaxId())
                        .address()
                            .streetAddress(merchantOnboardingRequest.getBusiness().getStreetAddress())
                            .locality(merchantOnboardingRequest.getBusiness().getLocality())
                            .region(merchantOnboardingRequest.getBusiness().getRegion())
                            .postalCode(merchantOnboardingRequest.getBusiness().getPostalCode())
                        .done()
                    .done()
                    .funding()
                        .descriptor(merchantOnboardingRequest.getFunding().getDescriptor())
                        .destination(MerchantAccount.FundingDestination.valueOf(merchantOnboardingRequest.getFunding().getDestination().toString()))
                        .email(merchantOnboardingRequest.getFunding().getEmail())
                        .mobilePhone(merchantOnboardingRequest.getFunding().getMobilePhone())
                        .accountNumber(merchantOnboardingRequest.getFunding().getAccountNumber())
                        .routingNumber(merchantOnboardingRequest.getFunding().getRoutingNumber())
                    .done()
                    .tosAccepted(true)
                    .masterMerchantAccountId("pappustore")
                    .id("Store_1");
            Result<MerchantAccount> result = gateway.merchantAccount().create(merchantAccountRequest);
            merchantOnboardingResponseJSON = objectMapper.writeValueAsString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return merchantOnboardingResponseJSON;
    }

    /**
     * This method will be used to update the device details
     */
    @RequestMapping(value = "data/device/{device_id}", produces = "application/json", method = RequestMethod.PATCH)
    @ResponseBody
    public void udpateDeviceDetails(@RequestBody String request, @PathVariable String device_id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Patch devicePatch = objectMapper.readValue(request, Patch.class);
            if (devicePatch.getOp().toString().equalsIgnoreCase("remove")) {
                deviceRepository.delete(Long.valueOf(device_id));
            } else if (devicePatch.getOp().toString().equalsIgnoreCase("replace")) {
                DeviceEntity device = deviceRepository.findOne(Long.valueOf(device_id));
                deviceRepository.updateDeviceDetails(devicePatch.getItemId(), devicePatch.getQuantity(), device_id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return item details by item id
     */
    @RequestMapping(value = "data/item/{itemId}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public String getItemDetails(@PathVariable String itemId) {
        ObjectMapper objectMapper = new ObjectMapper();
        String itemResponseJSON = null;
        try {
            ItemEntity itemEntity = itemRepository.findOne(Long.valueOf(itemId));
            Item item = new Item();
            item.setItemId(String.valueOf(itemEntity.getItemId()));
            item.setItemName(itemEntity.getItemName());
            item.setItemUpc(itemEntity.getItemUpc());
            item.setItemDesc(itemEntity.getItemDesc());
            itemResponseJSON = objectMapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return itemResponseJSON;
    }

    /**
     * Return item details by item id
     */
    @RequestMapping(value = "data/items", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public String getItemList() {
        ObjectMapper objectMapper = new ObjectMapper();
        String itemListResponseJSON = null;
        try {
            List<ItemEntity> itemEntityList = (List<ItemEntity>) itemRepository.findAll();
            List<Item> itemList = new ArrayList<Item>();
            for (ItemEntity itemEntity : itemEntityList) {
                Item item = new Item();
                item.setItemId(String.valueOf(itemEntity.getItemId()));
                item.setItemName(itemEntity.getItemName());
                item.setItemUpc(itemEntity.getItemUpc());
                item.setItemDesc(itemEntity.getItemDesc());
                itemList.add(item);
            }
            itemListResponseJSON = objectMapper.writeValueAsString(itemList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return itemListResponseJSON;
    }

    /**
     * Return catalog by item upc
     */
    @RequestMapping(value = "data/catalog/{itemUpc}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public String getItemCatalog(@PathVariable String itemUpc) {
        ObjectMapper objectMapper = new ObjectMapper();
        String itemCatalogJSON = null;
        try {
            List<CatalogEntity> catalogEntityList = catalogRepository.findByItemUpc(itemUpc);
            List<Catalog> catalogList = new ArrayList<Catalog>();
            for (CatalogEntity catalogEntity : catalogEntityList) {
                Catalog catalog = new Catalog();
                catalog.setMerchantId(String.valueOf(catalogEntity.getMerchantId()));
                catalog.setItemUpc(catalogEntity.getItemUpc());
                catalog.setAvailability(catalogEntity.getAvailability());
                catalog.setPrice(String.valueOf(catalogEntity.getPrice()));
                catalog.setQuantity(String.valueOf(catalogEntity.getQuantity()));
                catalogList.add(catalog);
            }
            itemCatalogJSON = objectMapper.writeValueAsString(catalogList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return itemCatalogJSON;
    }

    /**
     * Save Transaction
     */
    @RequestMapping(value = "data/transaction", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public String createTransaction(@RequestBody String transactionRequest) {
        String transactionResponseJSON = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            TransactionInfo transaction = objectMapper.readValue(transactionRequest, TransactionInfo.class);
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setTransactionId(transaction.getTransactionId());
            transactionEntity.setItemUpc(transaction.getItemUpc());
            transactionEntity.setCustomerId(Long.valueOf(transaction.getCustomerId()));
            transactionEntity.setMerchantId(Long.valueOf(transaction.getMerchantId()));
            transactionEntity.setAmount(Double.valueOf(transaction.getAmount()));
            transactionEntity.setTransactionType(transaction.getTransactionType().toString());
            transactionEntity.setStatus(transaction.getStatus().toString());
            transactionEntity.setSessionId(transaction.getSessionId());
            transactionRepository.save(transactionEntity);
            transactionResponseJSON = objectMapper.writeValueAsString(transaction);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactionResponseJSON;
    }

    /**
     * This method archives the merchant information
     *
     * @param merchantOnboarding Merchant onboarding Request
     */
    public void saveMerchant(MerchantOnboarding merchantOnboarding) {
        MerchantEntity merchantEntity = new MerchantEntity();
        merchantEntity.setBusinessName(merchantOnboarding.getBusiness().getLegalName());
        merchantEntity.setSubMerchantId(merchantOnboarding.getId());
        merchantRepository.save(merchantEntity);
    }

    /**
     * Save Transaction
     */
    @RequestMapping(value = "merchant/create", method = RequestMethod.GET)
    @ResponseBody
    public String makePayment() {
                MerchantAccountRequest merchantAccountRequest = new MerchantAccountRequest().
                individual().
                firstName("Jane").
                lastName("Doe").
                email("jane@14ladders.com").
                phone("5553334444").
                dateOfBirth("1981-11-19").
                ssn("456-45-4567").
                address().
                streetAddress("111 Main St").
                locality("Chicago").
                region("IL").
                postalCode("60622").done().done().business()
                .legalName("Jane's Ladders")
                .dbaName("Jane's Ladders")
                .taxId("98-7654321")
                .address()
                .streetAddress("111 Main St").
                locality("Chicago").
                region("IL").
                postalCode("60622").
                done().done().funding().
                descriptor("Blue Ladders").
                destination(MerchantAccount.FundingDestination.BANK).
                email("funding@blueladders.com").
                mobilePhone("6006006666").
                accountNumber("1123581321").
                routingNumber("071101307").
                done().
                tosAccepted(true).
                masterMerchantAccountId("syntelinc").
                id("Store_3");


        Result<MerchantAccount> result = gateway.merchantAccount().create(merchantAccountRequest);
        return "";

    }
    /**
     * Save Transaction
     */
    @RequestMapping(value = "merchant/transaction", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public String makePayment(@RequestBody String paymentRequest) {
        String paymentResponseJSON = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Payment payment = objectMapper.readValue(paymentRequest, Payment.class);
            Result<PaymentMethodNonce> paymentMethodNonceResult = gateway.paymentMethodNonce().create(payment.getCustomerToken());
            TransactionRequest transactionRequest = new TransactionRequest();
            transactionRequest
                    .amount(new BigDecimal(payment.getAmount()))
                    .serviceFeeAmount(new BigDecimal(payment.getServiceFee()))
                    .merchantAccountId(payment.getSubMerchantId())
                    .paymentMethodNonce(paymentMethodNonceResult.getTarget().getNonce()).options().submitForSettlement(true).done();
            Result<com.braintreegateway.Transaction> paymentTransaction = gateway.transaction().sale(transactionRequest);

            if (paymentTransaction != null && paymentTransaction.getErrors() != null) {
                List<ValidationError> allValidationErrors = paymentTransaction.getErrors().getAllValidationErrors();
                payment.setResponseDescription(appendValidationErrors(allValidationErrors));
            } else {
                payment.setPaymentId(paymentTransaction.getTarget().getId());
                payment.setResponseAuthorizationCode(paymentTransaction.getTarget().getProcessorAuthorizationCode());
            }
            paymentResponseJSON = objectMapper.writeValueAsString(payment);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paymentResponseJSON;
    }

    public String appendValidationErrors(List<ValidationError> allValidationErrors) {
        StringBuffer sb = new StringBuffer();
        for (ValidationError validationError:allValidationErrors) {
            sb.append(validationError.getMessage()).append("\n");
        }
        return sb.toString();
    }
}
