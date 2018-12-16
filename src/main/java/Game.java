import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import structures.AIType;
import structures.Configuration;
import structures.Settings;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Game extends Application {

    @FXML
    ComboBox<String> initAIType;
    @FXML
    ComboBox<String> initConfiguration;
    @FXML
    Button play;

    // stages
    Stage window;
    Scene menu,game;

    // starting
    public static String ai;
    public static String configuration;


    public void initialize() throws IOException {

        initConfiguration.setPromptText("Choose config");

        if(Configuration.setup()){
            Configuration.loadConfigs();
        }

        for(Configuration c : Configuration.configs){

            initConfiguration.getItems().add(c.parse());

        }

        initAIType.setPromptText("Choose AI type");
        initAIType.getItems().addAll(Arrays.stream(AIType.values()).map(Enum::toString).collect(Collectors.toList()));

        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("second");
                Settings.config = new Configuration(initConfiguration.getValue());
                Settings.aitype = AIType.AGG;
                FXMLLoader loaderGame = new FXMLLoader(getClass().getResource("/game.fxml"));
                try {
                    Parent gameRoot = loaderGame.load();
                    game = new Scene(gameRoot, 800, 600);
                    window = (Stage)play.getScene().getWindow();
                    window.setScene(game);
                } catch (IOException e){

                }

            }
        });



    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("/init.fxml"));
        Parent root = loaderMenu.load();
        primaryStage.setTitle("Toguz Korgool");
        menu = new Scene(root, 800, 600);
        System.out.println("first");
        primaryStage.setScene(menu);
        primaryStage.show();
    }
}
