package com.api.backendapi.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Service
public class FCMInitialize {

    @PostConstruct
    public void inialize() {
        System.out.println("Start Init");
        try {
//            FileInputStream serviceAccount =
//                    new FileInputStream("C:\\Users\\duong\\Downloads\\succotask-87df4-firebase-adminsdk-2zrsc-5f4b33e5a5.json");

//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .setDatabaseUrl("https://succotask-87df4.firebaseio.com")
//                    .build();
//            if (FirebaseApp.getApps().isEmpty()) {
//                FirebaseApp.initializeApp(options);
//                System.out.println("Fire base application has been initialized");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
