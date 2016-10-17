package com.awesome;

import java.util.Arrays;

/**
 * @author snk Class Node 表达式树的节点类 l,r是左右子树指针
 *         data是该节点数据，对于叶节点是变量名或者数字，内部节点是操作符(+、-、*、^)，括号用来构造树结构
 * 
 */

/* 为了完成实验三而作的一点修改，请无视*/
/* 为了实验三做的第一个修改*/
/*C4上的第二个修改*/
class Node
{
    String data;
    Node l, r;

    Node()
    {
    }

    Node(String data, Node l, Node r)
    {
	this.data = data;
	this.l = l;
	this.r = r;
    }
}

/**
 * @author snk Class ExpressionTree 表达式树类 methods: buildExpTree():
 *         根据字符串s的[x,y)范围的子串来构造表达式树的子树，找到“最后计算”的操作符，
 *         以此为划分递归构造左右子树，直到变量或数字结点(使用正则表达式匹配)
 *
 *         postTravel(): 后根遍历，测试表达式树是否构建正确
 * 
 *         calcExpTree(): 计算表达式树根节点的Expression结果，其本质是后根遍历，先递归计算左右子树，
 *         再利用Expression类的相关计算方法计算根节点
 * 
 * 
 *         *main()*: 全局主方法
 */
public class ExpressionTree
{

    private boolean assignMode = false;

    public boolean isAssignMode()
    {
	return assignMode;
    }

    public void setAssignMode(boolean assignMode)
    {
	this.assignMode = assignMode;
    }

    /**
     * 
     * @param s
     *            构造表达式树所用的字符串，已被处理(去空格、加乘号、补0等)
     * @param x
     *            所考虑s的子串的左边界(包含)
     * @param y
     *            所考虑s的子串的右边界(不包含)
     * @return 表达式树的根节点
     */
    public Node buildExpTree(String s, int x, int y)
    {
	/*
	 * 寻找最后计算的操作符，如3+2*5，+号就是最后计算的操作符，将该操作符作为根节点， 以该操作符的位置(denoted to
	 * lastPos)划分s[x,y)为s[x,lastPos)和s[lastPos+1,y)，递归构造左右子树
	 */

	// p表示当前是否在括号内，遇到左括号p++，遇到右括号p--，因此p=0时，意味着当前在括号外，括号内的一定不是最后计算的
	// c1表示括号外最右边的加法或减法，由于+和-同优先级，且从左至右以此计算，因此值为最后计算的+或者-的下标位置
	// c2表示括号外最右边的乘法的下标位置
	// c3表示括号外最右边的幂操作符(^)的下标位置
	// 三者优先级 c3 > c2 > c1

	int c1 = -1, c2 = -1, c3 = -1, p = 0;
	String sub = s.substring(x, y);
	if (Utils.isVariable(sub) || Utils.isDouble(sub)) // 利用工具类Utils的相关方法，利用正则表达式匹配s[x,y)内容
	{
	    if (assignMode && Utils.isVariable(sub) && !Utils.getAssignMap()[Utils.getVariableID(sub)].equals(""))
		return new Node(Utils.getAssignMap()[Utils.getVariableID(sub)], null, null); // 构造叶子节点，赋值状态，如果有变量被赋值，替换成该值
	    else
		return new Node(s.substring(x, y), null, null); // 构造叶子节点，非赋值状态
	}
	for (int i = x; i < y; i++) // 从左至右依次考虑
	{
	    switch (s.charAt(i))
	    {
	    case '(':
		p++;
		break;
	    case ')':
		p--;
		break;
	    case '+':
	    case '-':
		if (p == 0)
		    c1 = i;
		break; // 如果当前在括号外(p=0),更新c1，以下类似
	    case '*':
		if (p == 0)
		    c2 = i;
		break;
	    case '^':
		if (p == 0)
		    c3 = i;
		break;
	    }
	}

	// 按照优先级，从最低开始考虑，寻找最后运算的操作符位置
	if (c1 < 0)
	    c1 = c2; // c1此时保存着 考虑+\-\*运算符时最后计算的位置
	if (c1 < 0)
	    c1 = c3; // c1此时保存着 考虑+\-\*\^运算符时最后计算的位置
	// 如果c1还是-1，意味着没有在括号外的操作符，即此时表达式形为 (1+2*3)，则去掉括号，递归计算
	if (c1 < 0)
	    return buildExpTree(s, x + 1, y - 1);
	return new Node(s.charAt(c1) + "", buildExpTree(s, x, c1), buildExpTree(s, c1 + 1, y)); // 递归构造左右子树，返回根节点
    }

    /**
     * 后根遍历，打印各个节点的data数据（测试用）
     * 
     * @param root
     *            当前根节点
     */
    public void postTravel(Node root)
    {
	if (root == null)
	    return;
	postTravel(root.l);
	postTravel(root.r);
	System.out.print(root.data + "  ");
    }

    /**
     * 表达式树的求值化简
     * 
     * @param root
     *            当前根节点
     * @return 计算的根节点对应的Expression
     */
    public Expression calcExpTree(Node root)
    {
	Expression l, r;
	if (root.l == null && root.r == null) // 叶节点
					      // 直接由变量名或者数字(作为系数)构造多项式Expression对象
	    return new Expression(new Monomial(root.data));
	l = calcExpTree(root.l);
	r = calcExpTree(root.r);
	if (root.data.equals("*"))
	    return l.multiply(r).simplify(); // 如果当前运算是*,调用Expression的multiply方法，返回一个未经化简合并的积Expression，
					     // 然后调用simplify()进行化简(即括号展开，合并同类项，处理相关细节等)，以下+、-和^均类似
	else if (root.data.equals("+"))
	    return l.add(r).simplify();
	else if (root.data.equals("-"))
	    return l.sub(r).simplify();
	else if (root.data.equals("^"))
	    return l.power(r).simplify();
	else
	    return null; // 其他情况，便于扩展，提高 robust
    }

    /**
     * 注意命令格式: !simplify var1=value1 var2=value2 ... 不带参数的 !simplify
     * 命令将打印出当前表达式本身 (注意var=value中间不能有任何空格，不同k=v对用1个空格分割) !d/dvar 或者 !d/d var
     * 均可对变量var求导 !exit 或者 !quit 可以退出程序
     * 
     * 对于表达式的输入限制宽松，可以任意空白字符分隔，
     * 变量名可以取所有满足Java变量名要求的变量(以字母或下划线_开头，以数字、字母和下划线组成的字符串，长度不限) 操作符包括 + - * ^ (
     * )
     * 
     * 所有运算结果都经过化简处理，对于系数1和系数0经过某些细节处理，
     * 对于*的省略也在数字和变量之间，括号和括号之间，数字和括号之间、变量和括号之间处理过
     */
    public static void main(String[] args)
    {
	ExpressionTree tree = new ExpressionTree(); // 构建表达式树
	String lastExp = null; // 上次输入的表达式，指定!simplify和!d/d var的作用的表达式
	Expression lastResult = null; // 上次表达式的化简Expression结果
	boolean commandMode = false; // 当前输入是否是!开头的命令
	while (true)
	{
	    commandMode = false;
	    tree.setAssignMode(false);
	    InputProcessor.inputRawExp(); // 输入原始字符串
	    String input = InputProcessor.getRawExp();
	    if (input.startsWith("!")) // 如果以!开头，则是命令
	    {
		try
		{
		    commandMode = true;
		    if (input.startsWith("!simplify"))
		    {
			if (lastExp == null)
			{
			    System.out.println("请先输入表达式!");
			    continue;
			}
			else
			{
			    tree.setAssignMode(true);
			    Arrays.fill(Utils.getAssignMap(), ""); // 清空映射表
			    String replacedExp = lastExp;
			    String[] assigns = input.split(" "); // 以空格分割字符串，提取出被赋值变量及其所赋的值，如x=1
								 // y=2则被分割开来
			    boolean notAppear = false; // 变量是否未出现过
			    StringBuffer msg = new StringBuffer(); // 构造错误信息
			    msg.append("变量");
			    for (int i = 1; i < assigns.length; i++)
			    {
				String[] kv = assigns[i].split("=");
				if (!Utils.isVariableAppear(kv[0]))
				{
				    if (!notAppear)
				    {
					msg.append(kv[0]);
					notAppear = true;
				    }
				    else
					msg.append("、" + kv[0]);
				}
				else
				    Utils.getAssignMap()[Utils.getVariableID(kv[0])] = kv[1]; // 代入值
			    }
			    if (notAppear)
			    {
				msg.append("未出现!");
				System.out.println(msg);
				continue;
			    }
			    InputProcessor.setProcessedExp(replacedExp);
			}
		    }
		    else if (input.startsWith("!d/d"))
		    {
			if (lastResult == null)
			{
			    System.out.println("请先输入表达式!");
			    continue;
			}
			String v = input.substring(4, input.length()).trim(); // 获取求导变量
			if (!Utils.isVariableAppear(v))
			    System.out.println("变量" + v + "未出现!");
			else
			{
			    System.out.println(lastResult.derivative(v).simplify());
			}
			continue;
		    }
		    else if (input.equals("!exit") || input.equals("!quit"))
		    {
			System.out.println("退出程序...");
			System.exit(0);
		    }
		    else
		    {
			System.out.println("命令不存在!");
			continue;
		    }
		}
		catch (Exception e)
		{
		    System.out.println("命令有误!");
		    continue;
		}
	    }
	    Expression result = null;
	    try
	    {
		if (!commandMode)
		    InputProcessor.preprocess();
		String s = InputProcessor.getProcessedExp();
		Node root = tree.buildExpTree(s, 0, s.length()); // 构建表达式树
		result = tree.calcExpTree(root); // 计算结果
		// tree.postTravel(root);
		// System.out.println("\n= " + result);
		System.out.println(result);

	    }
	    catch (Exception e)
	    {
//		e.printStackTrace();
		System.out.println("输入有误!"); // 输入不符合格式
	    }
	    if (!commandMode)
	    {
		lastExp = InputProcessor.getProcessedExp(); // 记录上次表达式的相关信息
		lastResult = result;
	    }
	}
    }

}
