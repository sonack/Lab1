package com.awesome;

import java.util.Scanner;

/**
 * 
 * 输入预处理类，整合包装Utils类的相关方法
 * @author snk
 *
 */
/* 为了实验三做的第三个修改*/
public class InputProcessor
{
    private static String rawExp;
    private static String processedExp;
    private static Scanner in = new Scanner(System.in);

    /**
     * 输入原始字符串
     */
    public static void inputRawExp()
    {
	System.out.print("> ");
	rawExp = in.nextLine();
    }

    public static String getRawExp()
    {
	return rawExp;
    }

    public static void setRawExp(String rawExp)
    {
	InputProcessor.rawExp = rawExp;
    }

    /**
     * 整合预处理操作
     */
    public static void preprocess()
    {
	processedExp = rawExp;
	processedExp = Utils.removeSpace(processedExp);
	processedExp = Utils.addOperator(processedExp);
	processedExp = Utils.addZero(processedExp);
	Utils.calcVariableCnt(processedExp);
    }

    public static String getProcessedExp()
    {
	return processedExp;
    }

    public static void setProcessedExp(String processedExp)
    {
	InputProcessor.processedExp = processedExp;
    }

    /**
     * 单元测试
     * 
     * @param args
     */
    public static void main(String[] args)
    {
	ExpressionTree.main(args);
//	InputProcessor.inputRawExp();
//	preprocess();
//	System.out.println(processedExp);
    }
}
