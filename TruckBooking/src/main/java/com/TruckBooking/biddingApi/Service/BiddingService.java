package com.TruckBooking.biddingApi.Service;

import java.util.List;

import com.TruckBooking.biddingApi.Entities.BiddingData;
import com.TruckBooking.biddingApi.Model.BidDeleteResponse;
import com.TruckBooking.biddingApi.Model.BidPostRequest;
import com.TruckBooking.biddingApi.Model.BidPostResponse;
import com.TruckBooking.biddingApi.Model.BidPutRequest;
import com.TruckBooking.biddingApi.Model.BidPutResponse;

public interface BiddingService {

	public BidPostResponse addBid(BidPostRequest bidPostRequest,String token);

	public List<BiddingData> getBid(Integer pageNo, String loadId, String transporterId,String token);

	public BidDeleteResponse deleteBid(String id,String token);

	public BiddingData getBidById(String id,String token);

	public BidPutResponse updateBid(String id, BidPutRequest bidPutRequest,String token);

}
