/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.syssoft_assignment;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 *
 * @author ntu-user
 */
public class DBController {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, int UserId) {
        Parent root = null;
        
        if (UserId != 0) {
            try {
                FXMLLoader loader = new FXMLLoader(DBController.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInfo(UserId);
                
                
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBController.class.getResource(fxmlFile));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));       
        stage.show();
    }
    
     public static void changeScene2(ActionEvent event, String fxmlFile, String title, int UserId) {
        Parent root = null;
        
        if (UserId != 0) {
            try {
                FXMLLoader loader = new FXMLLoader(DBController.class.getResource(fxmlFile));
                root = loader.load();
                EditProfileController editprofilecontroller= loader.getController();
                editprofilecontroller.setUserInfo(UserId);
                
                
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBController.class.getResource(fxmlFile));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));       
        stage.show();
    }
    
    
    
public static void RegistrateUser(ActionEvent event, String FullName, String Email, String Password) {
    try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/home/ntu-user/NetBeansProjects/System-Software-Assessment/CloudStorageDB.db");
         PreparedStatement psCheckUserExists = connection.prepareStatement("SELECT UserId FROM Users WHERE Email = ?");
         PreparedStatement psInsert = connection.prepareStatement("INSERT INTO Users (FullName, Email, Password) VALUES (?, ?, ?)")) {

        psCheckUserExists.setString(1, Email);
        ResultSet resultSet = psCheckUserExists.executeQuery();

        if (resultSet.isBeforeFirst()) {
            System.out.println("User already exists");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("This Email already used!");
            alert.show();
        } else {
            psInsert.setString(1, FullName);
            psInsert.setString(2, Email);
            psInsert.setString(3, Password);
            psInsert.executeUpdate();

            resultSet = psCheckUserExists.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found!");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("User not found!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    int retrievedUserId = resultSet.getInt("UserId");
                    UserSession.setUserID(retrievedUserId);
                    changeScene(event, "/fxml/loggedIn.fxml", "Registrated!", retrievedUserId);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


/**

@brief Attempts to log in a user with the provided email and password.
This function connects to a SQLite database and queries for a user with the provided email.
If a user is found, their password is compared to the provided password. If the passwords match,
the user is logged in and the scene is changed to the main logged in view. Otherwise, an error
message is displayed and the user is not logged in.
@param event The ActionEvent that triggered the login attempt.
@param Email The email address of the user attempting to log in.
@param Password The password provided by the user attempting to log in.
*/

    public static void LoginUser(ActionEvent event, String Email, String Password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:/home/ntu-user/NetBeansProjects/System-Software-Assessment/CloudStorageDB.db");
            preparedStatement = connection.prepareStatement("SELECT Password, UserId FROM Users WHERE Email = ?");
            preparedStatement.setString(1, Email);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found!");
                Alert alert = new Alert(AlertType.ERROR); 
                alert.setContentText("User not found!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("Password");
                    int retrievedUserId = resultSet.getInt("UserId");
                    UserSession.setUserID(retrievedUserId);
                    if (retrievedPassword.equals(Password)) {
                        changeScene(event, "/fxml/loggedIn.fxml", "Logged in!", retrievedUserId);
                    }
                }
            }            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }    
               
        }
    }   
        
    
    
  
    
    
}