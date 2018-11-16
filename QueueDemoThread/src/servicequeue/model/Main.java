	package servicequeue.model;

/**
 * 
 * @author Mohammed Batarfi
 * @author Heyley Gatewood
 *
 */
	public class Main {
	
	private static ServiceQueueManager manager;
	private static UniformCustomerGenerator customer;
	private static UniformCashier cashier;
	private static ServiceQueue serviceQueue;
	private static SimulationModel SimulationModel;
	
	public static void main(String[] args) throws InterruptedException {
//		manager = new ServiceQueueManager();
//		sm = new SimulationModel(1,1, 200);
		//customer = new UniformCustomerGenerator(10, 500, manager);
//		cashier = new UniformCashier(200,serviceQueue);
		//manager.StartSimulation();
		SimulationModel = new SimulationModel(20,500, 1000);
//		cashier.start();
		
	}

}
