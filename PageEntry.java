
import java.io.IOException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class PageEntry
{
    String pageName;
    PageIndex index = new PageIndex();
    int totalwords=0;
    AVLTree tree=new AVLTree();

    public PageEntry(String pageName)throws IOException, FileNotFoundException
    {
        this.pageName=pageName;
        try
        {
            //System.out.println("5");
            String newpath = new File("webpages",pageName).toString();
            FileInputStream inFile=new FileInputStream(newpath);
           // System.out.println("6");
            Scanner sc=new Scanner(inFile);
            //String censor[]={"a", "an", "the", "they", "these", "this", "for", "is", "are", "was", "of", "or","and", "does", "will", "whose"};
            int counter=1; //keeps track of word position
            int shadyCounter=1; //keeps track of word position without stop words
            boolean flag=false;
            while(sc.hasNext())
            {
                flag=false;
                String s=sc.next();
                String arr[]=this.cleanUp(s);
                //System.out.println(arr[0]);
                int i=0;
                while(i<arr.length)
                {
                    if(arr[i].length()==0)
                    {
                        i++; 
                        continue;
                    }
                    else if(!(isAStopWord(arr[i])))
                    {
                        Position p1=new Position(this, counter);
                        index.addPositionForWord(arr[i], p1);
                        //System.out.println(counter+" "+arr[i]+" "+this.pageName);
                        Position p2=new Position(this,shadyCounter,arr[i]);   // the counter you use here, use positions without stop words
                        tree.insert(p2);
                        counter++;
                        shadyCounter++;
                    }
                    else
                    {
                        counter++;
                    }
                    i++;
                }
            }
            totalwords=counter;
            sc.close();
        }

        catch(FileNotFoundException e)
        {
            System.out.println("FileNotFoundException: File with the name "+pageName+" is not present in the list of webpages.");
            return;
        }
    }

    public boolean isAStopWord(String s)
    {
        String censor[]={"a", "an", "the", "they", "these", "this", "for", "is", "are", "was", "of", "or","and", "does", "will", "whose"};
        boolean flag=false;
        for(int i=0; i<censor.length; i++)
        {
            if(s.equals(censor[i]))
            {
                flag=true;
                break;
            }
        }
        return flag;
    }

    public PageIndex getPageIndex()
    {
        return index;
    }

    public String[] cleanUp(String s)
    {
        //String arr[]= s.toLowerCase().split("\\W+");
        s = s.replaceAll("[\\{\\}\\[\\]\\<\\>\\=\\(\\)\\.\\,\\;\\'\\?\\#\\!\\-\\:]", " ");   //{ } [ ] < > = ( ) . , ; ' " ? # ! - :
        s = s.replace('"', ' ');
        String arr[]=s.trim().toLowerCase().split("\\s+");
        for(int i=0; i<arr.length; i++)
        {
            if (arr[i].equals("stacks") || arr[i].equals("structures") || arr[i].equals("applications"))
                arr[i] = arr[i].substring(0, arr[i].length() - 1);
        }
        return arr;
    }

    public float getTermFrequency(String word)
	{
		int freq=0;
		MyLinkedList<WordEntry> list = index.getWordEntries();
		for(int i=0;i<list.size;i++)
		{
			freq+=list.get(i).frequency(word);
		}
		return (float)freq/(float)totalwords;
    }

    public float getInverseDocFreq(String word, int numPages, InvertedPageIndex inv)
    {
        // It is the logarithm of the total number of web-
        // pages, denoted by N divide by the logarithm of the number of webpages
        // nw(p) that contain the word w. 
        int nw = inv.getPagesWhichContainWord(word).list.size;
        return (float)Math.log(((double)numPages)/(double)nw);
    }

    public int numPhrases(String str[], AVLTree t, int ans)
    {
        int n=t.root.key;
        int count=1;
        for(int i=1;i<str.length;i++)
        {
            WordEntry we=getWordEntry(str[i]);
            if(we==null)
                continue;
            Position check = new Position(this, n+count);
            if(we.tree.search(check)!=null)
                count++;
            else
                break;
        }
        if(count == str.length)
            ans++;
        if(t.root.left!=null)
        {
            AVLTree x=new AVLTree(t.root.left);
            ans=numPhrases(str, x, ans);
        }
        if(t.root.right!=null)
        {
            AVLTree x=new AVLTree(t.root.right);
            ans=numPhrases(str, x, ans);
        }
        return ans;
    }

    public WordEntry getWordEntry(String str)
    {
        MyLinkedList<WordEntry> w = this.index.wordEnt;
            for(int i=0;i<w.size;i++)
            {
                if(w.get(i).str.equals(str))
                    return w.get(i);
            }
        return null;
    }
    
    public float getRelevanceOfPage(String str[], boolean doTheseWordsRepresentAPhrase, int numPages, InvertedPageIndex inv, int nw)
    {
        float rel=0.0f;
        if (!doTheseWordsRepresentAPhrase)
        {
            for(int i=0;i<str.length;i++)
            {
                rel+=getTermFrequency(str[i])*getInverseDocFreq(str[i],numPages,inv);
            }
        }
        else
        {
            WordEntry w = getWordEntry(str[0]);
            AVLTree t=w.tree;
            int m=numPhrases(str,t,0); //phraseCounter in this page
            rel=(float)(((float)m/(float)(totalwords-((str.length-1)*m)))*(float)Math.log(((double)numPages)/(double)nw));
        }
        return rel;
    }
}