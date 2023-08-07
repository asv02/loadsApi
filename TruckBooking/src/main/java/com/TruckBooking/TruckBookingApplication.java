package com.TruckBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// import jakarta.mail.MessagingException;
@SpringBootApplication
@EnableScheduling
public class TruckBookingApplication {
    	public static void main(String[] args) {
		SpringApplication.run(TruckBookingApplication.class, args);
	}
}
