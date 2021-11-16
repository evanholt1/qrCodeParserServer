package com.example.demo;


import com.example.demo.exceptions.InvalidJsonFormatException;
import com.example.demo.exceptions.UnsuccessfulStandardReadException;
import com.example.demo.interfaces.QrCodeStandardReader;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonFileQrCodeStandardReader implements QrCodeStandardReader {
    private final String filename;

    public JsonFileQrCodeStandardReader() {
        this.filename = "standardDataObjects";
        this.readStandard();
    }

    public JsonFileQrCodeStandardReader(String fileName) {
        if (fileName == null)
            throw new NullPointerException("Custom File Name Cannot Be Null");

        this.filename = Objects.requireNonNullElse(fileName, "standardDataObjects");
        this.readStandard();
    }

    @Override
    public List<QrCodeStandardField> readStandard() {
        final var chosenFileName = "/" + this.filename + ".json";

        try (InputStream inputStream = this.getClass().getResourceAsStream(chosenFileName)) {
            if (inputStream == null)
                throw new UnsuccessfulStandardReadException("File Not Found");

            try (Reader reader = new InputStreamReader(inputStream)) {
                List<Map<String, Object>> result = new Gson().fromJson(
                    reader,
                    new TypeToken<List<Map<String, Object>>>() {}.getType()
                );

                return QrCodeStandardField.listFromJson(result);
            }
        } catch (IOException | JsonIOException exception) {
            throw new UnsuccessfulStandardReadException("Failed To Read Standard File! Try Again");
        } catch (JsonSyntaxException e) {
            throw new InvalidJsonFormatException("Json File Syntax Is Invalid");
        }
    }
}