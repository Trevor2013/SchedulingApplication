/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import DAO.AppointmentDAOImpl;
import DAO.ContactDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.DBConnection;
import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller class for the Appointment Screen
 *
 * @author Trevor Ross, tros114@wgu.edu
 */
public class AppointmentScreenController implements Initializable {

    private ObservableList<String> menuContacts = FXCollections.observableArrayList();
    private ObservableList<Integer> menuContactsIds = FXCollections.observableArrayList();
    
    
    private static String pattern = "yyyy-MM-dd HH:mm:ss";
    private static String pattern2 = "HH:mm:ss";
    private static final DateFormat dateTimeFormatLocal = new SimpleDateFormat(pattern);
    private static TimeZone localTZ = TimeZone.getDefault();
    
    private static final DateFormat dateTimeFormatEST = new SimpleDateFormat(pattern);
    private static TimeZone estTZ = TimeZone.getTimeZone("EST");
    private static final DateFormat dateTimeFormatUTC = new SimpleDateFormat(pattern);
    private static TimeZone utcTZ = TimeZone.getTimeZone("UTC");
    private static final SimpleDateFormat timeFormatEST = new SimpleDateFormat(pattern2);
    
    Stage stage;
    Parent scene;
    
    @FXML
    private TableView<Appointment> appointmentTable;
    
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleCol;

    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionCol;

    @FXML
    private TableColumn<Appointment, String> appointmentLocationCol;

    @FXML
    private TableColumn<Appointment, String> appointmentContactCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointment, String> appointmentStartCol;

    @FXML
    private TableColumn<Appointment, String> appointmentEndCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerIdCol;

    @FXML
    private RadioButton monthViewRadioButton;

    @FXML
    private ToggleGroup MonthWeekTG;

    @FXML
    private RadioButton weekViewRadioButton;

    @FXML
    private RadioButton viewAllRadioButton;

    @FXML
    private Button addAppointment;

    @FXML
    private Button updateAppointment;

    @FXML
    private Button deleteAppointment;

    @FXML
    private Button exit;

    @FXML
    private TextField appointmentIdText;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

    @FXML
    private Button cancel;

    @FXML
    private TextField typeText;

    @FXML
    private TextField startText;

    @FXML
    private TextField endText;

    @FXML
    private TextField customerIdText;

    @FXML
    private TextField userText;

    @FXML
    private ComboBox<String> selectContactMenu;

     /**
     * Initializes the controller class and populates the table.
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            appointmentTable.setItems(AppointmentDAOImpl.getAllAppointments());
        } catch (ParseException ex) {
            Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<> ("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<> ("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<> ("location"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<> ("contact"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<> ("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<> ("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<> ("end"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<> ("customerId"));
        menuContacts = ContactDAOImpl.menuContacts();
        selectContactMenu.setItems(ContactDAOImpl.menuContacts());
    }
    
    /**
     * Adds an appointment to the schedule using the information entered in the
     * text fields and menus.
     * 
     * @param event
     */
    @FXML
    void addAppointment(ActionEvent event) throws IOException, ParseException {

        menuContacts = ContactDAOImpl.menuContacts();
        menuContactsIds = ContactDAOImpl.menuContactIds();
        String selectedContact = selectContactMenu.getSelectionModel().getSelectedItem();
        int selectedContactId = menuContactsIds.get(menuContacts.indexOf(selectedContact));
        String title = new String(titleText.getText());
        String description = new String(descriptionText.getText());
        String location = new String(locationText.getText());
        String type = new String(typeText.getText());
        int customerId = Integer.parseInt(customerIdText.getText());
        int userId = Integer.parseInt(userText.getText());
        int contactId = selectedContactId;
        
        String startTS = startText.getText();
                
        ZoneId estZoneId = ZoneId.of("America/New_York");
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZoneId localZoneId = ZoneId.systemDefault();
        
        DateTimeFormatter longFormat = DateTimeFormatter.ofPattern(pattern);
        DateTimeFormatter shortFormat = DateTimeFormatter.ofPattern(pattern2);
        
        LocalDateTime startDate2 = LocalDateTime.parse(startTS, DateTimeFormatter.ofPattern(pattern));
        ZonedDateTime startDateLocal2 = startDate2.atZone(localZoneId);
        ZonedDateTime startDateEST2 = startDateLocal2.withZoneSameInstant(estZoneId);
        LocalTime startDateESTTime2 = startDateEST2.toLocalTime();
        ZonedDateTime startDateUTC2 = startDateLocal2.withZoneSameInstant(utcZoneId);
        
        //Strings may be used for troubleshooting
        String startDateLocalString2 = longFormat.format(startDateLocal2);
        String startTimeLocalString2 = longFormat.format(startDateLocal2);
        String startDateESTString2 = longFormat.format(startDateEST2);
        String startDateUTCString2 = longFormat.format(startDateUTC2);
        String startTimeESTString2 = shortFormat.format(startDateEST2);
        String startTimeUTCString2 = shortFormat.format(startDateUTC2);
      
        String endTS = endText.getText();
        
        LocalDateTime endDate2 = LocalDateTime.parse(endTS, DateTimeFormatter.ofPattern(pattern));
        ZonedDateTime endDateLocal2 = endDate2.atZone(localZoneId);
        ZonedDateTime endDateEST2 = endDateLocal2.withZoneSameInstant(estZoneId);
        LocalTime endDateESTTime2 = endDateEST2.toLocalTime();
        ZonedDateTime endDateUTC2 = endDateLocal2.withZoneSameInstant(utcZoneId);
        
        //Strings may be used for troubleshooting
        String endDateLocalString2 = longFormat.format(endDateLocal2);
        String endTimeLocalString2 = longFormat.format(endDateLocal2);
        String endDateESTString2 = longFormat.format(endDateEST2);
        String endDateUTCString2 = longFormat.format(endDateUTC2);
        String endTimeESTString2 = shortFormat.format(endDateEST2);
        String endTimeUTCString2 = shortFormat.format(endDateUTC2);
        
        String open = "08:00:00";
        String close = "22:00:00";
                
        LocalTime openTime = LocalTime.parse(open);
        LocalTime closeTime = LocalTime.parse(close);
        System.out.println(openTime);
        System.out.println(closeTime);
        String start = new String(startText.getText());
        String end = new String(endText.getText());

        if (selectedContact.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("No Contact Selected");
                alert.setContentText("A contact must be selected in order to add an appointment.");
                alert.showAndWait();
        }
        else if(title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || start.isEmpty() || end.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("One or More Fields is Empty");
                alert.setContentText("Verify all fields are filled out properly, then add the customer again.");
                alert.showAndWait();      
        }
        else if(startDateEST2.compareTo(endDateEST2) > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Start Date/Time is After End Date/Time");
                alert.setContentText("The end date and time must be later than the start date and time.");
                alert.showAndWait();
        }
        else if(startDateESTTime2.compareTo(openTime) < 0 || startDateESTTime2.compareTo(closeTime) > 0 || endDateESTTime2.compareTo(openTime) < 0 || endDateESTTime2.compareTo(closeTime) > 0)  {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment Outside of Working Hours");
                alert.setContentText("The start and end times must be within working hours of 8:00 AM EST to 10:00 PM EST.");
                alert.showAndWait();
        }
        else if(!AppointmentDAOImpl.getConflicts(customerId, startDateUTC2, endDateUTC2).isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment has Conflict");
                alert.setContentText("The specified appointment time conflicts with another appointment for that customer.  Please choose a different appointment time.");
                alert.showAndWait();
        }
        else{
            AppointmentDAOImpl.createAppointment(0, title, description, location, type, startTS, endTS, "", "", "", "", customerId, userId, contactId);
        
            appointmentTable.setItems(AppointmentDAOImpl.getAllAppointments());
            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
            appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<> ("title"));
            appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<> ("description"));
            appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<> ("location"));
            appointmentContactCol.setCellValueFactory(new PropertyValueFactory<> ("contact"));
            appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<> ("type"));
            appointmentStartCol.setCellValueFactory(new PropertyValueFactory<> ("start"));
            appointmentEndCol.setCellValueFactory(new PropertyValueFactory<> ("end"));
            appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<> ("customerId"));
            menuContacts = ContactDAOImpl.menuContacts();
            selectContactMenu.setItems(ContactDAOImpl.menuContacts());
        }
    }
    
    
    /**
     * Updates an appointment in the schedule
     * 
     * @param event
     */
    @FXML
    void updateAppointment(ActionEvent event)  throws IOException, ParseException {
        
        //System.out.println("Local Time Zone: "+localTZ.toString());
        
        menuContacts = ContactDAOImpl.menuContacts();
        menuContactsIds = ContactDAOImpl.menuContactIds();
        String selectedContact = selectContactMenu.getSelectionModel().getSelectedItem();
        int selectedContactId = menuContactsIds.get(menuContacts.indexOf(selectedContact));
        String title = new String(titleText.getText());
        String description = new String(descriptionText.getText());
        String location = new String(locationText.getText());
        String type = new String(typeText.getText());
        String start = new String(startText.getText());
        String end = new String(endText.getText());
        int customerId = Integer.parseInt(customerIdText.getText());
        int userId = Integer.parseInt(userText.getText());
        int contactId = selectedContactId;
        int appointmentId = Integer.parseInt(appointmentIdText.getText());
        
        String startTS = startText.getText();
                
        ZoneId estZoneId = ZoneId.of("America/New_York");
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZoneId localZoneId = ZoneId.systemDefault();
        
        DateTimeFormatter longFormat = DateTimeFormatter.ofPattern(pattern);
        DateTimeFormatter shortFormat = DateTimeFormatter.ofPattern(pattern2);
        
        LocalDateTime startDate2 = LocalDateTime.parse(startTS, DateTimeFormatter.ofPattern(pattern));
        ZonedDateTime startDateLocal2 = startDate2.atZone(localZoneId);
        ZonedDateTime startDateEST2 = startDateLocal2.withZoneSameInstant(estZoneId);
        LocalTime startDateESTTime2 = startDateEST2.toLocalTime();
        ZonedDateTime startDateUTC2 = startDateLocal2.withZoneSameInstant(utcZoneId);
        
        //Strings may be used for troubleshooting
        String startDateLocalString2 = longFormat.format(startDateLocal2);
        String startTimeLocalString2 = longFormat.format(startDateLocal2);
        String startDateESTString2 = longFormat.format(startDateEST2);
        String startDateUTCString2 = longFormat.format(startDateUTC2);
        String startTimeESTString2 = shortFormat.format(startDateEST2);
        String startTimeUTCString2 = shortFormat.format(startDateUTC2);
        
        String endTS = endText.getText();
        
        LocalDateTime endDate2 = LocalDateTime.parse(endTS, DateTimeFormatter.ofPattern(pattern));
        ZonedDateTime endDateLocal2 = endDate2.atZone(localZoneId);
        ZonedDateTime endDateEST2 = endDateLocal2.withZoneSameInstant(estZoneId);
        LocalTime endDateESTTime2 = endDateEST2.toLocalTime();
        ZonedDateTime endDateUTC2 = endDateLocal2.withZoneSameInstant(utcZoneId);
        
        //Strings may be used for troubleshooting
        String endDateLocalString2 = longFormat.format(endDateLocal2);
        String endTimeLocalString2 = longFormat.format(endDateLocal2);
        String endDateESTString2 = longFormat.format(endDateEST2);
        String endDateUTCString2 = longFormat.format(endDateUTC2);
        String endTimeESTString2 = shortFormat.format(endDateEST2);
        String endTimeUTCString2 = shortFormat.format(endDateUTC2);

        String open = "08:00:00";
        String close = "22:00:00";
                
        LocalTime openTime = LocalTime.parse(open);
        LocalTime closeTime = LocalTime.parse(close);
        System.out.println(openTime);
        System.out.println(closeTime);

        if (selectedContact.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("No Contact Selected");
                alert.setContentText("A contact must be selected in order to update an appointment.");
                alert.showAndWait();
        }
        else if(title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || start.isEmpty() || end.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("One or More Fields is Empty");
                alert.setContentText("Verify all fields are filled out properly, then re-attempt update.");
                alert.showAndWait();     
        }
        else if(startDateEST2.compareTo(endDateEST2) > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("End Date/Time is After Start Date/Time");
                alert.setContentText("The end date and time must be later than the start date and time.");
                alert.showAndWait();
        }
        else if(startDateESTTime2.compareTo(openTime) < 0 || startDateESTTime2.compareTo(closeTime) > 0|| endDateESTTime2.compareTo(openTime) < 0 || endDateESTTime2.compareTo(closeTime) > 0)  {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment Outside of Working Hours");
                alert.setContentText("The start and end times must be within working hours of 8:00 AM EST to 10:00 PM EST.");
                alert.showAndWait();
        }
        else if(!AppointmentDAOImpl.getUpdateConflicts(appointmentId, customerId, startDateUTC2, endDateUTC2).isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment has Conflict");
                alert.setContentText("The specified appointment time conflicts with another appointment for that customer.  Please choose a different appointment time.");
                alert.showAndWait();
        }
        else{
        AppointmentDAOImpl.updateAppointment(appointmentId, title, description, location, type, start, end, "", "", "", "", customerId, userId, contactId);

            appointmentTable.setItems(AppointmentDAOImpl.getAllAppointments());
            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
            appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<> ("title"));
            appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<> ("description"));
            appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<> ("location"));
            appointmentContactCol.setCellValueFactory(new PropertyValueFactory<> ("contact"));
            appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<> ("type"));
            appointmentStartCol.setCellValueFactory(new PropertyValueFactory<> ("start"));
            appointmentEndCol.setCellValueFactory(new PropertyValueFactory<> ("end"));
            appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<> ("customerId"));
            menuContacts = ContactDAOImpl.menuContacts();
            selectContactMenu.setItems(ContactDAOImpl.menuContacts());
            viewAllRadioButton.isSelected();
        }

    }
    
    /**
     * Returns user to main screen
     * 
     * @param event
     */
    @FXML
    void cancel(ActionEvent event) throws IOException {
        stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes an appointment from the schedule
     * 
     * @param event
     */
    @FXML
    void deleteAppointment(ActionEvent event) throws ParseException {
        
        int appointmentId = Integer.parseInt(appointmentIdText.getText());
        String appointmentType = typeText.getText();
        
        AppointmentDAOImpl.deleteAppointment(appointmentId);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Deleted");
                alert.setHeaderText("Appointment has been deleted.");
                alert.setContentText("Appointment has been deleted. Appointment ID: "+appointmentId +", Type: "+appointmentType);
                alert.showAndWait();
        
        appointmentTable.setItems(AppointmentDAOImpl.getAllAppointments());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<> ("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<> ("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<> ("location"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<> ("contact"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<> ("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<> ("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<> ("end"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<> ("customerId"));
        menuContacts = ContactDAOImpl.menuContacts();
        selectContactMenu.setItems(ContactDAOImpl.menuContacts());
        viewAllRadioButton.isSelected();
    }

    /**
     * Changes table view to show all appointments.
     * 
     * @param event
     */
    @FXML
    void displayAll(ActionEvent event) throws ParseException {
        appointmentTable.setItems(AppointmentDAOImpl.getAllAppointments());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<> ("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<> ("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<> ("location"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<> ("contact"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<> ("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<> ("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<> ("end"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<> ("customerId"));
        menuContacts = ContactDAOImpl.menuContacts();
        selectContactMenu.setItems(ContactDAOImpl.menuContacts());
    }

    /**
     * Changes table view to show current month's appointments
     * 
     * @param event
     */
    @FXML
    void displayMonthView(ActionEvent event) throws ParseException {
        
        appointmentTable.setItems(AppointmentDAOImpl.getMonthAppointments());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<> ("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<> ("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<> ("location"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<> ("contact"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<> ("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<> ("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<> ("end"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<> ("customerId"));
        menuContacts = ContactDAOImpl.menuContacts();
        selectContactMenu.setItems(ContactDAOImpl.menuContacts());
    }

    /**
     * Displays appointments from the current week in the table
     * 
     * @param event
     */
    @FXML
    void displayWeekView(ActionEvent event) throws ParseException {
        
        appointmentTable.setItems(AppointmentDAOImpl.getWeekAppointments());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<> ("id"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<> ("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<> ("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<> ("location"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<> ("contact"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<> ("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<> ("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<> ("end"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<> ("customerId"));
        menuContacts = ContactDAOImpl.menuContacts();
        selectContactMenu.setItems(ContactDAOImpl.menuContacts());
    }

    @FXML
    void exit(ActionEvent event) {
        //Not implemented
    }

    @FXML
    void selectContact(ActionEvent event) {
        //Not implemented
    }
    
    /**
     * Populates or depopulates the text fields based on the appointment
     * selection in the table.
     * 
     * @param event
     */
    @FXML
    void selectAppointment(MouseEvent event) throws ParseException {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedAppointment);

        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            appointmentIdText.setText(Integer.toString(selectedAppointment.getId()));
            titleText.setText(selectedAppointment.getTitle());
            descriptionText.setText(selectedAppointment.getDescription());
            locationText.setText(selectedAppointment.getLocation());
            typeText.setText(selectedAppointment.getType());
            startText.setText(selectedAppointment.getStart());
            endText.setText(selectedAppointment.getEnd());
            customerIdText.setText(Integer.toString(selectedAppointment.getCustomerId()));
            userText.setText(Integer.toString(selectedAppointment.getUserId()));
            selectContactMenu.setValue(selectedAppointment.getContact());
        }
        else {  
            appointmentTable.setItems(AppointmentDAOImpl.getAllAppointments());
            appointmentIdText.clear();
            titleText.clear();
            descriptionText.clear();
            locationText.clear();
            typeText.clear();
            startText.clear();
            endText.clear();
            customerIdText.clear();
            userText.clear();
            selectContactMenu.setValue(null);

        }
    }
}

    

