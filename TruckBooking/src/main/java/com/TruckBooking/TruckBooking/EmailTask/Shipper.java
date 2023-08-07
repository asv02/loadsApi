package com.TruckBooking.TruckBooking.EmailTask;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipper {
    @Id
    private String shipperId;
    private String shipperName;
    private String companyName;
    //@Column(unique=true)
    //@NotBlank(message = "Phone no. cannot be blank!")
    //	"^[6-9]\\d{9}$", "(^$|[0-9]{10})"
    @Pattern(regexp="(^[6-9]\\d{9}$)", message="Please enter a valid mobile number")
    private String phoneNo;
    @Column(unique=true)
    @NotBlank(message = "Email can not be blank!")
    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = "Please enter a valid email")
    private String emailId;
    private String gst;
    private String companyStatus;
    private String kyc;
    private String shipperLocation;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean companyApproved;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean accountVerificationInProgress;

    @CreationTimestamp
    public Timestamp timestamp;
}
