package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.function.Predicate;

/**
 * the contact class is used to build a contact object
 */
public class Contact {
    /**
     * build a contact object
     */
    private final int id;
    private final String name;

    /**
     * constructor for the contact object
     * @param id contact ID
     * @param name contact name
     */
    public Contact(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * gets contact ID
     * @return contact ID (int)
     */
    public int getId() { return id; }

    /**
     * gets contact name
     * @return contact name (string)
     */
    public String getName() { return name; }

    /**
     * observableList contains all contact objects
     */
    public static ObservableList<Contact> contactList = FXCollections.observableArrayList();

    /**
     * gets contact name by ID
     * @param ID contact ID
     * @return contact found by ID
     */
    public static Contact getContactByID(int ID) {
        int i = 0;
        while(i < contactList.size()) {
            if(ID == contactList.get(i).getId()) return contactList.get(i);
            i++;
        }
        return null;
    }

    /**
     * used to get the schedule of a selected contact on the reports screen
     * @param contactValue the contact
     * @return appointments of selected contact
     */
    public static Predicate<Appointment> contactSchedulePredicate(Contact contactValue) {
        return AppointmentObj -> AppointmentObj.getContactID() == contactValue;
    }

    /**
     * converts contacts name to string
     * @return name of the contact (string)
     */
    @Override
    public String toString() {
        return this.getName();
    }
}
