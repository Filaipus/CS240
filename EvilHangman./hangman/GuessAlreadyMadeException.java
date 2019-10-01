package hangman;

public class GuessAlreadyMadeException extends Exception {
    public GuessAlreadyMadeException(String error)
    {
        super(error);
    }
}
