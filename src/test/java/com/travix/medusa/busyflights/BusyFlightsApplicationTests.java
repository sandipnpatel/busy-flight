package com.travix.medusa.busyflights;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.client.crazyair.CrazyAirSearchRestClient;
import com.travix.medusa.busyflights.client.toughjet.ToughJetSearchRestClient;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusyFlightsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BusyFlightsApplicationTests {

	@LocalServerPort
	private Integer port;

	@MockBean
	private CrazyAirSearchRestClient crazyAirSearchRestClient;

	@MockBean
	private ToughJetSearchRestClient toughJetSearchRestClient;

	private ObjectMapper mapper = new ObjectMapper();

/*
	@Test
	public void contextLoads() {
	}
*/

	@Test
	public void searchFlights_respond200_when_flightsAvailable() {

		when(crazyAirSearchRestClient.getCrazyAirSearchResult(any(CrazyAirRequest.class)))
				.thenReturn(ResponseEntity.ok().body(MockData.getCrazyAirResponseList()));

		when(toughJetSearchRestClient.getToughJetSearchResult(any(ToughJetRequest.class)))
				.thenReturn(ResponseEntity.ok().body(MockData.getToughJetResponseList()));

		String jsonResponse = given()
				.port(port)
				.queryParam("origin", "BDQ")
				.queryParam("destination", "AMS")
				.queryParam("departureDate", "2019-03-31")
				.queryParam("returnDate", "2019-04-31")
				.queryParam("numberOfPassengers", "2")
				.when()
				.get("/busyflights/search")
				.then()
				.statusCode(200)
				.log().all()
				.extract()
				.response()
				.asString();

		try {
			final JsonNode actual = mapper.readTree(jsonResponse);
			final JsonNode expected = mapper.readTree(MockData.getExpectedJsonResponse());

			assertThat(actual, is(equalTo(expected)));
		} catch (IOException e) {
			log.error("Found Error", e);
			fail();
		}
	}


	@Test
	public void searchFlights_respond404_when_noFlightAvailable() {

		when(crazyAirSearchRestClient.getCrazyAirSearchResult(any(CrazyAirRequest.class))).thenReturn(ResponseEntity.notFound().build());

		when(toughJetSearchRestClient.getToughJetSearchResult(any(ToughJetRequest.class))).thenReturn(ResponseEntity.notFound().build());

		given()
				.port(port)
				.queryParam("origin", "CDG")
				.queryParam("destination", "AMS")
				.queryParam("departureDate", "2019-03-31")
				.queryParam("returnDate", "2019-04-31")
				.queryParam("numberOfPassengers", "4")
				.when()
				.get("/busyflights/search")
				.then()
				.statusCode(404)
				.log().all();
	}
	
	@Test
	public void searchFlights_respond400_when_invalidQueryParam() {

		given()
				.port(port)
				.queryParam("origin", "CDG")
				.queryParam("destination", "AMS")
				.queryParam("departureDate", "2019-03-31")
				.queryParam("returnDate", "2019-04-31")
				.queryParam("numberOfPassengers", "5")// Invalid
				.when()
				.get("/busyflights/search")
				.then()
				.statusCode(400)
				.log().all();
	}

}
