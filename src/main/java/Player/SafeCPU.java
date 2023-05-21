package Player;

import Game.Zonk;
import Main.UI;

import java.util.ArrayList;
import java.util.Collections;

public class SafeCPU extends CPU {
    public SafeCPU(String name, int score) {
        super(name, score);
    }


    /**
     * only will end the turn if the score for that turn is 250 or greater
      * @param scoreBuffer
     * @param ui
     * @return
     */
    @Override
    public boolean endTurnAsk(int scoreBuffer, UI ui) {
        if (scoreBuffer >= 250) {
            return true;
        } else {
            return false;
        }
    }
}