package hangman;

public class EmptyDictionaryException extends Exception {
	public EmptyDictionaryException(String error)
    {
        super(error);
    }
}
