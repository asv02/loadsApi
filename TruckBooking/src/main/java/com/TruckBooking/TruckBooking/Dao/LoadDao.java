package com.TruckBooking.TruckBooking.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Entities.Load.Publish;
import com.TruckBooking.TruckBooking.Entities.Load.Status;

@Repository
public interface LoadDao extends JpaRepository<Load, String> {

//	@Query("select l from Load l")
//	List<Load> findByAll(Pageable pageable);
	@Query("select l from Load l WHERE l.postLoadId=:postLoadId AND status in :status")
	List<Load> findByPostLoadIdAndStatus(String postLoadId, List<Status> status, Pageable pageable);

	Optional<Load> findByLoadId(String loadId);

	List<Load> findByLoadingPointCityAndUnloadingPointCityAndStatus(String loadingPointCity, String unloadingPointCity,
			Status status,	Pageable pageable);

	List<Load> findByTruckTypeAndStatus(String truckType,Status status, Pageable pageable);

	List<Load> findByLoadingPointCityAndStatus(String loadingPointCity, Status status,Pageable pageable);

	List<Load> findByLoadingPointStateAndStatus(String loadingPointState, Status status,Pageable pageable);

	List<Load> findByUnloadingPointCityAndStatus(String unloadingPointCity,Status status, Pageable pageable);

	List<Load> findByUnloadingPointStateAndStatus(String loadingPointState,Status status, Pageable pageable);
	
	List<Load> findByStatus(Status status, Pageable pageable);

	List<Load>findByPublishAndStatus(Publish publish,Status status);
}
