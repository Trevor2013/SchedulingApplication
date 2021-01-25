/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import DAO.AppointmentDAOImpl;
import DAO.DBConnection;
import DAO.Interface2;
import DAO.UserDAOImpl;
import Model.Appointment;
import Model.User;
import java.io.IOException;

import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;



/**
 * Controller class for the login form
 *
 * @author TRoss
 */
public class LoginFormController implements Initializable {

    Stage stage;
    Parent scene;
    private Locale userLocale = Locale.getDefault();

    public static User loggedInUser = new User();
    private static ObservableList<Appointment> checkList = FXCollections.observableArrayList();
    java.util.Date now = new java.util.Date();
    private static final DateFormat dateTimeFormatLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static TimeZone localTZ = TimeZone.getDefault();
    private static final DateFormat dateTimeFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static TimeZone utcTZ = TimeZone.getTimeZone("UTC");
    
    Logger logger = Logger.getLogger("login_activity.txt");  
    FileHandler fh;  
    
    @FXML
    private TextField userNameField;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label userLocationLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label titleLabel;

    @FXML
    private Label pleaseLogInLabel;

    @FXML
    private Button logInButton;

    @FXML
    private Button exitButton;
    
    @FXML
    private Label userLocation;

    /**
     * Initializes the controller class. Sets language based on user Locale.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        System.out.println(userLocale.toString());
        
        if("en_US".equals(userLocale.toString())) {
            userNameLabel.setText("User Name:");
            passwordLabel.setText("Password:");
            userLocationLabel.setText(userLocale.getDisplayCountry().toString());
            titleLabel.setText("Scheduling Application");
            pleaseLogInLabel.setText("Please Log In:");
            logInButton.setText("Log In");
            exitButton.setText("Exit");
            userLocation.setText("User Location:");
        }
        else {
            userNameLabel.setText("Nom d'utilisateur:");
            passwordLabel.setText("Mot de passe:");
            userLocationLabel.setText(userLocale.getDisplayCountry().toString());
            titleLabel.setText("Planification Application");
            pleaseLogInLabel.setText("Merci de vous connector");
            logInButton.setText("S'identifier");
            exitButton.setText("Sortie");
            userLocation.setText("Localisation de l'utilisateur:");
        }

        
    }   
    
    /**
     * Closes the application and closes the database connection.
     * 
     * @param event
     */
    @FXML
    void exit(ActionEvent event) {
        DBConnection.endConnection();
        System.exit(0);
    }

    /**
     * Checks the user's credentials, logs the user in, and writes login
     * activity to a log.  Displays alert stating whether there is an 
     * appointment within 15 minutes.
     * 
     * Section implements a lambda expression to convert time from UTC to local.
     * 
     * 
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void logIn(ActionEvent event) throws IOException, SQLException {
        
        String userName = new String(userNameField.getText());
        String password = new String(passwordField.getText());
        int userId = UserDAOImpl.getUserId(userName);
        
        try {  
                fh = new FileHandler("login_activity.txt", true);
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();  
                fh.setFormatter(formatter);  
            } catch (SecurityException e) {  
                e.printStackTrace();  
            }
        
        if (UserDAOImpl.checkPassword(userId, password)) {
            loggedInUser.setId(userId);
            loggedInUser.setUserName(userName);
            logger.info("SUCCESSFUL Login Attempt by User: "+userName +"; Time: "+now);;
            
            LocalDateTime currentDateTime = LocalDateTime.now();
            java.util.Date current = (java.util.Date) Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
            LocalDateTime checkDateTime = LocalDateTime.now().plus(Duration.of(15, ChronoUnit.MINUTES));
            java.util.Date check = (java.util.Date) Date.from(checkDateTime.atZone(ZoneId.systemDefault()).toInstant());
            checkList = UserDAOImpl.getReminderList(userId, current, check);
            Interface2 convert = s1 -> {
                    dateTimeFormatLocal.setTimeZone(localTZ);
                    dateTimeFormatUTC.setTimeZone(utcTZ);
                    java.util.Date dateTime = null;
                        try {
                            dateTime = dateTimeFormatUTC.parse(s1);
                        } catch (ParseException ex) {
                            Logger.getLogger(AppointmentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    String s = dateTimeFormatLocal.format(dateTime);
                    return s;
                    };
        
            if (checkList.isEmpty() && "en_US".equals(userLocale.toString())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("No Upcoming Appointments");
                alert.setContentText("There are no appointments in the next 15 minutes.");
                alert.showAndWait();
            }
            else if (!checkList.isEmpty() && "en_US".equals(userLocale.toString())) {
                String start = convert.convertToLocal(checkList.get(0).getStart2());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Upcoming Appointments!");
                alert.setContentText("There is an appointment within the next 15 minutes! \n" +
                        "Appointment ID: " +checkList.get(0).getApptId2() +"; Start Date/Time: " +start);
                alert.showAndWait();      
            }
            else if (checkList.isEmpty() && !"en_US".equals(userLocale.toString())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alerte!");
                alert.setHeaderText("No rendez-vous à venir");
                alert.setContentText("Il n'y a pas de rendez-vous dans les 15 prochaines minutes.");
                alert.showAndWait();      
            }
            else if (!checkList.isEmpty() && !"en_US".equals(userLocale.toString())) {
                String start = convert.convertToLocal(checkList.get(0).getStart2());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alerte!");
                alert.setHeaderText("Rendez-vous à venir!");
                alert.setContentText("Il y a un rendez-vous dans les 15 prochaines minutes! \n" +
                        "Rendez-vous ID: " +checkList.get(0).getApptId2() +"; Date/heure de début: " +start);
                alert.showAndWait();      
            }    
            
            stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } else {
            logger.info("UNSUCCESSFUL Login Attempt by User: "+userName +"; Time: "+now);
            if("en_US".equals(userLocale.toString())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Incorrect Username and/or Password");
                alert.setContentText("Enter valid Username and Password");
                Optional<ButtonType> result = alert.showAndWait();
                }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Nom d'utilisateur et / ou mot de passe incorrects");
                alert.setContentText("Entrez un nom d'utilisateur et un mot de passe valides.");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }
}
