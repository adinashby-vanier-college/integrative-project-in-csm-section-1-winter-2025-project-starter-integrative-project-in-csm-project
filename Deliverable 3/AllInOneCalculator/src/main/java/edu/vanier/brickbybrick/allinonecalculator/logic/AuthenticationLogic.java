package edu.vanier.brickbybrick.allinonecalculator.logic;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationLogic {
    private static final String CREDENTIALS_FILE = "user_credentials.txt";
    private Map<String, String> userCredentials;

    public AuthenticationLogic() {
        userCredentials = new HashMap<>();
        loadCredentials();
    }

    private void loadCredentials() {
        File file = new File(CREDENTIALS_FILE);
        if (!file.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            StringBuilder content = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                content.append((char) character);
            }
            
            String[] lines = content.toString().split("\n");
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    userCredentials.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCredentials() {
        try (FileWriter writer = new FileWriter(CREDENTIALS_FILE)) {
            for (Map.Entry<String, String> entry : userCredentials.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticate(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return false;
        }
        String storedPassword = userCredentials.get(email);
        return storedPassword != null && storedPassword.equals(password);
    }

    public boolean register(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return false;
        }
        if (userCredentials.containsKey(email)) {
            return false;
        }
        userCredentials.put(email, password);
        saveCredentials();
        return true;
    }
} 