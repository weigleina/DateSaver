package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * the user class is used to build a user object
 */
public class User {
    /**
     * builds a user object
     */
    private final int id;
    private final String name;
    private final String password;

    /**
     * constructor for the user object
     * @param id user ID
     * @param name user name
     * @param password user password
     */
    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    /**
     * gets the user ID
     * @return user ID (int)
     */
    public int getId() { return id; }

    /**
     * gets the user name
     * @return user name (string)
     */
    public String getName() { return name; }

    /**
     * gets the user password
     * @return user password (string)
     */
    public String getPassword() { return password; }

    /**
     * observableList contains all user objects
     */
    public static ObservableList<User> userList = FXCollections.observableArrayList();

    /**
     * gets user by ID
     * @param ID user ID
     * @return user found by ID
     */
    public static User getUserByID(int ID) {
        int i = 0;
        while(i < userList.size()) {
            if(ID == userList.get(i).getId()) return userList.get(i);
            i++;
        }
        return null;
    }

    /**
     * converts user name to a string
     * @return name of the user (string)
     */
    @Override
    public String toString() { return this.getName(); }
}