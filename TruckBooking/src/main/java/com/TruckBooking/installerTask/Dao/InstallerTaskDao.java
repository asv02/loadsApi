package com.TruckBooking.installerTask.Dao;


import com.TruckBooking.installerTask.Entities.InstallerTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstallerTaskDao extends JpaRepository<InstallerTask, String>  {
    List<InstallerTask> findByVehicleNo(String vehicleNo);

    List<InstallerTask> findByGpsInstallerId(String gpsInstallerId);
}
