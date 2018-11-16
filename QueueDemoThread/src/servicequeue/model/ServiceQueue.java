package servicequeue.model;

import java.util.LinkedList;

/**
 * 
 * @author Mohammed Batarfi
 * @author Heyley Gatewood
 *
 */
public class ServiceQueue extends Queue<Customer> {

	// private static UniformCashier cashier;
	// private static ServiceQueueManager manager;
	
	
	private int myNumCustomersServedSoFar = 0;
	private int myNumCustomersInLine = 0;
	private int myTotalWaitTime = 0;
	private int myTotalServiceTime = 0;
	private int myTotalIdleTime = 0;

	/**
	 * Constructor
	 */
	public ServiceQueue() {
		super();
	}

	/**
	 * This method checks if the line is empty or not
	 */
	public boolean empty() {
		if (myLine.isEmpty()) {
			System.out.println("LINE IS EMPTY!!!!");
			return true;
		} else {
			System.out.println("LINE IS NOT EMPTY!!!!");
			return false;
		}

	}

	/**
	 * This method inserts the customers in the line
	 */
	public void enqueue(Customer o) {
		myLine.addLast(o);
		System.out.println(o + " WAS ADDED!!!!");
		System.out.println(myLine.size());
		System.out.println(myLine);
		
	}

	/**
	 * This method peeks at the first customer in line
	 */
	public Customer peek() {
		return myLine.peekFirst();
	}

	/**
	 * This method removes the first customers from the line
	 */
	public Customer dequeue() {
		while (!myLine.isEmpty()) {
			myNumCustomersServedSoFar++;
			System.out.println("FIRST CUSTOMER REMOVED!!!!");
			return myLine.removeFirst();	
		}
		
		return null;
	}

	/**
	 * This method inserts the customers in the line
	 */
	public void insertCustomer(Customer customer)  {
		enqueue(customer);
	}

	/**
	 * This method serves the customers and remove them from the line
	 * @return
	 */
	public Customer serveCustomer() {
		Customer customer;
		customer = dequeue();
		return customer;
		
	}

	/**
	 * This method adds time to idle time
	 * @param idle
	 */
	public void addToIdleTime(int idle) {
		myTotalIdleTime += idle;
	}

	/**
	 * This method adds time to wait time
	 * @param wait
	 */
	public void addToWaitTime(int wait) {
		myTotalWaitTime += wait;
	}

	/**
	 * This method adds time to service time
	 * @param service
	 */
	public void addToServiceTime(int service) {
		myTotalServiceTime += service;
	}

	/**
	 * This method calculates average wait time
	 * @return averageWaitTime
	 */
	public int averageWaitTime() {
		int avgWaitTime = myTotalWaitTime / myNumCustomersServedSoFar;
		return avgWaitTime;
	}

	/**
	 * This method calculates average service time
	 * @return averageServiceTime
	 */
	public int averageServiceTime() {
		int avgServiceTime = myTotalServiceTime / myNumCustomersServedSoFar;
		return avgServiceTime;
	}

	/**
	 * This method calculates average idle time
	 * @return averageIdleTime
	 */
	public int averageIdleTime() {
		int avgIdleTime = myTotalIdleTime / myNumCustomersServedSoFar;
		return avgIdleTime;
	}

	/*
	 * Accessors
	 */
	
	public LinkedList<Customer> getMyLine() {
		return myLine;
	}

	public void setMyLine(LinkedList<Customer> line) {
		this.myLine = line;
	}
	
	public int getMyNumCustomersServedSoFar() {
		return myNumCustomersServedSoFar;
	}

	public void setMyNumCustomersServedSoFar(int numCustomersServedSoFar) {
		this.myNumCustomersServedSoFar = numCustomersServedSoFar;
	}

	public int getMyNumCustomersInLine() {
		myNumCustomersInLine = myLine.size();
		return myNumCustomersInLine;
	}

	public void setMyNumCustomersInLine(int numCustomersInLine) {
		this.myNumCustomersInLine = numCustomersInLine;
	}

	public int getMyTotalWaitTime() {
		return myTotalWaitTime;
	}

	public void setMyTotalWaitTime(int totalWaitTime) {
		this.myTotalWaitTime = totalWaitTime;
	}

	public int getMyTotalServiceTime() {
		return myTotalServiceTime;
	}

	public void setMyTotalServiceTime(int totalServiceTime) {
		this.myTotalServiceTime = totalServiceTime;
	}

	public int getMyTotalIdleTime() {
		return myTotalIdleTime;
	}

	public void setMyTotalIdleTime(int totalIdleTime) {
		this.myTotalIdleTime = totalIdleTime;
	}

}