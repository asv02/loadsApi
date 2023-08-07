package com.TruckBooking.hardwareData.Service;


import com.TruckBooking.hardwareData.Entities.Hardware;
import com.TruckBooking.hardwareData.Model.HardwareDataRequest;
import com.TruckBooking.hardwareData.Response.CreateHardwareDataResponse;
import com.TruckBooking.hardwareData.Response.UpdateHardwareDataResponse;

import java.util.List;
import java.util.Optional;

public interface HardwareDataService {

    public CreateHardwareDataResponse addHardwareData(HardwareDataRequest hardwareDataRequest);

    public Optional<Hardware> getHardwareData(String hardwareDataId);

    public List<Hardware> getSpecificHardwareData(String imei, String deviceId);

    public UpdateHardwareDataResponse updateHardwareDataResponse(String imei, HardwareDataRequest hardwareDataRequest);
}
