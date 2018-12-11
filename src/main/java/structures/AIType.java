package structures;

import java.util.Random;

public enum AIType {
    AGG,DEF,WILD;

    public static AIType getRandom() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
