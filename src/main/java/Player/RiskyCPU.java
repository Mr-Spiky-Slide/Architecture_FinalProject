package Player;

import Game.Zonk;
import Main.UI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class RiskyCPU extends CPU{

    public RiskyCPU(String name, int score) {
        super(name, score);

    }

    @Override
    public boolean endTurnAsk(int scoreBuffer, UI ui) {
        if (scoreBuffer >= 600) {
            return true;
        }else {
            return false;
        }
    }


}
