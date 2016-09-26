package com.awesome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author snk Utils 实用工具类，包含一些静态的工具性质的方法
 */
public class Utils
{
    private static ArrayList<String> pool = null; // 变量hash内存池，保存着所有当前出现过的变量
    private static Set<String> set = null; // 保存变量集合
    private static int variableCnt = 0; // 变量个数

    private static String[] assignMap; // 赋值映射表，采用同样的hash

    public static String[] getAssignMap()
    {
	return assignMap;
    }

    public static boolean isDigit(char c) // 是否为数字字符
    {
	return Character.isDigit(c);
    }

    public static boolean isAlpha(char c) // 是否为字母字符
    {
	return Character.isAlphabetic(c);
    }

    public static boolean isOperator(char c) // 是否为操作符
    {
	if ("+-*^".indexOf(c) == -1)
	    return false;
	return true;
    }

    public static boolean isDouble(String s) // 是否是double
    {
	boolean isdouble = true;
	try
	{
	    Double ds = Double.parseDouble(s);
	    if (ds instanceof Double == false)
		isdouble = false;
	}
	catch (NumberFormatException e)
	{
	    isdouble = false;
	}
	return isdouble;
    }

    public static boolean doubleEquals(double a, double b) // double比较相等，调用内置比较函数，会将其转化为longBits再比较，避免浮点误差
    {
	return Double.valueOf(a).equals(Double.valueOf(b));
    }

    public static boolean isVariable(String s) // 判断是否是变量
    {
	return s.matches("^[A-Za-z_][A-Za-z0-9_]*$"); // 变量名规则：字母\下划线开头，由数字、字母和下划线组成
    }

    public static String removeSpace(String s) // 去掉多余空格和制表符
    {
	return s.replace(" ", "").replace("\t", "");
    }

    public static String addOperator(String s) // 将省略的*号补回来
    {
	StringBuffer sb = new StringBuffer();
	sb.append(s);
	for (int i = 0; i < sb.length() - 1; i++)
	{
	    // 数字和字母之间，非操作符和'('之间补*
	    if ((isDigit(sb.charAt(i)) && (isAlpha(sb.charAt(i + 1)) || sb.charAt(i + 1) == '_'))
		    || (!isOperator(sb.charAt(i)) && sb.charAt(i + 1) == '('))
	    {
		sb.insert(i + 1, "*");
	    }
	}
	return sb.toString();
    }

    public static void calcVariableCnt(String s) // 计算变量个数
    {
	String[] ss;
	ss = s.split("\\(|\\)|\\*|\\+|\\-|\\^"); // 根据操作符分割字符串，注意转义
	Set<String> deduplicate = new HashSet<String>();
	set = new HashSet<String>();
	pool = new ArrayList<String>();
	for (String v : ss) // 去重
	    deduplicate.add(v);
	for (String v : deduplicate)
	    if (isVariable(v)) // 加入变量
		set.add(v);
	variableCnt = set.size(); // 计算变量个数
	assignMap = new String[variableCnt]; // 赋值
    }

    public static boolean isVariableAppear(String v) // 变量是否出现过
    {
	return set.contains(v);
    }

    public static int getVariableCnt()
    {
	return variableCnt;
    }

    public static int getVariableID(String v) // 获取变量hash值，即Monomial类的times数组的对应的下标位置
    {
	int pos = pool.indexOf(v); // 如果存在，直接返回变量在pool池中的下标作为hash值
	if (pos != -1)
	    return pos;
	pool.add(v); // 否则，将变量放入pool池中，返回下标
	return pool.size() - 1;
    }

    public static String getVariableFromID(int id) // 根据变量的hash值，获得变量名
    {
	return pool.get(id);
    }

    public static String addZero(String s) // 加0,将正号+v和负号-v，处理成0+v和0-v，即将正负号转变为加减法，便于处理
    {
	s = s.replace("(+", "(0+").replace("(-", "(0-");
	if (s.charAt(0) == '+' || s.charAt(0) == '-')
	    s = "0" + s;
	return s;
    }

    public static String shortDouble(double d) // 如果d小数部分为0，则显示为整数形式，否则显示为小数形式，这样更为美观简洁
    {
	if (doubleEquals(Math.round(d) - d, 0))
	    return String.valueOf((long) d);
	return String.valueOf(d);
    }

    /**
     * 单元测试 测试工具函数
     * 
     * @param args
     */
    public static void main(String[] args)
    {
	ExpressionTree.main(args);
	/*
	System.out.println("*********************");
	System.out.println((char) ('a' + 1));
	// InputProcessor.inputRawStr();
	// System.out.println(removeSpace(InputProcessor.getRawStr()));
	// System.out.println(addOp(removeSpace(InputProcessor.getRawStr())));
	// String sss = addOp(removeSpace(InputProcessor.getRawStr()));
	//
	// System.out.println(Arrays.toString(splitVariables(sss)));
	// System.out.println(addZero(sss));
	System.out.println("....." + isDouble("3.1s.23"));
	System.out.println(isVariable("3a"));
	System.out.println(isVariable("aaasd1a3_sad"));
	System.out.println(isVariable("_asdasd1"));
	System.out.println(isVariable("a1111"));
	System.out.println(shortDouble(1.00));
	System.out.println(shortDouble(1.001));
	System.out.println(isOperator('*'));

	String instantStr = "20:33:24:34";
	String resultStr = instantStr.replaceAll("(\\d{2}):\\d{2}$", ":034$1");
	System.out.println(resultStr);
	System.out.println(Arrays.toString("a b	c".split(" |\\t")));
	*/
	
    }
}
