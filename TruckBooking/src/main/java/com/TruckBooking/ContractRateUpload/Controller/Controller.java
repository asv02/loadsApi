package com.TruckBooking.ContractRateUpload.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.TruckBooking.ContractRateUpload.Service.ContractRateService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
public class Controller {

    @Autowired
     ContractRateService ContractRateService;

	//upload the excel file.
    @PostMapping("/ContractRateUpload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if (ContractRateService.checkExcelFormat(file)) {
            //true
            this.ContractRateService.save(file);

            return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to database"));


        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
    }

}