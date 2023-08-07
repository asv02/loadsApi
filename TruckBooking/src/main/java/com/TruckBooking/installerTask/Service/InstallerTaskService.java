package com.TruckBooking.installerTask.Service;

import com.TruckBooking.installerTask.Entities.InstallerTask;
import com.TruckBooking.installerTask.Model.InstallerTaskRequest;
import com.TruckBooking.installerTask.Response.CreateInstallerTaskResponse;
import com.TruckBooking.installerTask.Response.UpdateInstallerTaskResponse;

import java.util.List;

public interface InstallerTaskService {

    public CreateInstallerTaskResponse addInstallerTask(InstallerTaskRequest installerTaskRequest);

    public List<InstallerTask> getSpecifiedInstallerTasks(String vehicleNo, String gpsInstallerId);

    public InstallerTask getInstallerTask(String installerTaskId);

    public UpdateInstallerTaskResponse updateInstallerTask(String vehicleNo, InstallerTaskRequest installerTaskRequest);

    public void deleteInstallerTask(String installerTaskId);
}
