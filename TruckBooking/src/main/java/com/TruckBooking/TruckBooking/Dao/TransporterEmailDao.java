package com.TruckBooking.TruckBooking.Dao;

import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Entities.TransporterEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface TransporterEmailDao extends JpaRepository<TransporterEmail, Long> {
    @Query("SELECT t.load FROM TransporterEmail t WHERE t.transporterId = :transporterId")
    List<Load> findLoadsByTransporterId(@Param("transporterId") String transporterId);

    List<TransporterEmail> findByStatus(String status);
    
    @Modifying
    @Query("DELETE FROM TransporterEmail t WHERE t.load.loadId = :loadId")
    void deleteTransporterEmailsByLoadId(String loadId);

    List<TransporterEmail> findByLoadLoadId(String loadId);
}
