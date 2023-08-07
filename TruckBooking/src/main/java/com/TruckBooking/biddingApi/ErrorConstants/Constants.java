package com.TruckBooking.biddingApi.ErrorConstants;

public class Constants {

	public static String success = "Success";
	public static String pLoadIdAndTransPorterIdAreNull = "Failed: TransporterId and LoadId both cannot be null simulataneously";
	public static String pLoadIdIsNull = "Failed: LoadId cannot be null";

	public static String pBidExists = "Failed: LoadId already exists, where transporterId may be null or not";
	public static String pLoadIdAndTransporterIdExists = "Failed: LoadId is already associated with TransporterId";
	public static String dataSavingExcetion = "Failed: Problem with data integration";
	public static String uTransportIdIsNullTruckUpdate = "Failed: Cannot update TruckId's where TransporterId is Null";
	public static String TRUCK_ID_UPDATE_BY_SHIPPER = "Failed: Cannot update TruckId by shipper";
	public static String uDataNotExists = "Failed: Data doesn't exists for given Id";
	public static String uBothApproval = "Failed: Cannot update both Shipper and Transporter Approval";
	public static String uTApprovalWhereTidIsNull = "Failed: Cannot update Transporter Approval where TransporterId is Null";
	public static String uSuccess = "Updated Succcessfully";
	public static String dSuccess = "bid-Account successfully deleted";
	public static String dDataNotExists = "Failed: Data doesn't exists for given Id";
	public static String unitValueisNull = "Failed: unitValue cannot be null when Current Bid is not Null";
	public static String TRANSPORTER_ID_NULL = "Failed: TransporterId is null";
	public static String CURRENT_BID_NULL = "Failed: Current Bid is null";
	public static String UnknownUnit = "Failed: Cannot provide unknown unitValue";
	public static String TRANSPORTER_SHIPPER_APPROVAL_NULL = "Failed: Atleast one approval is mandatory for updating bid";
	public static String TRANSPORTER_SHIPPER_APPROVAL_NOT_NULL = "Failed: Can't update bid by both Shipper and Transporter simulataneously";

	// bidding data
	public static String ID = "bid:12345";
	// public static String TRANSPORTER_ID =
	// "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67";
	public static String URI = "/bid";
	public static String BID_ID_URI = "/bid/bid:12345";
	public static String LOAD_ID = "load:123";
	public static String TRANSPORTER_ID = "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69";

	public static String BASE_URI = "http://localhost:8080/bid";

	public static final int pageSize = 15;

}
