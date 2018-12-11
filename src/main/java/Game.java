import controllers.AI;
import controllers.Board;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import structures.AIType;
import structures.Configuration;
import structures.Hole;
import structures.Seat;

public class Game extends Application {

    // game board, AI and play status
    Board board = null;
    AI ai = null;
    boolean inGame = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/basic.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        if(Configuration.setup()){
        } else {
            // error message and quit
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("File System Error");
            alert.setHeaderText("Error 0001");
            alert.setContentText(Configuration.loadConfigs());
            alert.showAndWait();
            Platform.exit();
        }
    }

    public void play(Configuration config){

        inGame = true;
        board = new Board(config);
        ai = new AI(AIType.DEF, true);

    }

    public void play(){

        board = new Board();
        ai = new AI(AIType.DEF, true);

    }

    public void playerMove(Hole hole){

        if(inGame){
            board.move(hole, Seat.WHITE);
        }

    }

    public void AIMove(){

        board.move(ai.evaluate(board.getHoles()), Seat.BLACK);

    }

    public String add(String sav){

        Configuration config = new Configuration(sav);
        if(config.isValid()){
            return Configuration.saveConfigs();
        } else {
            return "Invalid configuration. Note that all Korgols on the board must add up to: " + Configuration.TOTALKORGOLS;
        }

    }

    public String load(){
        return Configuration.loadConfigs();
    }

    public Seat winner(){

        return board.hasWon();

    }

    public Board getBoard(){return board;}

}
