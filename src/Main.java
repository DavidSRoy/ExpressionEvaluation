import java.util.HashMap;

public class Main {
	static String[] statements = { "(12+3)", "( 1 + 3 ) * ( 2 - 1)", "{ 2 + 3 } * (2 - 1 )", "(6 * 3) - (24 - 12)" };
	static HashMap<Character, Character> pairs = new HashMap<>();
	static HashMap<String, String> errors = new HashMap<>(); // CODE = error message
	static HashMap<Character, Integer> precedence = new HashMap<Character, Integer>(); // operator precedence map
	static int j; // character index;
	public static final boolean DEBUG_MODE = false;

	public static void main(String[] args) {
		setErrorMessages();
		setStatements();
		setPrecedence();
		setPairs();

		for (int i = 0; i < statements.length; i++) {
			System.out.print(statements[i] + " = ");
			checkSyntax(i);
			String postfix = convertToPostfix(i);
			int result = evaluation(postfix);
			System.out.print(result);
			System.out.println();

		}

	}

	private static void setPrecedence() {
		precedence = new HashMap<Character, Integer>();
		precedence.put('+', 0);
		precedence.put('-', 0);
		precedence.put('*', 1);
		precedence.put('/', 1);
		precedence.put('%', 1);

	}

	private static void setStatements() {
		// TODO Auto-generated method stub

	}

	public static void setErrorMessages() {
		errors.put("BRAC", "Syntax Error: Brackets do not match!");
		errors.put("PRNS", "Syntax Error: Parentheses do not match!");
	}

	public static void setPairs() {
		pairs.put(')', '(');
		pairs.put('}', '{');
	}

	public static void printError(String statement, int location, String code) {
		System.out.println(errors.get(code));
		System.out.println(statement);
		for (int i = 0; i < location; i++) {
			System.out.print(" ");
		}

		System.out.println("^");

		System.out.println();
	}

	public static int extractOperand(String statement) {
		char c = statement.charAt(j);
		String s = "" + c;
		boolean done = false;
		while (!done && j < statement.length() - 1) {
			j++;
			c = statement.charAt(j);
			if (c >= '0' && c <= '9') {
				s = s + c;
			} else {
				j--;
				done = true;
			}
		} // end of while
		c = 0;
		int x = Integer.parseInt(s); // convert to integer
		if (DEBUG_MODE) {
			System.out.println("integer : " + x);
		}

		return x;
	}

	private static boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
	}

	public static void checkSyntax(int i) {

		Stack<Character> stack = new Stack<Character>();
		if (DEBUG_MODE) {
			System.out.println("Checking Syntax of Statement " + i);
			System.out.println(statements[i]);
			System.out.println();
		}

		String statement = statements[i];

		for (j = 0; j < statement.length(); j++) {
			char c = statement.charAt(j);

			String s = "" + c;
			if (c >= '0' && c <= '9') {
				extractOperand(statement);
			} else { // not (c>='0' && c<='9')
				if (DEBUG_MODE)
					System.out.println("String : " + s);
			}

			if (c == '(' || c == '{') {
				stack.push(c);
			}

			if (c == ')') {
				char popC = stack.pop();
				if (popC != pairs.get(c)) {
					printError(statements[i], j, "PRNS");
				}
			}

			if (c == '}') {
				char popC = stack.pop();
				if (popC != '{') {
					printError(statements[i], j, "BRAC");
				}
			}
		}

		if (DEBUG_MODE)
			System.out.println("Stage 1: Complete");
	}

	public static String convertToPostfix(int i) {
		Stack<Character> stack = new Stack<Character>();
		if (DEBUG_MODE) {
			System.out.println("Converting Infix to PostFix of Statement " + i);
			System.out.println(statements[i]);
			System.out.println();
		}
		String statement = statements[i];
		String postFix = "";

		for (j = 0; j < statement.length(); j++) {
			char c = statement.charAt(j);
			String s = "" + c;
			if (c >= '0' && c <= '9') {
				postFix += extractOperand(statement); // print operand to result string
				postFix += " "; // to separate operands
			} else { // not (c>='0' && c<='9')
				if (c == '(' || c == '{') {
					stack.push(c);
				}

				if (c == ')' || c == '}') {
					char x = ' ';
					// print operands
					while (!stack.isEmpty() && x != pairs.get(c)) {
						x = stack.pop();
						if (x != pairs.get(c)) {
							postFix += x + " ";
						}
					}
				}

				if (isOperator(c)) {
					while (!stack.isEmpty() && isOperator(stack.get())
							&& precedence.get(c) <= precedence.get(stack.get())) {
						postFix += stack.pop();
					}

					stack.push(c);
				}
			}
		}

		while (!stack.isEmpty()) {
			postFix += stack.pop();
		}

		if (DEBUG_MODE) {
			System.out.println("Postfix Expression for " + statement);
			System.out.println(postFix);
		}

		return (postFix);
	}

	public static int evaluation(String postfix) {
		Stack<Integer> stack = new Stack<Integer>();

		for (j = 0; j < postfix.length(); j++) {
			char c = postfix.charAt(j);
			if (isOperator(c)) {
				int num1 = stack.pop();
				int num2 = stack.pop();
				int result = evaluate(num1, num2, c);
				stack.push(result);
				if (DEBUG_MODE)
					System.out.println("Result: " + result);
			} else {

				if (c != ' ') { // ignore spaces
					int operand = extractOperand(postfix);
					stack.push(operand);
				}

			}

		}
		
		int answer = stack.pop();
		if (DEBUG_MODE)
			System.out.println("Final result: " + answer);
		return answer;

	}

	private static int evaluate(int a, int b, char operator) {
		switch (operator) {
		case '+':
			return b + a;
		case '-':
			return b - a;
		case '/':
			return b / a;
		case '*':
			return b * a;
		case '%':
			return b % a;
		default:
			throw new IllegalArgumentException("Invalid Operation");
			// return (Integer) null;
		}
	}
}
