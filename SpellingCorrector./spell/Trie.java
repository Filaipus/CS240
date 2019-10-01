package spell;

import java.util.Objects;

public class Trie implements ITrie{

    Node baseNode;
    private int wordcount;
    private int nodecount = 1;

    public Trie()
    {
        baseNode = new Node();
    }

    @Override
    public void add(String word) {
            word = word.toLowerCase();
            nodecount += baseNode.add(word);
            wordcount = baseNode.getWords();
    }

    @Override
    public INode find(String word) {
        word = word.toLowerCase();
        return baseNode.find(word);
    }


    @Override
    public int getWordCount() {
       //you have to transverse the tree and give the word number
       return wordcount; //numberofwords;
    }

    @Override
    public int getNodeCount() {
        //you have to transverse the tree and give the node number
        return nodecount; // numberofnodes;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder finaloutput = new StringBuilder();
        baseNode.toString(baseNode, stringBuilder, finaloutput);
        return finaloutput.toString();
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Trie tempnode = (Trie) o;

        return baseNode.equals(tempnode.baseNode);
    }

    @Override
    public int hashCode() {
       int finalhashcode = 0;
       finalhashcode = baseNode.HashCode(baseNode, finalhashcode);
        return finalhashcode;
    }
}
