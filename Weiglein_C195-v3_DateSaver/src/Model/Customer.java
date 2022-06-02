package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * the customer class is used to build a customer object
 */
public class Customer {
    /**
     * builds a customer object
     */
    private final int id;
    private final String name;
    private final String address;
    private final String postalCode;
    private final String phone;
    private final Division divID;

    /**
     * constructor for the customer object
     * @param id customer ID
     * @param name customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phone customer phone number
     * @param divID customer division ID
     */
    public Customer(int id, String name, String address, String postalCode, String phone, Division divID) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divID = divID;
    }

    /**
     * gets the customer ID
     * @return customer ID (int)
     */
    public int getId() { return id; }

    /**
     * gets the customer name
     * @return customer name (string)
     */
    public String getName() { return name; }

    /**
     * gets the customer address
     * @return customer address (string)
     */
    public String getAddress() { return address; }

    /**
     * gets the customer postal code
     * @return customer postal code (string)
     */
    public String getPostalCode() { return postalCode; }

    /**
     * gets the customer phone number
     * @return customer phone number (string)
     */
    public String getPhone() { return phone; }

    /**
     * gets the customers division ID
     * @return division ID (division)
     */
    public Division getDivID() { return divID; }

    /**
     * observableList contains all customer objects
     */
    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /**
     * gets customer by ID
     * @param ID customer ID
     * @return customer found by ID
     */
    public static Customer getCustomerByID(int ID) {
        int i = 0;
        while (i < customerList.size()) {
            if (ID == customerList.get(i).getId()) return customerList.get(i);
            i++;
        }
        return null;
    }

    /**
     * gets customer first name
     * @return customer first name (string)
     */
    public String getFirstName() {
        int firstSpace = name.indexOf(" ");
        return name.substring(0, firstSpace);
    }

    /**
     * gets customer last name
     * @return customer last name (string)
     */
    public String getLastName() {
        int firstSpace = name.indexOf(" ");
        return name.substring(firstSpace).trim();
    }

    // combines firstName and lastName to string
    /**
     * combines first name and last name of customer
     * @return customer name (string)
     */
    @Override
    public String toString() {
        return this.name;
    }

}
