package servicequeue.viewcontroller;

import servicequeue.model.Cashier;
import servicequeue.model.ServiceQueue;
import servicequeue.model.ServiceQueueManager;
import servicequeue.model.SimulationModel;

/**
 * 
 * @author Mohammed Batarfi
 * @author Heyley Gatewood
 *
 */
public class SimulationController implements Runnable {
	// Data Members
	private SimulationModel myModel;
	private SimulationView myView;
	private boolean mySuspended;
	private Thread myThread;
	private boolean isStarted = false;
	private int numClicks = 0;

	/**
	 * Basic Constructor
	 */
	public SimulationController() {
		myView = new SimulationView(this);
		myThread = new Thread(this);
		mySuspended = false;

		// this.startPause();

	}

	public static void main(String[] args) {
		new SimulationController();
	}

	public void setUp(int numCustomers, int maxTimeBetweenCustomers, int maxServiceTime) {
		myModel = new SimulationModel(numCustomers, maxTimeBetweenCustomers, maxServiceTime);
		isStarted = true;
		this.start();
	}

	public void switchStartPause() {
		// this.startPause();

	}

	/**
	 * Runs the thread that updates the view.
	 */
	public void run() {
		try {
			synchronized (this) {
				this.updateView();
				
			}
		} catch (InterruptedException e) {
			System.out.println("Thread suspended.");
		}
	}

	/**
	 * Updates the view.
	 * 
	 * @throws InterruptedException
	 */
	public void updateView() throws InterruptedException {
		while (true) {
			this.waitWhileSuspended();
			
			try {
				Thread.sleep(200);
					this.displayCustomers();
					

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Displays the customer images in the appropriate lines.
	 * 
	 * @param index
	 *            The queue index for the customers to be printed in.
	 */
	public void displayCustomers() {
		
		for (int i = 0; i < myModel.getServiceQueuesArray().length; i++) {
			ServiceQueue serviceQueue = myModel.getServiceQueuesArray()[i];
			synchronized (serviceQueue) {
				myView.setCustomersInLine(i, myModel.getServiceQueuesArray()[i].getMyNumCustomersInLine());
				myView.setCounters(i, myModel.getServiceQueuesArray()[i].getMyNumCustomersServedSoFar());
				
			}
		}
//		if(myModel.getServiceManager().getMyTotalServedSoFar() <= myModel.getMyNumOfCustomers()) {
			//myModel.stopThreads();
			float totalServiceTime = totalServiceTime();
		
			myView.setStats(totalServiceTime);
//		}
		
		
	}
	
	public float totalServiceTime() {
		float totalServiceTime = myModel.getServiceManager().totalServiceTime();
		return totalServiceTime;
	}


	/**
	 * This method make the thread waits while it's suspended
	 * @throws InterruptedException
	 */
	public void waitWhileSuspended() throws InterruptedException {
		while (mySuspended) {
			this.wait();
		}
	}

	/**
	 * This method set suspend to true
	 */
	public void suspend() {
		mySuspended = true;
	}

	/**
	 * This method starts the thread.
	 */
	public void start() {
		try {
			myThread.start();
		} catch (IllegalThreadStateException e) {
			System.out.println("Thread already started");
		}
	}

	/**
	 * This method set suspend to false
	 */
	public synchronized void resume() {
		mySuspended = false;
		this.notify();
	}

	/**
	 * This method starts and pause the thread.
	 */
	public void startPause() {
		numClicks++;
		myView.changeStartPause();
		if (numClicks == 1) {
			this.setUp(myView.getNumCustomers(), myView.getGenerateTime(), myView.getMaxServiceTime());
		} else {
			myModel.startPauseThreads();
			if (mySuspended) {
				this.resume();
			} else {
				this.suspend();
			}
		}
	}

}