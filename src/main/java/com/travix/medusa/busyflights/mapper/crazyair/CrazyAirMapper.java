package com.travix.medusa.busyflights.mapper.crazyair;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;

public class CrazyAirMapper {

    private static final String SUPPLIER_CRAZY_AIR = "CrazyAir";

    public static CrazyAirRequest toCrazyAirRequest(BusyFlightsRequest request) {

        return CrazyAirRequest.builder()
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .departureDate(request.getDepartureDate())
                .returnDate(request.getReturnDate())
                .passengerCount(request.getNumberOfPassengers())
                .build();
    }

    public static BusyFlightsResponse toBusyFlightsResponse(CrazyAirResponse response) {

        return BusyFlightsResponse.builder()
                .airline(response.getAirline())
                .supplier(SUPPLIER_CRAZY_AIR)
                .fare(response.getPrice())
                .departureAirportCode(response.getDepartureAirportCode())
                .destinationAirportCode(response.getDestinationAirportCode())
                .departureDate(response.getDepartureDate()) // TODO: Convert ISO_LOCAL_DATE_TIME format to ISO_DATE_TIME format
                .arrivalDate(response.getArrivalDate()) // TODO: Convert ISO_LOCAL_DATE_TIME format to ISO_DATE_TIME format
                .build();
    }
}
