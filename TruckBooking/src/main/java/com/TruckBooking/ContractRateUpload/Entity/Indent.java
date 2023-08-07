package com.TruckBooking.ContractRateUpload.Entity;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Indent {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
	private Long id;

    @Column(name="loadId")
    private final String loadId;

    @Column(name="transporterId")
    private final ArrayList<Integer> transporterId;

    @Column(name = "Transporter")
    private final Integer Transporter;

    @Column(name = "Email")
    private final String email;

    @UpdateTimestamp
    @Column(name = "AssignedTime")
    private Timestamp AssignedTime;

    @Enumerated(EnumType.STRING)
    public final TransporterStatus Transporterstatus;

    public enum TransporterStatus {
        EXPIRED, ASSIGNED, REJECTED, COMPLETED;
    }
}