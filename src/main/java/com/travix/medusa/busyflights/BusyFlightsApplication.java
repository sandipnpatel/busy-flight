package com.travix.medusa.busyflights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// If you face compilation issues with lombok annotations in youe IDE, then please install lombok plugin.
@SpringBootApplication
public class BusyFlightsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusyFlightsApplication.class, args);
	}
}
