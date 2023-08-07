package com.TruckBooking.TruckBooking.Entities;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Table(name = "transporterEmail")
@Data
public class TransporterEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "TransportId Cannot Be Empty")
    private String transporterId;
    @NotBlank(message = "Email Cannot Be Empty")
    private String email;

    @NotBlank(message = "Name Cannot Be Empty")
    private String name;
    @NotBlank(message = "Phone Number Cannot Be Empty")
    private String phoneNo;
    private String status="not-sent";
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "load_id", foreignKey = @ForeignKey(name = "FK_transporter_load"))
    private Load load;

    @CreationTimestamp
    public Timestamp timestamp;
}

