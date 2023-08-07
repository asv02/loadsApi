package com.TruckBooking.Booking.Model;

import java.util.List;

import com.TruckBooking.Booking.Entities.BookingData.Unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class BookingPutRequest {

	private Long rate;
	private Unit unitValue;
	private List<String> truckId;
	private String loadingPointCity;
	private String unloadingPointCity;
	private String truckNo;
	private String driverName;
	private String driverPhoneNum;
	private Boolean cancel;
	private Boolean completed;
	private String bookingDate;
	private String completedDate;

}
