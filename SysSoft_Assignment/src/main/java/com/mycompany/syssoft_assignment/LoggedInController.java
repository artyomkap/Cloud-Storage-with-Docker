package com.mycompany.syssoft_assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author ntu-user
 */

/**

FXML Controller class for the LoggedIn window. Handles user actions such as creating, uploading, renaming and deleting files, opening a terminal window, editing profile information, and logging out.

@author ntu-user
*/

public class LoggedInController implements Initializable { 
    // Map of container names to their IP addresses
    private Map<String, String> containerNamesToIpMap = new HashMap<>();
    // List of IP addresses of all containers
    private static List<String> Ipcontainers = new ArrayList<>();
    // Map of container names to their IP addresses
    private static final Map<String, String> CONTAINERS_INFO = new HashMap<>();
    static {
        CONTAINERS_INFO.put("comp20081-simple-container", "172.18.0.3");
        CONTAINERS_INFO.put("comp20081-simple-container2", "172.18.0.4");
        CONTAINERS_INFO.put("comp20081-simple-container3", "172.18.0.5");
        CONTAINERS_INFO.put("comp20081-simple-container4", "172.18.0.6");
        
        Ipcontainers.addAll(CONTAINERS_INFO.values());
    }
    
    private Stage stage;
    
    @FXML
    private Button edit_button;
    
    @FXML
    private Button logout_button;
    
    @FXML 
    private Label username_label;
    
    @FXML
    private Button create_file;
    
    @FXML
    private Button upload_file;
    
    @FXML
    private ListView view_files;
    
    @FXML
    private Button delete_file;
    
    @FXML
    private Button rename_file;
    
    @FXML 
    private Button update_button;
    
    @FXML 
    private Button terminal_button;
    
/**

Initializes the controller class.
Sets up event handlers for buttons on the LoggedIn window.
@param url The URL to the FXML document for the LoggedIn window.
@param resources The resources used by the FXML document for the LoggedIn window.
*/
   @Override
    public void initialize(URL url, ResourceBundle resources) {
        // Get the user ID of the logged in use
        int userId = UserSession.getUserID();
        // Set up an event handler for the Edit Profile button
        edit_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
                // Load the EditProfile window with the user's ID
                DBController.changeScene2(event,"/fxml/EditProfile.fxml", "login!", userId);
                
            }
        }); 
/**
@brief This method loads the list of files in the cloud storage.
 It catches IOException and InterruptedException exceptions and prints the stack trace if any of the exceptions occur.
*/
        try {
        loadFileList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(LoggedInController.class.getName()).log(Level.SEVERE, null, ex);
        }
/**
@brief This method sets the action for the logout button. It changes the scene to the Welcome_Window.fxml and displays "login!" message.
@param event The event object that was triggered by clicking the logout button.
*/        
        logout_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBController.changeScene(event,"/fxml/Welcome_Window.fxml", "login!", 0);
            }
        });    
/**
@brief This method sets the action for the create file button. It changes the scene to the Create.fxml and displays "CreateFile" message.
@param event The event object that was triggered by clicking the create file button.
*/        
        create_file.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBController.changeScene(event,"/fxml/Create.fxml", "CreateFile", 0);
            }
            
        });  
/**
@brief This method sets the action for the upload file button. It calls the selectAndSplitFile() method.
@param event The event object that was triggered by clicking the upload file button.
*/            
        upload_file.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
               selectAndSplitFile(); 
            }
        });    
/**
@brief This method sets the action for the rename file button. It calls the renameFile() method.
@param event The event object that was triggered by clicking the rename file button.
*/        
        rename_file.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                renameFile();
            }
        });
/**
@brief This method sets the action for the delete file button. It calls the deleteFile() method.
@param event The event object that was triggered by clicking the delete file button.
*/         
        delete_file.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                deleteFile();
            }
        });
/**

@brief This method sets the action for the update button. It calls the loadFileList() method to reload the file list.
   It catches IOException and InterruptedException exceptions and prints the stack trace if any of the exceptions occur.
@param event The event object that was triggered by clicking the update button.
*/        
        update_button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try {
                    loadFileList();
                } catch (IOException ex) {
                    Logger.getLogger(LoggedInController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LoggedInController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
/**
@brief This method sets the action for the terminal button. It changes the scene to the Terminal.fxml and displays "Terminal!" message.
@param event The event object that was triggered by clicking the terminal button.
*/        
        terminal_button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                DBController.changeScene(event, "/fxml/Terminal.fxml", "Terminal!", 0);
            }
        });
        
    }

/**
@brief This method sets the user information by retrieving the full name from the database using the user ID.
It catches SQLException and InterruptedException exceptions and prints the stack trace if any of the exceptions occur.
@param UserId The user ID whose information is being
*/
    void setUserInfo(int UserId) {
        try {
            Thread.sleep(2000);
            Connection connection = DriverManager.getConnection("jdbc:sqlite:/home/ntu-user/NetBeansProjects/System-Software-Assessment/CloudStorageDB.db");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT FullName FROM Users WHERE UserId = ?");
            preparedStatement.setInt(1, UserId); 
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String FullName = resultSet.getString("FullName");
                username_label.setText(("Welcome," + FullName + "!"));
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }                              
    }
/**
Method to delete a file from all containers
*/    
    private void deleteFile() {
    // Get the selected file name
    String choosenFileName = (String) view_files.getSelectionModel().getSelectedItem();
    // If a file was selected
    if (choosenFileName != null) {
        // Iterate over all containers
        for (int i = 0; i < 4; i++) {
            // Get the IP address of the current container
            String containerIp = getContainerIpByIndex(i);
            // Construct the name of the file chunk to delete
            String chunkFileName = choosenFileName + "_chunk" + (i + 1);
            // Print a message to indicate that the chunk is being deleted
            System.out.println("Deleting file: " + chunkFileName + " from container: " + containerIp);
            // Call the DeleteFromContainer method to delete the file chunk from the container
            DeleteFromContainer(chunkFileName, containerIp);
        }
    }
}
/**

Method to delete a file chunk from a container
@param fileName the name of the file to delete
@param containerIp the IP address of the container to delete the file from
*/    
   private void DeleteFromContainer(String FileName, String containerIp) {
        try {
            // Create a ProcessBuilder object to execute the necessary command on the container
            ProcessBuilder pbMkdir = new ProcessBuilder("ssh", "root@" + containerIp, "mkdir -p /container/files /container/basket");
            pbMkdir.redirectErrorStream(true);
            Process processMkdir = pbMkdir.start();
            // Wait for the command to complete
            processMkdir.waitFor();
             // Create a ProcessBuilder object to execute the necessary command on the container
            ProcessBuilder pb = new ProcessBuilder("ssh", "root@" + containerIp, "mv /container/files/" + FileName + " /container/basket/");
            pb.redirectErrorStream(true);
            Process process = pb.start();
             // Wait for the command to complete
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    
   }
/**
Method to rename a selected file
*/    
    private void renameFile() {
    String choosenFileName = (String) view_files.getSelectionModel().getSelectedItem();
    // Get the selected file name
    if (choosenFileName != null) {
        // Create a TextInputDialog to prompt the user for the new file name
        TextInputDialog dialog = new TextInputDialog(choosenFileName);
        dialog.setTitle("Change File Name");
        dialog.setHeaderText("Type In the name of the file:");
        Optional<String> result = dialog.showAndWait();
        // If the user entered a new file name
        result.ifPresent(newName -> {            
            for (int i = 0; i < 4; i++) {
                 // Get the IP address of the current container
            String containerIp = getContainerIpByIndex(i);
            // Construct the names of the old and new file chunks to rename
            String oldFileName = choosenFileName + "_chunk" + (i + 1);
            String newFileName = newName + "_chunk" + (i + 1);
            // Call the renameFileInContainer method to rename the file chunk in the container
            renameFileInContainer(oldFileName, newFileName, containerIp);
            }

            
                    // Remove the old file name from the list view
             view_files.getItems().remove(choosenFileName);
             // Add the new file name to the list view
             view_files.getItems().add(newName);
        });
    }
}
/**
 * Renames a file in a container using SSH.
 * @param oldFileName The old name of the file.
 * @param newFileName The new name of the file.
 * @param containerIp The IP address of the container where the file is located.
 */
private void renameFileInContainer(String oldFileName, String newFileName, String containerIp) {
    try {
        // Create a new process that executes a command to rename the file in the container
        ProcessBuilder pb = new ProcessBuilder("ssh", "root@" + containerIp, "mv /container/files/" + oldFileName + " /container/chunks/" + newFileName);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
}


/**
 * Loads the list of files from the first container and displays them in a view.
 * @throws IOException If an I/O error occurs.
 * @throws InterruptedException If the current thread is interrupted while waiting for the process to finish.
 */    
    private void loadFileList() throws IOException, InterruptedException {
    // Use the first container as a reference to get the list of files
    
        String containerIp = getContainerIpByIndex(0);
        // Create a new process that executes a command to list the files in the container
        ProcessBuilder pb = new ProcessBuilder("ssh", "root@" + containerIp, "ls /container/files");
        pb.redirectErrorStream(true);
        Process process = pb.start();
        // Read the output of the command and add each file name to the view
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String row;
        while ((row = reader.readLine()) != null) {        
            view_files.getItems().add(row.replaceAll("_chunk1", ""));
        }

    process.waitFor();
}
/**
 * Opens a file chooser dialog, splits the selected file into 4 chunks, and saves them to corresponding files.
 */        
    public void selectAndSplitFile() {
        // Create a new file chooser dialog and wait for the user to select a file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file!");      

        
        File selectedFile = fileChooser.showOpenDialog(stage);
        // If a file is selected, split it into 4 chunks and save them to corresponding files
        String FileName = selectedFile.getName();
        
         if (selectedFile != null) {
            try {
                // Read the contents of the file into a byte array
                byte[] fileBytes = Files.readAllBytes(selectedFile.toPath());
                
                int UserId = UserSession.getUserID();
                // Split the file into 4 chunks
                int chunkSize = (int) Math.ceil((double) fileBytes.length / 4);
                byte[][] chunks = new byte[4][];
                for (int i = 0; i < 4; i++) {
                int start = i * chunkSize;
                int end = Math.min(start + chunkSize, fileBytes.length);
                chunks[i] = Arrays.copyOfRange(fileBytes, start, end);     
                // Create a temporary folder for storing chunks
                File folder = new File("Chunks");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                // Create a temporary file for the chunk
                File tempFile = new File(folder, "{" + UserId + "}" + FileName + "_chunk" + i);                
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                outputStream.write(chunks[i]);
                outputStream.close();
                String Ipcontainer = getContainerIpByIndex(i);
                

               // Copy the chunk file to the corresponding container
                copyToDockerContainer(tempFile, Ipcontainer);
                
                
                
                }
                
                
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }
    }

/**
 * Returns the IP address of a container based on its index in the list of containers.
 * @param index the index of the container
 * @return the IP address of the container
 */
     private String getContainerIpByIndex(int index) {
        return new ArrayList<>(CONTAINERS_INFO.values()).get(index);
    }
         

/**
 * Copies a file to a Docker container using SSH and SCP.
 * @param tempFile the file to copy
 * @param containerIP the IP address of the container to copy the file to
 */   
    private void copyToDockerContainer(File tempFile, String containerIP) {
    try {     
        // Connect to the container via SSH
        System.out.println("Open the containers: " + containerIP);
        ProcessBuilder pb = new ProcessBuilder("ssh", "root@" + containerIP);
        pb.redirectErrorStream(true);
        pb.start();
        // Create a directory on the container for the file
        System.out.println("Creating the directory for: " + containerIP);
        ProcessBuilder CreateDir = new ProcessBuilder("ssh", "root@" + containerIP, "mkdir -p /container/files");
        CreateDir.redirectErrorStream(true);
        Process processCreate = CreateDir.start();        
        
        
        // Copy the file to the container using SCP
        System.out.println("Copying " + tempFile + " to container " + containerIP);
        System.out.println("scp " + tempFile.getAbsoluteFile() + " root@" + containerIP + ":/container/files/");        
        ProcessBuilder Upload = new ProcessBuilder("scp", tempFile.getAbsolutePath(), "root@" +  containerIP + ":/container/files/"); 
        Upload.environment().put("PATH", "/usr/bin:/bin:/usr/sbin:/sbin");        
        Process processUpload = Upload.start();
        // Print any errors that occur during the copy process
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(processUpload.getErrorStream()));
        String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            System.out.println("Error: " + errorLine);
        }
        
        // Wait for the copy process to complete and print its exit value
        processUpload.waitFor();
        int uploadExitValue = processUpload.exitValue();
        System.out.println("Upload exit value: " + uploadExitValue);
        
        
     } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
}



}
