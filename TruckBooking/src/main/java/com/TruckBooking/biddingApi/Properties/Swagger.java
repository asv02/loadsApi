package com.TruckBooking.biddingApi.Properties;

import java.util.Collections;

import org.springframework.context.annotation.Bean;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class Swagger {
	@Bean
	public Docket swaggerConfiguration() {
		// return a prepared socket instance
		return new Docket(DocumentationType.SWAGGER_2).select().build().apiInfo(apiDetails());

	}

	// url: http://localhost:8080/v2/api-docs
	// swaggerUI: http://localhost:8080/swagger-ui/

	private ApiInfo apiDetails() {

		return new ApiInfo("Bidding Api", "Api for Bids", "1.0", "Easy to use",
				new springfox.documentation.service.Contact("Pranav Gupta",
						"https://play.google.com/store/apps/details?id=com.chirag4601.new_truck_booking_app",
						"liveasy97@gmail.com"),
				"API License", "null", Collections.EMPTY_LIST);
	}

}
