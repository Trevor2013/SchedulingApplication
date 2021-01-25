
package Main;

import DAO.DBConnection;
import java.time.ZoneId;
import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class Main launches the Scheduling Application.
 * @author Trevor Ross, tros114@wgu.edu
 */
public class Main extends Application {
    
    private Locale userLocale = Locale.getDefault();

    /**
     * The main method launches the application.  The locale can be set here
     * for testing purposes.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        
        //Locale.setDefault(Locale.FRANCE);
        System.out.println(ZoneId.systemDefault().toString());
        
        DBConnection.startConnection();    
            
        launch(args);
        
        DBConnection.endConnection();
    }
       
    /**
     * Loads the Main screen.
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ViewController/LoginForm.fxml"));
        
        if ("en_US".equals(userLocale.toString())) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Scheduling Application");
        }
        else {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Planification Application");
        }
        stage.show();
    }
    
}
