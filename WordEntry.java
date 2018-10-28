import java.util.ArrayList;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class WordEntry
{
    String str;
    MyLinkedList<Position> pos;
    AVLTree tree; //=new AVLTree();

    public WordEntry(String word)
    {
        this.str=word;
        pos=new MyLinkedList<Position>();
        tree=new AVLTree();
    }

    public void addPosition(Position position)
    {
        pos.addLast(position);
        if (!treeSearch(position)) 
        {
            tree.insert(position);
        }
    }

    public void addPositions(MyLinkedList<Position> positions)
    {
        this.pos.union(positions);
        this.treeUnion(positions);
    }

    public MyLinkedList<Position> getAllPositionsForThisWord()
    {
        return this.pos;
    }

    public int frequency(String s)
    {
        if(s.equals(str))
        {
            return this.pos.size;
        }
        return 0;
    }

    public MyLinkedList<PageEntry> getPageEntryinOrder()
	{
		MyLinkedList<PageEntry> list = new MyLinkedList<PageEntry>();
		for(int i=0;i<pos.size;i++)
		{
			list.addLast(pos.get(i).getPageEntry());
		}
		// list.print();
		MyLinkedList<PageEntry> sortedlist = new MyLinkedList<PageEntry>();
		while(list.size>0)
		{	
            int j=0;
			float max=0;
			for(int i=0;i<list.size;i++)
			{
				if(list.get(i).getTermFrequency(str)>max)
				{
					j=i;
				}
			}
			sortedlist.addLast(list.get(j));
			list.deleteByIndex(j);
		}
		return sortedlist;
    }
    
    //*********POST PART 1 */
    public boolean treeSearch(Position p)
    {
        if(tree.search(p)==null)
            return false;
        else
            return true;
    }

    public AVLTree treeUnion(MyLinkedList<Position> l)
    {
        for (int i = 0; i < l.size; i++) 
        {
            addPosition(l.get(i));
        }
        return tree;
    }

    public AVLTree treeUnion(AVLTree tr)
    {
        ArrayList<Position> l = tr.traverse();
        for (int i = 0; i < l.size(); i++) 
        {
            addPosition(l.get(i));
        }
        return tree;
    }

    // public void addPosition(Position position) 
    // {
    //     if (!treeSearch(position)) 
    //     {
    //         tree.insert(position);
    //     }
    // }

    // public void addPositions(MyLinkedList<Position> p) 
    // {
    //     this.treeUnion(p);
    // }

    
    public void addPositions(AVLTree tr)
    {
        this.treeUnion(tr);
    }

    // public AVLTree getAllPositionsForThisWord()
    // {
    //     return tree;
    // }
    
}