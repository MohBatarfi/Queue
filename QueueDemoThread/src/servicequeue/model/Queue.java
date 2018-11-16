package servicequeue.model;

import java.util.LinkedList;

/**
 * @author Mohammed Batarfi
 * @author Heyley Gatewood
 */
@SuppressWarnings("hiding")
public abstract class Queue<Customer> {
	
	protected LinkedList<Customer> myLine;

	public Queue() {
		myLine = new LinkedList<Customer>();
	}
	
	
	
	public abstract boolean empty();
	
	public abstract void enqueue(Customer o);
	
	public abstract Customer peek();
	
	public abstract Customer dequeue();
	
	public abstract LinkedList<Customer> getMyLine();
	
	public abstract void setMyLine(LinkedList<Customer> line);
	
//	public abstract void insertCustomer(Customer customer);
//	
//	public abstract Customer serveCustomer();



}
