package com.TruckBooking.biddingApi.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.TruckBooking.biddingApi.Dao.BiddingDao;
import com.TruckBooking.biddingApi.Entities.BiddingData;
import com.TruckBooking.biddingApi.ErrorConstants.Constants;
import com.TruckBooking.biddingApi.Exception.BusinessException;
import com.TruckBooking.biddingApi.Exception.EntityNotFoundException;
import com.TruckBooking.biddingApi.Model.BidDeleteResponse;
import com.TruckBooking.biddingApi.Model.BidPostRequest;
import com.TruckBooking.biddingApi.Model.BidPostResponse;
import com.TruckBooking.biddingApi.Model.BidPutRequest;
import com.TruckBooking.biddingApi.Model.BidPutResponse;
import com.TruckBooking.biddingApi.Util.JwtUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BiddingServiceImpl implements BiddingService {

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	private BiddingDao biddingDao;

	@Override
	public BidPostResponse addBid(BidPostRequest request,String token) {

		BiddingData data = new BiddingData();
		BidPostResponse response = new BidPostResponse();

		String id = "bid:" + UUID.randomUUID();

		data.setBidId(id);

		data.setTransporterId(request.getTransporterId());
		data.setLoadId(request.getLoadId());
		data.setCurrentBid(request.getCurrentBid());

		if ("PER_TON".equals(String.valueOf(request.getUnitValue()))) {
			data.setUnitValue(BiddingData.Unit.PER_TON);
		} else if (String.valueOf(request.getUnitValue()).equals("PER_TRUCK")) {
			data.setUnitValue(BiddingData.Unit.PER_TRUCK);
		} else {
			log.error(Constants.UnknownUnit);
			throw new BusinessException(Constants.UnknownUnit);

		}

		if (request.getBiddingDate() != null) {
			data.setBiddingDate(request.getBiddingDate());
		}

		if (request.getTruckId() != null) {
			data.setTruckId(request.getTruckId());
		}

		data.setTransporterApproval(true);
		data.setShipperApproval(false);

		try {
			biddingDao.save(data);
			log.info("Bidding Data is saved");
		} catch (Exception ex) {
			log.error("Bidding Data is not saved -----" + String.valueOf(ex));
			throw ex;
		}

		response.setStatus(Constants.success);
		response.setBidId(id);
		response.setLoadId(data.getLoadId());
		response.setCurrentBid(data.getCurrentBid());
		response.setTransporterId(data.getTransporterId());
		response.setShipperApproval(data.getShipperApproval());
		response.setTransporterApproval(data.getTransporterApproval());
		response.setTruckId(data.getTruckId());
		response.setUnitValue(data.getUnitValue());
		response.setBiddingDate(data.getBiddingDate());

		try {
			log.info("Post Service Response returned");

			return response;
		} catch (Exception ex) {
			log.error("Post Service Response not returned -----" + String.valueOf(ex));
			throw ex;

		}
	}

	@Override
	public List<BiddingData> getBid(Integer pageNo, String loadId, String transporterId,String token) {
		// TODO Auto-generated method stub

		//		List<BiddingData> list = null;
		if (pageNo == null)
			pageNo = 0;

		if (loadId != null && transporterId == null) {


			try {
				Pageable page = PageRequest.of(pageNo, Constants.pageSize, Sort.Direction.DESC, "timestamp");
				//				list = biddingDao.findByLoadId(loadId, page);
				//				Collections.reverse(list);
				log.info("Bidding Data with params returned");
				return biddingDao.findByLoadId(loadId, page);
			} catch (Exception ex) {
				log.error("Bidding Data with params not returned -----" + String.valueOf(ex));
				throw ex;
			}

		} else if (loadId == null && transporterId != null) {

			try {
				Pageable page = PageRequest.of(pageNo, Constants.pageSize, Sort.Direction.DESC, "timestamp");
				//				list = biddingDao.findByTransporterId(transporterId, page);
				//				Collections.reverse(list);
				//				log.info("Bidding Data with params returned");
				return biddingDao.findByTransporterId(transporterId, page);
			} catch (Exception ex) {
				log.error("Bidding Data with params not returned -----" + String.valueOf(ex));
				throw ex;
			}

		} else if (loadId != null && transporterId != null) {

			try {
				Pageable page = PageRequest.of(pageNo, Constants.pageSize, Sort.Direction.DESC, "timestamp");
				//				list = biddingDao.findByLoadIdAndTransporterId(loadId, transporterId, page);
				//				Collections.reverse(list);
				log.info("Bidding Data with params returned");
				return biddingDao.findByLoadIdAndTransporterId(loadId, transporterId, page);
			} catch (Exception ex) {
				log.error("Bidding Data with params not returned -----" + String.valueOf(ex));
				throw ex;
			}

		} else {

			try {
				Pageable page = PageRequest.of(pageNo, Constants.pageSize, Sort.Direction.DESC, "timestamp");
				//				list = biddingDao.getAll(p);
				//				Collections.reverse(list);
				log.info("Bidding Data get all returned");
				return biddingDao.getAll(page);
			} catch (Exception ex) {
				log.error("Bidding Data get all not returned -----" + String.valueOf(ex));
				throw ex;
			}

		}
	}

	@Override
	public BidDeleteResponse deleteBid(String id,String token) {

		BidDeleteResponse response = new BidDeleteResponse();

		Optional<BiddingData> temp = (biddingDao.findById(id));

		if (temp.isEmpty()) {
			EntityNotFoundException ex = new EntityNotFoundException(BiddingData.class, "bidId", id.toString());
			log.error(String.valueOf(ex));
			throw ex;
		}
		try {
			biddingDao.deleteById(id);
			log.info("Deleted");
		} catch (Exception ex) {
			log.error(String.valueOf(ex));
			throw ex;

		}

		response.setStatus(Constants.dSuccess);

		try {
			log.info("Deleted Service Response returned");
			return response;
		} catch (Exception ex) {
			log.error("Deleted Service Response not returned -----" + String.valueOf(ex));
			throw ex;

		}
	}

	@Override
	public BiddingData getBidById(String id,String token) {
		Optional<BiddingData> temp = (biddingDao.findById(id));

		if (temp.isEmpty()) {
			EntityNotFoundException ex = new EntityNotFoundException(BiddingData.class, "bidId", id.toString());
			log.error(String.valueOf(ex));
			throw ex;
		}

		try {
			log.info("Bidding Data returned");
			return temp.orElse(null);
		} catch (Exception ex) {
			log.error("Bidding Data not returned -----" + String.valueOf(ex));
			throw ex;

		}
	}

	@Override
	public BidPutResponse updateBid(String id, BidPutRequest bidPutRequest,String token) {

		BidPutResponse response = new BidPutResponse();
		BiddingData data = biddingDao.findById(id).orElse(null);

		if (data == null) {
			EntityNotFoundException ex = new EntityNotFoundException(BiddingData.class, "bidId", id.toString());
			log.error(String.valueOf(ex));
			throw ex;

		}

		if (String.valueOf(bidPutRequest.getTransporterApproval()).equals("true")
				&& String.valueOf(bidPutRequest.getShipperApproval()).equals("null")) {
			if (bidPutRequest.getCurrentBid() != null) {

				if (bidPutRequest.getUnitValue() == null) {
					log.error(Constants.unitValueisNull);
					throw new BusinessException(Constants.unitValueisNull);

				} else {

					if ("PER_TON".equals(String.valueOf(bidPutRequest.getUnitValue()))) {
						data.setUnitValue(BiddingData.Unit.PER_TON);
					} else if (String.valueOf(bidPutRequest.getUnitValue()).equals("PER_TRUCK")) {
						data.setUnitValue(BiddingData.Unit.PER_TRUCK);
					} else {
						log.error(Constants.UnknownUnit);
						throw new BusinessException(Constants.UnknownUnit);

					}
					data.setPreviousBid(data.getCurrentBid());
					data.setCurrentBid(bidPutRequest.getCurrentBid());
					if (bidPutRequest.getBiddingDate() != null) {
						data.setBiddingDate(bidPutRequest.getBiddingDate());
					}

					if (bidPutRequest.getTruckId() != null) {
						data.setTruckId(bidPutRequest.getTruckId());
					}

					data.setTransporterApproval(true);
					data.setShipperApproval(false);

					try {
						biddingDao.save(data);
						log.info("Bidding Data is updated");
					} catch (Exception ex) {
						log.error("Bidding Data is not updated -----" + String.valueOf(ex));
						throw ex;
					}

					response.setStatus(Constants.uSuccess);
					response.setBidId(id);
					response.setLoadId(data.getLoadId());
					response.setCurrentBid(data.getCurrentBid());
					response.setPreviousBid(data.getPreviousBid());
					response.setTransporterId(data.getTransporterId());
					response.setShipperApproval(data.getShipperApproval());
					response.setTransporterApproval(data.getTransporterApproval());
					response.setTruckId(data.getTruckId());
					response.setUnitValue(data.getUnitValue());
					response.setBiddingDate(data.getBiddingDate());

					try {
						log.info("Put Service Response returned");
						return response;
					} catch (Exception ex) {
						log.error("Put Service Response not returned -----" + String.valueOf(ex));
						throw ex;

					}
				}

				// accept by transporter
			} else if (bidPutRequest.getCurrentBid() == null && data.getShipperApproval() == true) {

				data.setTransporterApproval(true);

				biddingDao.save(data);

				response.setStatus(Constants.uSuccess);
				response.setBidId(id);
				response.setLoadId(data.getLoadId());
				response.setCurrentBid(data.getCurrentBid());
				response.setPreviousBid(data.getPreviousBid());
				response.setTransporterId(data.getTransporterId());
				response.setShipperApproval(data.getShipperApproval());
				response.setTransporterApproval(data.getTransporterApproval());
				response.setTruckId(data.getTruckId());
				response.setUnitValue(data.getUnitValue());
				response.setBiddingDate(data.getBiddingDate());

				try {
					log.info("Put Service Response returned");
					return response;
				} catch (Exception ex) {
					log.error("Put Service Response not returned -----" + String.valueOf(ex));
					throw ex;

				}

			}

		} // update from shipper
		else if (String.valueOf(bidPutRequest.getShipperApproval()).equals("true")
				&& String.valueOf(bidPutRequest.getTransporterApproval()).equals("null")) {

			if (bidPutRequest.getCurrentBid() != null) {
				if (bidPutRequest.getUnitValue() == null) {
					log.error(Constants.unitValueisNull);
					throw new BusinessException(Constants.unitValueisNull);

				} else {
					if ("PER_TON".equals(String.valueOf(bidPutRequest.getUnitValue()))) {
						data.setUnitValue(BiddingData.Unit.PER_TON);
					} else if (String.valueOf(bidPutRequest.getUnitValue()).equals("PER_TRUCK")) {
						data.setUnitValue(BiddingData.Unit.PER_TRUCK);
					} else {
						log.error(Constants.UnknownUnit);
						throw new BusinessException(Constants.UnknownUnit);

					}
					data.setPreviousBid(data.getCurrentBid());
					data.setCurrentBid(bidPutRequest.getCurrentBid());
					if (bidPutRequest.getBiddingDate() != null) {
						data.setBiddingDate(bidPutRequest.getBiddingDate());
					}

					if (bidPutRequest.getTruckId() != null) {
						log.error(Constants.TRUCK_ID_UPDATE_BY_SHIPPER);
						throw new BusinessException(Constants.TRUCK_ID_UPDATE_BY_SHIPPER);

					}
					data.setShipperApproval(true);
					data.setTransporterApproval(false);

					try {
						biddingDao.save(data);
						log.info("Bidding Data is updated");
					} catch (Exception ex) {
						log.error("Bidding Data is not updated -----" + String.valueOf(ex));
						throw ex;
					}

					response.setStatus(Constants.uSuccess);
					response.setBidId(id);
					response.setLoadId(data.getLoadId());
					response.setCurrentBid(data.getCurrentBid());
					response.setPreviousBid(data.getPreviousBid());
					response.setTransporterId(data.getTransporterId());
					response.setShipperApproval(data.getShipperApproval());
					response.setTransporterApproval(data.getTransporterApproval());
					response.setTruckId(data.getTruckId());
					response.setUnitValue(data.getUnitValue());
					response.setBiddingDate(data.getBiddingDate());

					try {
						log.info("Put Service Response returned");
						return response;
					} catch (Exception ex) {
						log.error("Put Service Response not returned -----" + String.valueOf(ex));
						throw ex;

					}
				}
				// accept by shipper
			} else if (bidPutRequest.getCurrentBid() == null && data.getTransporterApproval() == true) {

				data.setShipperApproval(true);

				try {
					biddingDao.save(data);
					log.info("Bidding Data is updated");
				} catch (Exception ex) {
					log.error("Bidding Data is not updated -----" + String.valueOf(ex));
					throw ex;
				}

				response.setStatus(Constants.uSuccess);
				response.setBidId(id);
				response.setLoadId(data.getLoadId());
				response.setCurrentBid(data.getCurrentBid());
				response.setPreviousBid(data.getPreviousBid());
				response.setTransporterId(data.getTransporterId());
				response.setShipperApproval(data.getShipperApproval());
				response.setTransporterApproval(data.getTransporterApproval());
				response.setTruckId(data.getTruckId());
				response.setUnitValue(data.getUnitValue());
				response.setBiddingDate(data.getBiddingDate());

				try {
					log.info("Put Service Response returned");
					return response;
				} catch (Exception ex) {
					log.error("Put Service Response not returned -----" + String.valueOf(ex));
					throw ex;

				}
			}

		} else if (String.valueOf(bidPutRequest.getShipperApproval()).equals("null")
				&& String.valueOf(bidPutRequest.getTransporterApproval()).equals("null")) {

			log.error(Constants.TRANSPORTER_SHIPPER_APPROVAL_NULL);
			throw new BusinessException(Constants.TRANSPORTER_SHIPPER_APPROVAL_NULL);

		} else if (!String.valueOf(bidPutRequest.getShipperApproval()).equals("null")
				&& !String.valueOf(bidPutRequest.getTransporterApproval()).equals("null")) {

			log.error(Constants.TRANSPORTER_SHIPPER_APPROVAL_NOT_NULL);
			throw new BusinessException(Constants.TRANSPORTER_SHIPPER_APPROVAL_NOT_NULL);

		} else if (String.valueOf(bidPutRequest.getShipperApproval()).equals("false")
				&& String.valueOf(bidPutRequest.getTransporterApproval()).equals("null")) {

			data.setShipperApproval(false);
			data.setTransporterApproval(false);

			try {
				biddingDao.save(data);
				log.info("Bidding Data is updated");
			} catch (Exception ex) {
				log.error("Bidding Data is not updated -----" + String.valueOf(ex));
				throw ex;
			}

			response.setStatus(Constants.uSuccess);
			response.setBidId(id);
			response.setLoadId(data.getLoadId());
			response.setCurrentBid(data.getCurrentBid());
			response.setPreviousBid(data.getPreviousBid());
			response.setTransporterId(data.getTransporterId());
			response.setShipperApproval(data.getShipperApproval());
			response.setTransporterApproval(data.getTransporterApproval());
			response.setTruckId(data.getTruckId());
			response.setUnitValue(data.getUnitValue());
			response.setBiddingDate(data.getBiddingDate());

			try {
				log.info("Put Service Response returned");
				return response;
			} catch (Exception ex) {
				log.error("Put Service Response not returned -----" + String.valueOf(ex));
				throw ex;

			}
		}

		else if (String.valueOf(bidPutRequest.getShipperApproval()).equals("null")
				&& String.valueOf(bidPutRequest.getTransporterApproval()).equals("false")) {

			data.setShipperApproval(false);
			data.setTransporterApproval(false);
			try {
				biddingDao.save(data);
				log.info("Bidding Data is updated");
			} catch (Exception ex) {
				log.error("Bidding Data is not updated -----" + String.valueOf(ex));
				throw ex;
			}

			response.setStatus(Constants.uSuccess);
			response.setBidId(id);
			response.setLoadId(data.getLoadId());
			response.setCurrentBid(data.getCurrentBid());
			response.setPreviousBid(data.getPreviousBid());
			response.setTransporterId(data.getTransporterId());
			response.setShipperApproval(data.getShipperApproval());
			response.setTransporterApproval(data.getTransporterApproval());
			response.setTruckId(data.getTruckId());
			response.setUnitValue(data.getUnitValue());
			response.setBiddingDate(data.getBiddingDate());

			try {
				log.info("Put Service Response returned");
				return response;
			} catch (Exception ex) {
				log.error("Put Service Response not returned -----" + String.valueOf(ex));
				throw ex;

			}
		}

		try {
			log.info("Put Service Response returned");
			return response;
		} catch (Exception ex) {
			log.error("Put Service Response not returned -----" + String.valueOf(ex));
			throw ex;

		}
	}
}
