package com.TruckBooking.Booking.Controller;

import java.net.ConnectException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import com.TruckBooking.Booking.Constants.BookingConstants;
import com.TruckBooking.Booking.Entities.BookingData;
import com.TruckBooking.Booking.Exception.EntityNotFoundException;
import com.TruckBooking.Booking.Model.BookingDeleteResponse;
import com.TruckBooking.Booking.Model.BookingPostRequest;
import com.TruckBooking.Booking.Model.BookingPostResponse;
import com.TruckBooking.Booking.Model.BookingPutRequest;
import com.TruckBooking.Booking.Model.BookingPutResponse;
import com.TruckBooking.Booking.Service.BookingService;

import lombok.extern.slf4j.Slf4j;
@CrossOrigin
@RestController
@Slf4j
@EnableTransactionManagement
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@PostMapping("/booking")
	public ResponseEntity<BookingPostResponse> addBooking(@Valid @RequestBody BookingPostRequest request) throws Exception,ConnectException {
		log.info("Post Controller Started");
		
		ResponseEntity<BookingPostResponse> response = new ResponseEntity<>(bookingService.addBooking(request), HttpStatus.CREATED);
		//bookingService.updating_load_status_by_loadid(request.getLoadId(), BookingConstants.loadStatus_ongoing);
		return response;
		//return new ResponseEntity<>(bookingService.addBooking(request), HttpStatus.CREATED);
	}

	@PutMapping("/booking/{bookingId}")
	public ResponseEntity<BookingPutResponse> updateBooking(@Valid @RequestBody BookingPutRequest request,
			@PathVariable String bookingId) throws EntityNotFoundException, ConnectException ,Exception {
		log.info("Put Controller Started");
		ResponseEntity<BookingPutResponse> response = new ResponseEntity<>(bookingService.updateBooking(bookingId, request), HttpStatus.OK);
		
//		if(!request.getCancel() && request.getCompleted())
//		bookingService.updating_load_status_by_loadid(bookingService.getDataById(bookingId).getLoadId(), BookingConstants.loadStatus_completed);

		return response;
	}

	@GetMapping("/booking/{bookingId}")
	public ResponseEntity<BookingData> getDataById(@PathVariable String bookingId) throws EntityNotFoundException {
		log.info("Get Controller Started");
		return new ResponseEntity<>(bookingService.getDataById(bookingId), HttpStatus.OK);
	}

	@GetMapping("/booking")
	public ResponseEntity<List<BookingData>> getData(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "cancel", required = false) Boolean cancel,
			@RequestParam(value = "completed", required = false) Boolean completed,
			@RequestParam(value = "transporterId", required = false) String transporterId,
			@RequestParam(value = "postLoadId", required = false) String postLoadId,
			@RequestParam(value = "loadingPointCity", required = false) String loadingPointCity,
		    @RequestParam(value = "unloadingPointCity", required = false) String unloadingPointCity,
  		    @RequestParam(value = "truckNo", required = false) String truckNo,
		    @RequestParam(value = "driverPhoneNum", required = false) String driverPhoneNum,
            @RequestParam(value = "driverName", required = false) String driverName,
            @RequestParam(value = "deviceId", required = false) String deviceId) throws EntityNotFoundException {
		log.info("Get with Params Controller Started");
		return new ResponseEntity<>(bookingService.getDataById(pageNo, cancel, completed, transporterId, postLoadId, loadingPointCity, unloadingPointCity, truckNo, driverName, driverPhoneNum, deviceId),
				HttpStatus.OK);
	}

	@DeleteMapping("/booking/{bookingId}")
	public ResponseEntity<BookingDeleteResponse> deleteBooking(@PathVariable String bookingId)
			throws EntityNotFoundException {
		log.info("Delete Controller Started");
		return new ResponseEntity<>(bookingService.deleteBooking(bookingId), HttpStatus.OK);
	}
}
