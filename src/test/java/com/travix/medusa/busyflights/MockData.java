package com.travix.medusa.busyflights;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

import java.util.Arrays;
import java.util.List;

public class MockData {

    public static List<CrazyAirResponse> getCrazyAirResponseList() {
        return Arrays.asList(
                CrazyAirResponse.builder()
                        .airline("Air India")
                        .price(1000)
                        .cabinclass("Economy")
                        .departureAirportCode("BDQ")
                        .destinationAirportCode("AMS")
                        .departureDate("2019-03-31T09:30:15.312")
                        .arrivalDate("2019-03-31T20:30:15.312")
                        .build(),
                CrazyAirResponse.builder()
                        .airline("KLM")
                        .price(1500)
                        .cabinclass("Economy")
                        .departureAirportCode("BDQ")
                        .destinationAirportCode("AMS")
                        .departureDate("2019-03-31T10:00:15.312")
                        .arrivalDate("2019-03-31T21:45:15.312")
                        .build(),
                CrazyAirResponse.builder()
                        .airline("Qatar Airways")
                        .price(2000)
                        .cabinclass("Economy")
                        .departureAirportCode("BDQ")
                        .destinationAirportCode("AMS")
                        .departureDate("2019-03-31T11:15:15.312")
                        .arrivalDate("2019-03-31T23:30:45.312")
                        .build()
        );
    }

    public static List<ToughJetResponse> getToughJetResponseList() {
        return Arrays.asList(
                ToughJetResponse.builder()
                        .carrier("Lufthansa")
                        .basePrice(1400)
                        .tax(100)
                        .discount(150)
                        .departureAirportName("BDQ")
                        .arrivalAirportName("AMS")
                        .outboundDateTime("2019-03-31T08:05:23.653Z")
                        .inboundDateTime("2019-04-01T02:05:20.653Z")
                        .build(),
                ToughJetResponse.builder()
                        .carrier("Aeroflot")
                        .basePrice(1700)
                        .tax(140)
                        .discount(170)
                        .departureAirportName("BDQ")
                        .arrivalAirportName("AMS")
                        .outboundDateTime("2019-03-31T08:05:23.653Z")
                        .inboundDateTime("2019-04-01T02:05:20.653Z")
                        .build()
        );
    }

    public static String getExpectedJsonResponse() {

        return "[\n" +
                "    {\n" +
                "        \"airline\": \"Air India\",\n" +
                "        \"supplier\": \"CrazyAir\",\n" +
                "        \"fare\": 1000.0,\n" +
                "        \"departureAirportCode\": \"BDQ\",\n" +
                "        \"destinationAirportCode\": \"AMS\",\n" +
                "        \"departureDate\": \"2019-03-31T09:30:15.312\",\n" +
                "        \"arrivalDate\": \"2019-03-31T20:30:15.312\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"airline\": \"Lufthansa\",\n" +
                "        \"supplier\": \"ToughJet\",\n" +
                "        \"fare\": 1350.0,\n" +
                "        \"departureAirportCode\": \"BDQ\",\n" +
                "        \"destinationAirportCode\": \"AMS\",\n" +
                "        \"departureDate\": \"2019-03-31T08:05:23.653Z\",\n" +
                "        \"arrivalDate\": \"2019-04-01T02:05:20.653Z\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"airline\": \"KLM\",\n" +
                "        \"supplier\": \"CrazyAir\",\n" +
                "        \"fare\": 1500.0,\n" +
                "        \"departureAirportCode\": \"BDQ\",\n" +
                "        \"destinationAirportCode\": \"AMS\",\n" +
                "        \"departureDate\": \"2019-03-31T10:00:15.312\",\n" +
                "        \"arrivalDate\": \"2019-03-31T21:45:15.312\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"airline\": \"Aeroflot\",\n" +
                "        \"supplier\": \"ToughJet\",\n" +
                "        \"fare\": 1670.0,\n" +
                "        \"departureAirportCode\": \"BDQ\",\n" +
                "        \"destinationAirportCode\": \"AMS\",\n" +
                "        \"departureDate\": \"2019-03-31T08:05:23.653Z\",\n" +
                "        \"arrivalDate\": \"2019-04-01T02:05:20.653Z\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"airline\": \"Qatar Airways\",\n" +
                "        \"supplier\": \"CrazyAir\",\n" +
                "        \"fare\": 2000.0,\n" +
                "        \"departureAirportCode\": \"BDQ\",\n" +
                "        \"destinationAirportCode\": \"AMS\",\n" +
                "        \"departureDate\": \"2019-03-31T11:15:15.312\",\n" +
                "        \"arrivalDate\": \"2019-03-31T23:30:45.312\"\n" +
                "    }\n" +
                "]";
    }

}
