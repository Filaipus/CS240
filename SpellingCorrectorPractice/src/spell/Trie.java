package spell;

public class Trie implements ITrie {

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
        return wordcount;
    }

    @Override
    public int getNodeCount() {
        return nodecount;
    }

    @Override
    public int hashCode() {
        int finalhashcode = 0;
        finalhashcode = baseNode.HashCode(baseNode, finalhashcode);
        return finalhashcode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass())
        {
            return false;
        }

        Trie tempnode = (Trie) obj;

        return baseNode.equals(tempnode.baseNode);
    }

    @Override
    public String toString() {

       StringBuilder stringBuilder = new StringBuilder();
       StringBuilder finaloutput = new StringBuilder();
       baseNode.toString(baseNode, stringBuilder, finaloutput);
       return finaloutput.toString();
    }

}
