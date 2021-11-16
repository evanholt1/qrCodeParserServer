package com.example.demo;

import com.example.demo.interfaces.QrCodeStandardReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;

import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public Service service(QrCodeParser qrCodeParser) {
        return new Service(qrCodeParser);
    }

    @Bean
    public QrCodeParser qrCodeParser(CrcValidator crcValidator, QrCodeStandard qrCodeStandard) {
        return new QrCodeParser(crcValidator, qrCodeStandard);
    }

    @Bean
    public CrcValidator crcValidator() {
        return new CrcValidator();
    }

    @Bean
    public QrCodeStandard qrCodeStandard(QrCodeStandardReader qrCodeStandardReader) {
        return new QrCodeStandard(qrCodeStandardReader);
    }

    @Bean
    public QrCodeStandardReader qrCodeStandardReader() throws JsonProcessingException {
        var mapper = new ObjectMapper();
        List map = mapper.readValue("[\n" +
                "  {\n" +
                "    \"id\": \"00\",\n" +
                "    \"format\": \"NUM\",\n" +
                "    \"length\": \"02\",\n" +
                "    \"presence\": \"MANDATORY\",\n" +
                "    \"name\": \"id\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"01\",\n" +
                "    \"format\": \"NUM\",\n" +
                "    \"length\": \"02\",\n" +
                "    \"presence\": \"OPTIONAL\",\n" +
                "    \"name\": \"method\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"28\",\n" +
                "    \"format\": \"ANS\",\n" +
                "    \"length\":\"01-99\",\n" +
                "    \"presence\": \"OPTIONAL\",\n" +
                "    \"name\": \"Merchant Account Information\",\n" +
                "    \"adfs\": [\n" +
                "      {\n" +
                "        \"id\": \"00\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\":\"04\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"Merchant Switch GUID\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"01\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\":\"08\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"merchant acquirer id\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"02\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\":\"01-99\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"merchant account id\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"26\",\n" +
                "    \"format\": \"ANS\",\n" +
                "    \"length\":\"01-99\",\n" +
                "    \"presence\": \"OPTIONAL\",\n" +
                "    \"name\": \"Merchant Account Information\",\n" +
                "    \"adfs\": [\n" +
                "      {\n" +
                "        \"id\": \"00\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\":\"05\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"Merchant Switch GUID\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"01\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\":\"15\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"merchant acquirer id\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"02\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\":\"01-99\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"merchant account id\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"52\",\n" +
                "    \"format\": \"NUM\",\n" +
                "    \"length\": \"04\",\n" +
                "    \"presence\": \"MANDATORY\",\n" +
                "    \"name\": \"merchant category code\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"53\",\n" +
                "    \"format\": \"NUM\",\n" +
                "    \"length\": \"03\",\n" +
                "    \"presence\": \"MANDATORY\",\n" +
                "    \"name\": \"transaction currency\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"54\",\n" +
                "    \"format\": \"ANS\",\n" +
                "    \"length\": \"01-13\",\n" +
                "    \"presence\": \"OPTIONAL\",\n" +
                "    \"name\": \"transaction amount\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"55\",\n" +
                "    \"format\": \"NUM\",\n" +
                "    \"length\": \"02\",\n" +
                "    \"presence\": \"OPTIONAL\",\n" +
                "    \"name\": \"tip\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"56\",\n" +
                "    \"format\": \"ANS\",\n" +
                "    \"length\": \"01-13\",\n" +
                "    \"presence\": \"OPTIONAL\",\n" +
                "    \"name\": \"fixed convenience\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"57\",\n" +
                "    \"format\": \"ANS\",\n" +
                "    \"length\": \"01-05\",\n" +
                "    \"presence\": \"OPTIONAL\",\n" +
                "    \"name\": \"percentage convenience\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"58\",\n" +
                "    \"format\": \"ANS\",\n" +
                "    \"length\": \"02\",\n" +
                "    \"presence\": \"MANDATORY\",\n" +
                "    \"name\": \"country code\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"59\",\n" +
                "    \"format\": \"ANS\",\n" +
                "    \"length\": \"01-25\",\n" +
                "    \"presence\": \"MANDATORY\",\n" +
                "    \"name\": \"merchant name\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"60\",\n" +
                "    \"format\": \"ANS\",\n" +
                "    \"length\": \"01-15\",\n" +
                "    \"presence\": \"MANDATORY\",\n" +
                "    \"name\": \"merchant city\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"61\",\n" +
                "    \"format\": \"ANS\",\n" +
                "    \"length\": \"01-10\",\n" +
                "    \"presence\": \"OPTIONAL\",\n" +
                "    \"name\": \"postal code\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"80\",\n" +
                "    \"format\": \"STR\",\n" +
                "    \"length\": \"01-99\",\n" +
                "    \"presence\": \"OPTIONAL\",\n" +
                "    \"name\": \"Unreserved Templates\",\n" +
                "    \"adfs\": [\n" +
                "      {\n" +
                "        \"id\": \"00\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"04\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"qr date GUID\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"01\",\n" +
                "        \"format\": \"STR\",\n" +
                "        \"length\": \"01-99\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"qr date\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"81\",\n" +
                "    \"format\": \"STR\",\n" +
                "    \"length\": \"01-99\",\n" +
                "    \"presence\": \"OPTIONAL\",\n" +
                "    \"name\": \"Unreserved Templates\",\n" +
                "    \"adfs\": [\n" +
                "      {\n" +
                "        \"id\": \"00\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"04\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"verification code GUID\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"01\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"01-99\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"verification code PIN\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"62\",\n" +
                "    \"format\": \"STR\",\n" +
                "    \"length\": \"01-99\",\n" +
                "    \"presence\": \"OPTIONAL\",\n" +
                "    \"name\": \"Additional Data Field Template\",\n" +
                "    \"adfs\": [\n" +
                "      {\n" +
                "        \"id\": \"01\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"01-25\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"bill number\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"02\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"01-25\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"Mobile Number\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"03\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"01-25\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"store label\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"04\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"01-25\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"loyalty number\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"05\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"01-25\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"reference label\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"06\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"01-25\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"customer label\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"07\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"01-25\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"terminal number\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"08\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"01-25\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"purpose of transaction\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"09\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"02\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"additional customer data request\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"10\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"01-25\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"merchant tax information\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"11\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"01-10\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"merchant channel\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"59\",\n" +
                "        \"format\": \"STR\",\n" +
                "        \"length\": \"01-10\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"EMVco RFU\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"69\",\n" +
                "        \"format\": \"STR\",\n" +
                "        \"length\": \"01-10\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"Payment Specific\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"64\",\n" +
                "    \"format\": \"STR\",\n" +
                "    \"length\": \"01-99\",\n" +
                "    \"presence\": \"OPTIONAL\",\n" +
                "    \"name\": \"Merchant Information— Language Template\",\n" +
                "    \"adfs\": [\n" +
                "      {\n" +
                "        \"id\": \"00\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"02\",\n" +
                "        \"presence\": \"MANDATORY\",\n" +
                "        \"name\": \"Language Preference\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"01\",\n" +
                "        \"format\": \"STR\",\n" +
                "        \"length\": \"01-25\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"merchant name\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"02\",\n" +
                "        \"format\": \"ANS\",\n" +
                "        \"length\": \"01-15\",\n" +
                "        \"presence\": \"OPTIONAL\",\n" +
                "        \"name\": \"merchant city\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"63\",\n" +
                "    \"format\": \"ANS\",\n" +
                "    \"length\": \"04\",\n" +
                "    \"presence\": \"MANDATORY\",\n" +
                "    \"name\": \"cyclic dependency check\"\n" +
                "  }\n" +
                "]", List.class);
        return () -> QrCodeStandardField.listFromJson(map);
    }

}
