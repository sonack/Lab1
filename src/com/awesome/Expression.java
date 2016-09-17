package com.awesome;

import java.util.ArrayList;
import java.util.Arrays;

public class Expression
{

    public ArrayList<Monomial> exp = new ArrayList<Monomial>();

    public Expression() {}

    public Expression(Monomial m)
    {
	exp.add(m);
    }

    public Expression addMonomial(Monomial m)
    {
	exp.add(m);
	return this;
    }

    public Expression simplify()
    {
	Expression combineRes = new Expression();
	for (int i = 0; i < exp.size(); i++)
	{
	    int hasCheckedCnt = combineRes.exp.size();
	    if (hasCheckedCnt == 0)
		combineRes.addMonomial(exp.get(i));
	    else
	    {
		boolean compatible = false;
		for (int j = 0; j < hasCheckedCnt; j++)
		{
		    if (exp.get(i).isCompatible(combineRes.exp.get(j)))
		    {
			compatible = true;
			double C = exp.get(i).getC() + combineRes.exp.get(j).getC();
			combineRes.exp.get(j).setC(C);
			break;
		    }
		}
		if(!compatible)
			combineRes.addMonomial(exp.get(i));
	    }
	}
	Expression simplifyZero = new Expression();
	for(int i=0;i<combineRes.exp.size();i++)
	{
	    if(!Utils.doubleEquals(combineRes.exp.get(i).getC(),0))
	    {
		simplifyZero.exp.add(combineRes.exp.get(i));
	    }
	}
	if(simplifyZero.exp.size() == 0)
	    simplifyZero.exp.add(new Monomial(0));
	return simplifyZero;
    }

    public Expression multiply(Expression e)
    {
	Expression product = new Expression();
	for (int i = 0; i < exp.size(); i++)
	    for (int j = 0; j < e.exp.size(); j++)
	    {
		product.addMonomial(exp.get(i).multiply(e.exp.get(j)));
	    }
	return product;
    }

    public Expression power(Expression e)
    {
	int x = (int)e.exp.get(0).getC();
	Expression res = new Expression(new Monomial(1));
	for(int i=0;i<x;i++)
	    res = res.multiply(this);
	return res;
    }
    
    public Expression add(Expression e)
    {
	for (int i = 0; i < e.exp.size(); i++)
	{
	    addMonomial(e.exp.get(i));
	}
	return this;
    }
    
    public Expression sub(Expression e)
    {
	Monomial monomial;
	for(int i=0;i<e.exp.size();i++)
	{
	    monomial = e.exp.get(i);
	    monomial.setC(-monomial.getC());
	}
	add(e);
	return this;
    }
    
    public Expression derivative(String v)
    {
	Expression expression = new Expression();
	for(int i=0;i<exp.size();i++)
	{
	    expression.addMonomial(exp.get(i).derivative(v));
	}
	return expression;
    }
    
    @Override
    public String toString()
    {
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < exp.size(); i++)
	{
	    Monomial monomial = exp.get(i);
	    if (i == 0)
		sb.append(monomial);
	    else
	    {
		if(monomial.getC() > 0)
		    sb.append(" + " + monomial);
		else
		{
		    monomial.setC(-monomial.getC());
		    sb.append(" - " + monomial);
		}
	    }
	}
	return sb.toString();
    }

    public static void main(String[] args)
    {
	
    }

}

class Monomial
{
    private double C = 1.0;
    private int[] times = new int[Utils.getVariableCnt()];

    public Monomial() {}
    
    public Monomial(double C)
    {
	this.C = C;
    }
    
    public Monomial(String data)
    {
	if (Utils.isDouble(data))
	    C = Double.parseDouble(data);
	else
	    times[Utils.getVariableID(data)] = 1;
    }
    
    public double getC()
    {
	return C;
    }
    
    public void setC(double C)
    {
	this.C = C;
    }

    public boolean isCompatible(Monomial m)
    {
	return Arrays.equals(times, m.times);
    }

    public Monomial multiply(Monomial m)
    {
	Monomial product = new Monomial();
	product.C = C * m.C;
	for (int i = 0; i < times.length; i++)
	{
	    product.times[i] = times[i] + m.times[i];
	}
	return product;
    }
    
    public Monomial derivative(String v)
    {
	Monomial monomial = new Monomial();
	int id = Utils.getVariableID(v);
	if(times[id] > 0)
	{
	    monomial.times = times.clone();
	    monomial.C = times[id] * C;
	    monomial.times[id]--;
	}
	else
	{
	    monomial.C = 0;
	}
	return monomial;
    }
    @Override
    public String toString()
    {
	StringBuffer sb = new StringBuffer();
	boolean first = true;
	if (!Utils.doubleEquals(C, 1.0))
	    sb.append(Utils.shortDouble(C));
	for (int i = 0; i < times.length; i++)
	{
	    if (times[i] != 0)
	    {
		if (first)
		{
		    first = false;
		    sb.append(Utils.getVariableFromID(i)+(times[i]!=1?"^"+times[i]:""));
		}
		else
		    sb.append("*" + Utils.getVariableFromID(i)+(times[i]!=1?"^"+times[i]:""));
	    }
	}
	if(first && Utils.doubleEquals(C, 1.0)) sb.append(Utils.shortDouble(C));
	return sb.toString();
    }
}