package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Service {
    private final QrCodeParser qrCodeParser;

    public Service(QrCodeParser qrCodeParser) {
        this.qrCodeParser = qrCodeParser;
    }

    public ObjectNode parseQr(String qrData) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        var data = this.qrCodeParser.parse(qrData);

        var json = mapper.createObjectNode();
        json.put("data", mapper.writeValueAsString(data));
        return json;
    }
}
