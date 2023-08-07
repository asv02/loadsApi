package com.TruckBooking.TruckBooking.Model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import com.TruckBooking.TruckBooking.Entities.Load.Publish;
import com.TruckBooking.TruckBooking.Entities.Load.Status;

import lombok.Data;

import java.util.ArrayList;

@Data
public class LoadRequest {

	@NotBlank(message = "Loading Point Cannot Be Empty")
	private String loadingPoint;
	@NotBlank(message = "Loading Point City Cannot Be Empty")
	private String loadingPointCity;
	@NotBlank(message = "Loading Point State Cannot Be Empty")
	private String loadingPointState;
	@NotBlank(message = "PostLoad Id Cannot Be Empty")
	private String postLoadId;
	@NotBlank(message = "Unloading Point Cannot Be Empty")
	private String unloadingPoint;
	@NotBlank(message = "Unloading Point City Cannot Be Empty")
	private String unloadingPointCity;
	@NotBlank(message = "Unloading Point State Cannot Be Empty")
	private String unloadingPointState;

	private String loadingPoint2;		//optional
	private String loadingPointCity2;	//optional
	private String loadingPointState2;	//optional
	private String unloadingPoint2;		//optional
	private String unloadingPointCity2;  //optional
	private String unloadingPointState2; //optional

	private ArrayList<ArrayList<String>> transporterList;
	@NotBlank(message = "Product Type Cannot Be Empty")
	private String productType;
	@NotBlank(message = "Truck Type Cannot Be Empty")
	private String truckType;
	@NotBlank(message = "Weight Cannot Be Empty")
	private String weight;
	
	private String noOfTrucks;
	private String noOfTyres;
	private String LR; // optional
	
	private String comment; // this should be an optional
	private String loadingDate;
	private String publishMethod;
	private String loadingTime;
	private Long rate;

	@Enumerated(EnumType.STRING)
	private UnitValue unitValue;

	public enum UnitValue {
		PER_TON, PER_TRUCK
	}

	public Status status;
	public Publish publish;
}
