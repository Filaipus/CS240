package spell;

import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {

    Trie trie;

    public SpellCorrector()
    {
        trie = new Trie();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
       File dictionaryfile = new File(dictionaryFileName);
        Scanner scanner = new Scanner(dictionaryfile);
        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        while(scanner.hasNext())
        {
            trie.add(scanner.next());
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {

        if (inputWord == "") {
            return null;
        }
        inputWord = inputWord.toLowerCase();
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> stringbuilderEditWords = new ArrayList<String>();
        ArrayList<String> stringbuilderCorrectWords = new ArrayList<String>();
        ArrayList<String> tempstring = new ArrayList<String>();
        stringBuilder.append(inputWord);

        if (trie.find(inputWord) != null) {
            return inputWord;
        }

        //Deletion
        this.Deletion(inputWord, stringbuilderEditWords, stringbuilderCorrectWords);
        //transposition
        this.Transposition(inputWord, stringbuilderEditWords, stringbuilderCorrectWords);
        //alteration
        this.Alteration(inputWord, stringbuilderEditWords, stringbuilderCorrectWords);
        //insertion
        this.Insertion(inputWord, stringbuilderEditWords, stringbuilderCorrectWords);

        if (stringbuilderCorrectWords.size() > 0) {
            int maxValue = 0;
            int value = 0;
            String wordtoreturn = "";
            for (String temp : stringbuilderCorrectWords) {
                value = trie.baseNode.getValueOfWord(temp);
                if (value > maxValue) {
                    maxValue = value;
                    wordtoreturn = temp;
                }
            }

            return wordtoreturn;

        }

        for (String temp : stringbuilderEditWords)
        {
            //Deletion
            this.Deletion(temp, tempstring, stringbuilderCorrectWords);
            //transposition
            this.Transposition(temp, tempstring, stringbuilderCorrectWords);
            //alteration
            this.Alteration(temp, tempstring, stringbuilderCorrectWords);
            //insertion
            this.Insertion(temp, tempstring, stringbuilderCorrectWords);
        }

        if (stringbuilderCorrectWords.size() > 0) {
            int maxValue = 0;
            int value = 0;
            String wordtoreturn = "";
            for (String temp : stringbuilderCorrectWords) {
                value = trie.baseNode.getValueOfWord(temp);
                if (value > maxValue) {
                    maxValue = value;
                    wordtoreturn = temp;
                }
            }

            return wordtoreturn;

        }
        return null;
    }



public void Deletion(String inputword, ArrayList<String> stringbuilderEditWords, ArrayList<String> stringbuilderCorrectWords) {
    if (inputword.length() == 1) {

    }
    else{
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(inputword);
    for (int i = 0; i < inputword.length(); i++) {
        char chartouse = stringBuilder.charAt(i);
        stringBuilder.deleteCharAt(i);
        stringbuilderEditWords.add(stringBuilder.toString());
        if (trie.find(stringBuilder.toString()) != null) {
            stringbuilderCorrectWords.add(stringBuilder.toString());
        }
        stringBuilder.insert(i, chartouse);
    }
    }
}


    public void Transposition(String inputword, ArrayList<String> stringbuilderEditWords, ArrayList<String> stringbuilderCorrectWords) {
        if (inputword.length() == 1) {

        }
        else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(inputword);
            for (int i = 0; i < inputword.length()-1; i++) {
                char chartouse = stringBuilder.charAt(i);
                stringBuilder.deleteCharAt(i);
                stringBuilder.insert(i+1, chartouse);
                stringbuilderEditWords.add(stringBuilder.toString());
                if (trie.find(stringBuilder.toString()) != null) {
                    stringbuilderCorrectWords.add(stringBuilder.toString());
                }
                stringBuilder.deleteCharAt(i+1);
                stringBuilder.insert(i, chartouse);
            }
        }
    }

    public void Alteration(String inputword, ArrayList<String> stringbuilderEditWords, ArrayList<String> stringbuilderCorrectWords) {
        if (inputword.length() == 1) {

        }
        else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(inputword);
            for (int i = 0; i < inputword.length(); i++) {
                for (int c = 0; c < 26; c++)
                {
                    char chartouse = 'a';
                    chartouse += c;
                    stringBuilder.setCharAt(i, chartouse);
                    stringbuilderEditWords.add(stringBuilder.toString());

                    if (trie.find(stringBuilder.toString()) != null) {
                        stringbuilderCorrectWords.add(stringBuilder.toString());
                    }
                    stringBuilder.setCharAt(i, inputword.charAt(i));
                }

            }
        }
    }

    public void Insertion(String inputword, ArrayList<String> stringbuilderEditWords, ArrayList<String> stringbuilderCorrectWords) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(inputword);

            for (int i = 0; i <= inputword.length(); i++) {
                for (int c = 0; c < 26; c++)
                {
                    char chartouse = 'a';
                    chartouse += c;
                    stringBuilder.insert(i, chartouse);
                    stringbuilderEditWords.add(stringBuilder.toString());

                    if (trie.find(stringBuilder.toString()) != null) {
                        stringbuilderCorrectWords.add(stringBuilder.toString());
                    }
                    stringBuilder.deleteCharAt(i);
                }

            }
    }

}