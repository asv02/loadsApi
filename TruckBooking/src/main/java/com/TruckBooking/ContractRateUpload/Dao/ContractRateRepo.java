package com.TruckBooking.ContractRateUpload.Dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.TruckBooking.ContractRateUpload.Entity.Rates;

@Repository
public interface ContractRateRepo extends JpaRepository<Rates,Long> {
    List<Rates> findByUnLoadingPointAndWeightOrderByRateAsc(@PathVariable("unLoadingPoint") String unLoadingPoint,@PathVariable("weight") String weight);
} 