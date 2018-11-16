package servicequeue.model;

public class SimulationModel {

	private UniformCashier CashiersArray[] = new UniformCashier[5];
	private ServiceQueue ServiceQueuesArray[] = new ServiceQueue[5];
	private ServiceQueueManager myServiceManager;
	private UniformCustomerGenerator myCustomerGenerator;
	private boolean hasEnded = false;
	private int myNumOfCustomers;

	/**
	 * Constructor
	 * 
	 * @param numOfCustomers
	 * @param maxTimeBetweenCustomers
	 * @param maxServiceTime
	 */
	public SimulationModel(int numOfCustomers, int maxTimeBetweenCustomers, int maxServiceTime) {
		myNumOfCustomers = numOfCustomers;
		for (int i = 0; i < ServiceQueuesArray.length; i++) {
			ServiceQueuesArray[i] = new ServiceQueue();
			CashiersArray[i] = new UniformCashier(i, maxServiceTime, ServiceQueuesArray[i]);
			System.out.println("Cashier is made");
			CashiersArray[i].start();
		}
		myServiceManager = new ServiceQueueManager(ServiceQueuesArray);
		myCustomerGenerator = new UniformCustomerGenerator(numOfCustomers, maxTimeBetweenCustomers, myServiceManager);
		myCustomerGenerator.start();

	}

	/**
	 * This method stop the threads
	 */
	public void stopThreads() {
		if (ServiceQueuesArray[0].getMyLine().peek() == null && ServiceQueuesArray[1].getMyLine().peek() == null
				&& ServiceQueuesArray[2].getMyLine().peek() == null && ServiceQueuesArray[3].getMyLine().peek() == null
				&& ServiceQueuesArray[4].getMyLine().peek() == null) {
			myCustomerGenerator.suspend();
			for (int i = 0; i < CashiersArray.length; i++) {
				CashiersArray[i].suspend();
			}
		}
	}

	/**
	 * This method starts and pause the threads
	 */
	public void startPauseThreads() {
		myCustomerGenerator.startPause();
		for (Cashier cashier : CashiersArray) {
			cashier.startPause();
		}
	}

	/*
	 * Accessors
	 */
	public ServiceQueueManager getServiceManager() {
		return myServiceManager;
	}

	public void setServiceManager(ServiceQueueManager serviceManager) {
		this.myServiceManager = serviceManager;
	}

	public ServiceQueue[] getServiceQueuesArray() {
		return ServiceQueuesArray;
	}

	public void setServiceQueuesArray(ServiceQueue[] serviceQueuesArray) {
		ServiceQueuesArray = serviceQueuesArray;
	}

	public ServiceQueue getServiceQueue() {
		return myServiceManager.getMyServiceQueue();
	}

	public UniformCashier[] getCashiersArray() {
		return CashiersArray;
	}

	public void setCashiersArray(UniformCashier[] cashiersArray) {
		CashiersArray = cashiersArray;
	}

	public boolean isHasEnded() {
		return hasEnded;
	}

	public void setHasEnded(boolean ended) {
		this.hasEnded = ended;
	}

	public ServiceQueueManager getMyServiceManager() {
		return myServiceManager;
	}

	public void setMyServiceManager(ServiceQueueManager myServiceManager) {
		this.myServiceManager = myServiceManager;
	}

	public int getMyNumOfCustomers() {
		return myNumOfCustomers;
	}

	public void setMyNumOfCustomers(int myNumOfCustomers) {
		this.myNumOfCustomers = myNumOfCustomers;
	}

}
