package com.TruckBooking.operations.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OperationPutRequest {
    private Long amount;
    private String operation;
    private String transactionType;
    private String transactionDate;
}
