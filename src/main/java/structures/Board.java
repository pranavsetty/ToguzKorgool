package structures;

import java.util.ArrayList;

public class Board {

    // all hols on the board
    private ArrayList<Hole> board = new ArrayList<Hole>(18);

    // each players kazan
    private Kazan playerOneKazan;
    private Kazan playerTwoKazan;

    // indexes of each players' tuz
    private Integer blackTuz = null;
    private Integer whiteTuz = null;

    // win condition
    private static final int WINAMOUNT = 82;

    // constructor to set up a board with no configuration
    // @param: void
    // @return: new Board object
    public Board() {
        // setup kazans
        playerOneKazan = new Kazan(0);
        playerTwoKazan = new Kazan(0);

        // setup holes and initial seats
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

    // constructor to set up a board with a custom configuration
    // @param: configuration
    // @return: new Board object
    public Board(Configuration config) {
        // setup kazans
        playerOneKazan = config.getBlackKazan();
        playerTwoKazan = config.getWhiteKazan();

        // setup holes and initial seats
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

    // method to keep move method short. attempts to make a hole a tuz if the seat doesn't already have a tuz
    // otherwise fails
    // @param: a hole to make tuz and a seat that is making the move
    // @return: true if it was made a tuz, false otherwise
    private boolean makeTuz(Hole hole, Seat forthis){

        switch(forthis){
            case BLACK:
                if(blackTuz == null && (whiteTuz == null || board.indexOf(hole) != whiteTuz)){
                    blackTuz = board.indexOf(hole);
                    hole.setSeat(Seat.BLACK);
                    return true;
                } else {
                    return false;
                }
            case WHITE:
                if(whiteTuz == null && (blackTuz == null || board.indexOf(hole) != blackTuz)){
                    whiteTuz = board.indexOf(hole);
                    hole.setSeat(Seat.WHITE);
                    return true;
                } else {
                    return false;
                }

        }

        return false;

    }

    // moves the korgols from a target hole by the respective mover
    // checks to make sure the hole belongs to the mover should be handled naturally long before reaching this method
    // @param: target hole to move from and the seat doing the moving
    // @return: void
    public void move(Hole targetHole, Seat mover) {

        // number of korgols to move and index of hole to move them from
        int ballsToMove = targetHole.getKorgols();
        int targetHoleIndex = board.indexOf(targetHole);

        // clear hole in preparation for move
        targetHole.clear();

        Hole finalHole;
        switch(ballsToMove){

            case 1:
                finalHole = board.get((targetHoleIndex + 1) % 18);
                finalHole.addKorgol();
                break;
            default:
                finalHole = board.get((targetHoleIndex + ballsToMove - 1) % 18);
                for (int i = targetHoleIndex; i <= (targetHoleIndex + ballsToMove - 1); i++) {
                    board.get(i % 18).addKorgol();
                }
                break;
        }

        // handles the situations:
        // if the final hole has an even amount of korgols and is not owned by you
        // if the final hole has 3 and is not owned by you
        if(finalHole.getSeat() != mover && finalHole.isEven()){
            seatToKazan(mover).addKorgols(finalHole.getKorgols());
            finalHole.clear();
        } else if(finalHole.getSeat() != mover && finalHole.getKorgols() == 3){
            if(makeTuz(finalHole, mover)){
                seatToKazan(mover).addKorgols(finalHole.getKorgols());
                finalHole.clear();
            }
        }

    }

    // gets the Kazan associated to a certain seat colour
    // @param: seat colour of choice
    // @return: Kazan for that seat
    public Kazan seatToKazan(Seat seat){
        if(seat == Seat.WHITE){
            return playerOneKazan;
        } else {
            return playerTwoKazan;
        }
    }

    // method to return a seat if they have met the win condition
    // @param: void
    // @return: either a winning seat or null
    public Seat hasWon(){

        if(playerOneKazan.getKorgols() >= WINAMOUNT){
            return Seat.WHITE;
        } else if(playerTwoKazan.getKorgols() >= WINAMOUNT){
            return Seat.BLACK;
        } else {
            return null;
        }

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

    // getters
    public ArrayList<Hole> getHoles(){return board;}

}
