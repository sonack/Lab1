package com.awesome;

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

}

public class ExpressionTree
{
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
	if(root == null) return;
	postTravel(root.l);
	postTravel(root.r);
	System.out.print(root.data + "  ");
    }
    
    public Expression calcExpTree(Node root)
    {
	Expression l,r;
	if(root.l == null && root.r == null)
	    return new Expression(new Monomial(root.data));
	l = calcExpTree(root.l);
	r = calcExpTree(root.r);
	if(root.data.equals("*"))
	    return l.multiply(r).simplify();
	else if(root.data.equals("+"))
	    return l.add(r).simplify();
	else if(root.data.equals("-"))
	    return l.sub(r).simplify();
	else if(root.data.equals("^"))
	    return l.power(r).simplify();
	else
	    return null; //未知情况
    }
    
    public static void main(String[] args)
    {
	String lastExp = null;
	Expression lastResult = null;
	boolean commandMode = false;
	while(true)
	{
	    	commandMode = false;
		InputProcessor.inputRawExp();
		String input = InputProcessor.getRawExp();
		if(input.startsWith("!"))
		{
		    commandMode = true;
		    if(input.startsWith("!simplify"))
		    {
			if(lastExp == null)
			{
			    System.out.println("请先输入表达式!");
			    continue;
			}
			else
			{
			    String replacedExp = lastExp;
			    String[] assigns =  input.split(" ");
			    boolean notAppear = false;
			    StringBuffer msg = new StringBuffer();
			    msg.append("变量");
			    for(int i=1;i<assigns.length;i++)
			    {
				String[] kv = assigns[i].split("=");
				if(!Utils.isVariableAppear(kv[0]))
				{
				    if(!notAppear)
				    {
					msg.append(kv[0]);
					notAppear = true;
				    }
				    else
					msg.append("、"+kv[0]);
				}
				replacedExp = Utils.replaceVariableWithValue(replacedExp, kv[0], kv[1]);
			    }
			    if(notAppear) 
			    {
				msg.append("未出现!");
				System.out.println(msg);
				continue;
			    }
			    InputProcessor.setProcessedExp(replacedExp);
			}
		    }
		    else if(input.startsWith("!d/d"))
		    {
			String v = input.substring(4, input.length()).trim();
			if(!Utils.isVariableAppear(v))
			    System.out.println("变量"+v+"未出现!");
			else
			{
			    System.out.println(lastResult.derivative(v).simplify());
			}
			continue;
		    }
		    else if(input.equals("!exit") || input.equals("!quit"))
		    {
			System.out.println("退出程序...");
			System.exit(0);
		    }
		    else
		    {
			System.out.println("命令有误!");
			continue;
		    }
		}
	    Expression result = null;
	    try
	    {
		
		if(!commandMode)
		    InputProcessor.preprocess();
		String s = InputProcessor.getProcessedExp();
		ExpressionTree tree = new ExpressionTree();
		Node root = tree.buildExpTree(s,0,s.length());
		result = tree.calcExpTree(root);
//		tree.postTravel(root);
//		System.out.println("\n= " + result);
		System.out.println(result);
	    }
	    catch(Exception e)
	    {
		e.printStackTrace();
		System.out.println("输入有误!");
	    }
	    if(!commandMode) 
		{
			lastExp = InputProcessor.getProcessedExp();
			lastResult = result;
		}
	}
    }
}
