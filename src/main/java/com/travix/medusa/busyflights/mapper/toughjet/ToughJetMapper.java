package com.travix.medusa.busyflights.mapper.toughjet;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;


public class ToughJetMapper {

    private static final String SUPPLIER_TOUGH_JET = "ToughJet";

    public static ToughJetRequest toToughJetRequest(BusyFlightsRequest request) {

        return ToughJetRequest.builder()
                .from(request.getOrigin())
                .to(request.getDestination())
                .outboundDate(request.getDepartureDate())
                .inboundDate(request.getReturnDate())
                .numberOfAdults(request.getNumberOfPassengers())
                .build();
    }

    public static BusyFlightsResponse toBusyFlightsResponse(ToughJetResponse response) {

        return BusyFlightsResponse.builder()
                .airline(response.getCarrier())
                .supplier(SUPPLIER_TOUGH_JET)
                .fare(calculateFare(response))
                .departureAirportCode(response.getDepartureAirportName())
                .destinationAirportCode(response.getArrivalAirportName())
                .departureDate(response.getOutboundDateTime()) // TODO: Convert ISO_INSTANT format to ISO_DATE_TIME format
                .arrivalDate(response.getInboundDateTime()) // TODO: Convert ISO_INSTANT format to ISO_DATE_TIME format
                .build();
    }

    // fare = base price + tax - discount
    private static  double calculateFare(ToughJetResponse response) {
        return response.getBasePrice() + response.getTax() - response.getDiscount();
    }
}
