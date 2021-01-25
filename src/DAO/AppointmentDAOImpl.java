/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Appointment;
import Model.Customer;
import ViewController.LoginFormController;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Implements the DAO model for the appointment functions.
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class AppointmentDAOImpl {
    
    private static String pattern = "yyyy-MM-dd HH:mm:ss";
    private static final DateFormat dateTimeFormatLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final TimeZone localTZ = TimeZone.getDefault();
    private static final DateFormat dateTimeFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final TimeZone utcTZ = TimeZone.getTimeZone("UTC");
    private static final ZoneId localZoneId = ZoneId.systemDefault();
    private static final ZoneId zoneIdUTC = ZoneId.of("UTC");
    private static String monthStart;
    private static String monthEnd;
    private static Number countAppts;
    private static int year = 2020;
    private static Number countLongAppts;
    
    /**
     * Creates a list of all appointments in the database.
     * 
     * @return apptList List of all appointments
     * @throws ParseException
     */
    public static ObservableList<Appointment> getAllAppointments() throws ParseException {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();      
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, appointments.Contact_ID, contacts.Contact_Name " +
            "from appointments, contacts WHERE contacts.Contact_ID = appointments.Contact_ID";
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
        
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                dateTimeFormatLocal.setTimeZone(localTZ);
                dateTimeFormatUTC.setTimeZone(utcTZ);
                java.util.Date startDateTime = dateTimeFormatUTC.parse(rs.getString("Start"));
                String start = dateTimeFormatLocal.format(startDateTime);
                java.util.Date endDateTime = dateTimeFormatUTC.parse(rs.getString("End"));
                String end = dateTimeFormatLocal.format(endDateTime);
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contact = rs.getString("contacts.Contact_Name");
                        
                Appointment a = new Appointment(id, title, description, location, 
                        type, start, end, createDate, createdBy, lastUpdate, 
                        lastUpdatedBy, customerId, userId, contactId, contact);
                apptList.add(a);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();        
        }
        return apptList;
    }
    
    /**
     * Inserts a new appointment into the database.
     * 
     * @param id Appointment ID
     * @param title Appointment Title
     * @param description Appointment Description
     * @param location Appointment Location
     * @param type Appointment Type
     * @param start Start date and time
     * @param end End date and time
     * @param createDate Date created
     * @param createdBy User who created appointment
     * @param lastUpdate Date last updated
     * @param lastUpdatedBy User who last updated appointment
     * @param customerId Customer ID
     * @param userId User ID
     * @param contactId Contact ID
     * @throws ParseException 
     */
    public static void createAppointment(int id, String title, String description, 
            String location, String type, String start, String end, String createDate, 
            String createdBy, String lastUpdate, String lastUpdatedBy, 
            int customerId, int userId, int contactId) throws ParseException {
        
        try {
            String sqla = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, UTC_TIMESTAMP(), ?, UTC_TIMESTAMP(), ?, ?, ?, ?)";
            
            PreparedStatement psa = DBConnection.startConnection().prepareStatement(sqla, Statement.RETURN_GENERATED_KEYS); 
            
            psa.setString(1, title);
            psa.setString(2, description);
            psa.setString(3, location);
            psa.setString(4, type);
            
            dateTimeFormatLocal.setTimeZone(localTZ);
            dateTimeFormatUTC.setTimeZone(utcTZ);
            
            LocalDateTime startDateTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern(pattern));
            ZonedDateTime localStartDateTime = startDateTime.atZone(localZoneId);
            ZonedDateTime utcStartDateTime = localStartDateTime.withZoneSameInstant(zoneIdUTC);
            
            DateTimeFormatter longFormat = DateTimeFormatter.ofPattern(pattern);
            String startDateTimeUTC = longFormat.format(utcStartDateTime);
            psa.setString(5, startDateTimeUTC);
            
            LocalDateTime endDateTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern(pattern));
            ZonedDateTime localEndDateTime = endDateTime.atZone(localZoneId);
            ZonedDateTime utcEndDateTime = localEndDateTime.withZoneSameInstant(zoneIdUTC);
             
            String endDateTimeUTC = longFormat.format(utcEndDateTime);
            psa.setString(6, endDateTimeUTC);
            
            psa.setString(7, LoginFormController.loggedInUser.getUserName());
            psa.setString(8, LoginFormController.loggedInUser.getUserName());
            psa.setInt(9, customerId);
            psa.setInt(10, LoginFormController.loggedInUser.getId());
            psa.setInt(11, contactId);
           
            
            psa.execute();
            
            ResultSet rs = psa.getGeneratedKeys();
            rs.next();
            int appointmentId = rs.getInt(1);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Updates an appointment in the database.
     * 
     * @param id Appointment ID
     * @param title Appointment Title
     * @param description Appointment Description
     * @param location Appointment Location
     * @param type Appointment Type
     * @param start Start date and time
     * @param end End date and time
     * @param createDate Date created
     * @param createdBy User who created appointment
     * @param lastUpdate Date last updated
     * @param lastUpdatedBy User who last updated appointment
     * @param customerId Customer ID
     * @param userId User ID
     * @param contactId Contact ID
     * @throws ParseException 
     */
    public static void updateAppointment(int id, String title, String description, 
            String location, String type, String start, String end, String createDate, 
            String createdBy, String lastUpdate, String lastUpdatedBy, 
            int customerId, int userId, int contactId) throws ParseException {
        
        try {
            String sqlc = "UPDATE appointments SET Title = ?, Description = ?, "
                    + "Location = ?, Type = ?, Start = ?, End = ?, "
                    + "Last_Update = CURRENT_TIMESTAMP, Last_Updated_By = ?, "
                    + "Customer_ID = ?, User_ID = ?, Contact_ID = ? "
                    + "WHERE Appointment_ID = " +id;
            
            PreparedStatement psc = DBConnection.startConnection().prepareStatement(sqlc, Statement.RETURN_GENERATED_KEYS); 
            
            psc.setString(1, title);
            psc.setString(2, description);
            psc.setString(3, location);
            psc.setString(4, type);
            
            dateTimeFormatLocal.setTimeZone(localTZ);
            dateTimeFormatUTC.setTimeZone(utcTZ);
            
            java.util.Date startDateTime = dateTimeFormatLocal.parse(start);
            String startDateTimeUTC = dateTimeFormatUTC.format(startDateTime);
            psc.setString(5, startDateTimeUTC);  
            java.util.Date endDateTime = dateTimeFormatLocal.parse(end);
            String endDateTimeUTC = dateTimeFormatUTC.format(endDateTime);
            psc.setString(6, endDateTimeUTC);
            psc.setString(7, LoginFormController.loggedInUser.getUserName());
            psc.setInt(8, customerId);
            psc.setInt(9, LoginFormController.loggedInUser.getId());
            psc.setInt(10, contactId);
            psc.execute();          
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Deletes an appointment from the database.
     * 
     * @param id
     */
    public static void deleteAppointment(int id) {
    
         try {
            String sqlc = "DELETE FROM appointments WHERE Appointment_ID = " +id;
            
            PreparedStatement psc = DBConnection.startConnection().prepareStatement(sqlc); 
            
            psc.execute();          
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a list of appointments for the current month.
     * 
     * @return apptMonthList List of appointments in the current month
     * @throws ParseException
     */
    public static ObservableList<Appointment> getMonthAppointments() throws ParseException {
        ObservableList<Appointment> apptMonthList = FXCollections.observableArrayList(); 
        
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, appointments.Contact_ID, contacts.Contact_Name " +
            "from appointments, contacts WHERE contacts.Contact_ID = appointments.Contact_ID AND MONTH(Start) = MONTH(UTC_TIMESTAMP())";
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
        
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                dateTimeFormatLocal.setTimeZone(localTZ);
                dateTimeFormatUTC.setTimeZone(utcTZ);
                java.util.Date startDateTime = dateTimeFormatUTC.parse(rs.getString("Start"));
                String start = dateTimeFormatLocal.format(startDateTime);
                java.util.Date endDateTime = dateTimeFormatUTC.parse(rs.getString("End"));
                String end = dateTimeFormatLocal.format(endDateTime);
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contact = rs.getString("contacts.Contact_Name");
                        
                Appointment a = new Appointment(id, title, description, location, 
                        type, start, end, createDate, createdBy, lastUpdate, 
                        lastUpdatedBy, customerId, userId, contactId, contact);
                apptMonthList.add(a);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return apptMonthList;
    }
    
    /**
     * Creates a list of appointments for the current week.
     * 
     * Contains Lambda Expression which is used to convert from UTC to Local
     * time for display.
     * 
     * @return apptWeekList List of appointments for current week.
     * @throws ParseException
     */
    public static ObservableList<Appointment> getWeekAppointments() throws ParseException {
        ObservableList<Appointment> apptWeekList = FXCollections.observableArrayList(); 
        
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, appointments.Contact_ID, contacts.Contact_Name " +
            "from appointments, contacts WHERE contacts.Contact_ID = appointments.Contact_ID AND WEEK(Start) = WEEK(UTC_TIMESTAMP())";
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
        
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                dateTimeFormatLocal.setTimeZone(localTZ);
                dateTimeFormatUTC.setTimeZone(utcTZ);
                
                /*
                * Lambda Expression: converts from UTC to local time for display
                * 
                */
                Interface2 convert = s1 -> {
                java.util.Date dateTime = null;
                    try {
                        dateTime = dateTimeFormatUTC.parse(rs.getString(s1));
                    } catch (SQLException ex) {
                        Logger.getLogger(AppointmentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(AppointmentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                String s = dateTimeFormatLocal.format(dateTime);
                return s;
                };
                        

                String start = convert.convertToLocal("Start");
                String end = convert.convertToLocal("End");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contact = rs.getString("contacts.Contact_Name");
                        
                Appointment a = new Appointment(id, title, description, location, 
                        type, start, end, createDate, createdBy, lastUpdate, 
                        lastUpdatedBy, customerId, userId, contactId, contact);
                apptWeekList.add(a);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return apptWeekList;
    }
    
    /**
     * Creates a list of appointments with conflicting times for a customer.
     * 
     * @param id Appointment ID
     * @param start Start date
     * @param end End date
     * @return conflictList List of conflicting appointments
     * @throws ParseException
     */
    public static ObservableList<Appointment> getConflicts(int id, ZonedDateTime start, ZonedDateTime end) throws ParseException {
        ObservableList<Appointment> conflictList = FXCollections.observableArrayList(); 
                
        try {
                    
            String sql = "SELECT Appointment_ID from appointments WHERE (? BETWEEN start AND end OR ? BETWEEN start AND end OR ? < start AND ? > end) AND Customer_ID = "+id;
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql); 

            DateTimeFormatter longFormat = DateTimeFormatter.ofPattern(pattern);
            String startDateTimeUTC = longFormat.format(start);
            String endDateTimeUTC = longFormat.format(end);

            ps.setString(1, startDateTimeUTC);
            ps.setString(2, endDateTimeUTC);
            ps.setString(3, startDateTimeUTC);
            ps.setString(4, endDateTimeUTC);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int apptId = rs.getInt("Appointment_ID");                   
                Appointment a = new Appointment(apptId);
                conflictList.add(a);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();  
        }
        return conflictList;
    }
    
     /**
     * Creates a list of appointments with conflicting times for a customer when
     * updating an existing appointment.
     * 
     * @param apptId Appointment ID
     * @param custId Customer ID
     * @param start Start date
     * @param end End date
     * @return conflictList List of conflicting appointments
     * @throws ParseException
     */
    public static ObservableList<Appointment> getUpdateConflicts(int apptId, int custId, ZonedDateTime start, ZonedDateTime end) throws ParseException {
        ObservableList<Appointment> conflictList = FXCollections.observableArrayList(); 
                
        try {
                    
            String sql = "SELECT Appointment_ID from appointments WHERE (? BETWEEN start AND end OR ? BETWEEN start AND end OR ? < start AND ? > end) AND Customer_ID = "+custId +" AND Appointment_ID <> " +apptId;
            
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql); 

            DateTimeFormatter longFormat = DateTimeFormatter.ofPattern(pattern);
            String startDateTimeUTC = longFormat.format(start);
            String endDateTimeUTC = longFormat.format(end);

            ps.setString(1, startDateTimeUTC);
            ps.setString(2, endDateTimeUTC);
            ps.setString(3, startDateTimeUTC);
            ps.setString(4, endDateTimeUTC);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int apptId1 = rs.getInt("Appointment_ID");                   
                Appointment a = new Appointment(apptId1);
                conflictList.add(a);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();  
        }
        return conflictList;
    }
    
    /**
     * Creates a list of types of appointments.
     * 
     * @return apptTypeList List of appointment types.
     * @throws ParseException
     */
    public static ObservableList<String> getTypes() throws ParseException {
        ObservableList<String> apptTypeList = FXCollections.observableArrayList(); 
        
        try {
            String sql = "SELECT DISTINCT Type FROM appointments";
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
        
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                String a = rs.getString("Type");
                apptTypeList.add(a);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return apptTypeList;
    }
        
    /**
     * Counts the quantity of appointments in a selected month.
     * 
     * Contains a lambda expression to convert a month string to the
     * corresponding integer value.
     * 
     * @param month Selected month
     * @param type Appointment type
     * @return countAppts integer of the total quantity of appointments
     */
    public static Number apptQuantity(String month, String type) {
        
            try {         
                
            String sql = "SELECT count(Appointment_ID) AS Count" +
            " FROM appointments WHERE EXTRACT(MONTH FROM start) = EXTRACT(MONTH FROM ?) AND Type = ?";
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
            
            Interface1 MonthNumber = m -> {
               Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(new SimpleDateFormat("MMM").parse(m));
                } catch (ParseException ex) {
                    Logger.getLogger(AppointmentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
               Integer monthInt = cal.get(Calendar.MONTH) + 1;
               String monthString = monthInt.toString();
               return monthString;
            };
            
            monthStart = year + "-" +MonthNumber.getMonthNumber(month)+"-01 00:00:00";
            
            ps.setString(1, monthStart);
            ps.setString(2, type);
            
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            countAppts = (Number) rs.getObject(1);
        }
        catch(SQLException e) {
            e.printStackTrace();
            
        }
        return countAppts;
    }
        
    /**
     * Get the quantity of appointments with length greater than 1 hour.
     * 
     * @return countLongAppts Integer representing quantity of appointments >1 hour.
     */
    public static Number getLongAppointments() {
        
            try {         
                
            String sql = "SELECT count(Appointment_ID) AS Count" +
            " FROM appointments WHERE (EXTRACT(HOUR FROM end) - EXTRACT(HOUR FROM start)) > 1";
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            
            rs.next();
            countLongAppts = (Number) rs.getInt("Count");
        }

        catch(SQLException e) {
            e.printStackTrace();
        }   
        return countLongAppts;
    }        
}
