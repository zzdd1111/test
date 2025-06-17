package comp1110.ass2.entity;

import java.util.Random;

public class DieEntity extends Entity{

    private static int lowerBound = 1;
    private static int upperBound = 6;

    public int rollNumber ;
    @Override
    void decode(String representation) {

    }

    @Override
    String encode() {
        return null;
    }

    public static int rollDice(){
        Random r = new Random();
        return r.nextInt(upperBound - lowerBound + 1) + lowerBound;

    }
}