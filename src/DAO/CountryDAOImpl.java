/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Country;
import Model.Customer;
import java.sql.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Implements the DAO model for Countries
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class CountryDAOImpl {
    
    /**
     * Get a list of all countries
     * 
     * @return countryList List of countries
     */
    public static ObservableList<Country> getAllCountries() {
    ObservableList<Country> countryList = FXCollections.observableArrayList();     
        try {
            String sql = "SELECT Country_ID, Country, Create_Date, Created_By, Last_Update, Last_Updated_By FROM countries";
        
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
        
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                Country c = new Country(id, country, createDate, createdBy, lastUpdate, lastUpdatedBy);
                countryList.add(c);  
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return countryList;
    }
}
