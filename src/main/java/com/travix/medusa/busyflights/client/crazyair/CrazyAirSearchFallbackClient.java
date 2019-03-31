package com.travix.medusa.busyflights.client.crazyair;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class CrazyAirSearchFallbackClient implements CrazyAirSearchRestClient{

    @Value("${crazyairsearch.fallback:false}")
    public boolean alwaysTrue;

    @Override
    public ResponseEntity<List<CrazyAirResponse>> getCrazyAirSearchResult(CrazyAirRequest searchRequest) {
        //TODO: Return data from local cache/database if implemented.
        log.error("CrazyAirSearchFallbackClient is serving the request for {}", searchRequest);
        String now = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")));
        if(alwaysTrue) {
            return ResponseEntity.ok(
                    Arrays.asList(
                            CrazyAirResponse.builder()
                                    .airline("Dummy")
                                    .price(0.0)
                                    .cabinclass("Dummy")
                                    .departureAirportCode("DMY")
                                    .departureAirportCode("DMY")
                                    .departureDate(now)
                                    .arrivalDate(now)
                                    .build()
                    )
            );
        }
        return ResponseEntity.notFound().build();
    }
}
