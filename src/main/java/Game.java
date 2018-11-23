import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import structures.*;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/basic.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        // Temporary
        String setup = "5,5,5,5,5,5,5,5,5,4,4,4,5,5,5,5,5,5//0,0";
        String setup2 = "5,5,1,5,2,5,4,5,5,4,7,3,3,5,1,1,1,3//12,39";
        Configuration c = new Configuration(setup);
        Configuration c2 = new Configuration(setup2);
        System.out.println(Configuration.SaveConfigs());
        System.out.println(Configuration.LoadConfigs());
        System.out.println(Configuration.configs.size());
    }

    public static void main(String[] args) {
        if(Configuration.Setup()){
            launch(args);
        } else {
            // error message
        }
    }

}
