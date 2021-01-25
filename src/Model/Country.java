/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Country class contains the country constructor, getters, and setters for
 * countries. 
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class Country {
    
    private int id;
    private String country;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdatedBy;

    /**
     * Country constructor
     * 
     * @param id The country ID
     * @param country The country name
     * @param createDate The creation date/time for the country (in the database)
     * @param createdBy The user who created the country
     * @param lastUpdate The last date/time the country was updated
     * @param lastUpdatedBy The user who last updated the country
     */
    public Country(int id, String country, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy) {
        this.id = id;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets country ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets country ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets country name
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country name
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the date/time the country was created (in the database)
     * @return createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Sets the date/time the country was created (in the database)
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the user who created the country
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created the country
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the date/time of the last update to the country
     * @return lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date/time of the last update to the country
     * @param lastUpdate
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the user who last updated the country
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the user who last updated the country
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
}
