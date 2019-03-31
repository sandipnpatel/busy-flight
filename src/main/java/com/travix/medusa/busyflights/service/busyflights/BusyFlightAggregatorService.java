package com.travix.medusa.busyflights.service.busyflights;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.FlightSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BusyFlightAggregatorService {

    private List<FlightSearchService> flightSearchServices;

    // Load All Implementors of FlightSearchService Interface into List
    // In this case, we have two implementations
    // 1. CrazyAirFlightSearchService
    // 2. ToughJetFlightSearchService
    @Autowired
    public BusyFlightAggregatorService(List<FlightSearchService> flightSearchServices) {
        this.flightSearchServices = flightSearchServices;
    }

    public List<BusyFlightsResponse> searchFlights(BusyFlightsRequest request) {

        log.info("BusyFlightAggregatorService::searchFlights({})", request);

        List<CompletableFuture<List<BusyFlightsResponse>>> searchFlightsFutures = flightSearchServices
                .stream()
                .map(searchService -> searchFlightsFuture(searchService, request))
                .collect(Collectors.toList());

        List<List<BusyFlightsResponse>> searchResults = searchFlightsFutures
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        return Optional.ofNullable(searchResults)
                .orElseGet(ArrayList::new)
                .stream()
                .flatMap( results -> Optional.ofNullable(results).orElseGet(ArrayList::new).stream() )
                .sorted(Comparator.comparing(BusyFlightsResponse::getFare))// Sorted in order of fare
                .collect(Collectors.toList());
    }

    private CompletableFuture<List<BusyFlightsResponse>> searchFlightsFuture(
            FlightSearchService flightSearchService, BusyFlightsRequest request){

        return CompletableFuture.supplyAsync( () -> flightSearchService.searchFlights(request) );
    }
}
