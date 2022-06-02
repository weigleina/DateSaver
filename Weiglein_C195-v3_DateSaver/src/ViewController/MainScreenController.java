package ViewController;

import Model.*;
import Utils.DBConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * controller class for the main screen
 */
public class MainScreenController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML private ToggleGroup viewPeriod;
    @FXML private RadioButton weekRadio;
    @FXML private RadioButton allRadio;
    @FXML private RadioButton monthRadio;
    @FXML private Button backDateButton;
    @FXML private Button nextDateButton;
    @FXML private Button LogoutBtn;
    @FXML private Button reportsBtn;
    @FXML private Button customersBtn;
    @FXML private Button modifyAppointmentBtn;
    @FXML private Button deleteBtn;
    @FXML private Button saveBtn;
    @FXML private Button clearBtn;
    @FXML private Label errorLabel;
    @FXML private Label currentTime12Label;
    @FXML private Label currentTime24Label;
    @FXML private Label currentDateLabel;
    @FXML private Label errorLabel1;
    @FXML private Label apptRecordLabel;
    @FXML private Label apptViewDateLabel;
    @FXML private Label ESTLabel;
    @FXML private Label ESTLabel24;
    @FXML private ComboBox<User> userBox;
    @FXML private ChoiceBox<String> typeBox;
    @FXML private ComboBox<Customer> customerBox;
    @FXML private ComboBox<Contact> contactBox;
    @FXML private TableView<Appointment> apptTable;
    @FXML private TableColumn<Appointment, Integer> apptIDCol;
    @FXML private TableColumn<Appointment, String> titleCol;
    @FXML private TableColumn<Appointment, String> descriptionCol;
    @FXML private TableColumn<Appointment, String> locationCol;
    @FXML private TableColumn<Appointment, Customer> contactCol;
    @FXML private TableColumn<Appointment, String> typeCol;
    @FXML private TableColumn<Appointment, Date> startCol;
    @FXML private TableColumn<Appointment, Date> endCol;
    @FXML private TableColumn<Appointment, Customer> customerCol;
    @FXML private TableColumn<Appointment, User> userCol;
    @FXML private DatePicker date;
    @FXML private TextField startHour;
    @FXML private TextField startMin;
    @FXML private TextField endHour;
    @FXML private TextField endMin;
    @FXML private TextField location;
    @FXML private TextField title;
    @FXML private TextField apptID;
    @FXML private TextField description;
    @FXML private Pane typeError;
    @FXML private Pane dateError;
    @FXML private Pane startHourError;
    @FXML private Pane startMinError;
    @FXML private Pane endHourError;
    @FXML private Pane endMinError;
    @FXML private Pane locationError;
    @FXML private Pane titleError;
    @FXML private Pane descriptionError;
    @FXML private Pane customerError;
    @FXML private Pane contactError;
    @FXML private Pane userError;
    @FXML private Pane timeError;
    private boolean modify = false;

    // used to fill choicebox with appointment type options
    String[] appointmentType = {"Planning Session", "De-Briefing", "Business", "Conference", "Video Call", "Other"};
    public static ZonedDateTime ESTtime;
    public static ZonedDateTime startZDT;
    public static ZonedDateTime endZDT;

    /**
     * initializes the controller class
     * @param url url
     * @param rb resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //users time zone
        ZoneId userZone = ZoneId.systemDefault();

        startZDT = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
        endZDT = startZDT.plusMinutes(15);

        errorLabel.setText("");
        errorLabel1.setText("");
        apptRecordLabel.setText("NEW APPOINTMENT");
        System.out.println("SCHEDULE SCREEN");

        // Loads all lists from SQL database
        try {
            DBConnection.loadUsersFromDatabase();
            DBConnection.loadCountriesFromDatabase();
            DBConnection.loadDivisionsFromDatabase();
            DBConnection.loadContactsFromDatabase();
            DBConnection.loadCustomersFromDatabase();
            DBConnection.loadAppointmentsFromDatabase();

            System.out.println("\t\tUsers\t\t\tITEM COUNT: " + User.userList.size());
            System.out.println("\t\tAppointments\tITEM COUNT: " + Appointment.appointmentList.size());
            System.out.println("\t\tCustomers\t\tITEM COUNT: " + Customer.customerList.size());
            System.out.println("\t\tContacts\t\tITEM COUNT: " + Contact.contactList.size());
            System.out.println("\t\tCountries\t\tITEM COUNT: " + Country.countryList.size());
            System.out.println("\t\tDivisions\t\tITEM COUNT: " + Division.divisionList.size() + "\n");

            // time range to check for upcoming appointments
            startZDT = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
            endZDT = startZDT.plusMinutes(15);

            String begin = startZDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String end = endZDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            DBConnection.updateAppointmentListByStart(begin, end);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // if appointment list is greater than 0, alert notifies user of upcoming appointments
        if(Appointment.appointmentList.size() > 0) {
            Alert alertSE = new Alert(Alert.AlertType.WARNING);
            alertSE.setTitle("Alert");
            alertSE.setHeaderText("Appointment reminder");
            alertSE.setContentText("Appointment [ID: "+ Appointment.appointmentList.get(0).getAppointmentID() + "] starts at: " + Appointment.appointmentList.get(0).getStartDate());
            System.out.println("Appointment [ID: "+ Appointment.appointmentList.get(0).getAppointmentID() + "] starts at: " + Appointment.appointmentList.get(0).getStartDate());
            if(Appointment.appointmentList.size() >= 2){
                alertSE.setContentText("Appointment [ID: "+ Appointment.appointmentList.get(0).getAppointmentID() + "] starts at: " + Appointment.appointmentList.get(0).getStartDate() +
                            "\nAppointment [ID: " + Appointment.appointmentList.get(1).getAppointmentID() + "] starts at: " + Appointment.appointmentList.get(1).getStartDate());
                System.out.println("Appointment [ID: " + Appointment.appointmentList.get(1).getAppointmentID() + "] starts at: " + Appointment.appointmentList.get(1).getStartDate());
            }
            if(Appointment.appointmentList.size() >= 3){
                alertSE.setContentText("Appointment [ID: "+ Appointment.appointmentList.get(0).getAppointmentID() + "] starts at: " + Appointment.appointmentList.get(0).getStartDate() +
                            "\nAppointment [ID: " + Appointment.appointmentList.get(1).getAppointmentID() + "] starts at: " + Appointment.appointmentList.get(1).getStartDate() +
                            "\nAppointment [ID: " + Appointment.appointmentList.get(2).getAppointmentID() + "] starts at: " + Appointment.appointmentList.get(2).getStartDate());
                System.out.println("Appointment [ID: " + Appointment.appointmentList.get(2).getAppointmentID() + "] starts at: " + Appointment.appointmentList.get(2).getStartDate());
            }
            Stage stage = (Stage) alertSE.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
            stage.toFront();
            alertSE.show();
            errorLabel.setText("Appointment ID: "+ Appointment.appointmentList.get(0).getAppointmentID() + " starts at: " + Appointment.appointmentList.get(0).getStartDate());
        }

        // alert notifies user of zero upcoming appointments
        else {
            Alert alertSE = new Alert(Alert.AlertType.INFORMATION);
            alertSE.setTitle("Alert");
            alertSE.setHeaderText("Appointment reminder");
            alertSE.setContentText("There are no scheduled appointments within 15 minutes.");
            Stage stage = (Stage) alertSE.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
            stage.toFront();
            alertSE.show();
        }

        setTableCells();

        //loads list items into comboboxes
        userBox.setItems(User.userList);
        contactBox.setItems(Contact.contactList);
        customerBox.setItems(Customer.customerList);
        typeBox.getItems().setAll(appointmentType);

        // sets initial tableview to all appointments
        allRadio.fire();

        currentDateTimeLabel();

        // sets error graphics as disabled until error occurs
        typeError.setVisible(false);
        dateError.setVisible(false);
        startHourError.setVisible(false);
        startMinError.setVisible(false);
        endHourError.setVisible(false);
        endMinError.setVisible(false);
        locationError.setVisible(false);
        titleError.setVisible(false);
        descriptionError.setVisible(false);
        customerError.setVisible(false);
        contactError.setVisible(false);
        userError.setVisible(false);
        timeError.setVisible(false);
    }

    // if called, loads the appointment data into the form fields for modifying
    /**
     * if called, loads the appointment data into the form fields for modifying
     * @param selectedAppointment appointment selected in the appointment table
     */
    public void fillAppointmentData(Appointment selectedAppointment) {
        modify = true;
        apptID.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        location.setText(String.valueOf(selectedAppointment.getAppLocation()));
        description.setText(String.valueOf(selectedAppointment.getAppDescription()));
        title.setText(String.valueOf(selectedAppointment.getAppTitle()));
        customerBox.setValue(selectedAppointment.getCustomerID());
        typeBox.setValue(selectedAppointment.getAppType());
        contactBox.setValue(selectedAppointment.getContactID());
        userBox.setValue(selectedAppointment.getUserID());
        ZonedDateTime ZDTstart = selectedAppointment.getStartDateObj().withZoneSameInstant(ZoneId.systemDefault());
        date.setValue(ZDTstart.toLocalDate());
        startHour.setText(String.valueOf(ZDTstart.getHour()));
        startMin.setText(String.valueOf(ZDTstart.getMinute()));
        ZonedDateTime ZDTend = selectedAppointment.getEndDateObj().withZoneSameInstant(ZoneId.systemDefault());
        endHour.setText(String.valueOf(ZDTend.getHour()));
        endMin.setText(String.valueOf(ZDTend.getMinute()));
    }

    // filters tableview to display appointments by all, weekly, or monthly
    /**
     * filters tableview to display appointments by all, weekly, or monthly
     * @param event radio button is selected
     */
    @FXML
    private void viewSelectedRadio(ActionEvent event) {
        // All appointments radio button
        if(viewPeriod.getSelectedToggle().equals(allRadio)){
            System.out.println(">>> Viewing all appointments");
            apptViewDateLabel.setText("ALL APPOINTMENTS");
            apptViewDateLabel.setVisible(true);
            nextDateButton.setVisible(false);
            backDateButton.setVisible(false);
            startZDT = ZonedDateTime.now().minusMonths(100).withHour(23).withMinute(59);
            endZDT = ZonedDateTime.now().plusMonths(100).withHour(23).withMinute(59);
        }

        // Week appointments radio button
        if(viewPeriod.getSelectedToggle().equals(weekRadio)){
            System.out.println(">>> Viewing appointments by week");
            apptViewDateLabel.setVisible(true);
            nextDateButton.setVisible(true);
            backDateButton.setVisible(true);
            startZDT = ZonedDateTime.now().withHour(0).withMinute(0);
            endZDT = startZDT.plusWeeks(1).withHour(23).withMinute(59);
        }

        // Month appointments radio button
        if(viewPeriod.getSelectedToggle().equals(monthRadio)){
            System.out.println(">>> Viewing appointments by month");
            apptViewDateLabel.setVisible(true);
            nextDateButton.setVisible(true);
            backDateButton.setVisible(true);
            startZDT = ZonedDateTime.now().withDayOfMonth(1);
            endZDT = startZDT.plusMonths(1).withHour(23).withMinute(59);
        }
        updateView();
    }


    // displays previous appointments
    /**
     * the back arrow button changes the date range to show previous appointments
     * @param event back arrow button clicked
     */
    @FXML
    private void viewBackDate(ActionEvent event) {
        if (viewPeriod.getSelectedToggle().equals(weekRadio)) {
            errorLabel.setText("");
            errorLabel1.setText("");
            startZDT = startZDT.minusWeeks(1);
            endZDT = startZDT.plusWeeks(1);
        }

        if (viewPeriod.getSelectedToggle().equals(monthRadio)) {
            errorLabel.setText("");
            errorLabel1.setText("");
            startZDT = startZDT.minusMonths(1);
            endZDT = startZDT.plusMonths(1);
        }
        updateView();
    }

    // displays future appointments
    /**
     * the next arrow button changes the date range to show future appointments
     * @param event forward arrow button clicked
     */
    @FXML
    private void viewNextDate(ActionEvent event) throws ParseException {
        if (viewPeriod.getSelectedToggle().equals(weekRadio)) {
            errorLabel.setText("");
            errorLabel1.setText("");
            startZDT = startZDT.plusWeeks(1);
            endZDT = startZDT.plusWeeks(1);
            }

        if (viewPeriod.getSelectedToggle().equals(monthRadio)) {
            errorLabel.setText("");
            errorLabel1.setText("");
            startZDT = startZDT.plusMonths(1);
            endZDT = startZDT.plusMonths(1);
            }
        updateView();
    }

    // calls fillAppointmentData method to send customer information to the field boxes
    /**
     * calls fillAppointmentData method to send appointment information to the field boxes
     * @param event modify appointment button clicked
     */
    @FXML
    private void modifyAppointmentBtnClicked(ActionEvent event) {
        Appointment selectedAppointment = apptTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            errorLabel.setText("Select an appointment to modify");
        } else {
            errorLabel.setText("");
            apptRecordLabel.setText("MODIFY APPOINTMENT");
            fillAppointmentData(selectedAppointment);
            System.out.println("Modifying appointment... ");
            clearAllErrorBoxes();
        }
    }

    // checks entered appointment dates/times to compare against the business hours in EST
    /**
     * checks entered appointment dates/times to compare against the business hours in EST
     * @param appt appointment
     * @return valid business hours
     */
    private boolean isBusinessHours(Appointment appt) {
        // EST time zone
        ZonedDateTime ESTbusinessStart = appt.getStartDateObj().withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime ESTbusinessEnd = appt.getEndDateObj().withZoneSameInstant(ZoneId.of("America/New_York"));
        // Business hours are 08:00 - 22:00pm EST
        ZonedDateTime localStart1 = ZonedDateTime.of(ESTbusinessStart.toLocalDate(), LocalTime.of(7, 59), ZoneId.of("America/New_York"));
        ZonedDateTime localStop1 = ZonedDateTime.of(ESTbusinessStart.toLocalDate(), LocalTime.of(22, 1), ZoneId.of("America/New_York"));

        ZonedDateTime localStart2 = ZonedDateTime.of(ESTbusinessEnd.toLocalDate(), LocalTime.of(7, 59), ZoneId.of("America/New_York"));
        ZonedDateTime localStop2 = ZonedDateTime.of(ESTbusinessEnd.toLocalDate(), LocalTime.of(22, 1), ZoneId.of("America/New_York"));

        return localStart1.isBefore(ESTbusinessStart) && localStop1.isAfter(ESTbusinessStart) && localStart2.isBefore(ESTbusinessEnd) && localStop2.isAfter(ESTbusinessEnd);
    }

    // saves new appointment or modified appointment
    /**
     * saves an appointment as new or updates into database, depending on it's conditions when button is pressed.
     * If the appointment is new, a new ID is assigned. If it is being updated, the selected appointment data is
     * passed into the form, then the updates reflect in the appointment table.
     * @param event save button clicked
     * @throws SQLException
     */
    @FXML
    void saveBtnClicked(ActionEvent event) throws SQLException {
        Appointment selectedAppointment = apptTable.getSelectionModel().getSelectedItem();

        // display error boxes if empty
        locationError.setVisible(location.getText().trim().isEmpty());
        descriptionError.setVisible(description.getText().trim().isEmpty());
        titleError.setVisible(title.getText().trim().isEmpty());

        startHourError.setVisible(startHour.getText().trim().isEmpty() || startHour.getText().length() > 2);
        startMinError.setVisible(startMin.getText().trim().isEmpty() || startMin.getText().length() > 2);
        endHourError.setVisible(endHour.getText().trim().isEmpty() || endHour.getText().length() > 2);
        endMinError.setVisible(endMin.getText().trim().isEmpty() || endMin.getText().length() > 2);

        customerError.setVisible(customerBox.getSelectionModel().isEmpty());
        contactError.setVisible(contactBox.getSelectionModel().isEmpty());
        userError.setVisible(userBox.getSelectionModel().isEmpty());
        typeError.setVisible(typeBox.getSelectionModel().isEmpty());
        dateError.setVisible(date.getValue() == null);

        // check if empty
        if (location.getText().trim().isEmpty() ||
                description.getText().trim().isEmpty() ||
                title.getText().trim().isEmpty() ||
                startHour.getText().trim().isEmpty() ||
                customerBox.getSelectionModel().isEmpty() ||
                contactBox.getSelectionModel().isEmpty() ||
                userBox.getSelectionModel().isEmpty() ||
                typeBox.getSelectionModel().isEmpty() ||
                date.getValue() == null ||
                startHour.getText().trim().isEmpty() ||
                startMin.getText().trim().isEmpty() ||
                endHour.getText().trim().isEmpty() ||
                endMin.getText().trim().isEmpty()) {
            errorLabel1.setText("Enter data into all fields");
            errorLabel.setText("");
            return;

            // if no errors, then try to modify or add new appointment
        } else {
            String tmp;
            tmp = apptID.getText();
            int ID = 0;

            if (apptRecordLabel.getText().equals("MODIFY APPOINTMENT")) ID = Integer.parseInt(tmp);
            String tempTime = title.getText();
            String tempDescription = description.getText();
            String tempLocation = location.getText();
            Contact tempContact = contactBox.getValue();
            String tempType = typeBox.getValue();
            User tempUser = userBox.getValue();
            Customer tempCustomer = customerBox.getValue();

            LocalDate apptDate = date.getValue();

            String startHr = startHour.getText();
            String startMn = startMin.getText();
            if (startHr.length() < 2) { startHr = "0" + startHr; }
            if (startMn.length() < 2) { startMn = "0" + startMn; }
            LocalTime beginTime = LocalTime.parse(startHr + ":" + startMn, DateTimeFormatter.ISO_TIME);
            ZonedDateTime begin = ZonedDateTime.of(apptDate, beginTime, ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC+0"));

            String endHr = endHour.getText();
            String endMn = endMin.getText();
            if (endHr.length() < 2) { endHr = "0" + endHr; }
            if (endMn.length() < 2) { endMn = "0" + endMn; }
            LocalTime endTime = LocalTime.parse(endHr + ":" + endMn, DateTimeFormatter.ISO_TIME);
            ZonedDateTime end = ZonedDateTime.of(apptDate, endTime, ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC+0"));

            Appointment appt = new Appointment(ID, tempTime, tempDescription, tempLocation, tempContact, tempType, begin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), tempCustomer, tempUser);

            // check if end time is after start time
            if (beginTime.isAfter(endTime) || beginTime.equals(endTime)) {
                timeError.setVisible(true);
                errorLabel1.setText("End time must be after start time");
                return;
            } else {
                timeError.setVisible(false);
            }

            // check if appt is within business hours
            if (!isBusinessHours(appt)) {
                timeError.setVisible(true);
                convertLocalToBusinessHours();
                return;
            } else {
                timeError.setVisible(false);
            }

            // check if new appointment data is overlapping existing appointment data
            if(isOverlapping(appt)){
                return;
            }

            try {
                    // modify existing appointment
                    if (apptRecordLabel.getText().equals("MODIFY APPOINTMENT")) {
                        DBConnection.modifyAppointment(appt);
                        errorLabel.setText("Appointment [ID: " + selectedAppointment.getAppointmentID() + ", type: " + selectedAppointment.getAppType() + "] was modified");
                        System.out.print("Appointment [ID: " + selectedAppointment.getAppointmentID() + ", type: " + selectedAppointment.getAppType() + "] was modified\n");
                    }

                    // add new appointment
                    else {
                        DBConnection.addAppointment(appt);
                        errorLabel.setText("Appointment [ID: " + ID + ", type: " + tempType + "] was added");
                        System.out.print("Appointment [ID: " + ID + ", type: " + tempType + "] was added\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            apptRecordLabel.setText("NEW APPOINTMENT");
            clearAllInputFields();
        }

    // displays updated appointment tableview
    /**
     * updates the date range to display in the tableview
     */
    private void updateView() {
        String begin = startZDT.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String end = endZDT.format(DateTimeFormatter.ISO_LOCAL_DATE);

        if(monthRadio.isSelected()) {
            String monthName = String.valueOf(startZDT.getMonth());
            String year = String.valueOf(startZDT.getYear());
            apptViewDateLabel.setText(monthName + " " + year);
        } else if (weekRadio.isSelected()) {
            apptViewDateLabel.setText(begin + " — " + end);
        }

        begin = startZDT.withZoneSameInstant(ZoneId.of("UTC+0")).format(DateTimeFormatter.ISO_LOCAL_DATE);
        end = endZDT.withZoneSameInstant(ZoneId.of("UTC+0")).format(DateTimeFormatter.ISO_LOCAL_DATE);

        try {
            DBConnection.updateAppointmentList(begin, end);
        }
        catch(SQLException e) {
            System.out.println("SQL error: " + e);
        }
    }
    boolean overlapCheck = false;


    // checks for overlapping appointments for each appointment in list
    /**
     * checks for overlapping appointments for each appointment in list
     * @param appt appointment
     * @return overlap check
     */
    private boolean isOverlapping(Appointment appt){
        ZonedDateTime start = appt.getStartDateObj();
        ZonedDateTime end = appt.getEndDateObj();
        overlapCheck = false;
        String startDate = start.withZoneSameInstant(ZoneId.of("Z")).withHour(0).withMinute(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss" ));
        String endDate = end.withZoneSameInstant(ZoneId.of("Z")).withHour(23).withMinute(59).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss" ));
        ObservableList<Appointment> List = FXCollections.observableArrayList();

        try{
            List = DBConnection.getAppointmentList(startDate, endDate);
        }
        catch(SQLException e){
            System.out.println("SQL error: " + e);
        }

        List.forEach(appList -> {
            if(appList.getAppointmentID() != appt.getAppointmentID() ){
                if(appList.getStartDateObj().isBefore(start)){
                    if(appList.getEndDateObj().isAfter(start)){
                        overlapCheck = true;
                        System.out.println("Appointment is overlapping another appointment");
                        errorLabel1.setText("Appointment overlapping");
                    }
                }
                else{
                    if(end.isAfter(appList.getStartDateObj())){
                        overlapCheck = true;
                        System.out.println("Appointment is overlapping another appointment");
                        errorLabel1.setText("Appointment overlapping");
                    }
                }
            }
        });
        return overlapCheck;
    }

    // clears all form fields and errors
    /**
     * clears all form fields and errors
     * @param event clear button clicked
     */
    @FXML
    void clearBtnClicked(ActionEvent event) {
        apptRecordLabel.setText("NEW APPOINTMENT");
        clearAllInputFields();
        clearAllErrorBoxes();
    }

    // deletes an appointment after confirmation
    /**
     * deletes an appointment after confirmation
     * @param event delete button clicked
     */
    @FXML
    void deleteBtnClicked(ActionEvent event) {
        Appointment selectedAppointment = apptTable.getSelectionModel().getSelectedItem();

        // if no appointment selected, error is displayed
        if (selectedAppointment == null) {
            errorLabel.setText("Select an appointment to delete");

        // if appointment is selected, alert asks for delete confirmation
        } else {
            errorLabel.setText("");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText(selectedAppointment.getAppType() + " appointment [ID: " + selectedAppointment.getAppointmentID() + "] for customer [name: " + selectedAppointment.getCustomerID().getName() + "]\nwill be permanently deleted.");
            alert.setContentText("Do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            // if ok is clicked, appointment is deleted
            if (result.get() == ButtonType.OK) {
                try {
                    DBConnection.deleteAppointment(selectedAppointment);
                } catch (SQLException e) {
                    System.out.println("SQL error: " + e);
                }
                clearAllInputFields();
                clearAllErrorBoxes();
                System.out.println("Appointment [ID: " + selectedAppointment.getAppointmentID() + ", type: " + selectedAppointment.getAppType() + "] was deleted");;
                errorLabel.setText("Appointment [ID: " + selectedAppointment.getAppointmentID() + ", type: " + selectedAppointment.getAppType() + "] was deleted");

            // if alert is cancelled or closed, appointment is not deleted
            } else if (result.get() == ButtonType.CANCEL || result.get() == ButtonType.CLOSE) {
                System.out.println("Appointment not deleted");
            }
        }
    }

    // converts company business hours in EST to display in users local business hours for alert notification
    /**
     * converts company business hours in EST to display in users local business hours for alert notification
     */
    public void convertLocalToBusinessHours() {
        //business time zone
        LocalDate dateEST = LocalDate.of(2021, 9, 22);
        ZoneId ZoneIdEST = ZoneId.of("America/New_York");

        //start time business hours
        LocalTime timeESTstart = LocalTime.of(8, 00);
        LocalTime timeESTend = LocalTime.of(22, 00);


        //end time business hours
        ZonedDateTime ZDTestStart = ZonedDateTime.of(dateEST, timeESTstart, ZoneIdEST);
        ZonedDateTime ZDTestEnd = ZonedDateTime.of(dateEST, timeESTend, ZoneIdEST);

        //user time zone
        ZoneId ZoneIdlocal = ZoneId.of(TimeZone.getDefault().getID());

        //start time in users local hours
        Instant ESTToInstantstart = ZDTestStart.toInstant();
        int localStartHour = ESTToInstantstart.atZone(ZoneOffset.systemDefault()).getHour();

        //start time in users local hours
        Instant ESTToInstantend = ZDTestEnd.toInstant();
        int localEndHour = ESTToInstantend.atZone(ZoneOffset.systemDefault()).getHour();

        Alert alertSE = new Alert(Alert.AlertType.INFORMATION);
        alertSE.setTitle("Alert");
        alertSE.setHeaderText("Selected times are out side of business hours");
        alertSE.setContentText("Business hours are:\n8:00 - 22:00 EST\n\nBusiness hours in your local timezone are:\n" + localStartHour + ":00" + " - " + localEndHour + ":00\n\n7 days/week");
        alertSE.show();
        errorLabel1.setText("Invalid appointment time");
    }

    // takes user to customers screen
    /**
     * takes user to customers screen
     * @param event customer button clicked
     */
    @FXML
    void customersBtnClicked(ActionEvent event) {
        try {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../View/CustomersScreen.fxml"));
            stage.setTitle("Scheduling Program - Customers");
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException e) {
            System.out.println("SQL error: " + e);
        }
    }

    // takes user to reports screen
    /**
     * takes user to reports screen
     * @param event reports button clicked
     */
    @FXML
    void reportsBtnClicked(ActionEvent event) {
        try {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../View/ReportsScreen.fxml"));
            stage.setTitle("Scheduling Program - Reports");
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException e) {
            System.out.println("SQL error: " + e);
        }
    }

    // takes user to login screen
    /**
     * takes user to login screen
     * @param event logout button clicked
     */
    @FXML
    void LogoutBtnClicked(ActionEvent event) {
        System.out.println("Logged out");
        try {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../View/LoginScreen.fxml"));
            stage.setTitle("Scheduling Program - Login");
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException e) {
            System.out.println("SQL error: " + e);
        }
    }

    // lambda expression continually updates current time and date label
    /**
     * lambda expression continually updates current time and date label
     */
    @FXML
    public void currentDateTimeLabel() {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, event -> {
            LocalDateTime localDateTime = LocalDateTime.now();

            // Current EST time for business hours 12 hr
            ESTtime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("America/New_York"));
            String ESTzone = ESTtime.format(DateTimeFormatter.ofPattern("hh:mm a"));

            // Current EST time for business hours 24 hr
            ESTtime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("America/New_York"));
            String ESTzone24 = ESTtime.format(DateTimeFormatter.ofPattern("HH:mm"));

            // Current EST time for business hours
            ESTLabel.setText(ESTzone);
            ESTLabel24.setText(ESTzone24);

            // Current time in 12 hr clock
            DateTimeFormatter hr12 = DateTimeFormatter.ofPattern("hh:mm a");
            currentTime12Label.setText(localDateTime.format(hr12));

            // Current time in 24 hr clock
            DateTimeFormatter hr24 = DateTimeFormatter.ofPattern("HH:mm");
            currentTime24Label.setText(localDateTime.format(hr24));

            // Current date
            DateTimeFormatter dateFormatted = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            String dateFormatStr = dateFormatted.format(localDateTime);
            currentDateLabel.setText(dateFormatStr);
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // clears all error boxes
    /**
     * clears all error boxes
     */
    @FXML
    private void clearAllErrorBoxes() {
        typeError.setVisible(false);
        dateError.setVisible(false);
        startHourError.setVisible(false);
        startMinError.setVisible(false);
        endHourError.setVisible(false);
        endMinError.setVisible(false);
        locationError.setVisible(false);
        titleError.setVisible(false);
        descriptionError.setVisible(false);
        customerError.setVisible(false);
        contactError.setVisible(false);
        userError.setVisible(false);
        timeError.setVisible(false);
        errorLabel1.setText("");
    }

    // clears all text fields
    /**
     * clears all text fields
     */
    @FXML
    private void clearAllInputFields() {
        errorLabel1.setText("");
        apptTable.getSelectionModel().clearSelection();
        apptID.clear();
        location.clear();
        description.clear();
        title.clear();
        customerBox.getSelectionModel().clearSelection();
        contactBox.getSelectionModel().clearSelection();
        typeBox.getSelectionModel().clearSelection();
        userBox.getSelectionModel().clearSelection();
        date.setValue(null);
        startHour.clear();
        startMin.clear();
        endHour.clear();
        endMin.clear();
    }

    /**
     * sets table data
     */
    private void setTableCells() {
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        apptTable.setItems(Appointment.appointmentList);
    }
}
