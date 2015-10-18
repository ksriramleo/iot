package com.sriram.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by srirkumar on 10/11/2015.
 */
@RestController
public class SpringRestServiceSim {
    @Autowired
    private CommodityRepository commodityRepository;


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
