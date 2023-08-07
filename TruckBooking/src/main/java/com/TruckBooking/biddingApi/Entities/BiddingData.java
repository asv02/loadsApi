package com.TruckBooking.biddingApi.Entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "transporterId", "loadId" }) })
@Entity
public @Data class BiddingData {

	@Id
	private String bidId;

	@NotBlank(message = "Transporter Id can not be null")
	private String transporterId;
	@NotBlank(message = "Load Id can not be null")
	private String loadId;

	@NotNull(message = "Current Bid can not be null")
	private Long currentBid;

	private Long previousBid;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Unit can not be null")
	public Unit unitValue;

	public enum Unit {
		PER_TON, PER_TRUCK
	}

	@Column(name = "truckId")
	@ElementCollection(targetClass = String.class)
	private List<String> truckId;

	private Boolean transporterApproval;
	private Boolean shipperApproval;

	private String biddingDate;

	@CreationTimestamp
	public Timestamp timestamp;

}
