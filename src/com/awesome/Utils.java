package com.awesome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * @author snk
 *
 */
public class Utils
{
    private static ArrayList<String> pool = null;
    private static Set<String> set = null;
    private static int variableCnt = 0;

    public static boolean isDigit(char c)
    {
	return Character.isDigit(c);
    }
    
    public static boolean isAlpha(char c)
    {
	return Character.isAlphabetic(c);
    }
    
    public static boolean isOperator(char c)
    {
	if("+-*^".indexOf(c)==-1)
	    return false;
	return true;
    }
    public static boolean isDouble(String s)
    {
	boolean isdouble = true;
	try
	{
	    Double ds = Double.parseDouble(s);
	    if(ds instanceof Double == false)
		isdouble = false;
	}catch(NumberFormatException e)
	{
	    isdouble = false;
	}
	return isdouble;
    }
    
    
    public static boolean doubleEquals(double a,double b)
    {
	return Double.valueOf(a).equals(Double.valueOf(b));
    }
    
    public static boolean isVariable(String s)
    {
	return s.matches("^[A-Za-z_][A-Za-z0-9_]*$");	//变量名规则：字母\下划线开头，由数字、字母和下划线组成
    }
    
    public static String removeSpace(String s)
    {
	return s.replace(" ", "").replace("\t", "");
    }
    
    public static String addOperator(String s)
    {
	StringBuffer sb = new StringBuffer();
	sb.append(s);
	for(int i=0;i<sb.length()-1;i++)
	{
	    if((isDigit(sb.charAt(i)) && isAlpha(sb.charAt(i+1))) || ( !isOperator(sb.charAt(i)) && sb.charAt(i+1) == '(' ))
	    {
		sb.insert(i+1, "*");
	    }
	}
	return sb.toString();
    }
    
    public static void calcVariableCnt(String s)
    {
	String[] ss;
	ss = s.split("\\(|\\)|\\*|\\+|\\-|\\^");
	Set<String> deduplicate = new HashSet<String>();
	set = new HashSet<String>();
	pool = new ArrayList<String>();
	for(String v : ss)
	    deduplicate.add(v);
	for(String v : deduplicate)
	    if(isVariable(v))
		set.add(v);
	variableCnt = set.size();
    }
    
    public static boolean isVariableAppear(String v)
    {
	return set.contains(v);
    }
    
    public static int getVariableCnt()
    {
	return variableCnt;
    }
    
    public static int getVariableID(String v)
    {
	int pos = pool.indexOf(v);
	if(pos != -1)	return pos; 
	pool.add(v);
	return pool.size()-1;
    }
    
    public static String getVariableFromID(int id)
    {
	return pool.get(id);
    }
    
    public static String addZero(String s)
    {
	s = s.replace("(+", "(0+").replace("(-", "(0-");
	if(s.charAt(0) == '+' || s.charAt(0) == '-')
	    s = "0" + s;
	return s;
    }

    public static String shortDouble(double d)
    {
	if(doubleEquals(Math.round(d) - d,0))
	    return String.valueOf((long)d);
	return String.valueOf(d);
    }
    
    public static String replaceVariableWithValue(String exp,String variable,String value)
    {
	return exp.replaceAll(variable, value+"");
    }
    
    public static void main(String[] args)
    {
	System.out.println("*********************");
	System.out.println(getVariableID("a"));
	System.out.println(getVariableID("b"));
	System.out.println(getVariableID("a"));
	System.out.println(getVariableID("c"));
	System.out.println((char)('a'+1));
//	InputProcessor.inputRawStr();
//	System.out.println(removeSpace(InputProcessor.getRawStr()));
//	System.out.println(addOp(removeSpace(InputProcessor.getRawStr())));
//	String sss = addOp(removeSpace(InputProcessor.getRawStr()));
//	
//	System.out.println(Arrays.toString(splitVariables(sss)));
//	System.out.println(addZero(sss));
	System.out.println("....." + isDouble("3.1s.23"));
	System.out.println(isVariable("3a"));
	System.out.println(isVariable("aaasd1a3_sad"));
	System.out.println(isVariable("_asdasd1"));
	System.out.println(isVariable("a1111"));
	System.out.println(shortDouble(1.00));
	System.out.println(shortDouble(1.001));
	System.out.println(replaceVariableWithValue("a+b", "a", "3.1"));
	System.out.println(isOperator('*'));
	
    }

}
