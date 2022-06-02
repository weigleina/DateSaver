package Main;

import Utils.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Alyssa Weiglein
 * DateSaver main class
 */
public class Main extends Application {

    /**
     * @param primaryStage the primary stage of the application
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        ResourceBundle rb = ResourceBundle.getBundle("Language/Lang", Locale.getDefault());
        System.out.println(rb.getString("DateSaver"));

        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginScreen.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 1203, 663));
        primaryStage.show();
        DBConnection.startConnection();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
