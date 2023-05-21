package Player;

import Game.Zonk;
import Main.UI;

import java.util.ArrayList;

public interface Player {


    void roll(Zonk zonk, int activeDice, UI ui);

    boolean endTurnAsk(int scoreBuffer, UI ui);

    void saveDice(ArrayList<Integer> dice, UI ui, Zonk zonk);

    int getScore();

    String getName();

    void addToScore(int val);
}
