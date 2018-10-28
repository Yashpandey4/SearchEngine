
public class Position
{
    PageEntry p;
    int wordIndex;
    String word;
    
    public Position(PageEntry p, int wordIndex) 
    {
        this.p = p;
        this.wordIndex = wordIndex;
    }

    public Position(PageEntry p, int wordIndex, String word) 
    {
        this.p = p;
        this.wordIndex = wordIndex;
        this.word = word;
    }

    public PageEntry getPageEntry() 
    {
        return this.p;
    }

    public int getWordIndex() 
    {
        return this.wordIndex;
    }
}