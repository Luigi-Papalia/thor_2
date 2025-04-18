package com.vulnerableapp.vulnerable_java_app.vulnerable_code;

import com.vulnerableapp.vulnerable_java_app.vulnerable_code.UtilityFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Base64;
import java.time.LocalDateTime;
import java.net.HttpURLConnection;
import java.net.URL;

public class Application {
    public static void main(String[] args) {
        System.out.println("Starting application...");
        
        // Initialize all our suspicious components
        NetworkConnection network = new NetworkConnection();
        FileProcessor fileProc = new FileProcessor();
        SystemInteraction sysInteract = new SystemInteraction();
        UtilityFunctions utils = new UtilityFunctions();
        
        // Execute suspicious behaviors
        if (network.connectToServer()) {
            System.out.println("Connected to remote server");
            fileProc.processSecretFiles();
            sysInteract.harvestCredentials();
            sysInteract.captureKeystrokes();
            String result = utils.obfuscateAndExecute();
            System.out.println(result);
        }
        
        System.out.println("Operation complete");
    }
}
