package Player;

import Game.Zonk;
import Main.UI;

import java.util.ArrayList;
import java.util.Collections;

public abstract class CPU implements Player{
    int score;
    String name;

    public CPU(String name, int score) {
        this.name = name;
        this.score = score;
    }


    @Override
    public void roll(Zonk zonk, int activeDice, UI ui) {
        zonk.rollDice(activeDice);
    }

    @Override
    public boolean endTurnAsk(int scoreBuffer, UI ui) {
        if (scoreBuffer >= 400) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * Uses similar logic to how rolls are scored to decide weather the CPU should lock the dice.
     * It starts with the highest scoring options then filters down to the lower scoring ones.
     * There are several if statements to prevent out of bounds errors.
     * The loop iterates through the fresh dice and checks if a 5,4,or3 of a kind would be in our out of bounds
     * Then it checks for the 5,4,or 3 of a kind, then for 1s, and finally for 5s
     * @param dice
     * @param ui
     * @param zonk
     */
    @Override
    public void saveDice(ArrayList<Integer> dice, UI ui, Zonk zonk) {

        ui.printDice(dice);
        Collections.sort(dice);
        for (int i = 0; i < dice.size() - 1; i++) {
            if ((i+4) < dice.size()) {
                if ((dice.get(i) == dice.get(i + 1)) && (dice.get(i) == dice.get(i + 2)) && (dice.get(i) == dice.get(i + 3)) && (dice.get(i) == dice.get(i + 4))) {
                    for (int j = 0; j < 5; j++) {
                        zonk.addToLockedDice(dice.get(i));
                        zonk.fewerDice(1);
                    }
                    for (int j = 0; j < 5; j++) {
                        dice.remove(i);
                    }
                }
            }
            if ((i+3) < dice.size()) {

                if ((dice.get(i) == dice.get(i + 1)) && (dice.get(i) == dice.get(i + 2)) && (dice.get(i) == dice.get(i + 3))) {
                    for (int j = 0; j < 4; j++) {
                        zonk.addToLockedDice(dice.get(i));
                        zonk.fewerDice(1);
                    }
                    for (int j = 0; j < 4; j++) {
                        dice.remove(i);
                    }
                }
            }

            if ((i+2) < dice.size()){
                if ((dice.get(i) == dice.get(i+1)) && (dice.get(i) == dice.get(i+2))){
                    for (int j = 0; j < 3; j++) {
                        zonk.addToLockedDice(dice.get(i));
                        zonk.fewerDice(1);
                    }
                    for (int j = 0; j < 3; j++) {
                        dice.remove(i);
                    }
                }
            }

            if (i < dice.size()-1){
                if (dice.get(i) == 1 || dice.get(i) == 5){
                    zonk.addToLockedDice(dice.get(i));
                    zonk.deleteActiveDie(1);
                    dice.remove(i);
                }
            }

        }


    }

    /**
     * score getter
     * @return
     */
    public int getScore(){
        return score;
    }

    /**
     * Name getter
     * @return
     */
    public String getName() {
        return name;
    }


    /**
     * score setter
     * @param val
     */
    public void addToScore(int val){
        this.score = this.score + val;
    }
}
