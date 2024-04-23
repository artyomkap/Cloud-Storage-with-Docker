/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
/**

@file CreateController.java
@brief This file contains the implementation of the CreateController class which is an FXML Controller for creating a new file.
It contains the necessary methods to handle the user input from the GUI and write the input to a new file.
@author t0322864
@date 2023-04-24
*/

package com.mycompany.syssoft_assignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
/**

@brief The CreateController class is an FXML Controller for creating a new file.

It contains the necessary methods to handle the user input from the GUI and write the input to a new file.
*/


public class CreateController implements Initializable {

/**

@brief The save button that triggers the file creation process
*/    
    
    @FXML
    private Button save_button;
    
 /**

@brief The text field for the user to enter the desired file name
*/   
    @FXML
    private TextField file_name;
 /**

@brief The text area for the user to enter the contents of the new file
*/   
    @FXML 
    private TextArea file_text;

    /**

@brief Initializes the controller class and sets up the save button to write the input to a file.

@param url The URL of the FXML file

@param rb The resource bundle for the FXML file
*/

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        save_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    try {
                        // Get the desired file name and the text from the GUI
                        String fileName = file_name.getText();
                        
                        String text = file_text.getText();

                        
                        File file = new File(fileName);

                        
                        FileWriter fileWriter = new FileWriter(file);

                        
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        
                        bufferedWriter.write(text);

                        
                        bufferedWriter.close();
                        fileWriter.close();
                        
                        Alert alert = new Alert(Alert.AlertType.INFORMATION); 
                        alert.setContentText("The file was created successfully");
                        alert.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }            
            
        }); 
    }    
    
    
    
    
    
}
