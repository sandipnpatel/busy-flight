package com.travix.medusa.busyflights.controller.busyflights;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.busyflights.BusyFlightAggregatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/busyflights")
@Validated
public class BusyFlightsSearchController {

    @Autowired
    private BusyFlightAggregatorService busyFlightAggregatorService;

    @GetMapping(value = "/search")
    public ResponseEntity<List<BusyFlightsResponse>> searchFlights(
            @RequestParam @Size(min = 3, max = 3) String origin,
            @RequestParam @Size(min = 3, max = 3) String destination,
            @RequestParam String departureDate,
            @RequestParam String returnDate,
            @RequestParam @Min(1) @Max(4) Integer numberOfPassengers) {

        BusyFlightsRequest request = BusyFlightsRequest.builder()
                .origin(origin)
                .destination(destination)
                .departureDate(departureDate)
                .returnDate(returnDate)
                .numberOfPassengers(numberOfPassengers)
                .build();
        List<BusyFlightsResponse> response = busyFlightAggregatorService.searchFlights(request);

        if (response == null || response.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("Validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
