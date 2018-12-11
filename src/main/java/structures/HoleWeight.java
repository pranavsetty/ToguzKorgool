package structures;

public class HoleWeight {

    Hole hole;
    int weight;

    public HoleWeight(Hole hole, int weight){

        this.hole = hole;
        this.weight = weight;

    }

    public Hole getHole(){return hole;}
    public int getWeight(){return weight;}

}
