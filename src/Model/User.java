/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * User class contains the constructors, getters, and setters for users
 * @author Trevor Ross, tros114@wgu.edu
 */
public class User {
    
    private static int id;
    private static String userName;
    private String password;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdatedBy;

    /**
     * User constructor
     * 
     * @param id User ID
     * @param userName Username
     * @param password User Password
     * @param createDate Date/time user created
     * @param createdBy Entity who created the user
     * @param lastUpdate Date/time user last updated 
     * @param lastUpdatedBy Entity who last updated the user
     */
    public User(int id, String userName, String password, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * User constructor containing ID, username, and password
     * 
     */
    public User() {
        this.id = 0;
        this.userName = null;
        this.password = null;
    }

    /**
     * Gets the user's ID
     * @return id
     */
    public static int getId() {
        return id;
    }

    /**
     * Sets the user's ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the username
     * @return userName
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Sets the username
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the user's password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the date the user was created
     * @return createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Sets the date the user was created
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the name of the entity who created the user
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the name of the entity who created the user
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the date/time of the last update to the user
     * @return lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date/time of the last update to the user
     * @param lastUpdate
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the name of the entity that last updated the user
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the name of the entity that last updated the user
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
}
