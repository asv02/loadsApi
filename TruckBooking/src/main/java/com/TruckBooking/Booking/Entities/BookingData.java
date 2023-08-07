package com.TruckBooking.Booking.Entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "UniqueTransporterAndLoad", columnNames = { "transporterId", "loadId" }) })
@NoArgsConstructor
@AllArgsConstructor
public @Data class BookingData {

	public enum Unit {
		PER_TON, PER_TRUCK
	}

	@Id
	private String bookingId;

	@NotBlank(message = "Transporter Id can not be null")
	private String transporterId;
	@NotBlank(message = "Load Id can not be null")
	private String loadId;
	@NotBlank(message = "PostLoadId Id can not be null")
	private String postLoadId;

	//adding new columns
	@NotBlank(message = "loadingPointCity can not be null")
	private String loadingPointCity;
	@NotBlank(message = "unloadingPointCity can not be null")
	private String unloadingPointCity;
	@NotBlank(message = "truckNo can not be null")
	private String truckNo;
	@NotBlank(message = "driverName can not be null")
	private String driverName;
	@NotBlank(message = "phoneNum can not be null")
	private String driverPhoneNum;
	@NotBlank(message = "deviceId can not be null")
	private String deviceId;

	private Long rate;
	private Unit unitValue;

	@Column(name = "truckIds")
	@ElementCollection(targetClass = String.class)
	@NotEmpty
	private List<String> truckId;

	private Boolean cancel;
	private Boolean completed;
	private String bookingDate;
	private String completedDate;

	@CreationTimestamp
	public Timestamp timestamp;

}
