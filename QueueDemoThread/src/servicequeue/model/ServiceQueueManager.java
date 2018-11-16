package servicequeue.model;

/**
 * 
 * @author Mohammed Batarfi
 * @author Heyley Gatewood
 *
 */
import java.util.Random;

public class ServiceQueueManager {
	private Random myRandom;
	private int myNumCustomers = 10;
	private int myNumServiceQueues = 5;
	private int myNumCustomersAdded = 0;
	private ServiceQueue ServiceQueuesArray[];
	private ServiceQueue myShortestQueue;
	private ServiceQueue myServiceQueue;


	private int myTotalServedSoFar = 0;
	private long myTotalWaitTime = 0;
	private long myTotalServiceTime = 0;
	private long myTotalIdleTime = 0;
	private float myAverageWaitTime = 0;
	private float myAverageServiceTime = 0;
	private long myAverageIdleTime = 0;

	/**
	 * Constructor
	 * @param ServiceQueuesArray
	 */
	public ServiceQueueManager(ServiceQueue []ServiceQueuesArray) {
		this.ServiceQueuesArray = ServiceQueuesArray;
	}
	
	/**
	 * This methods determines the shortest line
	 * @return
	 * @throws InterruptedException
	 */
	public ServiceQueue determineShortestQueue() throws InterruptedException {
		myShortestQueue = null;
		for (ServiceQueue queue : ServiceQueuesArray) {
			if (myShortestQueue == null || queue.getMyNumCustomersInLine() < myShortestQueue.getMyNumCustomersInLine())
				myShortestQueue = queue;
		}
		System.out.println("We got shortest queue:");
		return myShortestQueue;
	}
	
	/**
	 * This method inserts the customer in the line
	 * @param customer
	 * @throws InterruptedException
	 */
	public void insertCustomer(Customer customer) throws InterruptedException{
		myServiceQueue = determineShortestQueue();
		myServiceQueue.insertCustomer(customer);
	}

	/**
	 * This method calculates total customers served so far
	 * @return myTotalServedSoFar
	 */
	public long totalServedSoFar() {
		for (ServiceQueue queue : ServiceQueuesArray) {
			//System.out.print
			myTotalServedSoFar += queue.getMyNumCustomersServedSoFar();
		}
		return myTotalServedSoFar;
	}
	
	/**
	 * This method calculates total wait time
	 * @return myTotalWaitTime
	 */
	public long totalWaitTime() {
		for (ServiceQueue queue : ServiceQueuesArray) {
		myTotalWaitTime += queue.getMyTotalWaitTime();
		}
		return myTotalWaitTime;
	}
	
	/**
	 * This method calculates total service time
	 * @return myTotalServiceTime
	 */
	public long totalServiceTime() {
		for (ServiceQueue queue : ServiceQueuesArray) {
			myTotalServiceTime += queue.getMyTotalServiceTime();
			}
		return myTotalServiceTime;
	}

	/**
	 * This method calculates total idle time
	 * @return myTotalIdleTime
	 */
	public float totalIdleTime() {
		for (ServiceQueue queue : ServiceQueuesArray) {
			myTotalIdleTime += queue.getMyTotalIdleTime();
			}
		return myTotalIdleTime;
	}

	/**
	 * This method calculates average wait time
	 * @return myAverageWaitTime
	 */
	public float averageWaitTime() {
		myAverageWaitTime = myTotalWaitTime / myTotalServedSoFar;
		System.out.println("The Average WAIT Time: " + myAverageWaitTime);
		return myAverageWaitTime;
	}

	/**
	 * This method calculates average service time
	 * @return myAverageServiceTime
	 */
	public float averageServiceTime() {
		myAverageServiceTime = myTotalServiceTime / myTotalServedSoFar;
		System.out.println("The Average SERVICE Time: " + myAverageServiceTime);
		return myAverageServiceTime;
	}

	/**
	 * This method calculates average idle time
	 * @return myAverageIdleTime
	 */
	public float averageIdleTime() {
		myAverageIdleTime = myTotalIdleTime / myTotalServedSoFar;
		System.out.println("The Average IDLE Time: " + myAverageIdleTime);
		return myAverageIdleTime;
	}

	/*
	 * Accessors
	 */
	public int getMyTotalServedSoFar() {
		return myTotalServedSoFar;
	}

	public void setMyTotalServedSoFar(int totalServedSoFar) {
		this.myTotalServedSoFar = totalServedSoFar;
	}

	public ServiceQueue getMyServiceQueue() {
		return myServiceQueue;
	}

	public ServiceQueue[] getServiceQueuesArray() {
		return ServiceQueuesArray;
	}

	public void setServiceQueuesArray(ServiceQueue[] serviceQueuesArray) {
		ServiceQueuesArray = serviceQueuesArray;
	}

	public int getMyNumCustomersAdded() {
		return myNumCustomersAdded;
	}

	public void setMyNumCustomersAdded(int numCustomersAdded) {
		this.myNumCustomersAdded = numCustomersAdded;
	}

}