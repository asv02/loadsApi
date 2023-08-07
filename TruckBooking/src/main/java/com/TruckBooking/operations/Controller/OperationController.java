package com.TruckBooking.operations.Controller;


import com.TruckBooking.operations.Entities.OperationData;
import com.TruckBooking.operations.Exception.EntityNotFoundException;
import com.TruckBooking.operations.Model.*;
import com.TruckBooking.operations.Service.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.ConnectException;
import java.util.List;
@CrossOrigin
@RestController
@Slf4j
public class OperationController {

    @Autowired
    private OperationService operationService;

    @PostMapping("/operations")
    public ResponseEntity<OperationPostResponse> addOperation(@Valid @RequestBody OperationPostRequest request) throws Exception, ConnectException {
        log.info("Post Controller Started");

        ResponseEntity<OperationPostResponse> response = new ResponseEntity<>(operationService.addOperation(request),HttpStatus.CREATED);
        return response;
    }

    @PutMapping("/operations/{operationId}")
    public ResponseEntity<OperationPutResponse> updateOperation(@Valid @RequestBody OperationPutRequest request, @PathVariable String operationId) throws EntityNotFoundException,ConnectException,Exception{
        log.info("Put Controller Started");
        ResponseEntity<OperationPutResponse> response = new ResponseEntity<>(operationService.updateOperation(operationId, request), HttpStatus.OK);

        return response;
    }
    @GetMapping("/operations")
    public  ResponseEntity<List<OperationData>> getData(@RequestParam(value = "operation", required = false) String operation,
                                                        @RequestParam(value = "bookingId", required = false) String bookingId,
                                                        @RequestParam(value = "Amount", required = false) String amount,
                                                        @RequestParam(value = "transactionType", required = false) String transactionType,
                                                        @RequestParam(value = "transactionDate", required = false)String transactionDate) throws EntityNotFoundException{
        log.info("Get with Params Controller Started");
        return new ResponseEntity<>(operationService.getDataById(operation, bookingId, amount, transactionDate, transactionType),HttpStatus.OK);
    }
    @GetMapping("/operations/{operationId}")
    public ResponseEntity<OperationData> getDataById(@PathVariable String operationId) throws EntityNotFoundException {
        log.info("Get Controller Started");
        return new ResponseEntity<>(operationService.getDataById(operationId), HttpStatus.OK);
    }

    @DeleteMapping("/operations/{operationId}")
    public ResponseEntity<OperationDeleteResponse> deleteOperation(@PathVariable String operationId)
        throws EntityNotFoundException{
        log.info("Delete Controller Started");
        return new ResponseEntity<>(operationService.deleteOperation(operationId),HttpStatus.OK);
    }
}

