import java.util.HashMap;

public class Main {
	static String[] statements = { "(12+3)", "( 1 + 3 ) * ( 2 - 1)", "( 2 + 3 } * { 2 - 1 )" };
	static HashMap<Character, Character> pairs = new HashMap<>();
	static HashMap<String, String> errors = new HashMap<>(); // CODE = error message

	public static void main(String[] args) {
		setErrorMessages();
		setPairs();

		for (int i = 0; i < statements.length; i++) {
			Stack<Character> stack = new Stack<Character>();
			System.out.println("Evaluating Statement " + i);
			System.out.println(statements[i]);
			System.out.println();
			String statement = statements[i];

			for (int j = 0; j < statements[i].length(); j++) {
				String s = "";
				char c = statement.charAt(j);
				s = s + c;
				if (c >= '0' && c <= '9') {
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
					System.out.println("integer : " + x);
				} else { // not (c>='0' && c<='9')
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


		}

		System.out.println("Expression Evaluation Stage 1: Complete");
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
}
