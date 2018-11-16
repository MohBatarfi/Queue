package servicequeue.model;

/**
 * This class creates the customers.
 * @author Heyley Gatewood
 * @author Mohammed Batarfi
 *
 */
public class Customer {

	private long myServiceTime;
	private long myEntryTime;
	private long myWaitTime;
	private long myExitTime;
	
	/*
	 * Constuctor
	 */
	public Customer(){
		System.out.println("I'm a new customer!");
	}
	
	/**
	 * This method gets and returns the customer's service time.
	 * @return myServiceTime
	 */
	public long getMyServiceTime() {
		return myServiceTime;
	}

	/**
	 * This method sets the customer's service time.
	 * @param serviceTime
	 */
	public void setMyServiceTime(long serviceTime) {
		this.myServiceTime = serviceTime;
	}

	/**
	 * This method gets and returns the customer's entry time.
	 * @return myEntryTime
	 */
	public long getMyEntryTime() {
		return myEntryTime;
	}

	/**
	 * This method sets the customer's entry time.
	 * @param entryTime
	 */
	public void setMyEntryTime(long entryTime) {
		this.myEntryTime = entryTime;
	}

	/**
	 * This customer gets and returns the customer's exit time.
	 * @return myExitTime
	 */
	public long getMyExitTime() {
		return myExitTime;
	}

	/**
	 * This method sets the customer's exit time.
	 * @param exitTime
	 */
	public void setMyExitTime(long exitTime) {
		this.myExitTime = exitTime;

	}

	/**
	 * This method gets and returns the customer's wait time.
	 * @return myWaitTime
	 */
	public long getMyWaitTime() {
		myWaitTime = myExitTime - myEntryTime;
		return myWaitTime;
	}

	/**
	 * This method sets the customers wait time.
	 * @param waitTime
	 */
	public void setMyWaitTime(int waitTime) {
		this.myWaitTime = waitTime;
	}
}
