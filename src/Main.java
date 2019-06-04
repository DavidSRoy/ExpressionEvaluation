import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
/**
 * 
 * @author David Roy
 * Last edited @ 03/06/19 19:03
 *
 */
public class Main {
	static ArrayList<String> statements = new ArrayList<String>();
	static HashMap<Character, Character> pairs = new HashMap<>();
	static HashMap<String, String> errors = new HashMap<>(); // CODE = error message
	static HashMap<Character, Integer> precedence = new HashMap<Character, Integer>(); // operator precedence map
	static int j; // character index;
	public static final boolean DEBUG_MODE = false; //debugging boolean

	public static void main(String[] args) {
		setErrorMessages();
		setStatements();
		setPrecedence();
		setPairs();

		for (int i = 0; i < statements.size(); i++) {
			String statement = statements.get(i);
			System.out.print(statement + " = ");
			boolean isValid = false;
			
			//check if syntax is valid
			try {
				 isValid = isSyntaxValid(statement);
				 if (DEBUG_MODE) {
					 System.out.println("isValid :" +  isValid);
				 }
			} catch (IllegalArgumentException e) {
				e.getMessage();
			}
			
			//convert the statement to postfix and evaluate
			if (isValid) {
				String postfix = convertToPostfix(statement);
				int result = evaluation(postfix);
				System.out.print(result);
				System.out.println();
				System.out.println();
			}
		}

	}

	private static void setPrecedence() {
		precedence = new HashMap<Character, Integer>();
		precedence.put('+', 0);
		precedence.put('-', 0);
		precedence.put('*', 1);
		precedence.put('/', 1);
		precedence.put('%', 1);
		precedence.put('^', 2);
	}

	private static void setStatements() {
		statements.add("( 12+3 +4 + 5)");
		statements.add("(( 1 + 3 ) * ( 2 - 1)");
		statements.add("{ 2 + 3 } * ((2 - 1 )");
		statements.add("(6 * 3) - (24 - 12)}"); 
		statements.add("(6 * 3} - (24 - 12)}"); 
		statements.add("{{54 * 3} - {24 - 12)"); 
		statements.add("( 10 % 4 )");
		statements.add("10 + 4 - 4 * (3 * 4 ) + { 3 / 4 } - 50*100- (5 +1)");
		statements.add("1 + 2 * 3 - (42 +5 *2) % 43 / (34 * 43 *5) + 12"); 
		statements.add("( 1 + 3) * {2 - 1) ");
		statements.add("( 1 + 3 * { 2 – 1 )}");
		statements.add("( 1 + 3 * ( 2 – 1 )");
		statements.add("1 + 2 * 3 ^ 4 * 5 + 6");
		statements.add("2 ^ 4 ^ 2");

	}

	public static void setErrorMessages() {
		errors.put("BRAC", "Syntax Error: Brackets do not match!");
		errors.put("PRNS", "Syntax Error: Parentheses do not match!");
		errors.put("MSPR", "Syntax Error: Matching parentheses not found!");
		errors.put("MSBR", "Syntax Error: Matching brackets not found!");
		
	}

	public static void setPairs() {
		pairs.put(')', '(');
		pairs.put('}', '{');
	}

	/**
	 * Print an error message and show where the source
	 * of the error is.
	 * @param statement
	 * @param location  Index of the error
	 * @param code  Error code
	 */
	public static void printError(String statement, int location, String code) {
		System.out.println(errors.get(code));
		
		if (DEBUG_MODE)
			System.out.println(statement);
		
		for (int i = 0; i < location; i++) {
			System.out.print(" ");
		}

		System.out.println("^");

		System.out.println();
		
		//throw an exception to prevent evaluation of invalid statements 
		throw new IllegalArgumentException(errors.get(code));
	}

	/**
	 * Find the operand in the statement at j
	 * @param statement
	 * @return The operand at the location of interest (j);
	 */
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
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^';
	}

	public static boolean isSyntaxValid(String statement) {
		boolean isValid = true;
		Stack<Character> stack = new Stack<Character>();
		if (DEBUG_MODE) {
			System.out.println("Checking Syntax of Statement ");
			System.out.println(statement);
			System.out.println();
		}


		for (j = 0; j < statement.length(); j++) {
			char c = statement.charAt(j);

			String s = "" + c;
			
			//if c is a digit...
			if (c >= '0' && c <= '9') {
				extractOperand(statement);
			} else { // not (c>='0' && c<='9')
				if (DEBUG_MODE)
					System.out.println("String : " + s);
			}

			//push brackets and parentheses
			if (c == '(' || c == '{') {
				stack.push(c);
			}

			//When a parenthesis is found, find the corresponding pair.
			if (c == ')') {
				if (stack.isEmpty()) {  //if the stack is empty, there is an extra or missing parentheses
					printError(statement, j, "MSBR");
				}
				char popC = stack.pop();
				if (popC != pairs.get(c)) {
					printError(statement, j, "PRNS");
					isValid = false;
				}
			}

			//When a brackets is found, find the corresponding pair.
			if (c == '}') {
				if (stack.isEmpty()) {  //if the stack is empty, there is an extra or missing bracket
					printError(statement, j, "MSBR");
				}
				char popC = stack.pop();
				if (popC != '{') {
					printError(statement, j, "BRAC");
					isValid = false;
				}
			}
		}

		//if the stack is not empty by the end, there are extra chars remaining.
		if (!stack.isEmpty()) {
			String code = "";
			switch (stack.pop()) {
			case '{' : code = "MSBR";
				break;
			case '}' : code = "MSBR";
				break;
			case '(' : code = "MSPR";
				break;
			case ')' : code = "MSPR";
				break;
			default: break;
			}
			
			printError(statement, 0, code);
		}
		
		if (DEBUG_MODE)
			System.out.println("Stage 1: Complete");
		return isValid;
	}

	
	public static String convertToPostfix(String statement) {
		Stack<Character> stack = new Stack<Character>();
		if (DEBUG_MODE) {
			System.out.println("Converting Infix to PostFix of Statement ");
			System.out.println(statement);
			System.out.println();
		}

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

				//push operators, pop operators of higher precedence
				if (isOperator(c)) {
					while (!stack.isEmpty() && isOperator(stack.get())
							&& precedence.get(c) <= precedence.get(stack.get())) {
						postFix += stack.pop();
					}

					stack.push(c);
				}
			}
		}

		//pop remaining elements
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
		case '^':
			return (int) Math.pow(b, a);
		default:
			
			throw new IllegalArgumentException("Invalid Operation");
			// return (Integer) null;
		}
	}
}
