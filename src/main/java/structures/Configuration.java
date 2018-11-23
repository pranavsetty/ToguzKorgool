package structures;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Configuration {

    // configuration for white and black side holes
    private ArrayList<Hole> holes = new ArrayList<Hole>(18);

    // white and black kazans
    private Kazan white;
    private Kazan black;

    // constructor
    public Configuration(String sav){
        Parse(sav);
    }

    // tries to parse a given input line to an arraylist of holes
    // @param: an input String of the format '-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-//-,-'
    // @return: a boolean that returns false if the parsing failed
    private boolean Parse(String input){

        // split into numeric strings and check that there are 18 separate values
        String[] lineSplit = input.split("//");
        String[] holeValues = lineSplit[0].split(",");
        String[] kazanValues = lineSplit[1].split(",");

        // setups kazans
        white = new Kazan(Integer.parseInt(kazanValues[0]));
        black = new Kazan(Integer.parseInt(kazanValues[1]));

        // loop over quantities and create hole for each
        for(String number : holeValues){

            try {
                holes.add(new Hole(Integer.parseInt(number)));
            } catch (NumberFormatException nfe) {
                return false;
            };

        }

        AddToList(this);
        return true;

    }

    // tries to parse this given configuration into a saveable string
    // @param: void
    // @return: a string to save
    private String Parse(){

        // setup separate strings
        String line = "";

        // loop over all holes
        for(Hole hole : holes){
            line += (hole.GetKorgols() + ",");
        }

        return line.substring(0, 35) + "//" + white.GetKorgols() + "," + black.GetKorgols();

    }

    // getters and setters
    public ArrayList<Hole> GetHoles(){return holes;}
    public Kazan GetWhiteKazan(){ return white;}
    public Kazan GetBlackKazan(){ return black;}

    // equality override
    // two configurations are equal if their setups are the same
    // TODO - Implement equals override for Hole and Kazan classes
    public boolean equals(Configuration other) {
        return holes.equals(other.holes) && white == other.white && black == other.black;
    }

    // ------------------------

    // list of configurations loaded at any given time
    public static ArrayList<Configuration> configs = new ArrayList<>();

    // adds a configuration to the list of current configurations
    // @param: a configuration to add
    // @return: void
    public static void AddToList(Configuration config){

        // loop over configurations to check if it exists
        for(Configuration c : configs){
            if(c.equals(config)){
                return;
            }
        }

        configs.add(config);

    }

    // loads configs from a sav file and populates the above list
    // @param: void
    // @return: a message on the success status of the load
    public static String LoadConfigs(){

        // prep static list for population
        configs.clear();

        // get path to saves/tgkg.sav
        String path = "saves/tgkg.sav";
        File sav = new File(path);

        // create input stream from given file
        FileInputStream fstream;
        try {
            fstream = new FileInputStream(sav);
        } catch (FileNotFoundException e){
            return "No save file was found, please either reinstall the program or restart.";
        }

        // create reder to read lines of file
        BufferedReader reader = new BufferedReader(new InputStreamReader(fstream));
        try {
            while (reader.ready()) {
                String line = reader.readLine();
                // creates a new configuration and adds it to the list if it is not duplicate
                Configuration config = new Configuration(line);
            }
        } catch (IOException e){

        }

        return "Load successful: " + configs.size() + " configurations loaded.";

    }

    // saves the current set of configurations into the save file
    // @param: void
    // @return: a message on the success status of the save
    public static String SaveConfigs(){

        // array list to be filled with parsed versions of current configs
        List<String> lines = new ArrayList<>();
        for(Configuration config : configs){
            // parse config into string
            lines.add(config.Parse());
        }
        // clear list of invalid configurations
        lines.removeIf(p -> p == "");

        // get path to saves/tgkg.sav
        String path = "saves/tgkg.sav";
        File sav = new File(path);

        // attempt to create print writer using above file
        PrintWriter writer;
        try {
            writer = new PrintWriter(sav);
        } catch (FileNotFoundException e){
            return "No save file was found, please either reinstall the program or restart.";
        }

        // write new config strings to save file
        for(String line : lines){
            writer.println(line);
        }
        writer.close();

        return "Save successful: " + lines.size() + " configurations saved.";

    }

    // sets up the project directory to accomodate for a sav file
    // @param: void
    // @return: boolean determining whether the setup succeeded or failed
    public static boolean Setup(){

        File file = new File("saves/tgkg.sav");
        if(!file.exists()){
            file.getParentFile().mkdir();
            try {
                file.createNewFile();
            } catch (IOException e){
                return false;
            }
        }

        return true;

    }

}
