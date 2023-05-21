package Player;


public class PlayerFactory {

    public  PlayerFactory(){}

    public static Player getPerson(String name, PlayerType playerType){

        if (playerType == (PlayerType.PERSON)){
            return new Person(name, 0);
        } else if (playerType == (PlayerType.CPU)) {
            return new SafeCPU(name, 0);
        } else if (playerType == PlayerType.RISKY_CPU) {
            return new RiskyCPU(name, 0);
        }
        return null;
    }

}
