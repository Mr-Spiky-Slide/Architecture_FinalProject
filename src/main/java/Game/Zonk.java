package Game;

import Main.UI;
import Player.*;

import java.util.*;
import java.util.stream.Collectors;

public class Zonk {
    private UI ui;

    private static Zonk zonk;

    public List<Player> players = new ArrayList<>();

    public Die die;

    private int activeDice = 5;

    private Player activePlayer;

    private int scoreBuffer = 0;
    private boolean gameOn = true;

    public ArrayList<Integer> freshDice = new ArrayList<>();
    public ArrayList<Integer> lockedDice = new ArrayList<>();


    public Zonk(UI ui, Die die) {
        this.ui = ui;
        this.die = die;
    }

    /**
     * This is for the singleton design pattern, checking if there is an instance of Zonk, if there isn't it makes one, if there is, it returns the existing one
     * @param ui
     * @param die
     * @return
     */
    public static Zonk getInstance(UI ui, Die die){
        if (zonk == null){
            zonk = new Zonk(ui, die);
        }
        return zonk;
    }

    /**
     * This initiates a new game by asking how many players it should have, then creates the players according to how many the user chose
     * Finally, it sets the first active player to the first player in the list
     */
    private void newGame(){
        ui.rules();
        int playerCount = ui.welcomeMessage();
        for (int i = 0; i < playerCount; i++) {
            addPlayer(ui);
        }
        activePlayer = players.get(0);


    }

    /**
     * This initiates the game with the newGame() method then loops through the takeTurn() method until someone reaches the win condition
     * Once someone reaches the win condition, it stops the loop, sorts the players, then prints out the winning info with the ui class
     */
    public void playGame(){
        newGame();
        while (gameOn){
            takeTurn();
            if (checkForWinner()){
                gameOff();
                makeLeaderboard();
                ui.winner(players, findHighestScorer());
            }
        }
    }

    /**
     * this sets the gameOn boolean to false to indicate the end of the loop and that the game is over
     */
    private void gameOff(){
        gameOn = !gameOn;
    }

    /**
     * Prints out the next player info, resets dice, initiates variables
     * a do while loop for rolling is made. While there is no Zonk and while the player wishes to continue it iterates through.
     * If a roll is not a zonk it allows the player to collect and score dice, if not, any points made that turn are lost and zonk is set to true
     * After the rolling is over, the turn is scored and printed. The score buffer is reset and the next player is made the active player
     */
    private void takeTurn(){
        ui.nextPlayer(activePlayer);
        resetDice();
        boolean isZonk;
        boolean keepRolling = true;

        do {
            lockedDice.clear();
            activePlayer.roll(zonk, activeDice, ui);
            ui.printDice(freshDice);
            isZonk = testZonk(lockedDice, freshDice);
            if (!isZonk){
                activePlayer.saveDice(freshDice, ui, zonk);
                scoreRoll();
                keepRolling = activePlayer.endTurnAsk(scoreBuffer, ui);
            }
            else {
                setScoreBufferToZero();
                ui.ZONK();
            }

        }while (!isZonk && keepRolling);
        scoreTurn();
        ui.printScore(activePlayer.getName(), activePlayer.getScore());
        resetBuffer();
        nextPlayer();
    }

    /**
     * @param allLockedDice
     * @param allFreshDice
     * @return booleana
     *
     * this takes in the locked dice and the fresh dice as parameters
     * It checks sorts them both then does the following test on the locked dice THEN the fresh dice:
     * It sees if there is a zonk... a zonk is when there is no 1, no 5, and no 3 or more of a kind in the set
     */
    public static boolean testZonk(ArrayList<Integer> allLockedDice, ArrayList<Integer> allFreshDice){

        Collections.sort(allLockedDice);
        Collections.sort(allFreshDice);
        if (!allLockedDice.stream().anyMatch(n -> n == 1) && !allLockedDice.stream().anyMatch(n -> n == 5) && !threeOfAKind(allLockedDice)){
            if ((!allFreshDice.stream().anyMatch(n -> n == 1) && !allFreshDice.stream().anyMatch(n -> n == 5) && !threeOfAKind(allFreshDice))){
                return true;
            }
        }
        return false;
    }

    /**
     * this resets the score buffer to 0
     */
    private void setScoreBufferToZero(){
        scoreBuffer = 0;
    }

    /**
     * This switches the active player to the next player by adding one to the index of the player list,
     * if it is the last player in the list then it goes back to the first in the list
     */
    private void nextPlayer(){

        if (players.indexOf(activePlayer) == (players.size() - 1)) {
            activePlayer = players.get(0);
        }
        else{
            activePlayer = players.get(players.indexOf(activePlayer) + 1);
        }
    }

    /**
     * private method sets buffer to 0
     */
    private void resetBuffer(){
        scoreBuffer = 0;
    }

    /**
     * this takes the index of a fresh die in as a parameter and removes it from the fresh dice list
     * @param die
     */
    public void deleteActiveDie(int die){
        freshDice.remove(die);
    }

    /**
     * this takes in a die face value as a parameter and adds it to the locked dice list
     * @param dieValue
     */
    public void addToLockedDice(int dieValue){
        lockedDice.add(dieValue);
    }

    /**
     * this method resets the dice by resetting active dice to 5 and clearing the fresh and locked dice lists if they are not already null
     */
    private void resetDice(){
        activeDice = 5;
        if (freshDice != null){
            freshDice.clear();
        }
        if (lockedDice != null){
            lockedDice.clear();
        }
    }

    /**
     * this method rolls the dice, it takes in the amount of active dice and uses the die class to find a random number for as many times as there are active dice
     * it sets the fresh dice list to the values it gets
     * @param activeDice
     */
    public void rollDice(int activeDice){
        ArrayList<Integer> rollValues = new ArrayList<>();
        for (int i = 0; i < activeDice; i++) {
            die.rollDie();
            rollValues.add(die.getValue());
        }
        freshDice = rollValues;
    }

    /**
     * Adds the score buffer from the turn to the active player's actual score
     */
    public void scoreTurn(){
        activePlayer.addToScore(scoreBuffer);
    }

    /**
     * This uses a switch statement to prevent out of bounds errors and uses the tests to score the roll
     */
    public void scoreRoll(){
        Collections.sort(lockedDice);
        switch (lockedDice.size()){
            case 5:
                scoreBuffer += (scoreFiveOfAKind()*3);
                scoreBuffer += (scoreFourOfAKind()*2);
                scoreBuffer += (scoreThreeOfAKind());
                scoreBuffer += (scoreOnes());
                scoreBuffer += (scoreFives());
                break;
            case 4:
                scoreBuffer += (scoreFourOfAKind()*2);
                scoreBuffer += (scoreThreeOfAKind());
                scoreBuffer += (scoreOnes());
                scoreBuffer += (scoreFives());
                break;
            case 3:
                scoreBuffer += (scoreThreeOfAKind());
                scoreBuffer += (scoreOnes());
                scoreBuffer += (scoreFives());
                break;
            default:
                scoreBuffer += (scoreOnes());
                scoreBuffer += (scoreFives());
                break;

        }

    }

    /**
     * This method goes through the locked dice and finds how many 1s are in the list.
     * It returns the amount of 1s times 100 which is how the game is scored
     * @return amount of 1s * 100
     */
    private int scoreOnes(){
        int ones = 0;
        for (int i = 0; i < lockedDice.size(); i++) {
            if (lockedDice.get(i) == 1){

                ones += 100;
            }
        }
        return ones;
    }

    /**
     * This method goes through the locked dice and finds how many 5s are in the list.
     * It returns the amount of 5s times 100 which is how the game is scored
     * @return amount of 5s * 100
     */
    private int scoreFives(){
        int fives = 0;
        for (int i = 0; i < lockedDice.size(); i++) {
            if (lockedDice.get(i) == 5){
                fives += 50;
            }
        }
        return fives;
    }

    /**
     * This method tests if there is a three of a kind in an ArrayList.
     * If the size is greater than three(to prevent errors) it will go through and check if the next two values are the same as the current
     * If it finds a three of a kind it will return true, if not, then false
     * @param dice
     * @return whether there is a 3 of a kind
     */
    public static boolean threeOfAKind(ArrayList<Integer> dice){
        Collections.sort(dice);
        if (dice.size() >= 3){
            for (int i = 0; i < dice.size()-1; i++) {
                if (i+2 < dice.size()){
                    if ((dice.get(i) == dice.get(i+1)) && (dice.get(i) == dice.get(i+2))){
                        return true;
                    }
                }


            }
        }

        return false;
    }

    /**
     * This uses a similar algorithm to the three of a kind test but returns a score instead.
     *  It also removes the dice from the locked list if it finds a three of a kind, so they are not scored again
     * @return
     */
    private int scoreThreeOfAKind(){

        for (int i = 0; i < lockedDice.size() - 1; i++) {
            if ((i+2) < lockedDice.size()){
            if ((lockedDice.get(i) == lockedDice.get(i+1)) && (lockedDice.get(i) == lockedDice.get(i+2))) {
                if (lockedDice.get(i) == 1) {
                    return 1000;
                }
                int lock = lockedDice.get(i);
                deleteLocked(i, 3);


                return (lock) * 100;
            }
            }
        }
        return 0;
    }

    /**
     * This uses a similar algorithm to the score three of a kind test
     *  It also removes the dice from the locked list if it finds a four of a kind, so they are not scored again
     * @return
     */
    public int scoreFourOfAKind(){

        for (int i = 0; i < lockedDice.size() - 1; i++) {
            if ((i+3) < lockedDice.size()){
            if ((lockedDice.get(i) == lockedDice.get(i+1)) && (lockedDice.get(i) == lockedDice.get(i+2)) && (lockedDice.get(i) == lockedDice.get(i+3))) {
                int lock = lockedDice.get(i);
                deleteLocked(i, 4);

                return (lock) * 100;
            }
            }
        }
        return 0;
    }

    /**
     * This uses a similar algorithm to the score four of a kind test
     *  It also removes the dice from the locked list if it finds a five of a kind, so they are not scored again
     * @return
     */
    public int scoreFiveOfAKind(){

        for (int i = 0; i < lockedDice.size() - 1; i++) {
            if ((i+4) < lockedDice.size()){
            if ((lockedDice.get(i) == lockedDice.get(i+1)) && (lockedDice.get(i) == lockedDice.get(i+2)) && (lockedDice.get(i) == lockedDice.get(i+3)) && (lockedDice.get(i) == lockedDice.get(i+4))) {
                int lock = lockedDice.get(i);
                deleteLocked(i, 5);

                return (lock) * 100;
            }
            }
        }
        return 0;
    }

    /**
     * Accepts which index of die should be deleted and how many of them will be deleted
     * @param whichOne
     * @param howMany
     */
    private void deleteLocked(int whichOne, int howMany){
        for (int j = 0; j < howMany; j++) {
            lockedDice.remove(whichOne);
        }
    }


    /**
     * This uses the ui class to get information for a new player. It uses the player factory to add a player
     * @param ui
     */
    public void addPlayer(UI ui){
        PlayerType playerType = ui.getPlayerType();
        String playerName = ui.getName(players);
        players.add(PlayerFactory.getPerson(playerName, playerType));
    }

    /**
     * this uses a stream to see who is the highest scoring player
     * @return winning player object
     */
    public Player findHighestScorer(){
        return players.stream()
                .max(Comparator.comparing(Player::getScore))
                .orElseThrow(NoSuchElementException::new);
    }

    /**
     * this sorts the players by score highest to lowest at the end of the game
     */
    public void makeLeaderboard(){
        players = players.stream()
                .sorted(Comparator.comparingInt(Player::getScore).reversed())
                .collect(Collectors.toList());
    }

    /**
     * This checks to see if any player has reached the winning value of 3000 points
     * @return
     */
    public boolean checkForWinner(){
        for (Player player: players) {
            if (player.getScore() >= 3000){
                return true;
            }
        }
        return false;
    }

    /**
     * This method takes in how many dice the active dice should be decreased by and decreases activeDice by that number
     * @param decreaseBy
     */
    public void fewerDice(int decreaseBy){
        activeDice -= decreaseBy;
    }

}
