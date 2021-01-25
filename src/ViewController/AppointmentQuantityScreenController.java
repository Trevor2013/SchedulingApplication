/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import DAO.AppointmentDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * AppointmentQuantityScreenController is the controller class for the
 * AppointmentQuantityScreen
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class AppointmentQuantityScreenController implements Initializable  {

    Stage stage;
    Parent scene;
    
    ObservableList<String> typeList = FXCollections.observableArrayList();
    ObservableList<String> monthList = FXCollections.observableArrayList("January", 
            "February", "March", "April", "May", "June", "July", "August", 
            "September", "October", "November", "December");
            
    @FXML
    private ComboBox<String> typeMenu;

    @FXML
    private ComboBox<String> monthMenu;

    @FXML
    private Button retrieveResults;

    @FXML
    private Label resultsLabel;

    @FXML
    private Button backButton;

    /**
     * Return user to main screen.
     * 
     * @param event
     */
    @FXML
    void back(ActionEvent event) throws IOException {
        stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Displays the number of appointments of a given type and in a given month
     * 
     * @param event
     */
    @FXML
    void displayResults(ActionEvent event) {
        String month = new String(monthMenu.getValue());
        String type = new String(typeMenu.getValue());
        Number count = AppointmentDAOImpl.apptQuantity(month, type);
        String countResults = count.toString();
        resultsLabel.setText(countResults);
    }

    @FXML
    void selectMonth(ActionEvent event) {
        //Not implemented
    }

    @FXML
    void selectType(ActionEvent event) {
        //Not implemented
    }

    /**
     * Initializes the screen and populates the menus
     * 
     * @param url
     * @param rb
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            typeList = AppointmentDAOImpl.getTypes();
        } 
        catch (ParseException ex) {
            Logger.getLogger(AppointmentQuantityScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        typeMenu.setItems(typeList);
        monthMenu.setItems(monthList);
    }

}

