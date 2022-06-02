package Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * the log class is used to append login attempt
 */
public class Log {

    private static final String loginActivity = "login_activity.txt";

    /**
     * creates the login_activity.txt file.
     */
    public static void createLogFile(){
        File file = new File(loginActivity);
        try {
            if (file.createNewFile()) {
                System.out.println("\tFile created: " + file.getName() + "\n");
                Log.writeLogFile("\tLog file created\n");
            } else {
                System.out.println("View login history at 'login_activity.txt'\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * writes to the login_activity.txt file
     * @param loginString the string that is written to the file
     */
    public static void writeLogFile(String loginString){
        loginString = LocalDateTime.now().toString() + "] " + loginString;
        File file = new File(loginActivity);
        if (!file.exists()) {
            createLogFile();
        } else {
            try {
                FileWriter myWriter = new FileWriter(loginActivity, true);
                myWriter.append("[" + loginString);
                myWriter.close();
                System.out.println("\tLogin record added: [" + loginString);
            } catch (IOException e) {
                System.out.println("File error: " + e);
            }
        }
    }
}
