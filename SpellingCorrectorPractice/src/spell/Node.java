package spell;

public class Node implements INode{

    private Node[] node;
    private int hashcode;
    private int numberofrefs;
    private int numberofnodes = 0;
    private int numberofwords = 0;
    private char character;

    public Node()
    {
        node = new Node[26];
        numberofrefs = 0;
    }

    @Override
    public int getValue() {
        return numberofrefs;
    }

    public int getValueOfWord(String word)
    {
        Node[] tempnode = node;
        while (word.length() > 1)
        {
            if (tempnode[word.charAt(0) - 'a'] == null){
                return 0;
            }
            tempnode = tempnode[word.charAt(0) - 'a'].node;
            word = word.substring(1);
        }

        if (tempnode[word.charAt(0) - 'a'] == null){
            return 0;
        }
        if (tempnode[word.charAt(0) - 'a'].getValue() >= 1){
            return tempnode[word.charAt(0) - 'a'].getValue();
        }
        return 0;
    }

    public int getWords(){
        return numberofwords;
    }

    public int add(String word)
    {
        Node[] tempnode = node;
        numberofnodes = 0;

        //BUILD THE NODES OF THE WORD UNTIL THE LAST LETTER
        while (word.length() > 1)
        {
            if (tempnode[word.charAt(0) - 'a'] == null)
            {
                tempnode[word.charAt(0) - 'a'] = new Node();
                tempnode[word.charAt(0) - 'a'].character = word.charAt(0);
                tempnode = tempnode[word.charAt(0) - 'a'].node;
                word = word.substring(1);
                numberofnodes++;
            }
            else
            {
                tempnode = tempnode[word.charAt(0) - 'a'].node;
                word = word.substring(1);
            }
        }
        if (tempnode[word.charAt(0) - 'a'] == null)
        {
            tempnode[word.charAt(0) - 'a'] = new Node();
            tempnode[word.charAt(0) - 'a'].character = word.charAt(0);
            tempnode[word.charAt(0) - 'a'].numberofrefs++;
            numberofnodes++;
            numberofwords++;
        }
        else
        {
            if (tempnode[word.charAt(0) - 'a'].numberofrefs < 1)
            {
                numberofwords++;
            }
            tempnode[word.charAt(0) - 'a'].numberofrefs++;
        }
        return numberofnodes;
    }

    public Node find(String word)
    {
        Node[] tempnode = node;

        while(word.length() > 1)
        {
            if (tempnode[word.charAt(0) - 'a'] == null)
            {
                return null;
            }
            tempnode = tempnode[word.charAt(0) - 'a'].node;
            word = word.substring(1);
        }

        if (tempnode[word.charAt(0) - 'a'] == null)
        {
            return null;
        }
        if (tempnode[word.charAt(0) - 'a'].getValue() >= 1){
            return this;
        }
        return null;
    }


    public void toString(Node tempnode, StringBuilder stringBuilder, StringBuilder finaloutput)
    {
        if (tempnode.getValue() > 0)
        {
            finaloutput.append(stringBuilder.toString() + "\n");
        }

        for (int i = 0; i < 26; i++)
        {
            if (tempnode.node[i] != null)
            {
                stringBuilder.append(tempnode.node[i].character);
                tempnode.node[i].toString(tempnode.node[i], stringBuilder, finaloutput);
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            }
        }
    }

    public int HashCode(Node tempnode, int finalhashcode)
    {
        for (int i = 0; i < 26; i++)
        {
            if (tempnode.node[i] != null)
            {
                finalhashcode += tempnode.node[i].hashcode = tempnode.node[i].numberofrefs + tempnode.node[i].character + i;
                finalhashcode += tempnode.node[i].HashCode(tempnode.node[i], finalhashcode);
            }
        }
        return finalhashcode;
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass())
        {
            return false;
        }

        Node tempnode = (Node) obj;
        if(tempnode.numberofrefs != this.numberofrefs)
        {
            return false;
        }

        for (int i = 0; i < 26; i++)
        {
            if (this.node[i] != null)
            {
                if (tempnode.node[i] == null)
                {
                    return false;
                }
                else
                {
                    if(!this.node[i].equals(tempnode.node[i]))
                    {
                        return false;
                    }

                }
            }
            else
            {
                if (tempnode.node[i] != null)
                {
                    return false;
                }
            }
        }

        return true;
    }
}
