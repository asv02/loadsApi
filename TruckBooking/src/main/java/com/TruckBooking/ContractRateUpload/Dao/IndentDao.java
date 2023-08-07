package com.TruckBooking.ContractRateUpload.Dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TruckBooking.ContractRateUpload.Entity.Indent;


@Repository
public interface IndentDao extends JpaRepository<Indent,Long>{

}