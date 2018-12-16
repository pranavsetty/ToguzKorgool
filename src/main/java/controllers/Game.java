package controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
    Button play, save, load;
    @FXML
    TextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9, tf10, tf11, tf12, tf13, tf14, tf15, tf16, tf17, tf18, tf19, tf20;

    // stages and scenes
    Stage window;
    Scene menu, game;

    // called to reload the dropdown menus
    private void reload() {

        initConfiguration.getItems().clear();
        initAIType.getItems().clear();

        initConfiguration.setPromptText("Choose config");

        if (Configuration.setup()) {
            Configuration.loadConfigs();
        }

        for (Configuration c : Configuration.configs) {
            initConfiguration.getItems().add(c.parse());
        }

        initAIType.setPromptText("Choose AI type");
        initAIType.getItems().addAll(Arrays.stream(AIType.values()).map(Enum::toString).collect(Collectors.toList()));

    }


    public void initialize() throws IOException {

        reload();

        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("second");
                Settings.config = new Configuration(initConfiguration.getValue());
                try {
                    Settings.aitype = AIType.valueOf(initAIType.getValue());
                } catch (IllegalArgumentException e){

                }
                FXMLLoader loaderGame = new FXMLLoader(getClass().getResource("/game.fxml"));
                try {
                    Parent gameRoot = loaderGame.load();
                    game = new Scene(gameRoot, 800, 600);
                    window = (Stage) play.getScene().getWindow();
                    window.setScene(game);
                } catch (IOException e) {

                }

            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                System.out.println(Configuration.configs.size());
                Configuration config = new Configuration(parse());
                System.out.println(Configuration.configs.size());
                System.out.println(parse());
                if (config.isValid()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Saved");
                    alert.setHeaderText("Configuration Saved. Load to see it.");
                    alert.setContentText(Configuration.saveConfigs());
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Configurations Not Saved");
                    alert.setContentText("Invalid configuration. Note that all Korgols on the board must add up to: " + Configuration.TOTALKORGOLS);
                    alert.showAndWait();
                }

            }
        });

        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Loaded");
                alert.setHeaderText("Configurations loaded");
                alert.setContentText(Configuration.loadConfigs());
                alert.showAndWait();
                reload();

            }
        });


    }

    private String parse() {

        String c = tf1.getText() + "," + tf2.getText() + "," + tf3.getText() + "," + tf4.getText() + "," + tf5.getText()
                + "," + tf6.getText() + "," + tf7.getText() + "," + tf8.getText() + "," + tf9.getText() + "," + tf10.getText()
                + "," + tf11.getText() + "," + tf12.getText() + "," + tf13.getText() + "," + tf14.getText() + "," + tf15.getText()
                + "," + tf16.getText() + "," + tf17.getText() + "," + tf18.getText() + "//" + tf19.getText() + "," + tf20.getText();
        return c;

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
