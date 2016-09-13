package com.awesome;

import java.util.ArrayList;

public class Expression
{
   
    private ArrayList<Monomial> exp;
    public Expression()
    {
	exp = new ArrayList<Monomial>();
    }
    
    public void simplify()
    {
	
    }
    
  
    
    public static void main(String[] args)
    {

    }

}

class Monomial
{
    private double C;
    private int[] times;
    public Monomial()
    {
	times = new int[Utils.getVariableCnt()];
    }
    
    public Monomial multiply(Monomial m1,Monomial m2)
    {
	m1.C *= m2.C;
	for (int i = 0; i < Utils.getVariableCnt(); i++)
	{
	    m1.times[i] += m2.times[1]; 
	}
	
	return m1;
	
    }
    
}