
import java.util.ArrayList;

class MyLL extends MyLinkedList<WordEntry>
{
    MyLL()
    {
        super();
    }
}
public class MyHashTable 
{
    // public MyLinkedList<WordEntry>[] l = new MyLinkedList[1009];; //primes tend to give lesser collisions.
    MyLL l[]=new MyLL[1009];
    public MyHashTable() // initialise lists for upto 1009 unique words
    {
        for(int i=0; i<1009; i++)
        {
            l[i]=new MyLL();
        }
    }
    
    public int getHashIndex(String str) //private
    {
        // long hc=0;
        // int n=str.length();
        // for(int i=0; i<n; i++)
        // {
        //     hc+=(long)s.charAt(i)*(long)Math.pow(31,n-i-1); //31 is magic number
        // }
        // return (int)(hc%1009);

        int h=0;
        String s=str.toLowerCase();
        for(int i=s.length()-1; i>=0; i--)
        {
            h=(s.charAt(i)+(128*h))%1009;
        }
        return h;
    }

    public void addPositionsForWord(WordEntry w)
    {
        int hc=getHashIndex(w.str);
        //System.out.println(w.str+" "+hc);
        if(!l[hc].alreadyExists(w.str))
            l[hc].addLast(w);
        else
            l[hc].getWordByData(w.str).element.pos.union(w.pos);
    }

}