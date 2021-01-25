/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import DAO.AppointmentDAOImpl;
import DAO.CustomerDAOImpl;
import Model.Customer;
import Model.Appointment;
import DAO.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class for main menu screen
 *
 * @author Trevor Ross, tros114@wgu.edu
 */
public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;
    
    Number numLongAppointments;
    
    @FXML
    private Button appointments;
    
    @FXML
    private Button customers;

    @FXML
    private Button exit;
    
    @FXML
    private Button appointmentQuantityButton;

    @FXML
    private Button contactSchedulesButton;

    @FXML
    private Button longAppointmentsButton;

    /**
     * Initializes the controller class.
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
               
    }

    /**
     * Moves user to contact schedule screen.
     * 
     * @param event
     */
    @FXML
    void goToContactSchedules(ActionEvent event) throws IOException {
        stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("ContactSchedules.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * Display a report showing the number of appointments >1 hour in length
     * 
     * @param event
     */
    @FXML
    void goToLongAppointments(ActionEvent event) {
        
        numLongAppointments = AppointmentDAOImpl.getLongAppointments();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Appointments Longer than 1 Hour");
            alert.setContentText("The number of appointments in the schedule longer than 1 hour is: "+numLongAppointments);
            alert.showAndWait();
    }

    /**
     * Go to the Appointment Quantity Screen.
     * 
     * @param event
     */
    @FXML
    void viewAppointmentQuantity(ActionEvent event) throws ParseException, IOException {
        stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AppointmentQuantityScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * Go to the appointments screen.
     * 
     * @param event
     */
    @FXML
    private void goToAppointments (ActionEvent event) throws IOException {
        stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AppointmentScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * Go to the customers screen.
     * 
     * @param event
     */
    @FXML
    void goToCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("CustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Exit the program and end the database connection.
     * 
     * @param event
     */
    @FXML
    private void exit(ActionEvent event) {
        DBConnection.endConnection();
        System.exit(0);
    }
    
}
