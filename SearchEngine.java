

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SearchEngine
{
    InvertedPageIndex inv;
    //int totalPages=0;

    public SearchEngine() 
    {
        inv = new InvertedPageIndex();
    }

    public void performAction(String actionMessage)throws FileNotFoundException, IOException
    {
        try
        {
            String[] arr=actionMessage.split(" ");

            if(arr[0].equals("addPage"))
            {
                //inv = new InvertedPageIndex();
                PageEntry p1=new PageEntry(arr[1]);
                inv.addPage(p1);
                //totalPages++;
            }

            else if(arr[0].equals("queryFindPagesWhichContainWord"))
            {
                String str=arr[1].toLowerCase();

                if (!str.equals("a") && !str.equals("an") && !str.equals("the") && !str.equals("they") && !str.equals("these") && !str.equals("this") && !str.equals("for") && !str.equals("is") && !str.equals("are") && !str.equals("of") && !str.equals("or")  && !str.equals("and") && !str.equals("does")   && !str.equals("will") && !str.equals("whose") && !str.equals("that")) 
                {
                    if (str.equals("stacks"))
                        str = "stack";
                    else if (str.equals("structures"))
                        str = "structure";
                    else if (str.equals("applications")) 
                        str = "application";
                     // MySet<PageEntry> set1 = (inv.getPagesWhichContainWord(str)); //set1<1 then noprint
                     
                    //  for(int i=0; i<set1.list.size; i++)
                    //  {  
                    //      if(i!=0)
                    //         System.out.print(", ");
                    //      System.out.print(set1.list.get(i).pageName);
                    //  }
                    // //  System.out.println(set1.list.get(set1.list.size-1).pageName);
                    // if(set1.list.size<1)
                    //     System.out.println("No webpage contains the word "+str);
                    // else
                    //     System.out.println("");

                    MyLinkedList<PageEntry> x = inv.getPagesWhichContainWordinOrder(str);
                    if(x.size < 1 || x == null)
				        System.out.println("No webpage contains word "+str);
                    else
                    {	
                        for(int i=0;i<x.size;i++)
                        {	
                            if(i!=0)
                            {
                                System.out.print(", ");
                            }
                            System.out.print(x.get(i).pageName); 
                        }
                        System.out.println("");
                    }
		
                }

                else 
                    System.out.println(actionMessage+": Error - Cannot search for a stop word.");

            
            }

            else if (arr[0].equals("queryFindPositionsOfWordInAPage"))
            {
                String str = arr[1].toLowerCase();
                boolean flag = false;
                for (int j=0; j<inv.pages.size; j++) 
                {
                    if (inv.pages.get(j).pageName.equals(arr[2]))
                       flag=true;
                }

                if(flag)
                {
                    if (!str.equals("a") && !str.equals("an") && !str.equals("the") && !str.equals("they") && !str.equals("these") && !str.equals("for") && !str.equals("is") && !str.equals("are") && !str.equals("of") && !str.equals("or")  && !str.equals("and") && !str.equals("does")   && !str.equals("will") && !str.equals("whose") && !str.equals("that")) 
                    {
                        //PageEntry p=new PageEntry(arr[2]);
                        if (str.equals("stacks")) 
                            str = "stack"; 
                        else if (str.equals("structures")) 
                            str = "structure";
                        else if (str.equals("applications")) 
                            str = "application";
                        MyLinkedList<WordEntry>.Node<WordEntry> el=(inv.ht.l[inv.ht.getHashIndex(str)]).getWordByData(str);
                        if(el.element != null)
                        {
                            MyLinkedList<Position> poslist=el.element.pos;
                            int counter=0; String s="";
                            for(int i=0; i<poslist.size; i++)
                            {
                                if(poslist.get(i).p.pageName.equals(arr[2]))
                                {
                                    s+=(poslist.get(i).wordIndex);
                                    s+=", ";
                                    counter++;
                                }
                            }
                            if(counter!=0)
                                System.out.println(s.substring(0,s.length()-2));
                            else
                                System.out.println("Webpage "+arr[2]+" does not contain word "+str);
                        }
                        else
                            System.out.println("Webpage "+arr[2]+" does not contain word "+str);
                    }
                    else
                        System.out.println("You have inputted a Stop Word.");
                }

                else
                    System.out.println("No WebPage "+arr[2]+" Found.");
            }

            // ************Post Part 1

            else if (arr[0].equals("queryFindPagesWhichContainAllWords"))
            {
                String[] str = new String[arr.length - 1];
                for (int i = 0; i < str.length; i++) 
                {
                    str[i] = arr[i + 1];
                } 
                ArrayList<SearchResult> res = inv.getPagesWhichContainAllWords(str);
                if(res.size()>0)
                {
                    for (int i = 0; i < res.size(); i++)
                    {
                        System.out.print(res.get(i).getPageEntry().pageName);
                        if(i!=res.size()-1)
                            System.out.print(", ");
                    }
                    System.out.println("");
                }
                else
                    System.out.println("Search Unsuccessful. No webpage with the given search queries found.");
            } 
            
            else if (arr[0].equals("queryFindPagesWhichContainAnyOfTheseWords")) 
            {
                String[] str = new String[arr.length - 1];
                for (int i = 0; i < str.length; i++) 
                {
                    if (arr[i].equals("stacks")) 
                        arr[i] = "stack";
                    
                    else if (arr[i].equals("structures")) 
                        arr[i] = "structure";
                    
                    else if (arr[i].equals("applications")) 
                        arr[i] = "application";
                    
                    str[i] = arr[i + 1];
                }

                ArrayList<SearchResult> res = inv.getPagesWhichContainAnyOfTheseWords(str);
                if(res.size()>0)
                {
                    for (int i = 0; i < res.size(); i++) 
                    {
                        System.out.print(res.get(i).getPageEntry().pageName);  
                        if(i!=res.size()-1)
                            System.out.print(", ");
                    }
                    System.out.println("");
                }
                else
                    System.out.println("Search Unsuccessful. No webpage with the given search queries found.");
            }
            
            else if (arr[0].equals("queryFindPagesWhichContainPhrase")) 
            {
                //System.out.print("***");
                String[] str = new String[arr.length - 1];
                for (int i = 0; i < str.length; i++) 
                {
                    if (arr[i + 1].equals("stacks")) 
                        str[i] = "stack";
                    
                    else if (arr[i + 1].equals("structures")) 
                        str[i] = "structure";
                    
                    else if (arr[i + 1].equals("applications")) 
                        str[i] = "application";
                    
                    else 
                        str[i] = arr[i + 1];
                }
                ArrayList<SearchResult> res = inv.getPagesWhichContainPhrase(str);
                if(res.size()>0)
                {
                    for (int i = 0; i < res.size(); i++) 
                    {
                        System.out.print(res.get(i).getPageEntry().pageName);  
                        if(i!=res.size()-1)
                            System.out.print(", ");
                    }
                    System.out.println("");
                }
                else
                    System.out.println("Search Unsuccessful. No webpage with the given search queries found.");
            }

            else
                System.out.println(actionMessage+": Error - Invalid Command.");
        }
        catch (NullPointerException e)
        { 
            String[] arr=actionMessage.split(" ");
            System.out.println(actionMessage+": Error - Search Unsuccessful. No webpage contains the word \""+arr[1]+"\".");
        }
        // catch (FileNotFoundException e) 
        // {
        //     System.out.println(actionMessage+": Error - WebPage Not Found.");
        // } 
        catch (Exception e)         //searching only for stop words
        {
            System.out.println(actionMessage+": Error - Search Unsuccessful.");
        }
    }
}