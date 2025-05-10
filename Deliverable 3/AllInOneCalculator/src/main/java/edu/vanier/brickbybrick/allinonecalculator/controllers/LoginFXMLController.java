package edu.vanier.brickbybrick.allinonecalculator.controllers;

import edu.vanier.brickbybrick.allinonecalculator.MainApp;
import edu.vanier.brickbybrick.allinonecalculator.logic.AuthenticationLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginFXMLController {
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Button signupButton;
    
    @FXML
    private Label messageLabel;
    
    private AuthenticationLogic auth;
    
    @FXML
    public void initialize() {
        auth = new AuthenticationLogic();
    }
    
    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();
        
        if (auth.authenticate(email, password)) {
            messageLabel.setText("Login successful!");
            // Navigate to arithmetic calculator after successful login
            MainApp.switchScene(MainApp.ARITHMETIC_CALCULATOR);
        } else {
            messageLabel.setText("Invalid email or password");
        }
    }
    
    @FXML
    private void handleSignup() {
        String email = emailField.getText();
        String password = passwordField.getText();
        
        if (auth.register(email, password)) {
            messageLabel.setText("Registration successful!");
        } else {
            messageLabel.setText("Registration failed. Email might already exist.");
        }
    }
} 