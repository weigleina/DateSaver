package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * the country class is used to build a country object
 */
public class Country {
    /**
     * builds a country object
     */
    private final int id;
    private final String name;

    /**
     * constructor for the country object
     * @param id country ID
     * @param name country Name
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * gets country Id
     * @return country ID (int)
     */
    public int getId() { return id; }

    /**
     * gets country name
     * @return country name (string)
     */
    public String getName() { return name; }

    /**
     * observableList contains all country objects
     */
    public static ObservableList<Country> countryList = FXCollections.observableArrayList();

    /**
     * gets country name by ID
     * @param ID country ID
     * @return country found by ID
     */
    public static Country getCountryById(int ID) {
        int i = 0;
        while (i < countryList.size()) {
            if (ID == countryList.get(i).id)
                return countryList.get(i);
            i++;
        }
        return null;
    }

    /**
     * converts country name to string
     * @return country name (string)
     */
    @Override
    public String toString() {
        return this.name;
    }
}
