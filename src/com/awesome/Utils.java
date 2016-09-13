package com.awesome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * @author snk
 *
 */
public class Utils
{
    private static ArrayList<String> pool = new ArrayList<String>();
    private static int variableCnt = 0;
  
    /**
     * 
     * @param v
     * @return 
     */
     
    public static boolean isDigit(char c)
    {
	return Character.isDigit(c);
    }
    
    public static boolean isAlpha(char c)
    {
	return Character.isAlphabetic(c);
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
    
    public static boolean isVariable(String s)
    {
	return s.matches("[A-Za-z]+");
    }
    
    public static String removeSpace(String s)
    {
	return s.replace(" ", "").replace("\t", "");
    }
    
    public static String addOp(String s)
    {
	StringBuffer sb = new StringBuffer();
	sb.append(s);
	for(int i=0;i<sb.length()-1;i++)
	{
	    if(isDigit(sb.charAt(i)) && isAlpha(sb.charAt(i+1)))
	    {
		sb.insert(i+1, "*");
	    }
	}
	return sb.toString();
    }
    
    public static String[] splitVariables(String s)
    {
	String[] ss;
	String[] vs = new String[52];
	ss = s.split("\\(|\\)|\\*|\\+|\\-|\\^");
	Set<String> set = new HashSet<String>();
	for(String v : ss)
	    set.add(v);
	
	for(String v : set)
	{
	    if(v.length() > 0  && isAlpha(v.charAt(0)))
	    {
		vs[variableCnt++] = v;
	    }
		
	}
	
	return vs;
    }
    public static char transform2char(int id)
    {
	char ret = 0;
	if(id < 26) ret = (char)('a'+id);
	else if(id < 52) ret =  (char)('A'+id-26);
	return ret;
    }
    
    public static int getVariableID(String v)
    {
	for(int i=0;i<pool.size();i++)
	{
	    if(pool.get(i).equals(v))
	    {
		return i;
	    }
	}
	pool.add(v);
	return pool.size()-1;
    }
    
    
    public static String addZero(String s)
    {
	s = s.replace("(+", "(0+").replace("(-", "(0-");
	StringBuffer sb = new StringBuffer();
	sb.append(s);
	if(sb.charAt(0) == '+' || sb.charAt(0) == '-')
	    sb.insert(0, "0");
	return sb.toString();
	
    }
    
    public static int getVariableCnt()
    {
	return variableCnt;
    }
    
    public static String replaceWithCharVariables(String s)
    {
	String[] vs = splitVariables(s);
	for(int i=0;i<variableCnt;i++)
	{
	    System.out.println("Replace" + vs[i] + transform2char(getVariableID(vs[i]))+"" );
	    s = s.replace(vs[i], transform2char(getVariableID(vs[i]))+"");
	}
	return s;
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
    }

}
