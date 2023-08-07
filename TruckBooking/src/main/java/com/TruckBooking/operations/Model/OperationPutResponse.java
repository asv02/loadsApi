package com.TruckBooking.operations.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OperationPutResponse {
    private String bookingID;
    private String operationId;
    private String operation;
    private Long amount;
    private String transactionType;
    private String transactionDate;
}
