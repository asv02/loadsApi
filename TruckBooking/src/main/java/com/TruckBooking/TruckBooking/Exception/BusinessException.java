package com.TruckBooking.TruckBooking.Exception;

public class BusinessException extends RuntimeException {

	public BusinessException(String s) {
		super(BusinessException.generateMessage(s));
	}

	private static String generateMessage(String s) {
		return "Error" + s;
	}
}
