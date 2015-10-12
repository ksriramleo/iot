package com.sriram.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by srirkumar on 10/11/2015.
 */
@RestController
public class SpringRestServiceSim {
    @RequestMapping(value = "", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public String raspBerryPiService(@RequestBody String request) {
        System.out.println(request);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Item item = objectMapper.readValue(request, Item.class);
            System.out.println(item.getQuantity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //gson.fromJson(request, JsonObject.class);
        return  "this is sample service saying hi " + request;
    }
}
