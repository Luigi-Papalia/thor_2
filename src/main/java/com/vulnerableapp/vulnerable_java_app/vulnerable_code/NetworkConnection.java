package com.vulnerableapp.vulnerable_java_app.vulnerable_code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Base64;
import java.time.LocalDateTime;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.Socket;
import java.io.IOException;

public class NetworkConnection {
    // Embedding known malicious C2 domains and IPs
    private static final String[] SERVER_ADDRESSES = {
        "malware.evil-domain.com",
        "185.193.141.12",
        "c2.attacker-infrastructure.net",
        "exfiltration.malicious-server.ru"
    };
    
    public boolean connectToServer() {
        for (String address : SERVER_ADDRESSES) {
            try {
                // Attempting connection to suspicious server
                Socket socket = new Socket(address, 443);
                return true;
            } catch (IOException e) {
                // Connection failed, try next address
                continue;
            }
        }
        return false;
    }
}
