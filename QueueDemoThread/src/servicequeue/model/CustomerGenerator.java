package servicequeue.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public abstract class CustomerGenerator implements Runnable {

	protected int myMaxTimeBetweenCustomers;// = 2000;
	protected int myTimeBetweenCustomers;
	protected int myNumCustomers;
	protected Thread myThread;
	protected ServiceQueueManager customerManager;// = new ServiceQueueManager();
	// protected ServiceQueue myServiceQueue;
	protected int myCounter = 0;
	private Customer myCustomer;
	private boolean mySuspended = false;

	public CustomerGenerator(int numCustomers, int maxTimeBetweenCustomers, ServiceQueueManager serviceQueueManager) {
		myNumCustomers = numCustomers;
		myMaxTimeBetweenCustomers = maxTimeBetweenCustomers;
		customerManager = serviceQueueManager;
		myThread = new Thread(this);
	}

	public abstract int generateTimeBetweenCustomers();

	public Customer generateCustomer() throws InterruptedException {
		Customer customer = new Customer();
		myCounter++;
		Thread.sleep(generateTimeBetweenCustomers());
		System.out.println("Time between these customers: " + myTimeBetweenCustomers);
		System.out.println("We have " + myCounter + " customers.");
		System.out.println("");
		return customer;

	}

	public void run() {
		System.out.println(myCounter);
		try {
			synchronized (this) {
				System.out.println("Customer Thread is running.");
				while (myCounter < myNumCustomers) {
					try {
						this.waitWhileSuspended();
						myCustomer = generateCustomer();
						System.out.println(myCounter);
						customerManager.insertCustomer(myCustomer);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				System.out.println("Customer Thread has stopped.");

			}

		} catch (Exception e) {
			System.out.println("Customer ERROR!");
		}

	}

	public void start() {

		myThread.start();

	}

	private void waitWhileSuspended() throws InterruptedException {
		//try {
			synchronized (this) {
				while (mySuspended) {
					this.wait();
				}
			}
		//} catch (InterruptedException e) {
		//	e.printStackTrace();
		//}

	}

	public void suspend() {
		mySuspended = true;
	}

	public synchronized void resume() {
		mySuspended = false;
		this.notify();
	}

	public void startPause() {
		// myView.changeStartPause();
		if (mySuspended) {
			this.resume();
		} else {
			this.suspend();
		}
	}

	public int getCounter() {
		return myCounter;
	}

	public void setCounter(int counter) {
		this.myCounter = counter;
	}

	public Customer getMyCustomer() {
		return myCustomer;
	}

	public void setMyCustomer(Customer customer) {
		this.myCustomer = customer;
	}

	public int setNumInLine() {
		return 1;
	}

	// public int getMyNumCustomers() {
	// return myNumCustomers;
	// }
	//
	// public void setMyNumCustomers(int myNumCustomers) {
	// this.myNumCustomers = myNumCustomers;
	// }

}
