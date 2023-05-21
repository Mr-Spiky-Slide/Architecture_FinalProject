package Player;

import Game.Zonk;
import Main.UI;

import java.util.ArrayList;

public class Person implements Player{
    int score;
    String name;



    public Person(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Takes in zonk active dice and ui in order to use the roll functions
     * @param zonk
     * @param activeDice
     * @param ui
     */
    @Override
    public void roll(Zonk zonk, int activeDice, UI ui) {
        ui.enterToRoll();
        zonk.rollDice(activeDice);
    }

    /**
     * This returns the current score buffer and allows the user to choose if they want to end the turn
     * @param scoreBuffer
     * @param ui
     * @return boolean if they want to end turn
     */
    @Override
    public boolean endTurnAsk(int scoreBuffer, UI ui) {
        if(ui.askToEndTurn(scoreBuffer)){
            return true;
        }
        return false;
    }

    /**
     * Asks user if they want to save the dice. If they do it prompts them which one they want to save.
     * When they select one to save it gets added to locked dice, removed from active dice, and fewer dice are rolled next turn
     * @param dice
     * @param ui
     * @param zonk
     */
    @Override
    public void saveDice(ArrayList<Integer> dice, UI ui, Zonk zonk) {
        boolean keep = true;
        while(keep) {
            keep = ui.keepSaving();
            if (keep){
                ui.printDice(dice);
                int indexSelection = ui.askToSave(dice);
                zonk.addToLockedDice(dice.get(indexSelection-1));
                zonk.deleteActiveDie(indexSelection-1);
                zonk.fewerDice(1);
            }
        }


    }

    /**
     * Score getter
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
