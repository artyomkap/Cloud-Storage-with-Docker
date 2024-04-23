
/**

FXML Controller class for the Welcome Window.
This class is responsible for controlling the behavior of the Welcome Window.
It initializes the login and registration buttons, and sets their event handlers.
The class also contains the necessary text fields for inputting user email and password.
@author ntu-user
*/

package com.mycompany.syssoft_assignment;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class Welcome_WindowController implements Initializable {

    @FXML
    private Button login_button;
    
    @FXML
    private Button goregister_button;
    
    @FXML
    private TextField user_email;
    
    @FXML
    private PasswordField user_password;
    
/**
 * Initializes the controller class.
 * 
 * This method sets the event handlers for the login and registration buttons.
 * It creates instances of the DBController class to handle the database operations.
 * 
 * @param url The location used to resolve relative paths for the root object.
 * @param rb  The resources used to localize the root object.
 */    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        login_button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                DBController.LoginUser(event, user_email.getText(), user_password.getText());
            }
        });
        
        goregister_button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                DBController.changeScene(event, "/fxml/Registration.fxml", "Register!", 0);
            }
         });
    }    
    
}
