package com.TruckBooking.operations.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;


@Entity
@Table(name= "operation")
@NoArgsConstructor
@AllArgsConstructor
public @Data class OperationData {
    @Id
    private String operationId;

    @NotBlank(message = "Booking Id can not be null")
    private String bookingId;
    @NotBlank(message = "Operation can not be null")
    private String operation;
    private Long amount;
    @NotBlank(message = "Transaction Type can not be null")
    private String transactionType;
    @NotBlank(message = "Transaction Date can not be blank")
    private String transactionDate;

    @CreationTimestamp
    public Timestamp timestamp;
}
