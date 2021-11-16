package com.example.demo;



import com.example.demo.enums.FieldPresence;
import com.example.demo.interfaces.QrCodeStandardReader;
import com.example.demo.QrCodeStandardField;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class QrCodeStandard {
    // TODO have this as immutable
    // TODO add a method to it to return a mutable version of it
    private final QrCodeStandardReader reader;

    // TODO accessible
    private final Map<String, QrCodeStandardField> fields;
    private Set<String> mandatoryFields;

    public Map<String, QrCodeStandardField> getFields() {
        return fields;
    }

    /**
     * Returns A Mutable Copy of MandatoryFields.
     */
    public Set<String> getMandatoryFieldsCopy() {
        //return Set.copyOf(mandatoryFields);
        return new HashSet<>(this.mandatoryFields);
    }

    public QrCodeStandard(QrCodeStandardReader reader) {
        if(reader == null)
            throw new NullPointerException("Reader Input Cannot Be Null");
        this.reader = reader;
        this.fields = new HashMap<>();


        this.setStandardFields();
    }

    private void setStandardFields() {
        Set<String> mandatoryFieldsSet = new HashSet<>();

        var standardList = reader.readStandard();

        for(QrCodeStandardField standard: standardList) {
            fields.put(standard.id(), standard);
            if(standard.presence() == FieldPresence.MANDATORY)
                mandatoryFieldsSet.add(standard.id());

            if(standard.adfs() != null && standard.adfs().size() > 0) {
                var prefix = standard.id() + "_";

                for(var adf: standard.adfs())  {
                    fields.put(prefix + adf.id(), adf);
                    if(adf.presence() == FieldPresence.MANDATORY)
                        mandatoryFieldsSet.add(prefix + adf.id());
                }
            }
        }

        this.mandatoryFields = Set.copyOf(mandatoryFieldsSet);
    }
}