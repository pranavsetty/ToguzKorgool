package structures;

public class Kazan {

    int korgols;

    //Constructor which creates a Kazan with "input Korgols"
    public Kazan(int input){
        korgols = input;
    }

    //Accessor method for number of korgols in Kazan
    public int GetKorgols(){return korgols;}

    //Mutator method which adds one korgol to the Kazan
    public void addKorgol(){korgols++;}

    //Mutator method which adds an indefinite number of korgols to the Kazan
    public void addKorgols(int newKorgs){
      korgols+=number;
    }

    public void clearKazan(){korgols = 0;}
    

}
