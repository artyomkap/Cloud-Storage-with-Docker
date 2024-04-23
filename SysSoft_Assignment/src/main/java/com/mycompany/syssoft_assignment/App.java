/**

@file App.java
@brief JavaFX application entry point
@author [Author Name]
@date [Date]
*/
package com.mycompany.syssoft_assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**

@class App

@brief JavaFX application entry point
*/
public class App extends Application {

/**

@brief JavaFX application scene
*/
    private static Scene scene;
/**

@brief JavaFX application entry point
@param stage JavaFX stage
@throws IOException if an I/O error occurs while reading a FXML file
*/
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Welcome_Window"));
        stage.setScene(scene);
        stage.show();
    }
    /**

@brief Loads a JavaFX scene graph from an FXML document
@param fxml FXML file name (without extension)
@return JavaFX scene graph
@throws IOException if an I/O error occurs while reading the FXML file
*/
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
/**

@brief Main entry point for the JavaFX application
@param args command line arguments
*/
    public static void main(String[] args) {
        launch();
    }
}