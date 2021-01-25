/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Customer;
import DAO.DBConnection;
import Model.User;
import ViewController.LoginFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * Implements the DAO model for customers
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class CustomerDAOImpl {

    /**
     * Creates a list of all customers
     * 
     * @return custList List of all customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> custList = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Create_Date, customers.Created_By, customers.Last_Update, customers.Last_Updated_By, customers.Division_ID, first_level_divisions.Country_ID, Division, Country "
                   + "FROM customers, countries, first_level_divisions "
                    + "WHERE customers.Division_ID = first_level_divisions.Division_ID "
                   + "AND first_level_divisions.Country_ID = countries.Country_ID";
        
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
        
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");
                int countryId = rs.getInt("Country_ID");
                String division = rs.getString("Division");
                String country = rs.getString("Country");
                Customer c = new Customer(id, name, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId, countryId, division, country);
                custList.add(c);
            }
        }
        catch(SQLException e) {
            e.printStackTrace(); 
        }
        return custList;
    }

    /**
     * Creates a customer in the database.
     * 
     * @param id
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionId
     */
    public static void createCustomer(int id, String name, String address, String postalCode, 
            String phone, String createDate, String createdBy, String lastUpdate, 
            String lastUpdatedBy, int divisionId) {
        
        try {
            String sqlc = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, ?)";
            
            PreparedStatement psc = DBConnection.startConnection().prepareStatement(sqlc, Statement.RETURN_GENERATED_KEYS); 
            
            psc.setString(1, name);
            psc.setString(2, address);
            psc.setString(3, postalCode);
            psc.setString(4, phone);
            psc.setString(5, LoginFormController.loggedInUser.getUserName());
            psc.setString(6, LoginFormController.loggedInUser.getUserName());
            psc.setInt(7, divisionId);
            
            psc.execute();
            
            ResultSet rs = psc.getGeneratedKeys();
            rs.next();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
        
    /**
     * Updates an existing customer in the database
     * 
     * @param id
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionId
     */
    public static void updateCustomer(int id, String name, String address, String postalCode, 
            String phone, String lastUpdate, 
            String lastUpdatedBy, int divisionId) {
        
        try {
            String sqlc = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = CURRENT_TIMESTAMP, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = " +id;
            
            PreparedStatement psc = DBConnection.startConnection().prepareStatement(sqlc, Statement.RETURN_GENERATED_KEYS); 
            
            psc.setString(1, name);
            psc.setString(2, address);
            psc.setString(3, postalCode);
            psc.setString(4, phone);
            psc.setString(5, LoginFormController.loggedInUser.getUserName());
            psc.setInt(6, divisionId); 
            
            psc.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Deletes a customer from the database
     * 
     * @param id
     */
    public static void deleteCustomer(int id) {
        
        try{
            String sql = "DELETE FROM appointments WHERE Customer_ID = " +id;
            
            PreparedStatement psa = DBConnection.startConnection().prepareStatement(sql); 
            
            psa.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        try{
            String sqlc = "DELETE FROM customers WHERE Customer_ID = " +id;
            
            PreparedStatement psc = DBConnection.startConnection().prepareStatement(sqlc); 
            
            psc.execute();
        }
        
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}

    

