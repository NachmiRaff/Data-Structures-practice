public interface Stack<T>{
	/*Tests if this stack is empty.
	 *Returns:
	 *true if and only if this stack contains no items; false otherwise.
	 */
	boolean empty();

	/*Looks at the object at the top of this stack without removing it from the stack.
	 *Returns:
	 *the object at the top of this stack (the last item of the Vector object).
	 */
	T peek();

	/*Removes the object at the top of this stack and returns that object as the value of this function.
	 *Returns:
	 *The object at the top of this stack (the last item of the Vector object).
	 */
	T pop();

	/*
	 *Pushes an item onto the top of this stack. This has exactly the same effect as:
	 *addElement(item)
	 *Parameters:
	 *item - the item to be pushed onto this stack.
	 *Returns:
	 *the item argument.
	 */
	T push(T thing);

	/*Returns the 1-based position where an object is on this stack. 
	 *If the object o occurs as an item in this stack, this method returns the distance from the top of the stack of the occurrence nearest the top of the stack; 
	 *the topmost item on the stack is considered to be at distance 1. The equals method is used to compare o to the items in this stack.
	 *Parameters:
	 *o - the desired object.
	 *Returns:
	 *the 1-based position from the top of the stack where the object is located; the return value -1 indicates that the object is not on the stack.
	 */
	int search(Object o);

}