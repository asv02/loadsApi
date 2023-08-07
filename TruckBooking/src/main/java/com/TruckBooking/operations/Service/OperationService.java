package com.TruckBooking.operations.Service;

import com.TruckBooking.operations.Entities.OperationData;
import com.TruckBooking.operations.Model.*;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

public interface OperationService {
    @Transactional
    public OperationPostResponse addOperation(OperationPostRequest request);

    OperationPutResponse updateOperation(String operationId, @Valid OperationPutRequest request);

    OperationData getDataById(String Id);

    List<OperationData> getDataById(String operation, String bookingId, String amount, String transactionType, String transactionDate);

    OperationDeleteResponse deleteOperation (String operationId);


}


