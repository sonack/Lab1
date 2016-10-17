package com.awesome;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * Monomial 单项式类
 * @author snk
 *
 */


/**
 * 
 * @author snk
 * Expression 多项式表达式类
 * 若干个单项式由+号连接而成
 */
<<<<<<< HEAD
=======
/* 为了实验三做的第二个修改*/
<<<<<<< HEAD
<<<<<<< HEAD
/*B1上的第一个修改*/
=======
/*C4上的第一个修改*/
>>>>>>> C4
=======
/*B2上的第一个修改*/
>>>>>>> B2
>>>>>>> origin/1143710310
public class Expression
{

    public ArrayList<Monomial> exp = new ArrayList<Monomial>();	//保存包含的单项式，默认+号连接，减法通过单项式系数为负实现

    public Expression() {}

    public Expression(Monomial m)	//以单项式构造一个特殊的多项式（只有一项）
    {
	exp.add(m);
    }

    public Expression addMonomial(Monomial m)	//多项式添加一个单项式
    {
	exp.add(m);
	return this;
    }

    public Expression simplify()	//多项式化简
    {
	Expression combineRes = new Expression();
	for (int i = 0; i < exp.size(); i++)
	{
	    int hasCheckedCnt = combineRes.exp.size();
	    if (hasCheckedCnt == 0)			//第一个单项式直接添加
		combineRes.addMonomial(exp.get(i));
	    else
	    {
		boolean compatible = false;			//两者是否可以合并同类项(即是否兼容)
		for (int j = 0; j < hasCheckedCnt; j++)
		{
		    if (exp.get(i).isCompatible(combineRes.exp.get(j)))
		    {
			compatible = true;
			double C = exp.get(i).getC() + combineRes.exp.get(j).getC();
			combineRes.exp.get(j).setC(C);		//对应系数相加
			break;
		    }
		}
		if(!compatible)
			combineRes.addMonomial(exp.get(i));
	    }
	}
//	System.out.println("合并同类项之后" + combineRes);
//	System.out.println(combineRes.exp);
	Expression simplifyZero = new Expression();		//把系数为0的单项式去掉
	for(int i=0;i<combineRes.exp.size();i++)
	{
	    if(!Utils.doubleEquals(Math.abs(combineRes.exp.get(i).getC()),0.0))		//加abs()，防止出现-0不等于0!
	    {
//		System.out.println(combineRes.exp.get(i).getC()+"不为0!");
		simplifyZero.exp.add(combineRes.exp.get(i));
	    }
	}
//	System.out.println("去掉零之后" + simplifyZero);
	if(simplifyZero.exp.size() == 0)
	    simplifyZero.exp.add(new Monomial(0));
	return simplifyZero;
    }

    public Expression multiply(Expression e)		//多项式乘以多项式
    {
	Expression product = new Expression();		//展开依次乘
	for (int i = 0; i < exp.size(); i++)
	    for (int j = 0; j < e.exp.size(); j++)
	    {
		product.addMonomial(exp.get(i).multiply(e.exp.get(j)));	//转化为单项式相乘，再相加
	    }
	return product;
    }

    public Expression power(Expression e)		//多项式的幂运算，转化为乘法
    {
	int x = (int)e.exp.get(0).getC();		//右操作数一定为单个整数构成的Expression，提取出来
	Expression res = new Expression(new Monomial(1));
	for(int i=0;i<x;i++)
	    res = res.multiply(this);
	return res;
    }
    
    public Expression add(Expression e)		//多项式加法，转化为依次加单项式
    {
	for (int i = 0; i < e.exp.size(); i++)
	{
	    addMonomial(e.exp.get(i));
	}
	return this;
    }
    
    public Expression sub(Expression e)		//多项式减法，转化为多项式加法
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
    
    public Expression derivative(String v)	//对变量v求导，每个单项式依次求导，再相加
    {
	Expression expression = new Expression();
	for(int i=0;i<exp.size();i++)
	{
	    expression.addMonomial(exp.get(i).derivative(v));
	}
	return expression;
    }
    
    @Override
    public String toString()			//输出表达式
    {
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < exp.size(); i++)
	{
	    Monomial monomial = exp.get(i);
	    if (i == 0)
		sb.append(monomial);
	    else
	    {
		if(monomial.getC() >= 0)
		    sb.append(" + " + monomial);
		else
		{
		    monomial.setC(-monomial.getC());		//把负系数的单项式打印成减法
		    sb.append(" - " + monomial);
		    monomial.setC(-monomial.getC());
		}
	    }
	}
	return sb.toString();
    }

    public static void main(String[] args)
    {
	ExpressionTree.main(args);
    }

}


/**
 * 
 * @author snk
 * 单项式类
 * times来表示各变量的次数，变量未曾出现，次数为0
 * 按照变量出现的顺序，依次将变量名哈希到下标0到n-1(n为变量数)
 * 如 a-(b+c)， 在构造表达式树时，会将a b c 分别映射到0 1 2
 * 所以times[0]=3，times[1]=2，times[2]=1 times[n-1>=i>=3]=0 代表 单项式a^3*b^2*c
 */
class Monomial
{
    private double C = 1.0;		//系数默认为1, 支持实数，使用double
    private int[] times = new int[Utils.getVariableCnt()];

    public Monomial() {}
    
    public Monomial(double C)		//以单个数字构造特殊的单项式(只有系数，没有变量出现)
    {
	this.C = C;
    }
    
    public Monomial(String data)		//自动将字符串数据data转化为系数或者变量构成的单项式
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

    public boolean isCompatible(Monomial m)	//如果两个单项式times数组完全一致，说明它们对应变量的次数均相同，两者为同类项，可以合并
    {
	return Arrays.equals(times, m.times);
    }

    public Monomial multiply(Monomial m)	//单项式乘法，系数相乘，对应变量指数相加
    {
	Monomial product = new Monomial();
	product.C = C * m.C;
	for (int i = 0; i < times.length; i++)
	{
	    product.times[i] = times[i] + m.times[i];
	}
	return product;
    }
    
    public Monomial derivative(String v)	//单项式对变量v求导，变量v的指数乘到系数，指数减1，
    {						//如果变量v不曾出现(指数为0)，则相当于对常数求导，结果为0,直接将系数置为0
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
    public String toString()	//输出单项式
    {
	StringBuffer sb = new StringBuffer();
	boolean first = true;
	if(Utils.doubleEquals(C, -1.0))		//系数-1,只打印负号，省略1
	    sb.append("-");
	else if (!Utils.doubleEquals(C, 1.0))	//系数1省略
	    sb.append(Utils.shortDouble(C));	//shortDouble，如果C小数部分为0,则显示为整数形式，这样更为美观简洁，否则显示为小数形式
	for (int i = 0; i < times.length; i++)
	{
	    if (times[i] != 0)
	    {
		if (first)	//第一个变量省略乘号*
		{
		    first = false;
		    sb.append(Utils.getVariableFromID(i)+(times[i]!=1?"^"+times[i]:""));
		}
		else
		    sb.append("*" + Utils.getVariableFromID(i)+(times[i]!=1?"^"+times[i]:""));
	    }
	}
	if(first && Utils.doubleEquals(Math.abs(C), 1.0)) sb.append(Utils.shortDouble(Math.abs(C)));	//如果只有一个1,则不该省略1,进行纠正
	return sb.toString();
    }
<<<<<<< HEAD
}修改Expression.java
=======
}
>>>>>>> origin/1143710310
