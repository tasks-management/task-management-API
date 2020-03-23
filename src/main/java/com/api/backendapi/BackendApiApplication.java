package com.api.backendapi;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;

@SpringBootApplication
public class BackendApiApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(BackendApiApplication.class, args);
	}

}
