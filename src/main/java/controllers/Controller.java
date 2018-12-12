package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import structures.*;

import java.util.ArrayList;

public class Controller {

    private Board board;
    private AI ai;
    private boolean inGame = false;
    private Configuration config;

    @FXML
    private GridPane black, white;
    @FXML
    private Text whiteKazan, blackKazan;

    public void initialize() {
        inGame = true;
        board = new Board();
        ai = new AI(AIType.DEF, true);
        updateBoard();
    }

    void updateBoard() {
        ArrayList<Hole> holesList = board.getHoles();
        clearBoard();
        updateKazans();

        for (int i = 0; i < holesList.size(); i++) {
            Hole currentKorgol = holesList.get(i);
            int korgolValue = currentKorgol.getKorgols();
            Seat korgolSeat = currentKorgol.getSeat();

            Text korgolTextValue = new Text();
            korgolTextValue.setText(String.valueOf(korgolValue));
            korgolTextValue.getStyleClass().add("counter");
            StackPane score = new StackPane(korgolTextValue);
            score.getStyleClass().add(korgolSeat.toString().toLowerCase());

            if (korgolSeat == Seat.WHITE) {
                final int ii = i;
                score.setOnMousePressed(event -> nextMove(holesList.get(ii)));
            }

            if (i < 9) {
                white.add(score, (i * 2) + 1, 1);
            } else {
                black.add(score, ((i % 9 * 2) + 1), 1);
            }
        }
    }

    private void clearBoard() {
        black.getChildren().clear();
        white.getChildren().clear();
    }

    private void updateKazans() {
        whiteKazan.setText(String.valueOf(board.getPlayerOneKazan().getKorgols()));
        blackKazan.setText(String.valueOf(board.getPlayerTwoKazan().getKorgols()));
    }

    private void nextMove(Hole hole) {
        playerMove(hole);
        AIMove();
        updateBoard();
    }

    private void playerMove(Hole hole) {
        System.out.println(hole);
        if (inGame) {
            board.move(hole, Seat.WHITE);
        }
    }

    private void AIMove() {
        board.move(ai.evaluate(board.getHoles()), Seat.BLACK);
    }

    public String add(String sav) {
        Configuration config = new Configuration(sav);
        if (config.isValid()) {
            return Configuration.saveConfigs();
        } else {
            return "Invalid configuration. Note that all Korgols on the board must add up to: " + Configuration.TOTALKORGOLS;
        }
    }

    public String load() {
        return Configuration.loadConfigs();
    }

}