package com.TruckBooking.Booking.Service;



import java.net.ConnectException;
import java.net.Socket;
import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.TruckBooking.Booking.Constants.BookingConstants;
import com.TruckBooking.Booking.Dao.BookingDao;
import com.TruckBooking.Booking.Entities.BookingData;
import com.TruckBooking.Booking.Exception.BusinessException;
import com.TruckBooking.Booking.Exception.EntityNotFoundException;
import com.TruckBooking.Booking.Model.BookingDeleteResponse;
import com.TruckBooking.Booking.Model.BookingPostRequest;
import com.TruckBooking.Booking.Model.BookingPostResponse;
import com.TruckBooking.Booking.Model.BookingPutRequest;
import com.TruckBooking.Booking.Model.BookingPutResponse;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {
	@Autowired
	private BookingDao bookingDao;

	private BookingConstants constants;
	@Transactional
	@Override
	public BookingPostResponse addBooking(BookingPostRequest request){

		BookingData bookingData = new BookingData();
		BookingPostResponse response = new BookingPostResponse();

		bookingData.setBookingId("booking:" + UUID.randomUUID());
		bookingData.setLoadId(request.getLoadId());
		bookingData.setTransporterId(request.getTransporterId());

		bookingData.setLoadingPointCity(request.getLoadingPointCity());
		bookingData.setUnloadingPointCity(request.getUnloadingPointCity());
		bookingData.setDriverName(request.getDriverName());
		bookingData.setDriverPhoneNum(request.getDriverPhoneNum());
		bookingData.setTruckNo(request.getTruckNo());
        bookingData.setDeviceId(request.getDeviceId());


        bookingData.setPostLoadId(request.getPostLoadId());
		bookingData.setTruckId(request.getTruckId());

		if (request.getRate() != null) {
			if (request.getUnitValue() == null) {
				log.error(constants.pUnitIsNull);
				throw new BusinessException(constants.pUnitIsNull);
			}

			if (String.valueOf(request.getUnitValue()).equals("PER_TON")) {
				bookingData.setUnitValue(BookingData.Unit.PER_TON);
			} else if (String.valueOf(request.getUnitValue()).equals("PER_TRUCK")) {
				bookingData.setUnitValue(BookingData.Unit.PER_TRUCK);
			} else {
				log.error(BookingConstants.uUnknownUnit);
				throw new BusinessException(BookingConstants.uUnknownUnit);

			}
		} else {
			if (request.getUnitValue() != null) {
				log.error(constants.pPostUnitRateIsNull);
				throw new BusinessException(constants.pPostUnitRateIsNull);

			}
		}

		if (request.getBookingDate() != null) {
			bookingData.setBookingDate(request.getBookingDate());
		}

		bookingData.setRate(request.getRate());
		bookingData.setCancel(false);
		bookingData.setCompleted(false);

		try {
			bookingDao.save(bookingData);
			log.info("Booking Data is saved");
		} catch (Exception ex) {
			log.error("Booking Data is not saved -----" + String.valueOf(ex));
			throw ex;
		}

		response.setStatus(constants.success);
		response.setBookingId(bookingData.getBookingId());
		response.setCancel(bookingData.getCancel());
		response.setCompleted(bookingData.getCompleted());
		response.setLoadId(bookingData.getLoadId());
		response.setPostLoadId(bookingData.getPostLoadId());

		response.setLoadingPointCity(bookingData.getLoadingPointCity());
		response.setUnloadingPointCity(bookingData.getUnloadingPointCity());
		response.setDriverName(bookingData.getDriverName());
		response.setDriverPhoneNum(bookingData.getDriverPhoneNum());
		response.setTruckNo(bookingData.getTruckNo());
        response.setDeviceId(bookingData.getDeviceId());

		response.setRate(bookingData.getRate());
		response.setTransporterId(bookingData.getTransporterId());
		response.setTruckId(bookingData.getTruckId());
		response.setUnitValue(bookingData.getUnitValue());
		response.setBookingDate(bookingData.getBookingDate());

		try {
			log.info("Post Service Response returned");

			return response;
		} catch (Exception ex) {
			log.error("Post Service Response not returned -----" + String.valueOf(ex));
			throw ex;

		}
	}

	
	//cancel = false, complete true
	@Override
	public BookingPutResponse updateBooking(String bookingId, BookingPutRequest request) {
		
		
		BookingPutResponse response = new BookingPutResponse();

		BookingData data = bookingDao.findByBookingId(bookingId);

		if (data == null) {
			EntityNotFoundException ex = new EntityNotFoundException(BookingData.class, "bookingId",
					bookingId.toString());

			log.error(String.valueOf(ex));
			throw ex;
		}

		if (request.getTruckId() != null && request.getTruckId().size() == 0) {
			log.error(BookingConstants.uTruckIdIsNull);
			throw new BusinessException(BookingConstants.uTruckIdIsNull);

		}

		if (request.getCompleted() != null && request.getCancel() != null && request.getCancel() == true
				&& request.getCompleted() == true) {
			log.error(BookingConstants.uCancelAndCompleteTrue);
			throw new BusinessException(BookingConstants.uCancelAndCompleteTrue);

		}

		if (request.getTruckId() != null) {
			data.setTruckId(request.getTruckId());
		}

		if (request.getRate() != null) {
			if (request.getUnitValue() == null) {
				log.error(BookingConstants.uUnitIsNull);
				throw new BusinessException(BookingConstants.uUnitIsNull);

			}
			if (String.valueOf(request.getUnitValue()).equals("PER_TON")) {
				data.setUnitValue(BookingData.Unit.PER_TON);
			} else if (String.valueOf(request.getUnitValue()).equals("PER_TRUCK")) {
				data.setUnitValue(BookingData.Unit.PER_TRUCK);
			} else {
				log.error(BookingConstants.uUnknownUnit);
				throw new BusinessException(BookingConstants.uUnknownUnit);

			}
		} else {
			if (request.getUnitValue() != null) {
				log.error(BookingConstants.uUpdateUnitRateIsNull);
				throw new BusinessException(BookingConstants.uUpdateUnitRateIsNull);

			}
		}

		if (request.getRate() != null) {
			data.setRate(request.getRate());
		}

		if (request.getLoadingPointCity() != null) {
			data.setLoadingPointCity(request.getLoadingPointCity());
		}

		if (request.getUnloadingPointCity() != null) {
			data.setUnloadingPointCity(request.getUnloadingPointCity());
		}

		if (request.getTruckNo() != null) {
			data.setTruckNo(request.getTruckNo());
		}

		if (request.getDriverPhoneNum() != null) {
			data.setDriverPhoneNum(request.getDriverPhoneNum());
		}

		if (request.getDriverName() != null) {
			data.setDriverName(request.getDriverName());
		}

		if (request.getCompleted() != null) {
			if (request.getCompleted() == true) {
				data.setCompleted(true);
				data.setCancel(false);
				////cancel = false complete true
			} else if (data.getCompleted() == true && request.getCompleted() == false) {
				log.error(BookingConstants.uAlreadyCompleted);
				throw new BusinessException(BookingConstants.uAlreadyCompleted);

			}
		}

		if (request.getCancel() != null) {
			if (request.getCancel() == true) {
				if ((data.getCompleted() == true)
						|| (request.getCompleted() != null && request.getCompleted() == true)) {
					log.error(BookingConstants.uCanelIsTrueWhenCompleteIsTrue);
					throw new BusinessException(BookingConstants.uCanelIsTrueWhenCompleteIsTrue);

				}
				data.setCancel(true);
			} else {
				data.setCancel(false);
			}
		}

		if (request.getBookingDate() != null) {
			data.setBookingDate(request.getBookingDate());
		}

		if (request.getCompletedDate() != null && (data.getCompleted() == null || data.getCompleted() == false)) {
			log.error(BookingConstants.uCompletedDateWhenCompletedIsNotTrue);
			throw new BusinessException(BookingConstants.uCompletedDateWhenCompletedIsNotTrue);

		} else if (request.getCompletedDate() != null) {
			data.setCompletedDate(request.getCompletedDate());
		}

		try {
			bookingDao.save(data);
			log.info("Booking Data is updated");
		} catch (Exception ex) {
			log.error("Booking Data is not updated -----" + String.valueOf(ex));
			throw ex;

		}

		response.setStatus(constants.UPDATE_SUCCESS);
		response.setBookingId(data.getBookingId());
		response.setCancel(data.getCancel());
		response.setCompleted(data.getCompleted());
		response.setLoadId(data.getLoadId());
		response.setPostLoadId(data.getPostLoadId());

		response.setLoadingPointCity(data.getLoadingPointCity());
		response.setUnloadingPointCity(data.getUnloadingPointCity());
		response.setDriverName(data.getDriverName());
		response.setDriverPhoneNum(data.getDriverPhoneNum());
		response.setTruckNo(data.getTruckNo());
        response.setDeviceId(data.getDeviceId());

		response.setRate(data.getRate());
		response.setTransporterId(data.getTransporterId());
		response.setTruckId(data.getTruckId());
		response.setUnitValue(data.getUnitValue());
		response.setBookingDate(data.getBookingDate());
		response.setCompletedDate(data.getCompletedDate());

		try {
			log.info("Put Service Response returned");
			return response;
		} catch (Exception ex) {
			log.error("Put Service Response not returned -----" + String.valueOf(ex));
			throw ex;

		}

	}

	@Override
	public BookingData getDataById(String Id) {

		BookingData bookingData = bookingDao.findByBookingId(Id);
		if (bookingData == null) {
			EntityNotFoundException ex = new EntityNotFoundException(BookingData.class, "bookingId", Id.toString());
			log.error(String.valueOf(ex));
			throw ex;
		}

		try {
			log.info("Booking Data returned");
			return bookingData;
		} catch (Exception ex) {
			log.error("Booking Data not returned -----" + String.valueOf(ex));
			throw ex;

		}

	}

	@Override
	public List<BookingData> getDataById(Integer pageNo, Boolean cancel, Boolean completed, String transporterId,
			String postLoadId, String loadingPointCity, String unloadingPointCity, String truckNo, String driverName,
            String driverPhoneNum, String deviceId) {

		if (pageNo == null) {
			pageNo = 0;
		}
		Pageable page = PageRequest.of(pageNo, BookingConstants.pageSize,Sort.Direction.DESC,"timestamp");
		//		List<BookingData> temp = null;

		if ((cancel == null || completed == null) && (transporterId != null || postLoadId != null || loadingPointCity != null || unloadingPointCity != null ||
                truckNo != null || driverName != null || driverPhoneNum != null || deviceId != null)) {
			EntityNotFoundException ex = new EntityNotFoundException(BookingData.class, "completed",
					String.valueOf(completed), "cancel", String.valueOf(cancel));
			log.error(String.valueOf(ex));
			throw ex;
		}
		if (cancel != null && completed != null && cancel == true && completed == true) {

			EntityNotFoundException ex = new EntityNotFoundException(BookingData.class, "completed",
					String.valueOf(completed), "cancel", String.valueOf(cancel));
			log.error(String.valueOf(ex));
			throw ex;

		}

		if (transporterId != null && postLoadId != null &&  loadingPointCity != null && unloadingPointCity != null
            && truckNo != null && driverName != null && driverPhoneNum != null && deviceId != null) {
			EntityNotFoundException ex = new EntityNotFoundException(BookingData.class, "transporterId",
					String.valueOf(transporterId), "postLoadId", String.valueOf(postLoadId), "loadingPointCity", String.valueOf(loadingPointCity),
					"unloadingPointCity", String.valueOf(unloadingPointCity), "truckNo", String.valueOf(truckNo),
					"driverName", String.valueOf(driverName), "driverPhoneNum",
                    String.valueOf(driverPhoneNum), "deviceId", String.valueOf(deviceId));
			log.error(String.valueOf(ex));
			throw ex;

		}

		if (transporterId != null) {
			try {
				log.info("Booking Data with params returned");
				return bookingDao.findByTransporterIdAndCancelAndCompleted(transporterId, cancel, completed, page);
			} catch (Exception ex) {
				log.error("Booking Data with params not returned -----" + String.valueOf(ex));
				throw ex;

			}

		}

		if (postLoadId != null) {
			try {
				log.info("Booking Data with params returned");
				return bookingDao.findByPostLoadIdAndCancelAndCompleted(postLoadId, cancel, completed, page);
			} catch (Exception ex) {
				log.error("Booking Data with params not returned -----" + String.valueOf(ex));
				throw ex;

			}
		}

		if (loadingPointCity != null) {
			try {
				log.info("Booking Data with params returned");
				return bookingDao.findByLoadingPointCityAndCancelAndCompleted(loadingPointCity, cancel, completed, page);
			} catch (Exception ex) {
				log.error("Booking Data with params not returned -----" + String.valueOf(ex));
				throw ex;
			}
		}

		if (unloadingPointCity != null) {
			try {
				log.info("Booking Data with params returned");
				return bookingDao.findByUnloadingPointCityAndCancelAndCompleted(unloadingPointCity, cancel, completed, page);
			} catch (Exception ex) {
				log.error("Booking Data with params not returned -----" + String.valueOf(ex));
				throw ex;

			}
		}

		if (truckNo != null) {
			try {
				log.info("Booking Data with params returned");
				return bookingDao.findByTruckNoAndCancelAndCompleted(truckNo, cancel, completed, page);
			} catch (Exception ex) {
				log.error("Booking Data with params not returned -----" + String.valueOf(ex));
				throw ex;

			}
		}

		if (driverPhoneNum != null) {
			try {
				log.info("Booking Data with params returned");
				return bookingDao.findByDriverPhoneNumAndCancelAndCompleted(driverPhoneNum, cancel, completed, page);
			} catch (Exception ex) {
				log.error("Booking Data with params not returned -----" + String.valueOf(ex));
				throw ex;

			}
		}

		if (driverName != null) {
			try {
				log.info("Booking Data with params returned");
				return bookingDao.findByDriverNameAndCancelAndCompleted(driverName, cancel, completed, page);
			} catch (Exception ex) {
				log.error("Booking Data with params not returned -----" + String.valueOf(ex));
				throw ex;

			}
		}

        if (deviceId != null) {
            try {
                log.info("Booking Data with params returned");
                return bookingDao.findByDeviceIdAndCancelAndCompleted(deviceId, cancel, completed, page);
            } catch (Exception ex) {
                log.error("Booking Data with params not returned -----" + String.valueOf(ex));
                throw ex;
            }
        }


        if (postLoadId != null) {
			try {
				log.info("Booking Data with params returned");
				return bookingDao.findByPostLoadIdAndCancelAndCompleted(postLoadId, cancel, completed, page);
			} catch (Exception ex) {
				log.error("Booking Data with params not returned -----" + String.valueOf(ex));
				throw ex;

			}
		}

		if (cancel != null && completed != null) {
			try {
				log.info("Booking Data with params returned");
				return bookingDao.findByCancelAndCompleted(cancel, completed, page);
			} catch (Exception ex) {
				log.error("Booking Data with params not returned -----" + String.valueOf(ex));
				throw ex;

			}
		}

		try {
			log.info("Booking Data with params returned");
			return bookingDao.getAll(page);
		} catch (Exception ex) {
			log.error("Booking Data with params not returned -----" + String.valueOf(ex));
			throw ex;

		}

	}

	@Override
	public BookingDeleteResponse deleteBooking(String bookingId) {

		BookingDeleteResponse response = new BookingDeleteResponse();

		BookingData temp = bookingDao.findByBookingId(bookingId);

		if (temp == null) {
			EntityNotFoundException ex = new EntityNotFoundException(BookingData.class, "bookingId",
					bookingId.toString());
			log.error(String.valueOf(ex));
			throw ex;
		}

		try {
			bookingDao.deleteById(bookingId);
			log.info("Deleted");
		} catch (Exception ex) {
			log.error(String.valueOf(ex));
			throw ex;

		}

		response.setStatus(constants.DELETE_SUCCESS);

		try {
			log.info("Deleted Service Response returned");
			return response;
		} catch (Exception ex) {
			log.error("Deleted Service Response not returned -----" + String.valueOf(ex));
			throw ex;

		}

	}
	
	
	@Value("${LOAD_URL}")
	private String loadUrl;
	
	@Value("${LOAD_IP}")
	private String loadIp;

	@Value("${LOAD_PORT}")
	private String loadPort;
	
	@Async
	@Retryable(maxAttempts = 24*60/15, value = { ConnectException.class, Exception.class, RuntimeException.class },
	backoff = @Backoff(15*60*1000))
	public void updating_load_status_by_loadid(String loadid, String inputJson) throws ConnectException, Exception
	{
		try {
			log.info("started update load status");
			Socket clientSocket = new Socket(loadIp, Integer.parseInt(loadPort));
			clientSocket.close();

			RestAssured.baseURI = loadUrl;

			Response responseupdate = RestAssured.given().header(" ", "").body(inputJson)
					.header("accept", "application/json")
					.header("Content-Type", "application/json")
					.put("/" + loadid).then().extract().response();
			log.info("update load status successful");
		}
		catch (ConnectException e) {
			log.error("ConnectException: update load status failed");
			throw e;
		}catch (Exception e) {
			log.error("Exception: update load status failed");
			throw e;
		}
	}
}
