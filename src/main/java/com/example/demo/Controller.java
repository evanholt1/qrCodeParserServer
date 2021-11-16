package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("")
public class Controller {
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping
    public String test() {
        System.out.println("In Get Request");
        return "hello world";
    }

    @GetMapping("/parseQr")
    public ObjectNode parseQr(@RequestParam("data")String qrCodeData) throws JsonProcessingException {
        //String qrCode = (String) payload.get("data");
        return this.service.parseQr(qrCodeData);
    }

//    @PostMapping
//    public ObjectNode parseQr(@RequestBody Map<String,Object> payload) throws JsonProcessingException {
//        String qrCode = (String) payload.get("data");
//        return this.service.parseQr(qrCode);
//    }
}
