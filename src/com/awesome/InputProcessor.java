// 输入类 高层 边界类 
package com.awesome;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 * 输入预处理类，整合包装Utils类的相关方法
 * 
 * @author snk
 *
 */
public class InputProcessor {
	private static String rawExp;
	private static String processedExp;
	private static Scanner in = new Scanner(System.in);
	private static String lastExp = null; // lastExp 上次输入的表达式，指定!simplify和!d/d
											// var的作用的表达式

	private static Expression lastResult = null; // 上次表达式的化简Expression结果

	public static Expression getLastResult() {
		return lastResult;
	}

	public static void setLastResult(Expression lastResult) {
		InputProcessor.lastResult = lastResult;
	}

	public static boolean isCommandMode() {
		return commandMode;
	}

	public static void setCommandMode(boolean commandMode) {
		InputProcessor.commandMode = commandMode;
	}

	private static boolean commandMode; // 当前输入是否是!开头的命令

	/**
	 * *
	 *
	 * 输入原始字符串
	 */

	public static void inputRawExp() {
		System.out.print("> ");
		rawExp = in.nextLine();
	}

	public static String getRawExp() {
		return rawExp;
	}

	public static void setRawExp(String rawExp) {
		InputProcessor.rawExp = rawExp;
	}

	/**
	 * 整合预处理操作
	 */
	public static void preprocess() {
		processedExp = rawExp;
		processedExp = BoundProcessor.removeSpace(processedExp);
		processedExp = BoundProcessor.addOperator(processedExp);
		processedExp = BoundProcessor.addZero(processedExp);
		BoundProcessor.calcVariableCnt(processedExp);
	}

	public static String getProcessedExp() {
		return processedExp;
	}

	public static void setProcessedExp(String processedExp) {
		InputProcessor.processedExp = processedExp;
	}

	public static boolean input(ExpressionTree tree) // tree 表达式树对象
	{
		commandMode = false;
		tree.setAssignMode(false);
		inputRawExp(); // 输入原始字符串
		if (rawExp.startsWith("!")) // 如果以!开头，则是命令
		{
			try {
				commandMode = true;
				if (rawExp.startsWith("!simplify")) {
					if (lastExp == null) {
						System.out.println("请先输入表达式!");
						return false;
					} else {
						tree.setAssignMode(true);
						Arrays.fill(BoundProcessor.getAssignMap(), ""); // 清空映射表
						String replacedExp = lastExp;
						String[] assigns = rawExp.split(" "); // 以空格分割字符串，提取出被赋值变量及其所赋的值，如x=1
						// y=2则被分割开来
						boolean notAppear = false; // 变量是否未出现过
						StringBuffer msg = new StringBuffer(); // 构造错误信息
						msg.append("变量");
						for (int i = 1; i < assigns.length; i++) {
							String[] kv = assigns[i].split("=");
							if (!BoundProcessor.isVariableAppear(kv[0])) {
								if (!notAppear) {
									msg.append(kv[0]);
									notAppear = true;
								} else
									msg.append("、" + kv[0]);
							} else
								BoundProcessor.getAssignMap()[BoundProcessor.getVariableID(kv[0])] = kv[1]; // 代入值
						}
						if (notAppear) {
							msg.append("未出现!");
							System.out.println(msg);
							return false;
						}
						setProcessedExp(replacedExp);
					}
				} else if (rawExp.startsWith("!d/d")) {
					if (lastResult == null) {
						System.out.println("请先输入表达式!");
						return false;
					}
					String v = rawExp.substring(4, rawExp.length()).trim(); // 获取求导变量
					if (!BoundProcessor.isVariableAppear(v))
						System.out.println("变量" + v + "未出现!");
					else {
						System.out.println(lastResult.derivative(v).simplify());
					}
					return false;
				} else if (rawExp.equals("!exit") || rawExp.equals("!quit")) {
					System.out.println("退出程序...");
					System.exit(0);
				} else {
					System.out.println("命令不存在!");
					return false;
				}
			} catch (Exception e) {
				System.out.println("命令有误!");
				return false;
			}
		}

		try {
			if (!commandMode)
				preprocess();
		} catch (Exception e) {
			System.out.println("输入有误!"); // 输入不符合格式
			return false;
		}

		if (!commandMode) {
			lastExp = processedExp; // 记录上次表达式的相关信息
		}
		return true;
	}

	/**
	 * 单元测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ExpressionTree.main(args);
		// InputProcessor.inputRawExp();
		// preprocess();
		// System.out.println(processedExp);
	}
}
