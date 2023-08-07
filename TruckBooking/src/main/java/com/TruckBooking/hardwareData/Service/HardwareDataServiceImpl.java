package com.TruckBooking.hardwareData.Service;

import com.TruckBooking.TruckBooking.Exception.BusinessException;
import com.TruckBooking.TruckBooking.Exception.EntityNotFoundException;
import com.TruckBooking.hardwareData.Dao.HardwareDataDao;
import com.TruckBooking.hardwareData.Entities.Hardware;
import com.TruckBooking.hardwareData.Model.HardwareDataRequest;
import com.TruckBooking.hardwareData.Response.CreateHardwareDataResponse;
import com.TruckBooking.hardwareData.Response.UpdateHardwareDataResponse;
import com.TruckBooking.installerTask.Entities.InstallerTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class HardwareDataServiceImpl implements HardwareDataService{

    @Autowired
    HardwareDataDao hardwareDataDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CreateHardwareDataResponse addHardwareData(HardwareDataRequest hardwareDataRequest) {

        List<Hardware> hardwareList = hardwareDataDao.findByImei(hardwareDataRequest.getImei().trim());
        if (!hardwareList.isEmpty()) {
            throw new BusinessException(": This imei already exists.");
        }

        else {
            if(hardwareDataRequest.getPhoneNo().trim().length() != 13){
                throw new BusinessException(": The Phone No. must be of 13 digits.");
            }

            else {
                String temp = "";
                Hardware hardware = new Hardware();
                CreateHardwareDataResponse response = new CreateHardwareDataResponse();

                temp = "hardwareDataID:" + UUID.randomUUID();
                hardware.setHardwareDataId(temp);
                response.setHardwareDataId(temp);

                temp = hardwareDataRequest.getImei().trim();
                hardware.setImei(temp);
                response.setImei(temp);

                temp = hardwareDataRequest.getDeviceId().trim();
                hardware.setDeviceId(temp);
                response.setDeviceId(temp);

                temp = hardwareDataRequest.getSimNumber().trim();
                hardware.setSimNumber(temp);
                response.setSimNumber(temp);

                temp = hardwareDataRequest.getPhoneNo().trim();
                hardware.setPhoneNo(temp);
                response.setPhoneNo(temp);

                hardwareDataDao.save(hardware);
                log.info("hardwareData is saved to the database");
                log.info("addHardwareData service response is returned");

                return response;
            }
        }
    }


    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Optional<Hardware> getHardwareData(String hardwareDataId) {
        log.info("getHardwareData service by imei is started");

        Optional<Hardware> hardware = hardwareDataDao.findById(hardwareDataId);
        if (hardware.isEmpty())
            throw new EntityNotFoundException(Hardware.class, "Id", hardwareDataId);

        log.info("getHardwareData service response is returned");
        return hardware;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Hardware> getSpecificHardwareData(String imei, String deviceId) {

        if(imei != null){
            List<Hardware> hardwareDataList = hardwareDataDao.findByImei(imei);

            if(hardwareDataList.isEmpty())
                throw new EntityNotFoundException(Hardware.class, "imei", imei);
            else
                return hardwareDataList;
        }

        if(deviceId != null){
            List<Hardware> hardwareDataList = hardwareDataDao.findByDeviceId(deviceId);

            if(hardwareDataList.isEmpty())
                throw new EntityNotFoundException(Hardware.class, "deviceId", deviceId);
            else
                return hardwareDataList;
        }

        return hardwareDataDao.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateHardwareDataResponse updateHardwareDataResponse(String imei, HardwareDataRequest updateHardwareDataRequest) {
        log.info("updateHardwareData service is started");

        List<Hardware> hardwareList = hardwareDataDao.findByImei(imei);
        if (hardwareList.isEmpty())
            throw new EntityNotFoundException(Hardware.class, "imei", imei);

        if(updateHardwareDataRequest.getPhoneNo().trim().length() != 13)
            throw new BusinessException(": The Phone No. must be of 13 digits.");

        String temp = "";
        Hardware hardware = hardwareList.get(0);
        UpdateHardwareDataResponse response = new UpdateHardwareDataResponse();

        temp = hardware.getHardwareDataId();
        response.setHardwareDataId(temp.trim());

        temp = hardware.getImei();
        response.setImei(temp.trim());

        temp = updateHardwareDataRequest.getDeviceId();
        if(StringUtils.isNotBlank(temp)) {
            hardware.setDeviceId(temp.trim());
            response.setDeviceId(temp.trim());
        }

        temp = updateHardwareDataRequest.getSimNumber();
        if(StringUtils.isNotBlank(temp)) {
            hardware.setSimNumber(temp.trim());
            response.setSimNumber(temp.trim());
        }

        temp = updateHardwareDataRequest.getPhoneNo();
        if(StringUtils.isNotBlank(temp)) {
            hardware.setPhoneNo(temp.trim());
            response.setPhoneNo(temp.trim());
        }

        hardwareDataDao.save(hardware);
        log.info("Hardware Data is updated in the database");
        log.info("updateHardwareData service response is returned");
        return response;
    }
}
