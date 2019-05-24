import java.util.ArrayList;

/**
 * 
 * @author David Roy
 *
 */
public class Stack <T> {
	ArrayList <T>stack = new ArrayList <T>();
	private int stackTop;
	
	public Stack() {
		stackTop = -1;
	}
	
	public void push(T c) {
		//System.out.println(c);
		stack.add(c);
		stackTop++;
		//System.out.println("StackTop : " + stackTop);
	}
	
	public T pop() {
		return stack.get(stackTop--);
	}
	
	public boolean isEmpty() {
		return stackTop < 0;
	}
}
