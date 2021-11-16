package com.example.demo;

import com.example.demo.enums.FieldPresence;
import com.example.demo.exceptions.InvalidCodeLengthException;
import com.example.demo.exceptions.InvalidQrCodeException;

import java.util.*;

public class QrCodeParser {
    final CrcValidator crcValidator;
    final QrCodeStandard codeStandard;

    private Set<String> mandatoryFields;
    private Set<String> parentIds;

    final Map<String, QrCodeField> parsedFields;

    public QrCodeParser(CrcValidator crcValidator, QrCodeStandard codeStandard) {
        if (crcValidator == null)
            throw new NullPointerException("A CrcValidator Instance Is Required");
        if (codeStandard == null)
            throw new NullPointerException("A QrCodeStandard Instance Is Required");

        this.crcValidator = crcValidator;
        this.codeStandard = codeStandard;
        this.parsedFields = new HashMap<>();
        this.parentIds = new HashSet<>();
    }

    // TODO even you pass the qrCode into the constructor or move the codeStandard here, or update QRCodeStandard to return
    // a mutable object from it
    public Map<String, QrCodeField> parse(String qrCode) {
        this.parsedFields.clear();
        this.parentIds.clear();
        this.mandatoryFields = codeStandard.getMandatoryFieldsCopy();
        
        this.validateQrCode(qrCode);

        this.crcValidator.validateCRC16(qrCode);

        if(!qrCode.startsWith("00"))
            throw new InvalidQrCodeException("Qr Code Does Not Start With Id Field");

        this.parse(qrCode, "");

        this.validateMandatoryFieldsPresence();

        return parsedFields;
    }

    private void parse(String qrCode, String idPrefix) {
        for (int i = 0, len = qrCode.length(); i < len; ) {
            var id = qrCode.substring(i, i + 2);

            this.validateId(id);

            if (!idPrefix.isEmpty())
                id = idPrefix + "_" + id;

            if (parsedFields.containsKey(id))
                throw new InvalidQrCodeException("Qr Code Cannot Contain Duplicate Fields");

            var fieldStandard = this.getIdStandard(id);

            var length = qrCode.substring(i + 2, i + 4);
            var intLength = this.validateLength(length, fieldStandard);

            final var valueSubstring =
                    qrCode.substring(i + 4, i + 4 + intLength);

            if (fieldStandard.adfs() != null) {
                this.parentIds.add(id);
                this.parse(valueSubstring, id);
            } else
                parsedFields.put(id, new QrCodeField(id, valueSubstring));

            mandatoryFields.remove(id);

            i = i + 4 + intLength;
        }
    }

    private int validateLength(String length, QrCodeStandardField fieldStandard) {
        var intLength = Integer.parseInt(length);

        if (!fieldStandard.lengthIsRange && fieldStandard.minLength != intLength) {
            throw new InvalidQrCodeException("Invalid Length for field with id " + fieldStandard.id());
        }

        if (intLength > fieldStandard.maxLength || intLength < fieldStandard.minLength)
            throw new InvalidQrCodeException("Invalid Length for field with id " + fieldStandard.id());

        return intLength;
    }

    private void validateId(String id) {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new InvalidQrCodeException("Invalid ID field of " + id);
        }
    }

    private QrCodeStandardField getIdStandard(String id) {
        if (codeStandard.getFields().get(id) != null)
            return codeStandard.getFields().get(id);

        throw new InvalidQrCodeException("Invalid ID field of " + id);
    }

    private void validateQrCode(String qrCode) {
        if (Objects.isNull(qrCode))
            throw new NullPointerException("A Qr Code Is Mandatory!");
        if (qrCode.length() < 46) // absolute min in standard
            throw new InvalidCodeLengthException("Code Is Too Short");
        if (qrCode.length() > 512) // absolute max is 670, but is 512 in standard
            throw new InvalidCodeLengthException("Code Is Too Long");
    }

    private void validateMandatoryFieldsPresence() {
        removeChildMandatoryFieldsWithNonexistentOptionalParentField();

        if (this.mandatoryFields.size() > 0)
            throw new InvalidQrCodeException("Some Mandatory Fields Are Missing: "
                    + mandatoryFields.stream().findFirst());
    }

    private void removeChildMandatoryFieldsWithNonexistentOptionalParentField() {
        mandatoryFields.removeIf(element -> {
            var split = element.split("_");
            var isChild = split.length > 1;

            if (isChild) {
                var parentId = element.substring(0, element.lastIndexOf("_"));
                var parent = codeStandard.getFields().get(parentId);

                return parent.presence() == FieldPresence.OPTIONAL && !parentIds.contains(parentId);
            }
            return false;
        });
    }
}