package hangman;

import java.io.*;
import java.util.*;

public class EvilHangman {

    public static void main(String[] args) {

        String dictionarypath = args[0];
        int wordLenght = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);
        Scanner scanner = new Scanner(System.in);
        char guess;

        if (dictionarypath == null)
        {
            new EmptyDictionaryException("hm boy what");
        }
        if (wordLenght < 2)
        {
            new EmptyDictionaryException("word lenght wrong bro");
        }
        if (guesses < 1)
        {
            new EmptyDictionaryException("guesse wrong bro");
        }


        File dictionary = new File(dictionarypath);




        EvilHangmanGame evilHangmanGame = new EvilHangmanGame();

        try
        {
            evilHangmanGame.startGame(dictionary, wordLenght);
        }
        catch (IOException | EmptyDictionaryException error)
        {
            System.out.println("not sure if I follow this exceptions thing but the program is screaming at me");
        }

        while (guesses > 0)
        {
            System.out.println("Number of guesses:" + guesses);
            System.out.println("Letters guessed:" + evilHangmanGame.guessedwords.toString());
            //System.out.println("Word:" + evilHangmanGame.outputPatern);
            for (int i = 0; i < evilHangmanGame.setofwords.first().length(); i++)
            {
             if (evilHangmanGame.guessedwords.contains(evilHangmanGame.setofwords.first().charAt(i)))
             {
                 System.out.print(evilHangmanGame.setofwords.first().charAt(i));
             }
             else
             {
                 System.out.print("-");
             }
            }
            System.out.println("\nEnter Guess:");
            guess = scanner.next().charAt(0);

            while (!Character.isAlphabetic(guess))
            {
                System.out.println("iNvAlId InPuT\nEnter Guess:");
                guess = scanner.next().charAt(0);
            }

            try {
                evilHangmanGame.makeGuess(guess);
            } catch (GuessAlreadyMadeException e) {
                e.printStackTrace();
                guesses++;
            }
                if (!evilHangmanGame.setofwords.first().contains(Character.toString(guess)))
                {
                    System.out.println("Sorry, there are no " + guess + "'s");
                }
                else
                {
                    char finalGuess = guess;
                    System.out.println("There is " + evilHangmanGame.setofwords.first().chars().filter(num -> num == finalGuess).count() + " " + guess + "'s\n");
                }

        guesses--;

        }

        if (evilHangmanGame.setofwords.size() == 1)
        {
            System.out.println("You win! The word was:" + evilHangmanGame.setofwords.first().toString());
        }
        else{
            System.out.println("You lose!");

            System.out.println("The word was:" + evilHangmanGame.setofwords.first().toString());}


    }

}
