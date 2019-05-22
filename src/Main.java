
public class Main {
	public static void main(String[] args) {
		String[] statements = {
											"( 1 + 3 ) * ( 2 - 1)"
											};
		
		for (int i = 0; i < statements.length; i++) {
			Stack stack = new Stack();
			
			for (int j = 0; j < statements[i].length(); j++) {
				char c = statements[i].charAt(j);
				
				if (c == '(' || c == '{') {
					stack.push(c);
				}
				
				if (c == ')') {
					char popC = stack.pop();
					if (popC != '(') {
						throw new IllegalArgumentException("Parentheses not matching");
					}
				}
				
				if (c == '}') {
					char popC = stack.pop();
					if (popC != '{') {
						throw new IllegalArgumentException("Brackets not matching");
					}
				}
			}
		}
	}
}
