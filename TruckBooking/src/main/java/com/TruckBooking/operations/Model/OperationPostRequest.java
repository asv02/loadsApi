package com.TruckBooking.operations.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OperationPostRequest {

    @NotBlank(message = "BookingId Cannot Be Empty")
    private String bookingId;
    @NotBlank(message = "operation Cannot Be Empty")
    private String operation;
    private Long amount;
    @NotBlank(message = "transactionType Cannot Be Empty")
    private String transactionType;
    @NotBlank(message = "transactionDate Cannot Be Empty")
    private String transactionDate;
}
