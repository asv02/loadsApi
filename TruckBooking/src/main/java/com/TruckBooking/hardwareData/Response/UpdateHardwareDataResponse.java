package com.TruckBooking.hardwareData.Response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateHardwareDataResponse {
    private String hardwareDataId;
    private String imei;
    private String deviceId;
    private String simNumber;
    private String phoneNo;
}
