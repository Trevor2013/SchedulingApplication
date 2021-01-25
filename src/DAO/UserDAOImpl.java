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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Implements DAO model for users.
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class UserDAOImpl {
    
    private static final DateFormat dateTimeFormatLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static TimeZone localTZ = TimeZone.getDefault();
    private static final DateFormat dateTimeFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static TimeZone utcTZ = TimeZone.getTimeZone("UTC");
        
    /**
     * Checks whether the provided password is valid for the corresponding
     * user ID.
     * 
     * @param userId
     * @param password
     * @return checkPassword Returns true if correct password is entered, false otherwise.
     * @throws SQLException
     */
    public static boolean checkPassword(int userId, String password) throws SQLException {
    
        String sql = "SELECT password FROM users WHERE User_ID ='" + userId + "'";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery(sql);

        while (rs.next()) {
            if (rs.getString("password").equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the user ID for the entered user name.
     * 
     * @param username
     * @return userID User ID (integer)
     * @throws SQLException
     */
    public static int getUserId(String username) throws SQLException {
        int userID = 0;

        //write SQL statement
        String sqlStatement = "SELECT User_ID FROM users WHERE User_Name ='" + username + "'";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(sqlStatement);

        ResultSet rs = ps.executeQuery(sqlStatement);

        while (rs.next()) {
            userID = rs.getInt("User_ID");
        }
        return userID;
    }
    
    /**
     * Gets a list of appointments that are within 15 minutes of the login time.
     * 
     * @param currentTime
     * @param checkTime
     * @return reminderList List of appointments within 15 minutes of login time.
     */
    public static ObservableList<Appointment> getReminderList(int userId, java.util.Date currentTime, java.util.Date checkTime) {
        ObservableList<Appointment> reminderList = FXCollections.observableArrayList(); 
                
        try {     
            String sql = "SELECT Appointment_ID, Start from appointments WHERE (Start > ? AND Start < ?) AND User_ID = "+userId;
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
            
            dateTimeFormatLocal.setTimeZone(localTZ);
            dateTimeFormatUTC.setTimeZone(utcTZ);
            String currentTimeString = dateTimeFormatUTC.format(currentTime);
            String checkTimeString = dateTimeFormatUTC.format(checkTime);
            
            ps.setString(1, currentTimeString);
            ps.setString(2, checkTimeString);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int apptId = rs.getInt("Appointment_ID");
                String start = rs.getString("Start");                       
                Appointment a = new Appointment(apptId, start);
                reminderList.add(a);
            }
        }
            
        catch(SQLException e) {
            e.printStackTrace();
        }
        return reminderList;       
    }
    
}
