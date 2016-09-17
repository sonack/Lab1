package com.awesome;

import java.util.Scanner;

public class InputProcessor
{
    private static String rawExp;
    private static String processedExp;
    private static Scanner in = new Scanner(System.in);
    
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
    
    public static void main(String[] args)
    {
       InputProcessor.inputRawExp();
       preprocess();
       System.out.println(processedExp);
    }
}
