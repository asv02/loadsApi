package com.TruckBooking.Booking.Constants;

public class BookingConstants {

	public static String pLoadIdIsNull = "Failed: LoadId cannot be Null";
	public static String pTransporterIdIsNull = "Failed: TransporterId cannot be Null";
	public static String pPostLoadIdIsNull = "Failed: postLoadId cannot be Null";
	public static String pTruckIdIsNull = "Failed: TruckId cannot be Null";
	public static String pDataExists = "Failed: Booking Data Already Exists with given LoadId and TransporterId";
	public static String pUnitIsNull = "Failed: Provide unitValue when rate is provided";
	public static String pUnknownUnit = "Failed: Cannot provide unknown unitValue";
	public static String pPostUnitRateIsNull = "Failed: Cannot Provide UnitValue when Rate is empty";
	public static String uDataNotFound = "Failed: Booking Data doesn't exists for given booking Id";
	public static String uTruckIdIsNull = "Failed: TruckId cannot be Empty after updating";
	public static String uCancelAndCompleteTrue = "Failed: Both Cancel and Completed Cannot be true Simultaneously";
	public static String uUnitIsNull = "Failed: Provide unitValue when rate is provided";
	public static String uUnknownUnit = "Failed: Cannot provide unknown unitValue";
	public static String uUpdateUnitRateIsNull = "Failed: Cannot Update UnitValue without providing rate";
	public static String uCanelIsTrueWhenCompleteIsTrue = "Failed: Cannot assign cancel true when Completed is already true";
	public static String uCompletedDateWhenCompletedIsNotTrue = "Failed: Cannot assign CompletedDate when completed is not true";
	public static String uAlreadyCompleted= "Failed: Cannot make complete false when booking is already completed";

	public static String success = "Success";
	public static String UPDATE_SUCCESS = "Updated Successfully";
	public static String ACCOUNT_NOT_FOUND = "Failed: Account not found";
	public static String DELETE_SUCCESS = "Deleted Successfuly";

	public static String BASE_URI = "http://localhost:8080/booking";

	// data
	public static String ID = "booking:492f08d9-132b-4d42-be47-bde1872775d7";
	public static String LOAD_ID = "load:a8924469-4783-4d0c-b827-dfc224d3a418";
	public static String TRANSPORTER_ID = "transporter:a3f10002-f80e-465f-bf00-ee8f1e69ab1b";
	public static String POST_LOAD_ID = "shipper:1b8a1a38-868a-4113-8749-ff63ac3abb55";
	public static String URI = "/booking";
	public static String ID_URI = "/booking/booking:492f08d9-132b-4d42-be47-bde1872775d7";
	public static String loadStatus_ongoing = "{\"status\":\"ON_GOING\"}";
	public static String loadStatus_completed = "{\"status\":\"COMPLETED\"}";
	public static int pageSize = 15;

}
