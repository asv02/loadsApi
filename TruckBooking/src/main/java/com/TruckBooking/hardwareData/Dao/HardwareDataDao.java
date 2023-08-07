package com.TruckBooking.hardwareData.Dao;

import com.TruckBooking.hardwareData.Entities.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HardwareDataDao extends JpaRepository<Hardware, String> {
    List<Hardware> findByImei(String imei);
    List<Hardware> findByDeviceId(String deviceId);
}
