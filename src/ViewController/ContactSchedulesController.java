/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import DAO.AppointmentDAOImpl;
import DAO.ContactDAOImpl;
import DAO.DBConnection;
import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controller class for contact schedules screen.
 *
 * @author Trevor Ross, tros114@wgu.edu
 */
public class ContactSchedulesController implements Initializable {
    
    Stage stage;
    Parent scene;

    private ObservableList<String> menuContacts = FXCollections.observableArrayList();
    private ObservableList<Integer> menuContactsIds = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Appointment> contactScheduleTable;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> startCol;

    @FXML
    private TableColumn<Appointment, String> endCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    private ComboBox<String> contactMenu;

    @FXML
    private Button back;
    
    /**
    * Initializes the controller class.  Populates the contacts menu.
    * 
    * @param url
    * @param rb
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuContacts = ContactDAOImpl.menuContacts();
        menuContactsIds = ContactDAOImpl.menuContactIds();
        contactMenu.setItems(ContactDAOImpl.menuContacts());
    }

    /**
     * Returns user to main menu.
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
     * Sets table view to a schedule for the selected contact.
     * 
     * @param event
     */
    @FXML
    void selectContact(ActionEvent event) throws ParseException {
        String selectedContact = contactMenu.getSelectionModel().getSelectedItem();
        int selectedContactId = menuContactsIds.get(menuContacts.indexOf(selectedContact));
        contactScheduleTable.setItems(ContactDAOImpl.getAppointments(selectedContactId));
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<> ("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<> ("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<> ("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<> ("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<> ("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<> ("customerId"));
    }
    

}    
