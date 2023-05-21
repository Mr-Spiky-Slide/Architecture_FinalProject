package Game;

import java.util.Random;

public class Die {
    int faceValue;
    static Random rand = new Random();

    private static Die die;

    /**
     * This is for the singleton design pattern, checking if there is an instance of Die, if there isn't it makes one, if there is, it returns the existing one
     * @return
     */
    public static Die getInstance(){
        if (die == null){
            die = new Die(6);
        }
        return die;
    }

    /**
     * this sets the face value of the die
     * @param faceValue
     */
    public Die(int faceValue ) {
        this.faceValue = faceValue;
    }

    /**
     * sets the face value to a random int between 1 and 6 inclusive
     */
    public void rollDie(){
            faceValue = rand.nextInt(1, 7);

    }


    /**
     * This returns the face value of the die
     * @return
     */
    public int getValue() {
        return faceValue;
    }

}
