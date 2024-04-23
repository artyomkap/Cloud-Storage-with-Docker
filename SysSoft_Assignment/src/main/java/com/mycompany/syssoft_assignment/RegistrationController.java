/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

/**

@file RegistrationController.java
@brief This is the controller class for the registration window of the application.
It allows the user to input a name, email, and password to create a new account.
@author t0322864
*/
package com.mycompany.syssoft_assignment;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 
 */

/**
 * Initializes the controller class.
 */
public class RegistrationController implements Initializable {

    @FXML
    private Button register_button;
    
    @FXML
    private Button gologin_button;
    
    @FXML
    private TextField user_email;
    
    @FXML
    private TextField user_name;
    
    @FXML
    private PasswordField user_password;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Register button action listener
        register_button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                 // Check if user has entered values in all fields
                if (!user_name.getText().isEmpty() && !user_email.getText().trim().isEmpty() && !user_password.getText().trim().isEmpty()) {
                    // Attempt to register user using the input values
                    DBController.RegistrateUser(event, user_name.getText(), user_email.getText(), user_password.getText());
                } else {
                    System.out.println("You need to fill all text fields!");
                    Alert alert = new Alert(Alert.AlertType.ERROR); 
                    alert.setContentText("Fill all Text fields!");
                    alert.show();
                }               
            }
        });
        // Go to login window button action listener
        gologin_button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                DBController.changeScene(event, "/fxml/Welcome_Window.fxml", "Login!", 0);
            }
         });
    }    
    
}
