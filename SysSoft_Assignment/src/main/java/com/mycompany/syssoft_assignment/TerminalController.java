/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


/**

@file TerminalController.java
@brief This file contains the implementation of the Terminal Controller class.
This class handles the user interaction with the terminal UI and provides the functionality to execute bash commands.
@author t0322864
@date 2023-04-24
*/

package com.mycompany.syssoft_assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author ntu-user
 */

/**

@class TerminalController

@brief This class handles the user interaction with the terminal UI and provides the functionality to execute bash commands.
*/
public class TerminalController implements Initializable {
    
    

    @FXML
    private Button logout_button;
    
    @FXML
    private Label username_label;
    
    @FXML
    private Button terminal_button;
    
    @FXML
    private TextField tf_terminal;
    
    @FXML
    private TextArea view_terminal;
    /**
     * Initializes the controller class.
     */
    
    /**

Initializes the controller class.

@param url The URL to initialize the controller.

@param rb The ResourceBundle to initialize the controller.
*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       terminal_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
                executeCommand(tf_terminal.getText());
                
            }
        }); 
       
    }  
    
 /**

This method sets the username label with the user's full name.
@param FullName The full name of the user.
*/   
    public void setUserInfo(String FullName) {
        username_label.setText(("Welcome," + FullName + "!"));
    }

/**

This method executes a bash command.
@param command The command to be executed.
*/    
    
    public void executeCommand(String command) {
    try {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
            view_terminal.appendText(line + "\n");
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    }


    
}

