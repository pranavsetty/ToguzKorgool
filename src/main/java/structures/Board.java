package structures;

import java.util.ArrayList;

public class Board {
    private ArrayList<Hole> board = new ArrayList<Hole>(18);
    private Kazan playerOneKazan;
    private Kazan playerTwoKazan;

    public static final int WINAMOUNT = 82;

    public Board() {
        for (int i = 0; i < 18; i++) {
            Hole hole = new Hole(9);
            board.add(hole);
            if(i >= 0 && i < 9){
                hole.setSeat(Seat.WHITE);
            } else {
                hole.setSeat(Seat.BLACK);
            }

        }
    }

    public Board(Configuration config) {
        playerOneKazan = config.getBlackKazan();
        playerTwoKazan = config.getWhiteKazan();
        for (int i = 0; i<18; i++) {
            Hole hole = config.getHoles().get(i);
            board.add(hole);
            if(i >= 0 && i < 9){
                hole.setSeat(Seat.WHITE);
            } else {
                hole.setSeat(Seat.BLACK);
            }
        }
    }

    public void move(Hole targetHole, Seat mover) {
        int ballsToMove = targetHole.getKorgols();
        int targetHoleIndex = board.indexOf(targetHole);
        targetHole.clear();
        Hole finalHole = board.get((targetHoleIndex + ballsToMove - 1) % 18);
        for (int i = targetHoleIndex; i < (targetHoleIndex + ballsToMove); i++) {
            board.get(i % 18).addKorgol();
        }
        if (board.indexOf(targetHole) < 9 && board.indexOf(finalHole) >= 9 && finalHole.isEven()) {
            playerOneKazan.addKorgols(finalHole.getKorgols());
            finalHole.clear();
        }
        else if (board.indexOf(targetHole) >= 9 && board.indexOf(finalHole) < 9 && finalHole.isEven()) {
            playerOneKazan.addKorgols(finalHole.getKorgols());
            finalHole.clear();
        }
        else {}
    }

    // a 'shadow' move method that alters a given board to give you a
    // predicted state after said move, does not alter Kazans
    public static ArrayList<Hole> move(int holeIndex, ArrayList<Hole> board, boolean all) {

        ArrayList<Hole> shadowBoard = new ArrayList<Hole>();
        for(Hole h : board){
            shadowBoard.add(new Hole(h.getKorgols()).setSeat(h.getSeat()));
        }

        Hole targetHole = shadowBoard.get(holeIndex);

        int ballsToMove = targetHole.getKorgols();
        int targetHoleIndex = shadowBoard.indexOf(targetHole);
        targetHole.clear();
        Hole finalHole = shadowBoard.get((targetHoleIndex + ballsToMove - 1) % 18);
        for (int i = targetHoleIndex; i < (targetHoleIndex + ballsToMove); i++) {
            shadowBoard.get(i % 18).addKorgol();
        }


        if(all){
        } else {
            shadowBoard.clear();
            shadowBoard.add(finalHole);
        }
        return shadowBoard;
    }

    public ArrayList<Hole> getHoles(){return board;}

    public Seat hasWon(){

        if(playerOneKazan.GetKorgols() == WINAMOUNT){
            return Seat.BLACK;
        } else if(playerTwoKazan.GetKorgols() == WINAMOUNT){
            return Seat.WHITE;
        } else {
            return null;
        }

    }

}
