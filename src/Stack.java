import java.util.ArrayList;

/**
 * 
 * @author David Roy
 *
 */
public class Stack <T> {
	ArrayList <T>stack = new ArrayList <T>();
	
	public Stack() {
		
	}
	
	public void push(T c) {
		//System.out.println(c);
		stack.add(c);
	}
	
	public T pop() {
		if (!isEmpty()) {
			return stack.remove(stack.size() - 1);
		}
		
	}
	
	public boolean isEmpty() {
		return stack.size() < 0;
	}
}
