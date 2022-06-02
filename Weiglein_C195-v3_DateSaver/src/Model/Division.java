package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * the division class is used to build a division object
 */
public class Division {
    /**
     * builds a division object
     */
    private final int divisionID;
    private final String divisionName;
    private final Country countryID;

    /**
     * constructor for the division object
     * @param divisionID division ID
     * @param divisionName division name
     * @param countryID country ID
     */
    public Division(int divisionID, String divisionName, Country countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * get division ID
     * @return division ID (int)
     */
    public int getDivisionID() { return divisionID; }

    /**
     * get division name
     * @return division name (string)
     */
    public String getName() {
        return this.divisionName;
    }

    /**
     * get country
     * @return country ID (country)
     */
    public Country getCountry() {
        return this.countryID;
    }

    /**
     * observableList contains all division objects
     */
    public static ObservableList<Division> divisionList = FXCollections.observableArrayList();

    /**
     * observableList contains all division objects filtered by country
     */
    public static ObservableList<Division> getDivisionByCountry(Country country) {
        ObservableList<Division> temp = FXCollections.observableArrayList();
        int i = 0;
        while (i < divisionList.size()) {
            if (country == divisionList.get(i).getCountry())
                temp.add(divisionList.get(i));
            i++;
        }
        return temp;
    }

    /**
     * get division ID
     * @param ID division ID
     * @return division ID (int)
     */
    public static Division getDivisionByID(int ID) {
        int i = 0;
        while (i < divisionList.size()) {
            if (ID == divisionList.get(i).getDivisionID())
                return divisionList.get(i);
            i++;
        }
        return null;
    }

    /**
     * converts division name to string
     * @return division name (string)
     */
    @Override
    public String toString() { return(divisionName); }
}
