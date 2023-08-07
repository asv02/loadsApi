package com.TruckBooking.installerTask.Controller;


import com.TruckBooking.installerTask.Entities.InstallerTask;
import com.TruckBooking.installerTask.Model.InstallerTaskRequest;
import com.TruckBooking.installerTask.Service.InstallerTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@CrossOrigin
@Slf4j
@RestController
public class InstallerTaskController {

    @Autowired
    public InstallerTaskService installerTaskService;

    @GetMapping("/installerTaskHome")
    public String getMessage(){
        return "Installer Service Working.";
    }

    @PostMapping("/installerTask")
    public ResponseEntity<Object> InstallerTask(@Valid @RequestBody InstallerTaskRequest installerTaskRequest){
        log.info("Post Controller Started");
        return new ResponseEntity<>(installerTaskService.addInstallerTask(installerTaskRequest), HttpStatus.CREATED);
    }

    @GetMapping("/installerTask")
    public ResponseEntity<List<InstallerTask>> findSpecificInstallerTask(
            @RequestParam(name = "vehicleNo", required = false) String vehicleNo,
            @RequestParam(name = "gpsInstallerId", required = false) String gpsInstallerId
            )
    {
        log.info("Get Controller with params Started");
        return new ResponseEntity<>(installerTaskService.getSpecifiedInstallerTasks(vehicleNo, gpsInstallerId),
                HttpStatus.OK);
    }

    @GetMapping("/installerTask/{installerTaskId}")
    public ResponseEntity<Object> findInstallerTask(@PathVariable String installerTaskId) {
        log.info("Get by installerTaskId Controller Started");
        return new ResponseEntity<>(installerTaskService.getInstallerTask(installerTaskId), HttpStatus.OK);
    }

    @PutMapping("/installerTask/{vehicleNo}")
    public ResponseEntity<Object> updateInstallerTask(
            @PathVariable String vehicleNo,
            @RequestBody InstallerTaskRequest installerTaskRequest){
            log.info("Put controller Started");
            return new ResponseEntity<>
                    (installerTaskService.updateInstallerTask(vehicleNo, installerTaskRequest), HttpStatus.OK);

    }

    @DeleteMapping("/installerTask/{installerTaskId}")
    public ResponseEntity<Object> deleteInstallerId
            (@PathVariable String installerTaskId) {
        log.info("Delete Controller Started");
        installerTaskService.deleteInstallerTask(installerTaskId);
        return new ResponseEntity<>("Installer Task Deleted", HttpStatus.OK);
    }

}
