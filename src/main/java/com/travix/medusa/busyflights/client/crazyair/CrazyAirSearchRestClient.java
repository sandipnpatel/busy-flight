package com.travix.medusa.busyflights.client.crazyair;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "${crazyairsearch.service.name:crazyairservice-v1}",
        url = "${crazyairsearch.service.url:http://crazyairservice-v1:8080}",
        fallback = CrazyAirSearchFallbackClient.class)
public interface CrazyAirSearchRestClient {


    @GetMapping(value = "/crazyair/flight/search")
    ResponseEntity<List<CrazyAirResponse>> getCrazyAirSearchResult(
            @RequestBody CrazyAirRequest searchRequest);
}

