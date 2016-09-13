package com.awesome;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * 
 * @author snk
 *
 */
public class Utils
{
    private static ArrayList<String> pool = new ArrayList<String>();
  
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
	String[] vs;
	vs = s.split("\\(|\\)|\\*|\\+|\\-|\\^");
	System.out.println(Arrays.toString(vs));
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
    
    public static int getVariableCnt()
    {
	return pool.size();
    }
    
    
    
    public static void main(String[] args)
    {
	System.out.println(getVariableID("a"));
	System.out.println(getVariableID("b"));
	System.out.println(getVariableID("a"));
	System.out.println(getVariableID("c"));
	System.out.println((char)('a'+1));
	InputProcessor.inputRawStr();
	System.out.println(removeSpace(InputProcessor.getRawStr()));
	System.out.println(addOp(removeSpace(InputProcessor.getRawStr())));
	String sss = addOp(removeSpace(InputProcessor.getRawStr()));
	
	System.out.println(splitVariables(sss));
    }

}
