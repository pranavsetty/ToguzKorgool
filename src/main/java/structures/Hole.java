package structures;

public class Hole {

    private int korgols;
    private boolean tuz;
    private Seat seat;

    //Constructor for a hole object
    //@param number of korgols in a hole
    //@return new Hole object
    public Hole(int input){
        korgols = input;
        tuz = false;
    }

    //Korgol getter
    public int getKorgols(){return korgols;}

    public boolean isTuz(){return tuz;}

    //Mutator method that adds one korgol in a hole
    public void addKorgol() {korgols++;}

    //Mutator method that adds a variable number of korgols to a hole
    public void addKorgols(int number) {korgols+=number;}

    public void clear() {korgols=0;}

    //checks whether the hole has an even amoung of korgols
    public boolean isEven() {
        if (korgols%2==0){return true;}
        else {return false;}
    }

    //checks whether the hole is empty
    public boolean isEmpty(){
        if(korgols == 0){return true;}
        return false;
    }

    public Hole setSeat(Seat seat){
        this.seat = seat;
        return this;
    }

    public Seat getSeat(){ return seat; }

}
