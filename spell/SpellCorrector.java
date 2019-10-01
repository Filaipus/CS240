package spell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector{

    Trie trie;
    INode wordnode;
     public SpellCorrector() {
        trie = new Trie();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {

        File dictionaryfile = new File(dictionaryFileName);
        Scanner scanner = new Scanner(dictionaryfile);
        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        while(scanner.hasNext()) {
            trie.add(scanner.next());
        }


    }

    @Override
    public String suggestSimilarWord(String inputWord) {

        if (inputWord == "")
        {
            return null;
        }
         inputWord = inputWord.toLowerCase();
         StringBuilder stringBuilder = new StringBuilder();
         ArrayList<String> stringBuilderEditWords = new ArrayList<String>();
        ArrayList<String> stringBuilderCorrectWords = new ArrayList<String>();
         ArrayList<String> tempuseless = new ArrayList<String>();
         stringBuilder.append(inputWord);


         if (trie.find(inputWord) != null)
         {
             return inputWord;
         }

        //Deletion
        if (this.Deletion(inputWord, stringBuilderCorrectWords, stringBuilderEditWords) != null)
        {

            //return
                    this.Deletion(inputWord, stringBuilderCorrectWords, stringBuilderEditWords);
        }

        //transposition

        if (this.Transposition(inputWord, stringBuilderCorrectWords, stringBuilderEditWords) != null)
        {

           // return
                    this.Transposition(inputWord, stringBuilderCorrectWords, stringBuilderEditWords);
        }


        //alteration

        if (this.Alteration(inputWord, stringBuilderCorrectWords, stringBuilderEditWords) != null)
        {

           // return
                    this.Alteration(inputWord, stringBuilderCorrectWords, stringBuilderEditWords);
        }


        //Insertion

        if (this.Insertion(inputWord, stringBuilderCorrectWords, stringBuilderEditWords) != null)
        {
           // return
                    this.Insertion(inputWord, stringBuilderCorrectWords, stringBuilderEditWords);
        }

        if (stringBuilderCorrectWords.size() > 0)
        {
            int maxValue=0;
            int value=0;
            String wordtoreturn = "";
            for (String temp: stringBuilderCorrectWords)
            {
                value = trie.baseNode.getValueOfWord(temp);
                if (value > maxValue)
                {
                    wordtoreturn = temp;
                    maxValue = value;
                }
                else if (value == maxValue)
                {
                    if (wordtoreturn.compareTo(temp) > 0){
                        wordtoreturn = temp;
                    }
                }
            }
            return wordtoreturn;
        }

        //round 2
        for (String temp: stringBuilderEditWords)
        {
            //Deletion
            if (this.Deletion(temp, stringBuilderCorrectWords, tempuseless) != null)
            {
                //stringBuilder.append(this.Alteration(inputWord));
                //return
                        this.Deletion(temp, stringBuilderCorrectWords, tempuseless);
            }

            //transposition

            if (this.Transposition(temp, stringBuilderCorrectWords, tempuseless) != null)
            {
                //stringBuilder.append(this.Alteration(inputWord));
                //return
                        this.Transposition(temp, stringBuilderCorrectWords, tempuseless);
            }

            //alteration

            if (this.Alteration(temp, stringBuilderCorrectWords, tempuseless) != null)
            {
                //stringBuilder.append(this.Alteration(inputWord));
               // return
                        this.Alteration(temp, stringBuilderCorrectWords, tempuseless);
            }



            //Insertion

            if (this.Insertion(temp, stringBuilderCorrectWords, tempuseless) != null)
            {
                //return
                        this.Insertion(temp, stringBuilderCorrectWords, tempuseless);
            }


        }
        if (stringBuilderCorrectWords.size() > 0)
        {
            int maxValue=0;
            int value=0;
            String wordtoreturn = "";
            for (String temp: stringBuilderCorrectWords)
            {
                value = trie.baseNode.getValueOfWord(temp);

                if (value >= maxValue)
                {
                    wordtoreturn = temp;
                    maxValue = value;
                }
            }
            return wordtoreturn;
        }



        return null;
    }

    public String Alteration(String inputWord,ArrayList<String> stringBuilderEditCorrectWords, ArrayList<String> stringBuilderEditWords)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(inputWord);
        for (int i = 0; i < inputWord.length(); i++)
        {
            for (int c = 0; c < 26; c++)
            {
                char chartouse = 'a';
                chartouse += c;
                stringBuilder.setCharAt(i, chartouse);
                stringBuilderEditWords.add(stringBuilder.toString());
                if(trie.find(stringBuilder.toString()) != null)
                {
                    stringBuilderEditCorrectWords.add(stringBuilder.toString());
                    //return stringBuilder.toString();
                }

                stringBuilder.setCharAt(i, inputWord.charAt(i));
            }
        }
        return null;
    }

    public String Insertion(String inputWord,ArrayList<String> stringBuilderEditCorrectWords, ArrayList<String> stringBuilderEditWords)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(inputWord);
        for (int i = 0; i <= inputWord.length(); i++)
        {
            for (int c = 0; c < 26; c++)
            {
                char chartouse = 'a';
                chartouse += c;
                stringBuilder.insert(i, chartouse);
                stringBuilderEditWords.add(stringBuilder.toString());
                if(trie.find(stringBuilder.toString()) != null)
                {
                    stringBuilderEditCorrectWords.add(stringBuilder.toString());
                    //return stringBuilder.toString();
                }
                stringBuilder.deleteCharAt(i);
            }
        }
        return null;
    }

    public String Transposition(String inputWord,ArrayList<String> stringBuilderEditCorrectWords, ArrayList<String> stringBuilderEditWords)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(inputWord);

        for (int i = 0; i < inputWord.length()-1; i++)
        {

            char chartouse = stringBuilder.charAt(i);
            stringBuilder.deleteCharAt(i);
            stringBuilder.insert(i+1, chartouse);
            stringBuilderEditWords.add(stringBuilder.toString());
            if(trie.find(stringBuilder.toString()) != null)
            {
                stringBuilderEditCorrectWords.add(stringBuilder.toString());
                //return stringBuilder.toString();
            }
            stringBuilder.deleteCharAt(i+1);
            stringBuilder.insert(i, chartouse);

        }
        return null;
    }


    public String Deletion(String inputWord,ArrayList<String> stringBuilderEditCorrectWords, ArrayList<String> stringBuilderEditWords)
    {
        if (inputWord.length() == 1)
        {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(inputWord);
        for (int i = 0; i < inputWord.length(); i++)
        {
            char chartouse = stringBuilder.charAt(i);
            stringBuilder.deleteCharAt(i);
            stringBuilderEditWords.add(stringBuilder.toString());
            if(trie.find(stringBuilder.toString()) != null)
            {
                stringBuilderEditCorrectWords.add(stringBuilder.toString());
                //return stringBuilder.toString();
            }
            stringBuilder.insert(i, chartouse);

        }
        return null;
    }
}
