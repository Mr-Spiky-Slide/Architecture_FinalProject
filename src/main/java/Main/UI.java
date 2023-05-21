package Main;

import Player.PlayerType;
import Player.*;


import java.util.ArrayList;
import java.util.List;

public class UI {
    private static UI ui;
    private IOStrategy ioStrategy;
    private UI (IOStrategy ioStrategy) {
        this.ioStrategy = ioStrategy;
    };

    public static UI getInstance(IOStrategy ioStrategy){
        if (ui == null){
            ui = new UI(ioStrategy);
        }
        return ui;
    }

    public int welcomeMessage(){
        ioStrategy.outputLine("Welcome to Zonk! \n How many players do you want?");
        int playerCount = ioStrategy.inputInt();
        while (playerCount < 2){
            ioStrategy.outputLine("Too few players");
            playerCount = ioStrategy.inputInt();
        }
        return playerCount;
    }

    public void ZONK(){
        ioStrategy.outputLine("ZONK!!!");
    }

    public PlayerType getPlayerType() {
        ioStrategy.outputLine("(P)erson or (C)PU: ");
        switch (ioStrategy.inputCharacter()) {
            case 'C' -> {
                ioStrategy.outputLine("(R)isky CPU or (N)ormal CPU");
                switch (ioStrategy.inputCharacter()) {
                    case 'R' -> {
                        return PlayerType.RISKY_CPU;
                    }
                    default -> {
                        return PlayerType.CPU;
                    }
                }
            }
                default -> {
                    return PlayerType.PERSON;
                }
            }
        }


    public void printDice(ArrayList<Integer> diceList){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < diceList.size(); i++) {
            output.append(String.format("Die #%d: %d \n", i+1, diceList.get(i)));
        }
        ioStrategy.outputLine(output.toString());

    }

    public void nextPlayer(Player player){
        ioStrategy.outputLine(player.getName()+"'s Turn...");
    }

    public String getName(List<Player> players){
        ioStrategy.outputLine("please enter player name: ");
        String name;
        boolean sameName = false;
        do {
            name = ioStrategy.inputString();
            for(Player player : players){
                if (player.getName() == name){
                    sameName = true;
                }else {
                    sameName = false;
                }
            }
        }while (sameName);
        return name;
    }

    public void enterToRoll(){
        ioStrategy.outputLine("Press enter to roll...");
        ioStrategy.inputString();
    }

    public boolean askToEndTurn(int score){
        ioStrategy.outputLine(String.format("Your score is %d, do you want to end your turn? (y/N)", score));
        char choice = ioStrategy.inputCharacter();
        switch (choice){
            case 'Y':
                return false;
            case 'N':
                return true;
            default:
                ioStrategy.outputLine("Invalid input, ending turn...");
                return false;
        }
    }

    public int askToSave(ArrayList<Integer> dice){
        int saveNumber = 0;
        try {
            ioStrategy.outputLine("Enter the die you want to save");
            saveNumber = ioStrategy.inputInt();
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw(e);
        }

        return saveNumber;
    }

    public boolean keepSaving(){
        ioStrategy.outputLine(String.format("Do you want to keep saving dice? (y/N)"));
        char choice = ioStrategy.inputCharacter();
        switch (choice){
            case 'Y':
                return true;
            case 'N':
                return false;
            default:
                ioStrategy.outputLine("Invalid input, ending save...");
                return false;
        }
    }

    public void printScore(String name, int score){
        ioStrategy.outputLine(String.format("%s has a score of %d", name, score));
    }

    public void winner(List<Player> players, Player winner){
        ioStrategy.outputLine(String.format("%s wins with %d points!\n", winner.getName(), winner.getScore()));

        for (Player player:players) {
            ioStrategy.outputLine(String.format("\t%s with %d points", player.getName(), player.getScore()));
        }
    }

    public void rules(){
        ioStrategy.outputLine("Welcome to Zonk! Lock dice that are three, four, or five of a kind. \n" +
                "You can also lock single dice. \n" +
                "1: 100 Points\n" +
                "5: 50 Points\n" +
                "3 of a kind: Face value times 100\n" +
                "4 of a kind: Face value times 200\n" +
                "5 of a kind: Face value times 300\n" +
                "All other singular dice of double are zero\n" +
                "You may re-roll as long as points were scored on your current roll.\n" +
                "If no points are scored then you Zonked and your turn is over");
    }

}
