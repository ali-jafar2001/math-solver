
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static Scanner scanner;
	static String in;
	static String changed_in;
	static Tree tree;
	public static String[] numbers;
	static char[] array = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public static void main(String[] args) {
		main();
	}

	private static void main() {
		input();
		convert();
		createTree();
		evulate();
		prefix();
		infix();
		postfix();
	}

	private static void infix() {
		tree.inorder(tree.root);
		System.out.println();
	}

	private static void prefix() {
		tree.preOrder(tree.root);
		System.out.println();
	}

	private static void postfix() {
		tree.postOrder(tree.root);
	}

	private static void format(double d) {
		double l = d;
		if (d < 0)
			d = d * -1;
		long i = (long) d;
		long j = i * 1000;
		double m = d * 1000;
		long ashar = (long) m - j;
		ashar += 5;
		ashar /= 10;
		if (ashar >= 100) {
			i++;
			if (l < 0)
				System.out.print('-');
			System.out.print(i + ".00");
		} else if (ashar < 10) {
			if (l < 0)
				System.out.print('-');
			System.out.print(i + ".0" + ashar);
		} else {
			if (l < 0)
				System.out.print('-');
			System.out.print(i + "." + ashar);
		}
//		DecimalFormat df = new DecimalFormat();
//		df.setMaximumFractionDigits(2);
//		System.out.print(df.format(d));
	}

	private static void evulate() {
		format(tree.evulate(tree.root));
		System.out.println();
	}

	private static void createTree() {
		tree = new Tree();
		tree.insert(changed_in);
	}

	private static void convert() {
		String str = in;
		int j = 0;
		int k = 0;
		numbers = new String[50];
		int i = 0;
		while (i < in.length()) {
			if (isnumber(in.charAt(i))) {
				String st = "";
				int t = i;
				while (i < in.length() && isnumber(in.charAt(i))) {
					i++;
				}
				int s = i;
				char c = array[k];
//				System.out.println("c= " + c);
				st = in.substring(t, s);
//				System.out.println("st= " + st);
				numbers[j] = st;
				int k1 = firstandis(str, st);
				int k2 = k1 + st.length();
				String temp1 = "";
				temp1 = str.substring(0, k1);
//				System.out.println("temp1= " + temp1);
				temp1 = temp1 + c;
				String temp2 = "";
				temp2 = str.substring(k2);
//				System.out.println("temp2= " + temp2);
				str = temp1.concat(temp2);
//				System.out.println("str= " + str);
				j++;
				i--;
				k++;
			}
			i++;
		}
		changed_in = removeSpaces(str);
	}

	private static String removeSpaces(String in) {
		String st = in.trim();
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < st.length(); i++) {
			if (st.charAt(i) == ' ')
				list.add(i);
		}
		int counter = 0;
		for (int i = 0; i < list.size(); i++) {
			list.set(i, list.get(i) - counter);
			counter++;
		}
		for (int i = 0; i < list.size(); i++) {
			String temp1 = st.substring(0, list.get(i));
			String temp2 = st.substring(list.get(i) + 1);
			st = temp1.concat(temp2);
		}
		return st;
	}

	private static int firstandis(String big, String small) {
		for (int i = 0; i < big.length(); i++) {
			if (small.charAt(0) == big.charAt(i))
				return i;
		}
		return 0;
	}

	private static boolean isnumber(char c) {
		if (c >= '0' && c <= '9' || c == '.')
			return true;
		return false;
	}

	private static void input() {
		scanner = new Scanner(System.in);
		in = scanner.nextLine();
	}

}

class NodeStack {
	private Node[] memory;
	private int top;

	public NodeStack(int max) {
		memory = new Node[max];
		top = -1;
	}

	public boolean isEmpty() {
		if (top == -1)
			return true;
		return false;
	}

	public void push(Node node) {
		top++;
		memory[top] = node;
	}

	public Node pop() {
		if (isEmpty())
			return null;
		Node node = memory[top];
		memory[top] = null;
		top--;
		return node;
	}
}

class CharStack {
	private char[] memory;
	private int top;

	public CharStack(int max) {
		top = -1;
		memory = new char[max];
	}

	public boolean isEmpty() {
		if (top == -1)
			return true;
		return false;
	}

	public void push(char in) {
		top++;
		memory[top] = in;
	}

	public char pop() {
		char c = memory[top];
		memory[top] = ' ';
		top--;
		return c;
	}
}

class Tree {
	Node root;
	Conversion c;

	public Tree() {
		root = null;
	}

	public double evulate(Node n) {
		if (isoperand(n))
			return n.number;
		if (n.data == '+')
			return evulate(n.left) + evulate(n.right);
		if (n.data == '-')
			return evulate(n.left) - evulate(n.right);
		if (n.data == '*')
			return evulate(n.left) * evulate(n.right);
		if (n.data == '/')
			return evulate(n.left) / evulate(n.right);
		if (n.data == '^')
			return Math.pow(evulate(n.left), evulate(n.right));
		if (n.data == '_')
			return -1 * evulate(n.right);
		return 0;
	}

	public boolean isoperand(Node n) {
		if ((n.data >= 'a' && n.data <= 'z') || (n.data >= 'A' && n.data <= 'Z'))
			return true;
		return false;
	}

	public void insert(String s) {
		c = new Conversion(s);
		s = c.intopost();
		NodeStack stack = new NodeStack(s.length());
		s = s + "#";
		int i = 0;
		char symbol = s.charAt(i);
		Node newnode;
		while (symbol != '#') {
			if (symbol >= 'A' && symbol <= 'Z') {
				newnode = new Node(symbol);
				int temp = (int) symbol;
				temp = temp - 65;
				newnode.number = (Double.parseDouble(Main.numbers[temp]));
				newnode.operand = Main.numbers[temp];
				stack.push(newnode);
			} else if (symbol >= 'a' && symbol <= 'z') {
				newnode = new Node(symbol);
				int temp = (int) symbol;
				temp = temp - 71;
				newnode.number = (Double.parseDouble(Main.numbers[temp]));
				newnode.operand = Main.numbers[temp];
				stack.push(newnode);
			} else if (symbol == '+' || symbol == '-' || symbol == '/' || symbol == '*' || symbol == '^') {
				Node ptr1 = stack.pop();
				Node ptr2 = stack.pop();
				newnode = new Node(symbol);
				newnode.left = ptr2;
				newnode.right = ptr1;
				stack.push(newnode);
			} else if (symbol == '_') {
				newnode = new Node(symbol);
				Node ptr1 = stack.pop();
				newnode.right = ptr1;
				stack.push(newnode);
			}
			symbol = s.charAt(++i);
		}
		root = stack.pop();
	}

	public void postOrder(Node localRoot) {
		if (localRoot != null) {
			postOrder(localRoot.left);
			postOrder(localRoot.right);
			localRoot.display();
			System.out.print(" ");
		}
	}

	public void preOrder(Node localRoot) {
		if (localRoot != null) {
			localRoot.display();
			System.out.print(" ");
			preOrder(localRoot.left);
			preOrder(localRoot.right);
		}
	}

	public void inorder(Node localRoot) {
		if (isoperand(localRoot))
			localRoot.display();
		if (localRoot.data == '+' || localRoot.data == '-' || localRoot.data == '*' || localRoot.data == '/'
				|| localRoot.data == '^') {
			System.out.print("( ");
			inorder(localRoot.left);
			System.out.print(" ");
			localRoot.display();
			System.out.print(" ");
			inorder(localRoot.right);
			System.out.print(" )");
		}
		if (localRoot.data == '_') {
			System.out.print("( ");
			localRoot.display();
			System.out.print(" ");
			inorder(localRoot.right);
			System.out.print(" )");
		}
	}

//	private boolean isoperand(char t) {
//		if (t >= '0' && t <= '9' || t == '.')
//			return true;
//		return false;
//	}
}

class Node {
	char data;
	Node left;
	Node right;
	Double number;
	String operand;

	public Node(char data) {
		this.data = data;
	}

	public void display() {
		if (data == '_')
			System.out.print('-');
		else if (data == '-' || data == '*' || data == '+' || data == '/' || data == '^')
			System.out.print(data);
		else
			System.out.print(operand);
	}
}

class Conversion {
	private CharStack stack;
	private String in;
	String out = "";

	public Conversion(String str) {
		in = str;
		stack = new CharStack(str.length());
	}

	public String intopost() {
		for (int i = 0; i < in.length(); i++) {
			char ch = in.charAt(i);
			switch (ch) {
			case ' ':
				break;
			case '+':
				gotOperator(ch, 1);
				break;
			case '-':
				int prec = prec(in, i);
				char ch2 = '-';
				if (prec == 4)
					ch2 = '_';
				gotOperator(ch2, prec);
				break;
			case '*':
			case '/':
				gotOperator(ch, 2);
				break;
			case '^':
				gotOperator(ch, 3);
				break;
			case '(':
				stack.push(ch);
				break;
			case ')':
				gotParenthesis();
				break;
			default:
				out = out + ch;
			}
		}
		while (!stack.isEmpty())
			out = out + stack.pop();
		return out;
	}

	private int prec(String st, int i) {
		if (i == 0)
			return 4;
		char c = st.charAt(i - 1);
//		if (i == 1 && c == ' ')
//			return 4;
//		if (i > 1 && c == ' ') {
//			c = st.charAt(i - 2);
//			if (c == ' ' && i > 2)
//				c = st.charAt(i - 3);
//		}
		if (c == '(' || c == '-' || c == '+' || c == '*' || c == '/' || c == '^' || c == '_')
			return 4;
		return 1;
	}

	private void gotParenthesis() {
		while (!stack.isEmpty()) {
			char c = stack.pop();
			if (c == '(')
				break;
			else
				out = out + c;
		}
	}

	private void gotOperator(char opthis, int prec1) {
		while (!stack.isEmpty()) {
			char opTop = stack.pop();
			if (opTop == '(') {
				stack.push(opTop);
				break;
			} else {
				int prec2;
				if (opTop == '+' || opTop == '-')
					prec2 = 1;
				else if (opTop == '*' || opTop == '/')
					prec2 = 2;
				else if (opTop == '^')
					prec2 = 3;
				else
					prec2 = 4;
				
				if ((prec1 == prec2 && prec1 == 3) || (prec1 == prec2 && prec1 == 4)) {
					stack.push(opTop);
					break;
				}
				else {
					if (prec2 < prec1) {
						stack.push(opTop);
						break;
					} else
						out = out + opTop;
				}
				
			}
		}
		stack.push(opthis);
	}
}