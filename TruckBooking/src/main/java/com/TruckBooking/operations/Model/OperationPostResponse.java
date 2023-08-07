package com.TruckBooking.operations.Model;

import com.TruckBooking.Booking.Entities.BookingData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public @Data class OperationPostResponse {

    private String operationId;
    private String bookingID;
    private String operation;
    private Long amount;
    private String transactionType;
    private String transactionDate;
}













