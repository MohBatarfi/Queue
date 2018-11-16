package servicequeue.model;

/**
 * This class is the abstract class for the Uniform Cashier.
 * @author Mohammed Batarfi
 * @author Heyley Gatewood
 *
 */

import javax.swing.Timer;

public abstract class Cashier implements Runnable {

	protected int myMaxTimeOfService;// = 1000;
	protected int myServiceTime;
	protected int myNumOfCustomersServed;
	protected Thread myThread;
	protected ServiceQueue myQueue;// = new ServiceQueue();
	private boolean mySuspended;
	private int myQueueNum;

	/**
	 * Constructor
	 * 
	 * @param queueNum
	 * @param maxTimeService
	 * @param serviceQueue
	 */
	public Cashier(int queueNum, int maxTimeService, ServiceQueue serviceQueue) {
		myQueueNum = queueNum;
		myNumOfCustomersServed = 0;
		mySuspended = false;
		myThread = new Thread(this);
		myQueue = serviceQueue;

	}

	/**
	 * This method serves the customers in the queue.
	 * 
	 * @returns the customer's service time or 0
	 * @throws InterruptedException
	 */
	public int serveCustomer() throws InterruptedException {
		Customer customer;
		synchronized (myQueue) {
			customer = myQueue.serveCustomer();
			myNumOfCustomersServed++;
		}

		if (!myQueue.empty()) {
			this.generateServiceTime();
			System.out.print("Service Time: " + myServiceTime);
			sleep(myServiceTime);
			return myServiceTime;
		} else {// (myQueue.empty()) {
			sleep(200);
			return 0;
		}
		// else {

		// }

	}

	public abstract int generateServiceTime();

	/**
	 * This method runs the Cashier thread.
	 */
	public void run() {
		try {
			// synchronized (this) {

			while (!mySuspended) {
				this.waitWhileSuspended();
				this.serveCustomer();

			}

		} catch (InterruptedException e) {
			System.out.println("Cashier Thread is suspended (not running).");
			// suspend();
		}
	}

	/**
	 * This method starts the Thread.
	 */
	public void start() {
		try {
			myThread.start();
		} catch (IllegalThreadStateException e) {
			System.out.println("Cashier Thread already started");
		}
	}

	/**
	 * This method puts the Cashier to sleep for a certain amount of time
	 * 
	 * @param time
	 */
	private void sleep(int time) {
		try {
			Thread.sleep(time);
			System.out.println("I'm Sleeping ");

		} catch (InterruptedException e) {
			suspend();
		}
	}

	/**
	 * This method tells the cashier to wait when it is suspended.
	 * 
	 * @throws InterruptedException
	 */
	private void waitWhileSuspended() throws InterruptedException {
		synchronized (this) {
			while (mySuspended) {
				this.wait();
			}
		}
	}

	/**
	 * This method suspends the cashier.
	 */
	public void suspend() {
		mySuspended = true;
	}

	/**
	 *  This method resumes the cashier.
	 */
	public synchronized void resume() {
		mySuspended = false;
		this.notify();
	}

	/**
	 * This method starts and pauses the cashier thread.
	 */
	public void startPause() {
		// myView.changeStartPause();
		if (mySuspended) {
			this.resume();
		} else {
			this.suspend();
		}
	}

	/*
	 * This method gets and returns the maximum service time.
	 */
	public int getMyMaxTimeOfService() {
		return myMaxTimeOfService;
	}

	/**
	 * This method sets the maximum service time.
	 * @param maxTimeOfService
	 */
	public void setMyMaxTimeOfService(int maxTimeOfService) {
		this.myMaxTimeOfService = maxTimeOfService;
	}

	/**
	 * This method gets and returns the service time.
	 * @return myServiceTime
	 */
	public int getMyServiceTime() {
		myServiceTime += generateServiceTime();
		return myServiceTime;
	}

	/**
	 * This method sets the service time.
	 * @param serviceTime
	 */
	public void setMyServiceTime(int serviceTime) {
		this.myServiceTime = serviceTime;
	}

	/**
	 * This method gets and returns the serviceQueue.
	 * @return myQueue
	 */
	public Queue<Customer> getMyQueue() {
		return myQueue;
	}

	/**
	 * This method sets the serviceQueue.
	 * @param queue
	 */
	public void setMyQueue(ServiceQueue queue) {
		this.myQueue = queue;
	}

}