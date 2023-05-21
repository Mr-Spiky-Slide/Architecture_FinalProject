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

    @Override
    public boolean endTurnAsk(int scoreBuffer, UI ui) {
        if(ui.askToEndTurn(scoreBuffer)){
            return true;
        }
        return false;
    }

    @Override
    public void saveDice(ArrayList<Integer> dice, UI ui, Zonk zonk) {
        boolean keep = true;
        while(keep) {
            //TODO make it so they MUST save one die per turn
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

    public int getScore(){
        return score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addToScore(int val){
        this.score = this.score + val;
    }
}
