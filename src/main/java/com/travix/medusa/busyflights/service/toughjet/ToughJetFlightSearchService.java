package com.travix.medusa.busyflights.service.toughjet;

import com.travix.medusa.busyflights.client.crazyair.CrazyAirSearchRestClient;
import com.travix.medusa.busyflights.client.toughjet.ToughJetSearchRestClient;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.mapper.crazyair.CrazyAirMapper;
import com.travix.medusa.busyflights.mapper.toughjet.ToughJetMapper;
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
public class ToughJetFlightSearchService implements FlightSearchService {

    @Autowired
    private ToughJetSearchRestClient toughJetSearchRestClient;

    @Override
    public List<BusyFlightsResponse> searchFlights(BusyFlightsRequest request) {

        log.info("ToughJetFlightSearchService::searchFlights({})", request);

        return Optional.of(request)
                .map(ToughJetMapper::toToughJetRequest)
                .map(this::searchToughJetFlights)
                .orElseGet(ArrayList::new)
                .stream()
                .map(ToughJetMapper::toBusyFlightsResponse)
                .collect(Collectors.toList());
    }

    private List<ToughJetResponse> searchToughJetFlights(ToughJetRequest request) {

        log.debug("CrazyAirFlightSearchService::searchToughJetFlights({})", request);

        ResponseEntity<List<ToughJetResponse>> responseEntity = toughJetSearchRestClient.getToughJetSearchResult(request);

        if ( !responseEntity.getStatusCode().is2xxSuccessful() ) {
            log.error("CrazyAirFlightSearchService::searchToughJetFlights({}), responseCode {}", request, responseEntity.getStatusCode().getReasonPhrase());
            return null;
        }

        return responseEntity.getBody();
    }
}
