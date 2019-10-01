package hangman;

import java.io.*;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame{

   SortedSet<Character> guessedwords = new TreeSet<>();
    TreeSet<String> setofwords = new TreeSet<>();
    String outputPatern;
    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
    Scanner dictionaryreader = new Scanner(dictionary);

        if (dictionary == null)
        {
            throw new EmptyDictionaryException("No lenght no words boyz");
        }
        if (dictionary.length() < 1)
        {
            throw new EmptyDictionaryException("No lenght no words boyz");
        }

        guessedwords.clear();
        setofwords.clear();
        outputPatern = new String(new char[wordLength]).replace('\0', '-');
        String tempword;
        while (dictionaryreader.hasNext())
        {
            tempword = dictionaryreader.next();
            if(tempword.length() == wordLength)
            {
                setofwords.add(tempword);
            }
        }
        if (setofwords.size() < 1)
        {
            throw new EmptyDictionaryException("No lenght no words boyz");
        }

    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        TreeMap<String, TreeSet<String>> partitionsofwords = new TreeMap<>();
        String pattern;

        guess = Character.toLowerCase(guess);

        if (getGuessedLetters().contains(guess))
        {
            throw new GuessAlreadyMadeException("You a dumdum, you already guessed this!");
        }
         guessedwords.add(guess);

        for(String temp: setofwords )
        {
            TreeSet<String> settoinsert = new TreeSet<>();
            settoinsert.add(temp);
            pattern = PatternMaker(temp, guess);
            if (partitionsofwords.containsKey(pattern))
            {
                partitionsofwords.get(pattern).addAll(settoinsert);
            }
            else
            {
                partitionsofwords.put(pattern, settoinsert);
            }
        }

        Map.Entry<String, TreeSet<String>> thechosenset = null;
        for (Map.Entry<String, TreeSet<String>> tempset : partitionsofwords.entrySet())
        {
            if (thechosenset == null)
            {
                thechosenset = tempset;
            }
            if (tempset.getValue().size() > thechosenset.getValue().size())
            {
                thechosenset = tempset;
            }
            else if (tempset.getValue().size() == thechosenset.getValue().size())
            {
                if(!tempset.getKey().contains(Character.toString(guess)))
                {
                    thechosenset = tempset;
                }
                else
                {
                    long count1;
                    long count2;
                    char finalGuess = guess;
                    count1 = tempset.getKey().chars().filter(num -> num == finalGuess).count();
                    count2 = thechosenset.getKey().chars().filter(num -> num == finalGuess).count();

                    if (count2 > count1)
                    {
                        thechosenset = tempset;
                    }
                    else if (count1 == count2)
                    {
                        if(tempset.getKey().lastIndexOf(guess) > thechosenset.getKey().lastIndexOf(guess))
                        {
                            thechosenset = tempset;
                        }
                    }
                }

            }
        }


        setofwords = thechosenset.getValue();
        outputPatern = thechosenset.getKey();
        return setofwords;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {

        return guessedwords;
    }


    public String PatternMaker(String temp, char guess)
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (char ch : temp.toCharArray()){
            if (ch != guess)
            {
                stringBuilder.append('-');
            }
            else
            {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }
}
