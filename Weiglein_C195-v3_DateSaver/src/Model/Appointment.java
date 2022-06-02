package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * the appointment class is used to build an appointment object
 */
public class Appointment {
    /**
     * builds an appointment object
     */
    private final int appointmentID;
    private final String appTitle;
    private final String appDescription;
    private final String appLocation;
    private final Contact contactID;
    private final String appType;
    private final ZonedDateTime startDate;
    private final ZonedDateTime endDate;
    private final Customer customerID;
    private final User userID;

    /**
     * constructor for the appointment object
     * @param appointmentID appointment ID
     * @param appTitle appointment title
     * @param appDescription appointment description
     * @param appLocation appointment location
     * @param contactID the contact ID for an appointment
     * @param appType appointment type
     * @param startDate appointment start date
     * @param endDate appointment end date
     * @param customerID the customer ID for an appointment
     * @param userID the user ID for an appointment
     */
    public Appointment(int appointmentID, String appTitle, String appDescription, String appLocation, Contact contactID, String appType, String startDate, String endDate, Customer customerID, User userID) {
        this.appointmentID = appointmentID;
        this.appTitle = appTitle ;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.contactID = contactID;
        this.appType = appType;
        this.startDate = ZonedDateTime.of(LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ZoneId.of("UTC+0"));
        this.endDate = ZonedDateTime.of(LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ZoneId.of("UTC+0"));
        this.customerID = customerID;
        this.userID = userID;
    }

    /**
     * gets appointment ID
     * @return appointment ID (int)
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * gets appointment title
     * @return appointment title (string)
     */
    public String getAppTitle() {
        return appTitle;
    }

    /**
     * gets appointment description
     * @return appointment description (string)
     */
    public String getAppDescription() {
        return appDescription;
    }

    /**
     * gets appointment location
     * @return appointment location (string)
     */
    public String getAppLocation() {
        return appLocation;
    }

    /**
     * gets contact ID
     * @return contact ID (contact)
     */
    public Contact getContactID() { return contactID; }

    /**
     * gets appointment type
     * @return appointment type (string)
     */
    public String getAppType() { return appType; }

    /**
     * observableList contains all appointment objects
     */
    public static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    /////////////////////////////
    /**
     * gets appointment start date/time in users local time zone
     * @return appointment start date/time in users local time zone
     */
    public String getStartDate() {
        ZonedDateTime localTime = this.startDate.withZoneSameInstant(ZoneId.systemDefault());
        return localTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * gets appointment start date/time as an object
     * @return appointment start date/time as an object
     */
    public ZonedDateTime getStartDateObj() {
        return this.startDate;
    }

    /**
     * gets the appointment start date/time in UTC
     * @return the appointment start date/time in UTC
     */
    public String getStartDateUTC() {
        return this.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    //////////////////////////////
    /**
     * gets appointment end date/time in users local time zone
     * @return appointment end date/time in users local time zone
     */
    public String getEndDate() {
        ZonedDateTime localTime = this.endDate.withZoneSameInstant(ZoneId.systemDefault());
        return localTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * gets appointment end date/time as an object
     * @return appointment end date/time as an object
     */
    public ZonedDateTime getEndDateObj() {
        return this.endDate;
    }

    /**
     * gets the appointment end date/time in UTC
     * @return the appointment end date/time in UTC
     */
    public String getEndDateUTC() {
        return this.endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    ///////////////////////////

    /**
     * gets the customer ID
     * @return the customer ID
     */
    public Customer getCustomerID() { return customerID; }

    /**
     * gets the customer ID
     * @return the customer ID
     */
    public User getUserID() { return userID; }

    /**
     * gets the contact for the contact schedule report
     * @return the contact ID
     */
    public Contact getAppContact() { return contactID; }
}
