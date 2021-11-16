package com.example.demo;



import com.example.demo.enums.FieldFormat;
import com.example.demo.enums.FieldPresence;
import com.example.demo.exceptions.InvalidJsonFormatException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class QrCodeStandardField {
    private final String id; // split with '-' for range
    private final String length; // split with '-' for range
    private final String name;
    private final FieldFormat format;
    private final FieldPresence presence;
    private final List<QrCodeStandardField> adfs;

    public final int minLength;
    public final int maxLength;
    public final boolean lengthIsRange;

    private QrCodeStandardField(Builder builder) {
        this.id = builder.id;
        this.length = builder.length;
        this.name = builder.name;
        this.format = builder.format;
        this.presence = builder.presence;
        this.adfs = builder.adfs;

        var lengthRange = this.length.split("-");
        if(lengthRange.length == 1) {
            minLength = maxLength = Integer.parseInt(this.length);
            lengthIsRange = false;
        } else {
            minLength = Integer.parseInt(lengthRange[0]);
            maxLength = Integer.parseInt(lengthRange[1]);
            lengthIsRange = true;
        }
    }

    public String id() {
        return this.id;
    }

    public String length() {
        return this.length;
    }

    public String name() {
        return this.name;
    }

    public FieldFormat format() {
        return this.format;
    }

    public FieldPresence presence() {
        return this.presence;
    }

    public List<QrCodeStandardField> adfs() {
        return this.adfs;
    }

    public static QrCodeStandardField fromJson(Map<String,Object> json) {
        return Builder
            .init()
            .setId((String)json.get("id"))
            .setLength((String)json.get("length"))
            .setFormat((String)json.get("format"))
            .setName((String)json.get("name"))
            .setPresence((String)json.get("presence"))
            .setAdfs(json.get("adfs"))
            .build();
    }

    public static List<QrCodeStandardField> listFromJson(List<Map<String,Object>> json) {
        return json.stream()
                .map(QrCodeStandardField::fromJson)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static class Builder implements IdStep, LengthStep, FormatStep, NameStep,PresenceStep , AdfsStep, BuildStandardField {
        private String id;
        private String length;
        private String name;
        private FieldFormat format;
        private FieldPresence presence;
        private List<QrCodeStandardField> adfs;

        private Builder(){}

        public static IdStep init() {
            return new Builder();
        }

        @Override
        public LengthStep setId(String id) {
            validateId(id);
            this.id = id;
            return this;
        }

        @Override
        public FormatStep setLength(String length) {
            validateLength(length);
            this.length = length;
            return this;
        }

        @Override
        public NameStep setFormat(String formatString) {
            if(formatString == null)
                throw new NullPointerException("Format Input Cannot Be Null");

            try {
                this.format = FieldFormat.valueOf(formatString);
            } catch (IllegalArgumentException e) {
                throw new InvalidJsonFormatException("Invalid Format Value of " + formatString);
            }

            return this;
        }

        @Override
        public PresenceStep setName(String name) {
            if(name == null)
                throw new NullPointerException("Name Input Cannot Be Null");
            this.name = name;
            return this;
        }

        @Override
        public AdfsStep setPresence(String presenceString) {
            if(presenceString == null)
                throw new NullPointerException("Presence Input Cannot Be Null");

            try {
                this.presence = FieldPresence.valueOf(presenceString);
            } catch (IllegalArgumentException e) {
                throw new InvalidJsonFormatException("Invalid Presence Value of " + presenceString);
            }

            return this;
        }
        @Override
        public Builder setAdfs(Object adfs) {
            if(adfs == null) {
                return this;
            }
            this.adfs = QrCodeStandardField.listFromJson((ArrayList<Map<String,Object>>) adfs);
            return this;
        }

        @Override
        public QrCodeStandardField build() {
            return new QrCodeStandardField(this);
        }
    }

    private static void validateLength(String length) {
        if(length == null)
            throw new NullPointerException("Length Input Cannot Be Null");
        try {
            var lenRange = length.split("-");
            for(String strLen: lenRange) {
                var intLen = Integer.parseInt(strLen);
                if(strLen.length() < 2 || intLen < 0 || intLen > 99)
                    throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new InvalidJsonFormatException("Length field Data Is Invalid");
        }
    }

    private static void validateId(String id) {
        if(id == null)
            throw new NullPointerException("Id Input Cannot Be Null");
        try {
            var idRange = id.split("-");
            for(String strId: idRange) {
                var intId = Integer.parseInt(strId);
                if(strId.length() < 2 || intId < 0 || intId > 99)
                    throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new InvalidJsonFormatException("ID field Data Is Invalid");
        }
    }

    public interface IdStep {
        LengthStep setId(String id);
    }

    public interface LengthStep {
        FormatStep setLength(String length);
    }

    public interface FormatStep {
        NameStep setFormat(String formatString);
    }

    public interface NameStep {
        PresenceStep setName(String name);
    }

    public interface PresenceStep {
        AdfsStep setPresence(String presenceString);
    }

    public interface AdfsStep {
        BuildStandardField setAdfs(Object adfs);
    }

    public interface BuildStandardField {
        QrCodeStandardField build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QrCodeStandardField that = (QrCodeStandardField) o;
        return id.equals(that.id) &&
                length.equals(that.length) &&
                name.equals(that.name) &&
                format == that.format &&
                presence == that.presence;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, length, name, format, presence);
    }
}