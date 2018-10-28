
public class MySet<X> // change everywhere to MySet<X>
{
    MyLinkedList<X> list=new MyLinkedList<X>();

    static class MyException extends Exception
    { 
        private static final long serialVersionUID = 1L; 
    }

    public Boolean IsEmpty()
    {
        return this.list.isEmpty();
    }

    public Boolean IsMember(X o)
    {
        if(this.IsEmpty()) 
            return false;
        MyLinkedList<X>.Node<X> n=this.list.head;
        int k=0;
        while(n!=null)
        {
            if((n.element).equals(o))
            {
                k=1; break;
            }
            n=n.next;
        }
        if(k==1)
            return true;
        return false;
    }

    public void addElement(X element) //used to be Insert
    {
        if(this.IsMember(element))
            return;
        this.list.addLast(element);
    }

    public void Delete(X o)
    {
        if(!(this.IsMember(o)))
        {
            try
            {
                throw new MyException();
            }
            catch(MyException ex) 
            {
                //System.out.println("Error - No mobile phone with identifier "+o.number()+" found in the network");
                System.out.println("Error - No mobile phone with the given identifier found in the network");
            }
        }

        MyLinkedList<X>.Node<X> temp=this.list.head, prev=null;
        if(temp!=null && (temp.element).equals(o))
        {
            this.list.head=temp.next;
            this.list.size--;
            if(this.list.size==0)
                this.list.tail=null;
            return;
        }
        while(temp!=null && !((temp.element).equals(o)))
        {
            prev=temp;
            temp=temp.next;
        }
        prev.next=temp.next;
        if(temp.next==null)
            this.list.tail=prev;
        this.list.size--;
        if(this.IsMember(o))
            this.Delete(o);
    }

    // public MySet<X> union(MySet<X> otherSet) //previously Union(MySet<X> a)
    // {
    //     MySet<X> unset=new MySet<X>();
    //     MyLinkedList<X>.Node<X> ptr=this.list.head;
    //     while(ptr!=null)
    //     {
    //         // ptr2.element=ptr1.element;
    //         // if(ptr1.next=null) 
    //         //     unset.list.tail=ptr2;
    //         // ptr1=ptr1.next;
    //         // ptr2=ptr2.next;
    //         (unset.list).addLast(ptr.element);
    //         ptr=ptr.next;
    //     }
    //     ptr=otherSet.list.head;
    //     while(ptr!=null)
    //     {
    //         if(!(unset.IsMember(ptr.element)))
    //             (unset.list).addLast(ptr.element);
    //         ptr=ptr.next;
    //     }
    //     return unset;
    // }

    public MySet<X> intersection(MySet<X> otherSet) //previously Intersection(MySet<X> a)
    {
        MySet<X> inset=new MySet<X>();
        MyLinkedList<X>.Node<X> ptr=this.list.head;
        while(ptr!=null)
        {
            if(otherSet.list.search(ptr.element))
                (inset.list).addLast(ptr.element);
            ptr=ptr.next;
        }
        return inset;
    }
    
    public MySet<X> union(MySet<X> a)
    {
        this.list.union(a.list);
        return this;
    }

    // public MySet<X> intersection(MySet<X> a)
    // {
    //     this.list.intersection(a.list);
    //     return this;
    // }



    public void tester()
    {
        MyLinkedList<X>.Node<X> ptr=this.list.head;
        while(ptr!=null)
        {
            System.out.print(ptr.element+" ");
            ptr=ptr.next;
        }
        System.out.println();
    }
}