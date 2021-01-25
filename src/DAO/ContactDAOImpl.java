/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Appointment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Implements the DAO model for contacts
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class ContactDAOImpl {

    private static final DateFormat dateTimeFormatLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static TimeZone localTZ = TimeZone.getDefault();
    private static final DateFormat dateTimeFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static TimeZone utcTZ = TimeZone.getTimeZone("UTC");
    private static final SimpleDateFormat timeFormatEST = new SimpleDateFormat("HH:mm:ss");
    
    /**
     * Generates a list of contacts for display in the contact menu
     * 
     * @return contactNames List of contact names
     */
    public static ObservableList<String> menuContacts() {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        try {
            String sqlContactMenu = "SELECT contact_name FROM contacts";

            PreparedStatement pstContactMenu = DBConnection.startConnection().prepareStatement(sqlContactMenu);

            ResultSet contactMenuResult = pstContactMenu.executeQuery(sqlContactMenu);

            while (contactMenuResult.next()) {
                String contactName = contactMenuResult.getString("Contact_Name");
                contactNames.add(contactName);
            }   
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
            return contactNames;
    }
    
    /**
     * Generates a list of integer contact IDs 
     * 
     * @return contactIds List of contact IDs
     */
    public static ObservableList<Integer> menuContactIds() {
        ObservableList<Integer> contactIds = FXCollections.observableArrayList();
        
        try {
            String sqlContactIds = "SELECT Contact_ID FROM contacts";

            PreparedStatement pstContactIds = DBConnection.startConnection().prepareStatement(sqlContactIds);

            ResultSet contactIdResult = pstContactIds.executeQuery(sqlContactIds);

            while (contactIdResult.next()) {
                int contactId = contactIdResult.getInt("Contact_Id");
                contactIds.add(contactId);
            }   
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return contactIds;
    }
    
    /**
     * Gets a list of appointments for a specified contact ID.
     * 
     * @param contactId
     * @return apptList List of appointments corresponding to specific contact ID
     * @throws ParseException
     */
    public static ObservableList<Appointment> getAppointments(int contactId) throws ParseException {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID" +
            " FROM appointments WHERE Contact_ID = "+contactId;
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
        
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = "";
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
                Appointment a = new Appointment(id, title, description, location, 
                        type, start, end, createDate, createdBy, lastUpdate, 
                        lastUpdatedBy, customerId, userId, contactId, "");
                apptList.add(a);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            
        }
        return apptList;
    }
}
