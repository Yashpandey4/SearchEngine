
import java.util.ArrayList;

public class InvertedPageIndex
{
    MyLinkedList<PageEntry> pages = new MyLinkedList<PageEntry>();
    MyHashTable ht = new MyHashTable();

    public void addPage(PageEntry p)
    {
        //System.out.println("3");
        int n=p.index.wordEnt.size;
        pages.addLast(p);
        //test(p);
        //System.out.println("4");
        for(int i=0; i<n; i++)
        {
            ht.addPositionsForWord(p.index.wordEnt.get(i));
        }
    }

    public void test (PageEntry p)
    {
        for(int i=0;i<p.index.wordEnt.size;i++)
        {
            for(int j=0;j<p.index.wordEnt.get(i).pos.size;j++)
            {
                System.out.println(p.pageName+" "+p.index.wordEnt.get(i).str+" "+p.index.wordEnt.get(i).pos.get(j).wordIndex);
            }
        }
    }

    public MySet<PageEntry> getPagesWhichContainWord(String str)
    {
        str=str.toLowerCase();
        // System.out.println("*");
        //WordEntry we=(ht.l[ht.getHashIndex(str)]).getWordByData(str).element;
        MyLinkedList<WordEntry>.Node<WordEntry> el=(ht.l[ht.getHashIndex(str)]).getWordByData(str);
        if(el.element != null)
        {MyLinkedList<Position> poslist=el.element.pos;
        
        MySet<PageEntry> pent=new MySet<PageEntry>();
        // System.out.print("asdhvahsjd");
        for(int i=0; i<poslist.size; i++)
        {
            //System.out.println(str+" "+poslist.get(i).p.pageName+" "+poslist.get(i).wordIndex);
            pent.list.addLast(poslist.get(i).p);
        }
        return pent;}
        return new MySet<PageEntry>();
    }

    public MyLinkedList<PageEntry> getPagesWhichContainWordinOrder(String str)
	{
		WordEntry w = (ht.l[ht.getHashIndex(str)]).getWordByData(str).element;
		
		// System.out.println(w.getWord());
		if(w.str.length()>0)
		    return w.getPageEntryinOrder();
		return new MyLinkedList<PageEntry>();
    }
    
    public ArrayList<SearchResult> getPagesWhichContainAllWords(String[] str)
    {
        MySet<PageEntry> ms= new MySet<PageEntry>();
        MySet<PageEntry> m= new MySet<PageEntry>();
        ms=this.getPagesWhichContainWord(str[0]);
        for(int i=0;i<str.length;i++)
        {
            m=this.getPagesWhichContainWord(str[i]);
            ms=ms.intersection(m);
        }
        MySet<SearchResult> res = new MySet<SearchResult>();
        for(int i=0;i<ms.list.size;i++)
        {
            PageEntry p=ms.list.get(i);
            double d=p.getRelevanceOfPage(str,false,pages.size,this,0); 
            // if(d>0.0)
            // {
                SearchResult s = new SearchResult(p, d);
                res.list.addLast(s);
            // }
        }
        MySort<SearchResult> m1 = new MySort<SearchResult>();
        return m1.sortThisList(res);    
    }
    
    public ArrayList<SearchResult> getPagesWhichContainAnyOfTheseWords(String[] str)
    {
        MySet<PageEntry> ms= new MySet<PageEntry>();
        ms=this.getPagesWhichContainWord(str[0]);
        for(int i=0;i<str.length;i++)
        {
            MySet<PageEntry> m=this.getPagesWhichContainWord(str[i]);
            ms=ms.union(m);
        }
        MySet<SearchResult> res = new MySet<SearchResult>();
        for(int i=0;i<ms.list.size;i++)
        {
            PageEntry p=ms.list.get(i);
            double d=p.getRelevanceOfPage(str,false,pages.size,this,0); 
            
            SearchResult s = new SearchResult(p, d);
            res.list.addLast(s);
        }
        MySort<SearchResult> m = new MySort<SearchResult>();
        return m.sortThisList(res);    
    }

    public ArrayList<SearchResult> getPagesWhichContainPhrase(String[] str) 
    {
        MySet<PageEntry> ms= new MySet<PageEntry>();
        int counter=0;
        for(int i=0;i<pages.size;i++)
        {
            WordEntry w = pages.get(i).getWordEntry(str[0]);
            if(w!=null)
            {
                AVLTree t=w.tree;
                if(pages.get(i).numPhrases(str,t,0)>0);
                {   
                    ms.list.addLast(pages.get(i));
                    counter++;
                }
            }
        }
        MySet<SearchResult> res = new MySet<SearchResult>();
        for(int i=0;i<ms.list.size;i++)
        {
            PageEntry p=ms.list.get(i);
            double d=p.getRelevanceOfPage(str,true,pages.size,this,counter);
            if(d>0.0)
            {
                SearchResult s = new SearchResult(p, d);
                res.list.addLast(s);
            }
        }
        MySort<SearchResult> m = new MySort<SearchResult>();
        return m.sortThisList(res);    
    }
}