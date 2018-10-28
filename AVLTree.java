import java.util.ArrayList;

public class AVLTree
{
    class Node
    {
        Node left, right;
        Position p;
        int ht,key;

        public Node()
        {
            left=null;
            right=null;
            p=null;
            ht=0;
            key=p.wordIndex;
        }

        public Node(Position pos)
        {
            left=null;
            right=null;
            p=pos;
            ht=0;
            key=p.wordIndex;
        }

        public Position getPosition() 
        {
            return p;
        }
    
        public int ht() 
        {
            return ht;
        }
    
    }

    Node root;

    public AVLTree()
    {
        root=null;
    }

    public AVLTree(Node a) 
    {
        root = a;
    }

    public boolean isEmpty() 
    {
        return (root == null);
    }

    public int ht(Node r) 
    {
        if (r == null) 
            return 0;
        else 
            return r.ht;
    }

    public Node insert(Position p, Node r)
    {
        if(r==null)
            r=new Node(p);
        else
        {
            if(p.wordIndex < r.p.wordIndex)
            {
                r.left=insert(p,r.left);
                if(ht(r.left)-ht(r.right)==2)
                {
                    if(p.wordIndex<r.left.p.wordIndex)
                    {
                        r=leftRot(r);
                    }
                    else
                    {
                        r.left=rightRot(r.left);
                        r=leftRot(r);
                    }
                }
            }

            else if(p.wordIndex>r.p.wordIndex)
            {
                r.right=insert(p, r.right);
                if(ht(r.right)-ht(r.left)==2)
                {
                    if(p.wordIndex>r.right.p.wordIndex)
                    {
                        r=rightRot(r);
                    }
                    else
                    {
                        r.right=leftRot(r.right);
                        r=rightRot(r);
                    }
                }
            }
        }
        r.ht=Integer.max(ht(r.right),ht(r.left))+1;
        return r;
    }
    
    public Node leftRot(Node r)
    {
        Node lch=r.left;
        r.left=lch.right;
        lch.right=r;
        r.ht=Integer.max(ht(r.right),ht(r.left))+1;
        lch.ht=Integer.max(ht(lch.right),ht(lch.left))+1;
        return lch;
    }

    public Node rightRot(Node r)
    {
        Node rch=r.right;
        r.right=rch.left;
        rch.left=r;
        r.ht=Integer.max(ht(r.right),ht(r.left))+1;
        rch.ht=Integer.max(ht(rch.right),ht(rch.left))+1;
        return rch;
    }

    public void insert(Position p)
    {
        if(search(p)==null)
            this.root=this.insert(p,this.root);
    }

    public Node search(Position p)
    {
        if(isEmpty())
            return null;
        else
        {
            if(root.p.wordIndex==p.wordIndex)
                return root;

            else if(p.wordIndex<root.p.wordIndex)
            {
                AVLTree x=new AVLTree(root.left);
                return x.search(p);
            }

            else
            {
                AVLTree x=new AVLTree(root.right);
                return x.search(p);
            }
        }
    }

    public ArrayList<Position> traverse()
    {
        ArrayList<Position> travel = new ArrayList<Position>();
        if (this.isEmpty()) 
            return travel;

        if(root.left==null && root.right==null)
            travel.add(root.p);
        
        else //dangling?
        {
            AVLTree lt_tree = new AVLTree(root.left);
            AVLTree rt_tree = new AVLTree(root.right);
            travel.addAll(lt_tree.traverse());
            travel.add(root.p);
            travel.addAll(rt_tree.traverse());
        }
        return travel;
    }

    public Node max() 
    {
        if (isEmpty()) 
            return root;
        
        else if (root.right == null)
            return root;
        
        else
        {
            AVLTree avl = new AVLTree(root.right);
            return avl.max();
        }
    }

    public Node min() 
    {
        if (isEmpty()) 
            return root;
        
        else if (root.left == null)
            return root;
        
        else
        {
            AVLTree avl = new AVLTree(root.left);
            return avl.min();
        }
    }

    public Position succ(int p)
    {
        ArrayList<Position> a = this.traverse();
        for (int i = 0; i < a.size(); i++) 
        {
            if (a.get(i).wordIndex == (p)) 
                return a.get(i + 1);
        }
        return null;
    }
}