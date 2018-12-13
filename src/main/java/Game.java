import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Toguz Korgool");
        Scene scene = new Scene(root, 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
