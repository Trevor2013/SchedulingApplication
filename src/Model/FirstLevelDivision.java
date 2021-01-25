/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * FirstLevelDivision Class contains the constructors, getters, and setters for
 * the first-level divisions
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class FirstLevelDivision {
    
    private int id;
    private String division;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdatedBy;
    private int countryId;

    /**
     *First-level Division constructor
     * 
     * @param id First-level division ID
     * @param division First-level division name
     * @param createDate First-level division creation date/time (in database)
     * @param createdBy User who created the first-level division 
     * @param lastUpdate First-level division last update date/time
     * @param lastUpdatedBy User who last updated the first-level division
     * @param countryId ID of the country corresponding to the first-level division.
     */
    public FirstLevelDivision(int id, String division, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, int countryId) {
        this.id = id;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }
    
    /**
     * Constructor containing only ID and division name.
     * 
     * @param id
     * @param division
     */
    public FirstLevelDivision(int id, String division) {
        this.id = id;
        this.division = division;
    }

    /**
     * Gets the ID of the first-level division
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the first-level division
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the first-level division
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the name of the first-level division
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets the creation date (in database) of the first-level division
     * @return createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date (in database) of the first-level division
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the user who created the first-level division
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created the first-level division
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the date of the last update to the first-level division
     * @return lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date of the last update to the first-level division
     * @param lastUpdate
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the user who last updated the first-level division
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the user who last updated the first-level division
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the ID of the country corresponding to the first-level division
     * @return countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the ID of the country corresponding to the first-level division
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    
}
