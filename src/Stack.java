import java.util.ArrayList;

/**
 * Last edited @ 03/06/19 19:03
 * @author David Roy
 *
 */
public class Stack <T> {
	ArrayList <T>stack = new ArrayList <T>();
	private int size;
	
	public Stack() {
		size = 0;
	}
	
	public void push(T c) {
		stack.add(c);
	}
	
	public T pop() {
		if (!isEmpty()) {
			return stack.remove(stack.size() - 1);
		} 
		return null;
	}
	
	/**
	 * View the top stack element
	 * without popping.
	 * @return The top element
	 */
	public T get() {
		return stack.get(stack.size() - 1);
	}
	
	public boolean isEmpty() {
		return stack.size() <= 0;
	}
	
	public void clear() {
		stack.clear();
	}
	
	public int size() {
		return this.size;
	}
}
