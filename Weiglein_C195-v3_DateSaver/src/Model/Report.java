package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * the report class is used to build a report object
 */
public class Report {
    /**
     * biulds a report object
     */
    private final String month;
    private final String type;
    private final String amount;

    /**
     * constructor for the report object
     * @param month month name
     * @param type appointment type
     * @param amount appointment amount
     */
    public Report(String month, String type, String amount) {
        this.month = month;
        this.type = type;
        this.amount = amount;
    }

    /**
     * gets the appointment month
     * @return appointment month (string)
     */
    public String getMonth() { return month; }

    /**
     * gets the appointment type
     * @return appointment type (string)
     */
    public String getType() { return type; }

    /**
     * gets the appointment type & month amount
     * @return amount (string)
     */
    public String getAmount() { return amount; }

    /**
     * observableList contains all report objects
     */
    public static ObservableList<Report> typeReport = FXCollections.observableArrayList();
}