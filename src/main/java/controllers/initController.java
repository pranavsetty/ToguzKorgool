package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import structures.AIType;
import structures.Configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;


public class initController {

    @FXML
    ComboBox<String> initAIType;
    @FXML
    ComboBox<String> initConfiguration;
    @FXML
    Button play;

    static String ai;
    static String configuration;



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
                configuration = initConfiguration.getValue();
                ai = initAIType.getValue();
            }
        });



    }


}