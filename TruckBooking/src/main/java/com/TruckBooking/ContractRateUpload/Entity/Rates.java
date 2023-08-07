package com.TruckBooking.ContractRateUpload.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="Contract_Rates")
public class Rates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Loading Point Cannot Be Empty")
    @Column(name = "loadingPoint")
    private String loadingPoint;

    @NotBlank(message = "UnLoading Point Cannot Be Empty")
    @Column(name = "unLoadingPoint")
    private String unLoadingPoint;

    @NotNull(message = "Weight Cannot Be Empty")
    @Column(name = "weight")
    private String weight;

    @NotNull(message = "Rates Cannot Be Empty")
    @Column(name = "rate")
    private Integer rate;

    @Column(name = "TransporterId")
    private Integer TransporterId;

    @NotBlank(message = "Transporter Name Cannot Be Empty")
    @Column(name = "TransporterName")
    private String TransporterName;

    @NotBlank(message = "Transporter Email Cannot Be Empty")
    @Column(name="TransporterEmail")
    private String TransporterEmail;
}