//package com.TruckBooking.biddingApi.Config;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.context.annotation.Configuration;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//
//@Configuration
//public class FirebaseConfig {
//
//	//	@PostConstruct
//	public void Initialize() throws IOException {
//
//		InputStream serviceAccount =this.getClass().getClassLoader().getResourceAsStream("./firebase.json");
//
//		FirebaseOptions options = FirebaseOptions.builder()
//				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
//				.build();
//
//		FirebaseApp Myapp =FirebaseApp.initializeApp(options);
//
//		//		System.out.println(Myapp.getName());
//	}
//}
