package com.TruckBooking.routeData.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class RouteDataValidationErrorResponse extends RouteDataSubErrorResponse {
	private String object;
	private String field;
	private Object rejectedValue;
	private String message;

	RouteDataValidationErrorResponse(String object, String message) {
		this.object = object;
		this.message = message;
	}
}
