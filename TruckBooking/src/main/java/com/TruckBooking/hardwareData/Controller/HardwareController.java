package com.TruckBooking.hardwareData.Controller;

import com.TruckBooking.TruckBooking.Model.LoadRequest;
import com.TruckBooking.hardwareData.Model.HardwareDataRequest;
import com.TruckBooking.hardwareData.Service.HardwareDataServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
@CrossOrigin
@Slf4j
@RestController
public class HardwareController {

    @Autowired
    public HardwareDataServiceImpl hardwareDataService;

    @GetMapping("/hardwareHome")
    public String home(){
        return "Hardware service is working.";
    }

    @PostMapping("/hardwaredata")
    public ResponseEntity<Object> hardware(@Valid @RequestBody HardwareDataRequest hardwareDataRequest){
        log.info("Post Controller Started");
        return new ResponseEntity<>(hardwareDataService.addHardwareData(hardwareDataRequest), HttpStatus.CREATED);
    }

    @GetMapping("/hardwaredata/{hardwareDataId}")
    public ResponseEntity<Object> findHardwareData(@PathVariable String hardwareDataId){
        log.info("Get by imei Controller Started");
        return new ResponseEntity<>(hardwareDataService.getHardwareData(hardwareDataId), HttpStatus.OK);
    }

    @GetMapping("/hardwaredata")
    public ResponseEntity<Object> findSpecificHardwareData(
            @RequestParam(name = "imei", required = false) String imei,
            @RequestParam(name = "deviceId", required = false) String deviceId
    ){
        log.info("Get All hardware Data Controller Started");
        return new ResponseEntity<>(hardwareDataService.getSpecificHardwareData(imei, deviceId), HttpStatus.OK);
    }

    @PutMapping("/hardwaredata/{imei}")
    public ResponseEntity<Object> updateHardwareData(@PathVariable String imei, @RequestBody HardwareDataRequest hardwareDataRequest) {
        log.info("Put Controller Started");
        return new ResponseEntity<>(hardwareDataService.updateHardwareDataResponse(imei, hardwareDataRequest), HttpStatus.OK);
    }

}
