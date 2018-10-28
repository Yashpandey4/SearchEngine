
public class MyLinkedList<E>
{
    
    class Node<E>
    {
        public E element;
        public Node<E> next;
        public Node<E> prev;
        public Node()
        {
            element=null;
            next=null;
            prev=null;
        }
        public Node(E e,Node<E> n)
        {
            element=e;
            next=n;
        }
        public Node(E e,Node<E> p,Node<E> n)
        {
            element=e;
            next=n;
            prev=p;
            n.setPrev(this);
            p.setNext(this);
        }
        public void setNext(Node<E> e)
        {
            next=e;
            e.prev=this;
        }
        public void setPrev(Node<E> e)
        {
            prev=e;
            e.next=this;
        }
    }
    
    public Node<E> head;
    public Node<E> tail;
    public int size;
    public MyLinkedList() 
    {
        head=new Node<E>();
        tail=new Node<E>();
        head.setNext(tail);
        tail.setPrev(head);
        size=0;
    }
    // public int size()
    // {
    //     return size;
    // }
    public boolean isEmpty()
    {
        return size==0;
    }
    // public E first()
    // {
    //     if(isEmpty()) return null;
    //     return head.getElement();
    // }
    // public E last()
    // {
    //     if(isEmpty()) return null;
    //     return tail.getElement();
    // }
    public void addFirst(E e)
    {
        if(!this.search(e))
        {
            Node<E> d=new Node<E>(e,head,head.next);
            size++;
        }
    }
    public void addLast(E e)
    {
        if(!this.search(e))
        {
            Node<E> d=new Node<E>(e,tail.prev,tail);
            size++;
        }
    }
    public E removeFirst()
    {
        if(isEmpty())
            return null;
        E answer=head.element;
        head=head.next;
        size--;
        if(size==0)
            tail=null;
        return answer;
    }

    public E get(int i) // n is 0 to size-1
    {
        Node<E> a = this.head.next;
        if (i < size) {
            for (int j = 0; j < i; j++) {
                a = a.next;
            }
            return a.element;
        } 
        else return tail.element;
    }

    // public void test()
    // {
    //     Node<E> ptr=head;
    //     for(int i=0; i<size;i++)
    //     {
    //         System.out.println(ptr.element+" ");
    //         ptr=ptr.next;
            
    //     }
    // }
        
    // ******** Assn 4 stuff *******

    public MyLinkedList<E> union(MyLinkedList<E> l) {
        if (this.isEmpty()) {
            return l;
        } 
        else {
            Node<E> a = l.head;
            while (!a.next.equals(l.tail)) {
                Node<E> b = a.next;
                if (!search(b.element)) {
                    this.addLast(b.element);
                    a = b;
                } 
                else {
                    a = b;
                }
            }
            return this;
        }
    }

    public MyLinkedList<E> intersection(MyLinkedList<E> l) 
    {
        MyLinkedList<E> n = new MyLinkedList<E>();
        if (this.isEmpty()) 
        {
            return n;
        } 
        else 
        {
            Node<E> a = l.head;
            while (!a.next.equals(l.tail))
            {
                Node<E> b = a.next;
                if (search(b.element)) 
                {
                    n.addLast(b.element);
                    a = b;
                } 
                else 
                {
                    a = b;
                }
            }
            return n;
        }
    }

    public boolean search(E o) {
        if (isEmpty()) {
            return false;
        } 
        else {
            Node<E> a = this.head;
            while (!a.next.equals(tail)) {
                Node<E> b = a.next;
                if (b.element == o) {
                    return true;
                } 
                else {
                    a = b;
                }
            }
            return false;
        }
    }
    
    public boolean alreadyExists(String s)
    {
        if(this.isEmpty())
            return false;
        MyLinkedList<WordEntry>.Node<WordEntry> n=(MyLinkedList<WordEntry>.Node<WordEntry>)this.head;
        while(!n.next.equals(this.tail))
        {
            MyLinkedList<WordEntry>.Node<WordEntry> n1=n.next;
            if(n1.element.str.equals(s))
                return true;
            else
                n=n1;
        }
        return false;
    }

    public MyLinkedList<WordEntry>.Node<WordEntry> getWordByData(String s)
    {
        MyLinkedList<WordEntry>.Node<WordEntry> n=(MyLinkedList<WordEntry>.Node<WordEntry>)this.head.next;
        while(n!=this.tail)
        {
            if(n.element.str.equals(s))
                return n;
            else
                n=n.next;
        }
        return n;
    }

    public void delete(E o) 
    {
        if (isEmpty()) 
        {
            System.out.println("Error - Deleting from Empty list");
        } 
        else {
            Node<E> a = this.head;
            boolean chk = false;
            while (!a.next.equals(tail)) {
                Node<E> b = a.next;
                if (b.element != o) {
                    a = b;
                } 
                else {
                    Node<E> c = b.next;
                    a.next=c;
                    size--;
                    chk = true;
                    break;
                }
            }
            if (!chk) {
                System.out.println("Error - Element to be deleted not Found");
            }
        }
    }

    public void deleteByIndex(int i)
    {
        delete(this.get(i));
    }
}