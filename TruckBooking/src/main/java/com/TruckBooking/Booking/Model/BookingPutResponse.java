package com.TruckBooking.Booking.Model;

import java.util.List;

import com.TruckBooking.Booking.Entities.BookingData.Unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class BookingPutResponse {

	private String status;

	private String bookingId;
	private String transporterId;
	private String loadId;
	private String postLoadId;
	private String loadingPointCity;
	private String unloadingPointCity;
	private String truckNo;
	private String driverName;
	private String driverPhoneNum;
	private String deviceId;
	private Long rate;
	private Unit unitValue;
	private List<String> truckId;
	private Boolean cancel;
	private Boolean completed;
	private String bookingDate;
	private String completedDate;

}
