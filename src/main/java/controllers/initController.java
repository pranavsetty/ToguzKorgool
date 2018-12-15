package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import structures.AIType;

import java.util.Arrays;
import java.util.stream.Collectors;


public class initController {

    @FXML
    ComboBox<String> initAIType;
    @FXML
    ComboBox<String> initConfiguration;

    public void initialize() {
        initConfiguration.setPromptText("Choose config");
        initConfiguration.getItems().addAll("config1", "config2", "config3");
        initAIType.setPromptText("Choose AI type");
        initAIType.getItems().addAll(Arrays.stream(AIType.values()).map(Enum::toString).collect(Collectors.toList()));
    }


}