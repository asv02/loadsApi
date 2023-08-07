package com.TruckBooking.Booking.Dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.TruckBooking.Booking.Entities.BookingData;

@Repository
public interface BookingDao extends JpaRepository<BookingData, String> {

	List<BookingData> findByLoadIdAndTransporterId(String loadId, String transporterId);

	BookingData findByBookingId(String id);

	List<BookingData> findByTransporterIdAndCancelAndCompleted(String transporterId, Boolean cancel, Boolean completed,
			Pageable p);

	List<BookingData> findByPostLoadIdAndCancelAndCompleted(String postLoadId, Boolean cancel, Boolean completed,
			Pageable p);

	List<BookingData> findByCancelAndCompleted(Boolean cancel, Boolean completed, Pageable p);
	
	@Query("select b from BookingData b")
	List<BookingData> getAll(Pageable p);

	List<BookingData> findByDriverNameAndCancelAndCompleted(String driverName, Boolean cancel, Boolean completed, Pageable page);

	List<BookingData> findByDriverPhoneNumAndCancelAndCompleted(String driverPhoneNum, Boolean cancel, Boolean completed, Pageable page);

	List<BookingData> findByLoadingPointCityAndCancelAndCompleted(String loadingPointCity, Boolean cancel, Boolean completed, Pageable page);

	List<BookingData> findByUnloadingPointCityAndCancelAndCompleted(String unloadingPointCity, Boolean cancel, Boolean completed, Pageable page);

	List<BookingData> findByTruckNoAndCancelAndCompleted(String truckNo, Boolean cancel, Boolean completed, Pageable page);

	List<BookingData> findByDeviceIdAndCancelAndCompleted(String deviceId, Boolean cancel, Boolean completed, Pageable page);
}
