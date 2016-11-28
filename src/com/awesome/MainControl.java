//主控类 控制类
package com.awesome;

import java.util.Arrays;

import javax.print.DocFlavor.INPUT_STREAM;
import javax.swing.tree.ExpandVetoException;

public class MainControl {

	public static void main(String[] args) {
		ExpressionTree tree = new ExpressionTree(); // 构建表达式树
		while (true) {
			boolean res = InputProcessor.input(tree);
			if (!res)
				continue;
			String s = InputProcessor.getProcessedExp();
			try {
				Node root = tree.buildExpTree(s, 0, s.length()); // 构建表达式树
				Expression result = tree.calcExpTree(root); // 计算结果
				System.out.println(result);
				if (!InputProcessor.isCommandMode()) {
					InputProcessor.setLastResult(result);
				}
			} catch (Exception e) {
				System.out.println("输入有误!");
			}

		}
	}
}
