package com.iot.dataservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.dataservice.datatype.Device;
import com.iot.dataservice.data.DeviceEntity;
import com.iot.dataservice.data.MerchantEntity;
import com.iot.dataservice.datatype.Merchant;
import com.iot.dataservice.datatype.Patch;
import com.iot.dataservice.repository.DeviceRepository;
import com.iot.dataservice.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;

/**
 * Created by srirkumar on 10/11/2015.
 */
@RestController
public class IOTDatabaseService {
    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    private DeviceRepository deviceRepository;


    @RequestMapping(value = "", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public String raspBerryPiService(@RequestBody String request, @RequestHeader(value="MAC", defaultValue = "123") String mac) {
        System.out.println(request);
        System.out.println(mac);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Item item = objectMapper.readValue(request, Item.class);
            Commodity commodity = new Commodity();
            commodity.setId(Long.valueOf(item.getSku()));
            commodity.setEmail(item.getItem());
            commodityRepository.save(commodity);

            System.out.println(item.getQuantity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //gson.fromJson(request, JsonObject.class);
        return  "this is sample service saying hi " + request;
    }
}
