package com.example.demo;



import com.example.demo.exceptions.InvalidQrCodeException;

import java.nio.charset.StandardCharsets;

public class CrcValidator {
    public void validateCRC16(String qrText) {
        if (qrText == null)
            throw new NullPointerException("Validation String Must Not Be Null");
        if(qrText.length() <=8)
            throw new InvalidQrCodeException("Qr Code Length Is Too Short!");
// TODO invalid length
        String crcField = qrText.substring(qrText.length() - 8);
        if (!crcField.startsWith("6304"))
            throw new InvalidQrCodeException("Qr Code Does Not End With Valid CRC");

        var crc = qrText.substring(qrText.length() - 4);
        var code = qrText.substring(0, qrText.length() - 4);
        var codeCrc = this.getCodeCrc16(code.getBytes(StandardCharsets.UTF_8));

        if (!crc.equalsIgnoreCase(codeCrc))
            throw new InvalidQrCodeException("CRC Code Check Failed");
    }

    private String getCodeCrc16(final byte[] value) {
        final int polynomial = 0x1021;

        int result = 0xFFFF;

        for (final byte b : value) {
            for (int i = 0; i < 8; i++) {
                final boolean bit = (b >> 7 - i & 1) == 1;
                final boolean c15 = (result >> 15 & 1) == 1;
                result <<= 1;
                if (c15 ^ bit) {
                    result ^= polynomial;
                }
            }
        }
        result &= 0xffff;

        return Integer.toHexString(result);
    }
}