package structures;

public class Hole {

    private int korgols;
    private boolean tuz;
    private Seat seat;

    public Hole(int input){
        korgols = input;
        tuz = false;
    }

    public int getKorgols(){return korgols;}

    public boolean isTuz(){return tuz;}

    public void addKorgol() {korgols++;}

    public void addKorgols(int number) {korgols+=number;}

    public void clear() {korgols=0;}

    public boolean isEven() {
        if (korgols%2==0){return true;}
        else {return false;}
    }

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
