package com.awesome;

import java.util.Scanner;

class Node
{
    String data;
    Node l,r;
    Node()
    {}
    
    Node(String data,Node l,Node r)
    {
	this.data = data;
	this.l = l;
	this.r = r;
    }
    
    @Override
    public String toString()
    {
        return data;
    }
}

public class ExpressionTree
{
    
    private String inputExp;
    
    public ExpressionTree()
    {
	
    }
    
    public ExpressionTree(String inputExp)
    {
	this.inputExp = inputExp;
    }
    
   
    
    public Node buildExpTree(String s,int x,int y)
    {
	int c1 = -1,c2 = -1,c3 = -1,p = 0;
	String sub = s.substring(x,y);
	if(Utils.isVariable(sub) || Utils.isDouble(sub))
	{
	    return new Node(s.substring(x, y),null,null);
	}
	for(int i=x;i<y;i++)
	{
	    switch(s.charAt(i))
	    {
	    case '(': p++; break;
	    case ')': p--; break;
	    case '+': case '-': if(p == 0) c1=i;break;
	    case '*': if(p == 0) c2=i;break;
	    case '^': if(p == 0) c3=i;break; 
	    }
	}
	
	if(c1 < 0) c1 = c2;
	if(c1 < 0) c1 = c3;
	if(c1 < 0) return buildExpTree(s, x+1, y-1);
	return new Node(s.charAt(c1)+"",buildExpTree(s, x, c1),buildExpTree(s, c1+1, y));
    }
    
    
    public void postTravel(Node root)
    {
	if(root.l != null) postTravel(root.l);
	if(root.r != null) postTravel(root.r);
	System.out.print(root+"\t");
	
    }
    public static void main(String[] args)
    {
	InputProcessor.inputRawStr();
	InputProcessor.processInput();
	String s = InputProcessor.getProcessedInput();
	System.out.println(s);
	ExpressionTree tree = new ExpressionTree();
	Node root = tree.buildExpTree(s,0,s.length());
	tree.postTravel(root);
	
    }

}
