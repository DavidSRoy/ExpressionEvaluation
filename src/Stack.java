import java.util.ArrayList;

/**
 * @author David Roy
 * Last edited @ 06/07/19 09:15
 *
 */
public class Stack <T> {
	ArrayList <T>stack = new ArrayList <T>();

	/**
	 * Add an element to the top of the stack
	 * @param c
	 */
	public void push(T c) {
		stack.add(c);
	}
	
	/**
	 * Remove and return the top
	 * element of the stack.
	 * @return The top element of the stack
	 */
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
	
	/**
	 * 
	 * @return Is the stack is empty?
	 */
	public boolean isEmpty() {
		return this.size() <= 0;
	}
	
	/**
	 * Clear the stack
	 */
	public void clear() {
		stack.clear();
	}
	
	/**
	 * 
	 * @return Size of the stack
	 */
	public int size() {
		return stack.size();
	}
}
