package com.travix.medusa.busyflights.client.toughjet;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "${toughjetsearch.service.name:toughjetservice-v1}",
        url = "${toughjetsearch.service.url:http://toughjetservice-v1:8080}",
        fallback = ToughJetSearchFallbackClient.class)
public interface ToughJetSearchRestClient {

    @GetMapping(value = "/toughjet/flight/search")
    ResponseEntity<List<ToughJetResponse>> getToughJetSearchResult(
            @RequestBody ToughJetRequest searchRequest);
}
