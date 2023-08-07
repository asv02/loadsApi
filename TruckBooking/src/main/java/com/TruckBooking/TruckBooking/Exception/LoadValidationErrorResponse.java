package com.TruckBooking.TruckBooking.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class LoadValidationErrorResponse extends LoadSubErrorResponse {
	private String object;
	private String field;
	private Object rejectedValue;
	private String message;

	LoadValidationErrorResponse(String object, String message) {
		this.object = object;
		this.message = message;
	}
}
