package com.example.demo.interfaces;



import com.example.demo.QrCodeStandardField;

import java.util.List;

// TODO move them under contract, spi, or just move them under root package
public interface QrCodeStandardReader {
    public List<QrCodeStandardField> readStandard();
}