package com.TruckBooking.biddingApi.Dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.TruckBooking.biddingApi.Entities.BiddingData;

@Repository
public interface BiddingDao extends JpaRepository<BiddingData, String> {

	public List<BiddingData> findByLoadId(String loadId);

	public BiddingData findByLoadIdAndTransporterId(String loadId, String transporterId);

	public List<BiddingData> findByLoadId(String loadId, Pageable p);

	public List<BiddingData> findByTransporterId(String transporterId, Pageable p);

	public List<BiddingData> findByLoadIdAndTransporterId(String loadId, String transporterId, Pageable p);

//	@Query("SELECT u FROM BiddingData u WHERE u.loadId = ?1 and (u.transporterApproval != true or u.shipperApproval != true)")
//	public List<BiddingData> findByLoad(String loadId, Pageable p);
//
//	@Query("SELECT u FROM BiddingData u WHERE u.transporterId = ?1 and (u.transporterApproval != true or u.shipperApproval != true)")
//	public List<BiddingData> findByTransporter(String transporterId, Pageable p);
//
//	@Query("SELECT u FROM BiddingData u WHERE u.loadId = ?1 and u.transporterId = ?2 and  (u.transporterApproval != true or u.shipperApproval != true)")
//	public List<BiddingData> findByLoadAndTransporter(String loadId, String transporterId, Pageable p);
//
//	@Query("SELECT u FROM BiddingData u WHERE (u.transporterApproval != true or u.shipperApproval != true)")
//	public List<BiddingData> getAll();
	
	
	@Query("select b from BiddingData b")
	List<BiddingData> getAll(Pageable p);

}
