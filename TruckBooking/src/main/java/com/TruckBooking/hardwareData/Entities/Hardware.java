package com.TruckBooking.hardwareData.Entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name= "hardware")
@Data
public class Hardware {

    @Id
    private String hardwareDataId;

    @CreationTimestamp
    public Timestamp timestamp;

    @NotBlank(message = "imei can't be left blank.")
    private String imei;

    @NotBlank(message = "deviceId can't be left blank.")
    private String deviceId;

    @NotBlank(message = "simNumber can't be left blank.")
    private String simNumber;

    @NotBlank(message = "phoneNo can't be left blank.")
    private String phoneNo;

}
