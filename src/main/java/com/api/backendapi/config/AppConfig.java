package com.api.backendapi.config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

public class AppConfig {

    public static String encodeImageToBase64String(String imageName){
        File dest;
        String base64String = "";
        FileInputStream fis;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes;
        try {
            dest = new File(imageName);
            if (dest.exists()) {
                fis = new FileInputStream(dest);
                for (int readNum; (readNum = fis.read()) != -1;) {
                    baos.write(readNum);
                }
                imageBytes = baos.toByteArray();
                base64String = Base64.getEncoder().encodeToString(imageBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64String;
    }
}
