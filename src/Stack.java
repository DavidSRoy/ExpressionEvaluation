
/**
 * 
 * @author David Roy
 *
 */
public class Stack {
	public char[] stack = new char[40];
	private int stackTop;
	
	public Stack() {
		stackTop = -1;
	}
	
	public void push(char c) {
		//System.out.println(c);
		
		stack[++stackTop] = c;
		//System.out.println("StackTop : " + stackTop);
	}
	
	public char pop() {
		return stack[stackTop--];
	}
	
	public boolean isEmpty() {
		return stackTop < 0;
	}
}
