/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.syssoft_assignment;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
/**

This is the controller class for the Edit Profile page.

It handles user input and updates the user's profile information in the database.

It also retrieves the user's information from the database and displays it on the page.

@author ntu-user
*/

public class EditProfileController implements Initializable {
// The ID of the currently logged in user    
    int UserId = UserSession.getUserID();
    
  

    // FXML fields for the user's name and email
    @FXML
    private TextField user_name;
    @FXML
    private TextField user_email;

    // FXML fields for displaying the user's name and email
    @FXML
    private Label label_fname;
    @FXML
    private Label label_email;

    // FXML buttons for updating the user's name and email
    @FXML
    private Button update_fname;
    @FXML
    private Button update_email;

    // FXML button for returning to the main menu
    @FXML
    private Button return_button;

/**

Initializes the controller class.

Sets the action handlers for the update and return buttons.

Retrieves the user's information from the database and displays it on the page.

@param url The location used to resolve relative paths for the root object, or null if the location is not known.

@param rb The resources used to localize the root object, or null if the root object was not localized.
*/    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the action handler for the update name button
        update_fname.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {               
                Connection connection1 = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;
                try {
                    // Update the user's name in the database
                    connection1 = DriverManager.getConnection("jdbc:sqlite:/home/ntu-user/NetBeansProjects/System-Software-Assessment/CloudStorageDB.db");
                    preparedStatement = connection1.prepareStatement("UPDATE Users SET FullName = ? WHERE UserId = ?");
                    preparedStatement.setString(1, user_name.getText());
                    preparedStatement.setInt(2, UserId);
                    preparedStatement.executeUpdate();
                    
                    
                    connection1.close();
                } catch (SQLException e) {
                   e.printStackTrace(); 
                }  
                }
            }); 
        // Set the action handler for the update email button
        update_email.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {               
                Connection connection1 = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;
                try {
                    // Update the user's email in the databas
                    connection1 = DriverManager.getConnection("jdbc:sqlite:/home/ntu-user/NetBeansProjects/System-Software-Assessment/CloudStorageDB.db");       
                    preparedStatement = connection1.prepareStatement("UPDATE Users SET Email = ? WHERE UserId = ?");
                    preparedStatement.setString(1, user_email.getText());
                    preparedStatement.setInt(2, UserId);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }); 
        
        // Set the action handler for the return button
        return_button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                // Return to the main menu
                DBController.changeScene(event, "/fxml/loggedIn.fxml", "Main menu", UserId);
            }
        });
    }
        
        // Set the user's name and email fields to their current values in the database
        public void setUserInfo(int UserId) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:/home/ntu-user/NetBeansProjects/System-Software-Assessment/CloudStorageDB.db");
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT FullName, Email FROM Users WHERE UserId = ?");
                preparedStatement.setInt(1, UserId); 
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
/**

Retrieves the user's information from the database and displays it on the page.
@param UserId The ID of the
*/                     
                    String FullName = resultSet.getString("FullName");
                    String Email = resultSet.getString("Email");            
                    label_fname.setText(FullName);
                    label_email.setText(Email);    
                    
                    connection.close();
                } else {
                    System.out.println("No user found!");
                    connection.close();
                }
                
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }      
        
        
    }    
    
    
    
    
    

