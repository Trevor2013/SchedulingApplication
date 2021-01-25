/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Customer;
import Model.FirstLevelDivision;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Implements DAO model for first-level divisions.
 * 
 * @author TRoss
 */
public class DivisionDAOImpl {
    
    /**
     * Creates a list of all first-level divisions
     * 
     * @return divList List of divisions
     */
    public static ObservableList<FirstLevelDivision> getAllDivisions() {
        ObservableList<FirstLevelDivision> divList = FXCollections.observableArrayList();
        
        try {
           String sql = "SELECT Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID "
                   + "FROM first_level_divisions, countries "
                    + "WHERE first_level_divisions.Country_ID = countries.Country_ID";
        
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(sql);
        
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("Country_ID");
                FirstLevelDivision d = new FirstLevelDivision(id, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
                divList.add(d);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return divList;
    }
    
    /**
     * Generates a list of divisions to be displayed in the first-level 
     * division menu.
     * 
     * @param countryId
     * @return menuDivisionList List of divisions for display in menu
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivision> menuDivisions(int countryId) throws SQLException {
        ObservableList<FirstLevelDivision> menuDivisionList = FXCollections.observableArrayList();
                
        String sqlDivMenu = "SELECT Division, Division_ID FROM first_level_divisions WHERE Country_ID = " +countryId;

        PreparedStatement pstDivMenu = DBConnection.startConnection().prepareStatement(sqlDivMenu);

        ResultSet divisionMenuResult = pstDivMenu.executeQuery(sqlDivMenu);

        while (divisionMenuResult.next()) {
            String div = divisionMenuResult.getString("division");  
            int divId = divisionMenuResult.getInt("Division_ID");
            FirstLevelDivision d = new FirstLevelDivision(divId, div);
            menuDivisionList.add(d);
        }
        return menuDivisionList;
    }
    
    /**
     * Generates a list of strings of first-level division names based on a
     * specified country.
     * 
     * @param countryId
     * @return menuDivisionStringsList List of division names in string format.
     * @throws SQLException
     */
    public static ObservableList<String> menuDivisionStrings(int countryId) throws SQLException {
        ObservableList<String> menuDivisionStringsList = FXCollections.observableArrayList();
                
        String sqlDivMenu = "SELECT Division, Division_ID FROM first_level_divisions WHERE Country_ID = " +countryId;

        PreparedStatement pstDivMenu = DBConnection.startConnection().prepareStatement(sqlDivMenu);

        ResultSet divisionMenuResult = pstDivMenu.executeQuery(sqlDivMenu);

        while (divisionMenuResult.next()) {
            String div = divisionMenuResult.getString("division");
            menuDivisionStringsList.add(div);
        }
        return menuDivisionStringsList;
    }
    
    /**
     * Generates a list of first-level division IDs based on a specified 
     * country.
     * 
     * @param countryId
     * @return
     * @throws SQLException
     */
    public static ObservableList<Integer> menuDivisionIds(int countryId) throws SQLException {
        ObservableList<Integer> menuDivisionIdsList = FXCollections.observableArrayList();
                
        String sqlDivMenu = "SELECT Division, Division_ID FROM first_level_divisions WHERE Country_ID = " +countryId;

        PreparedStatement pstDivMenu = DBConnection.startConnection().prepareStatement(sqlDivMenu);

        ResultSet divisionMenuResult = pstDivMenu.executeQuery(sqlDivMenu);

        while (divisionMenuResult.next()) {
            int divId = divisionMenuResult.getInt("Division_ID");
            menuDivisionIdsList.add(divId);
        }
        return menuDivisionIdsList;
    }
    
}
