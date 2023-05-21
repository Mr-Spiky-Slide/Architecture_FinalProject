package Main;

public interface IOStrategy {
    char inputCharacter();
    int inputInt();
    String inputString();
    void outputLine(String line);
}
