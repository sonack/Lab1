package com.awesome;

import java.util.Arrays;
import java.util.Scanner;

public class InputProcessor
{
    private static String rawInput;
    private static String processedInput;
    
    public static void inputRawStr()
    {
	Scanner in = new Scanner(System.in);
	rawInput = in.nextLine();
    }
    
    public static String getRawStr()
    {
	return rawInput;
    }
    
    public static void processInput()
    {
	processedInput = rawInput;
	processedInput = Utils.removeSpace(processedInput);
	processedInput = Utils.addOp(processedInput);
	processedInput = Utils.addZero(processedInput);
//	processedInput = Utils.replaceWithCharVariables(processedInput);
	
	
    }
    public static String getProcessedInput()
    {
	return processedInput;
    }
    
    
    
    public static void main(String[] args)
    {
       InputProcessor.inputRawStr();
       processInput();
    }

}
