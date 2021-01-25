/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Appointment class contains the appointment constructors, getters, and setters
 * for appointments.
 *
 * @author Trevor Ross, tros114@wgu.edu
 */
public class Appointment {

    private int id;
    private int apptId;
    private int apptId2;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String start2;
    private String end;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;
    private String contact;

    /**
     * Appointment Constructor
     * 
     * @param id The appointment ID
     * @param title The appointment title
     * @param description The appointment description
     * @param location The appointment location
     * @param type The appointment type
     * @param start The appointment start date/time
     * @param end The appointment end date/time
     * @param createDate The appointment creation date/time
     * @param createdBy The appointment creation user
     * @param lastUpdate The appointment last updated date/time
     * @param lastUpdatedBy The appointment last update user
     * @param customerId The appointment associated customer ID
     * @param userId The appointment associated User ID
     * @param contactId The appointment associated contact ID
     * @param contact The appointment associated contact name
     */
    public Appointment(int id, String title, String description, String location, String type, String start, String end, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId, String contact) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contact = contact;
    }

    /**
     * Appointment Constructor (ID only)
     * 
     * @param apptId
     */
    public Appointment(int apptId) {
        this.apptId = apptId;
    }

    /**
     * Appointment Constructor (ID and start time only)
     * 
     * @param apptId2 the appointment ID
     * @param start2
     */
    public Appointment(int apptId2, String start2) {
        this.apptId2 = apptId2;
        this.start2 = start2;
    }

    /**
     * Gets appointment ID
     * @return apptId2
     */
    public int getApptId2() {
        return apptId2;
    }

    /**
     * Sets appointment ID
     * @param apptId2
     */
    public void setApptId2(int apptId2) {
        this.apptId2 = apptId2;
    }
    
    /**
     * Gets start date/time
     * @return start2
     */
    public String getStart2() {
        return start2;
    }

    /**
     * Sets start date/time
     * @param start2
     */
    public void setStart2(String Start2) {
        this.start2 = start2;
    }
    
    /**
     * Gets appointment ID
     * @return apptID
     */
    public int getApptId() {
        return apptId;
    }
    
    /**
     * Sets appointment ID
     * @param apptId
     */
    public void setApptId(int apptId) {
        this.apptId = apptId;
    }
    
    /**
     * Gets appointment ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets appointment ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets appointment title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets appointment title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets appointment description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets appointment description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets appointment location
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets appointment location
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets appointment type
     * @return type
     */ 
    public String getType() {
        return type;
    }

    /**
     * Sets appointment type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets appointment start date/tim
     * @return start
     */
    public String getStart() {
        return start;
    }

    /**
     * Sets appointment start date/time
     * @param start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Gets appointment end date/time
     * @return end
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets appointment end date/time
     * @param end
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Gets appointment creation date/time
     * @return createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Sets appointment creation date/time
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets user who created appointment
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets user who created appointment
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the date/time of last update
     * @return lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date/time of last update
     * @param lastUpdate
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the user who last updated the appointment
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the user who last updated the appointment
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets associated customer ID
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets associated customer ID
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets associated user ID
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets associated User ID
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets associated contact ID
     * @return contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets associated Contact ID
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    
    /**
     * Gets associated contact Name
     * @return contact
     */
    public String getContact() {
        return contact;
    }
    
    /*
     * Sets associated Contact ID
     * @param contact
     */
    public void setContact() {
        this.contact = contact;
    }
    
    
    
    
    
}
