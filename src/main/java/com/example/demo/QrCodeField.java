package com.example.demo;

public class QrCodeField {
    private final String id;
    private final String value;

    public QrCodeField(String id, String value) {
        if(id == null)
            throw new NullPointerException("Id Field Cannot be Null");
        if(value == null)
            throw new NullPointerException("Value Field Cannot Be Null");
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }
}