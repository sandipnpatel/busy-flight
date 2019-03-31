package com.travix.medusa.busyflights.service.crazyair;

import com.travix.medusa.busyflights.client.crazyair.CrazyAirSearchRestClient;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.mapper.crazyair.CrazyAirMapper;
import com.travix.medusa.busyflights.service.FlightSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrazyAirFlightSearchService implements FlightSearchService {

    @Autowired
    private CrazyAirSearchRestClient crazyAirSearchRestClient;

    @Override
    public List<BusyFlightsResponse> searchFlights(BusyFlightsRequest request) {

        log.info("CrazyAirFlightSearchService::searchFlights({})", request);

        return Optional.of(request)
                .map(CrazyAirMapper::toCrazyAirRequest)
                .map(this::searchCrazyAirFlights)
                .orElseGet(ArrayList::new)
                .stream()
                .map(CrazyAirMapper::toBusyFlightsResponse)
                .collect(Collectors.toList());
    }

    private List<CrazyAirResponse> searchCrazyAirFlights(CrazyAirRequest request) {

        log.debug("CrazyAirFlightSearchService::searchCrazyAirFlights({})", request);

        ResponseEntity<List<CrazyAirResponse>> responseEntity = crazyAirSearchRestClient.getCrazyAirSearchResult(request);

        if ( !responseEntity.getStatusCode().is2xxSuccessful() ) {
            log.error("CrazyAirFlightSearchService::searchCrazyAirFlights({}), responseCode {}", request, responseEntity.getStatusCode().getReasonPhrase());
            return null;
        }

        return responseEntity.getBody();
    }
}
