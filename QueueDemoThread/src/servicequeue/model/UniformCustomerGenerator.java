package servicequeue.model;

import java.util.Random;

/**
 * This class generates the customers.
 * @author Heyley Gatewood
 * @author Mohammed Batarfi
 *
 */

public class UniformCustomerGenerator extends CustomerGenerator {
	private Random myRandom;
	
	/*
	 * Constructor
	 */
	public UniformCustomerGenerator(int numCustomers, int maxTimeBetweenCustomers, ServiceQueueManager serviceQueueManager) {
		super(numCustomers, maxTimeBetweenCustomers, serviceQueueManager);
		myRandom = new Random();
		myNumCustomers = numCustomers;
		myMaxTimeBetweenCustomers = maxTimeBetweenCustomers;
		customerManager = serviceQueueManager;
		
	}

	/**
	 * This method randomly generates the time between each customer.
	 */
	public int generateTimeBetweenCustomers() { 
		myTimeBetweenCustomers = myRandom.nextInt(myMaxTimeBetweenCustomers) + 1;
		return myTimeBetweenCustomers;
	}



}
