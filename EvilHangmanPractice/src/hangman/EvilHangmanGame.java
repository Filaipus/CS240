package hangman;

import com.sun.source.tree.Tree;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {

    SortedSet<Character> guessedwords = new TreeSet<>();
    TreeSet<String> setofwords = new TreeSet<>();
    String outputPatern;


    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        Scanner dictionaryreader = new Scanner(dictionary);

        if (dictionary == null || dictionary.length() < 1)
        {
            throw new EmptyDictionaryException("Empty Dictionary");
        }

        guessedwords.clear();
        setofwords.clear();
        outputPatern = new String(new char[wordLength]).replace('\0','-');
        String tempword;
        while (dictionaryreader.hasNext())
        {
            tempword = dictionaryreader.next();
            if (tempword.length() == wordLength)
            {
                setofwords.add(tempword);
            }
        }
        if (setofwords.size() < 1)
        {
            throw new EmptyDictionaryException("Empty Dictionary");
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {

        TreeMap<String, TreeSet<String>> partitionofwords = new TreeMap<>();
        String pattern;

        guess = Character.toLowerCase(guess);
        if (getGuessedLetters().contains(guess))
        {
            throw new GuessAlreadyMadeException("You've already guessed this letter");
        }

        guessedwords.add(guess);

       for (String temp : setofwords)
       {
           TreeSet<String> settoinsert = new TreeSet<>();
           settoinsert.add(temp);
           pattern = PatternMaker(temp, guess);
           if (partitionofwords.containsKey(pattern))
           {
               partitionofwords.get(pattern).addAll(settoinsert);
           }
           else
           {
               partitionofwords.put(pattern, settoinsert);
           }

       }

       Map.Entry<String, TreeSet<String>> theset = null;
       for (Map.Entry<String, TreeSet<String>> tempset : partitionofwords.entrySet())
       {
           if (theset == null)
           {
               theset = tempset;
           }
           if (tempset.getValue().size() > theset.getValue().size())
           {
               theset = tempset;
           }
           else if (tempset.getValue().size() == theset.getValue().size())
           {
               if (!tempset.getKey().contains(Character.toString(guess)))
               {
                   theset = tempset;
               }
               else
               {
                   long count1, count2;
                   char finalGuess = guess;
                   count1 = tempset.getKey().chars().filter(num -> num == finalGuess).count();
                   count2 = theset.getKey().chars().filter(num -> num == finalGuess).count();

                   if (count2 > count1)
                   {
                       theset = tempset;
                   }
                   else if (count2 == count1)
                   {
                       if(tempset.getKey().lastIndexOf(guess) > theset.getKey().lastIndexOf(guess))
                       {
                           theset = tempset;
                       }
                   }
               }
           }
       }

       setofwords = theset.getValue();
       outputPatern = theset.getKey();
        return setofwords;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedwords;
    }

    public String PatternMaker(String temp, char guess) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char ch : temp.toCharArray())
        {
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
