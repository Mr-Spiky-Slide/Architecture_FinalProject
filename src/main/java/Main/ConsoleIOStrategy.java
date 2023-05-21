package Main;

import java.util.Scanner;

public class ConsoleIOStrategy implements IOStrategy {
    private Scanner keyboard = new Scanner(System.in);

    @Override
    public char inputCharacter() {
        return keyboard.nextLine().toUpperCase().charAt(0);
    }

    @Override
    public int inputInt() { return Integer.parseInt(keyboard.nextLine()); }

    @Override
    public String inputString() {
        return keyboard.nextLine();
    }

    @Override
    public void outputLine(String line) {
        System.out.println(line);
    }


}
