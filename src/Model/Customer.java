/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Customer class contains the constructors, getters, and setters for customers.
 * 
 * @author Trevor Ross, tros114@wgu.edu
 */
public class Customer {

    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;
    private int countryId;
    private String division;
    private String country;

    /**
     * Empty constructor
     */
    public Customer(){
        
    }
    
    /**
     * Customer constructor
     * 
     * @param id The customer ID
     * @param name The customer name
     * @param address The customer address
     * @param postalCode The customer postal code
     * @param phone The customer phone number
     * @param createDate The date/time the customer was created in the database
     * @param createdBy The user who created the customer
     * @param lastUpdate The date/time the customer was last updated
     * @param lastUpdatedBy The user who last updated the customer
     * @param divisionId The ID of the customer's first-level division
     * @param countryId The ID of the customer's country
     * @param division The name of the customer's first-level division
     * @param country The name of the customer's country
     */
    public Customer(int id, String name, String address, String postalCode, String phone, String createDate, String createdBy, 
            String lastUpdate, String lastUpdatedBy, int divisionId, int countryId, String division, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.countryId = countryId;
        this.division = division;
        this.country = country;
    }

    /**
     * Gets the customer's ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the customer's ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the customer's name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer's address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the customer's postal code
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the customer's postal code
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the customer's phone number
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the customer's phone number
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the date the customer was created in the database
     * @return create Date
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Sets the date the customer was created in the database
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the user who created the customer
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created the customer
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the date/time of the last time the customer was updated
     * @return lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date/time the customer was last updated
     * @param lastUpdate
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the user who last updated the customer
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the user who last updated the customer
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the ID of the customer's first-level division
     * @return divsionID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the ID of the customer's first-level division
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets the ID of the customer's country
     * @return countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the ID of the customer's country
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets the name of the customer's first-level division
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the customer's first-level division
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets the name of the customer's country
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the customer's country
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
}
