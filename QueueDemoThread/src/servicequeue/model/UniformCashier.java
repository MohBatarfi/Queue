package servicequeue.model;
import java.awt.event.ActionEvent;
/**
 * 
 * @author Mohammed Batarfi
 * @author Heyley Gatewood
 *
 */
import java.util.Random;
/**
 * This class creates/establishes a cashier.
 * @author Heyley Gatewood
 * @author Mohammed Batarfi
 *
 */
public class UniformCashier extends Cashier{

	private Random myRandom;

	/*
	 * Constuctor
	 */
	public UniformCashier(int queueNum, int maxTimeService, ServiceQueue serviceQueue){
		super(queueNum, maxTimeService, serviceQueue);
			myRandom = new Random();
			myMaxTimeOfService = maxTimeService;
			myQueue = serviceQueue;
		}
	
	/**
	 * This method randomly generates a service time for the cashier for each customer.
	 */
	public int generateServiceTime() { // working
		myServiceTime = myRandom.nextInt(myMaxTimeOfService) + 1;
		System.out.println(myServiceTime);
		return myServiceTime;
	}

}
