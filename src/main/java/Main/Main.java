package Main;
import Game.Die;
import Game.Zonk;

public class Main {
    public static void main(String[] args) {
        IOStrategy ioStrategy;
        switch (args[0].indexOf(0)){
            case 'C': ioStrategy = new ConsoleIOStrategy(); break;
            default: ioStrategy = new ConsoleIOStrategy();
        }

        UI ui = UI.getInstance(ioStrategy);
        Die die = Die.getInstance();
        Zonk zonk = Zonk.getInstance(ui, die);
        zonk.playGame();

    }
}
