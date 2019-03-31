package com.travix.medusa.busyflights.client.toughjet;


import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
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
public class ToughJetSearchFallbackClient implements ToughJetSearchRestClient{

    @Value("${toughjetsearch.fallback:false}")
    public boolean alwaysTrue;

    @Override
    public ResponseEntity<List<ToughJetResponse>> getToughJetSearchResult(ToughJetRequest searchRequest) {
        //TODO: Return data from local cache/database if implemented.
        log.error("CrazyAirSearchFallbackClient is serving the request for {}", searchRequest);
        String now = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")));
        if(alwaysTrue) {
            return ResponseEntity.ok(
                    Arrays.asList(
                            ToughJetResponse.builder()
                                    .carrier("Dummy")
                                    .basePrice(0.0)
                                    .tax(0.0)
                                    .discount(0.0)
                                    .departureAirportName("DMY")
                                    .arrivalAirportName("DMY")
                                    .outboundDateTime(now)
                                    .inboundDateTime(now)
                                    .build()
                    )
            );
        }
        return ResponseEntity.notFound().build();
    }
}

