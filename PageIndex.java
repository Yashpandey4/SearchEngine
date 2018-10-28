
public class PageIndex
{
    MyLinkedList<WordEntry> wordEnt = new MyLinkedList<WordEntry>();

    public void addPositionForWord(String str, Position p) 
    {
        boolean flag = false;
        for(int i=0; i<wordEnt.size; i++) 
        {
            WordEntry we=wordEnt.get(i);
            if(we.str.equals(str))  //word already present
            {
                we.pos.addLast(p);
                Position p1=p;
                p.word=str;
                we.tree.insert(p1);
                flag=true;
                //System.out.println("repeating "+str+" "+p.p.pageName+" "+p.wordIndex);
                break;
            }
        }
        if(!flag)
        {
            WordEntry we=new WordEntry(str); //word not already present
            we.pos.addLast(p);;
            Position p1=p;
            p.word=str;
            we.tree.insert(p1);
            wordEnt.addLast(we);
            //System.out.println("Unique "+str+" "+p.p.pageName+" "+p.wordIndex);
        }
    }

    public MyLinkedList<WordEntry> getWordEntries()
    {
        return wordEnt;
    }

}