package servicequeue.viewcontroller;

/**
 * 
 * @author Mohammed Batarfi
 * @author Heyley Gatewood
 *
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import javax.swing.*;

import servicequeue.model.ServiceQueueManager;

@SuppressWarnings("serial")
public class SimulationView extends JFrame {
	// Constants
	private final int TELLER_WIDTH = 100;
	private final int TELLER_HEIGHT = 92;
	private final String TELLER_IMG = "images/mini cashier.png";
	private final String FACE_IMG = "images/business man.png";
	private final int COUNTER_BOX_WIDTH = 50;
	private final int COUNTER_BOX_HEIGHT = 20;
	private final int CUSTOMER_WIDTH = 100;
	private final int CUSTOMER_HEIGHT = 50;
	private final int ROW_1 = 450; // position of cashier at the bottom of the screen
	private final int ROW_2 = 550; // position of the # of customers served JLabel
	private final int MAX_PEOPLE_IN_LINE = 500;
	private final int MAX_NUM_OF_TELLERS = 5;
	private int generateTime;
	private int numCustomers;
	private int numCashiers = 5;
	private int maxServiceTime;

	// Data Members
	private Image myScaledImage;
	private SimulationController myController;
	private Container myContentPane;
	private JTextArea textArea1, textArea2;
	private JLabel[] myTotalServed, myTeller;
	private JLabel[][] myCustomer;
	private JButton myStartPauseButton;
	private JPanel mySimPanel, myInfoPanel, myInputPanel;
	private SpringLayout layout = new SpringLayout();
	private String[] labels = { "Generation Time: ", "# of Customers: ", "# of Cashiers: ", "MAX Service Time: " };
	private JTextField[] textFields = new JTextField[4];

	private ButtonListener myStartPauseListener;

	/**
	 * Constructor that creates the view.
	 * 
	 * @param controller
	 *            the SimulationController that gives function to the buttons.
	 */
	public SimulationView(SimulationController controller) {

		Image face = Toolkit.getDefaultToolkit().getImage(FACE_IMG);
		myScaledImage = face.getScaledInstance(CUSTOMER_WIDTH / 2, CUSTOMER_HEIGHT, Image.SCALE_SMOOTH);

		myController = controller;

		/*
		 * JPanels: Simulation Panel, Info Panel, Input Panel
		 */

		mySimPanel = new JPanel();
		mySimPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		mySimPanel.setLayout(null);

		myInfoPanel = new JPanel();
		myInfoPanel.setBounds(525, 0, 200, 375);
		myInfoPanel.setLocation(525, 0);
		myInfoPanel.setLayout(new BoxLayout(myInfoPanel, BoxLayout.Y_AXIS));

		myInputPanel = new JPanel(layout);
		myInputPanel.setBounds(525, 100, 200, 200);
		myInputPanel.setLocation(525, 375);

		// Create and populate the panel
		for (int i = 0; i < 4; i++) {
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			myInputPanel.add(l);
			textFields[i] = new JTextField(5);
			l.setLabelFor(textFields[i]);
			myInputPanel.add(textFields[i]);
		}
		textFields[2].setText("5");
		textFields[2].setEditable(false);
		// Lay out the panel
		SpringUtilities.makeCompactGrid(myInputPanel, 4, 2, // rows, cols
				3, 0, // initX, initY
				5, 0); // xPad, yPad

		/*
		 * Start/Pause Button
		 */
		myStartPauseButton = new JButton("Start");
		myStartPauseButton.setBounds(725, 0, 75, 580);
		myStartPauseButton.setLocation(725, 0);

		this.associateListeners();

		/*
		 * Frame info
		 */
		this.setSize(800, 600);
		this.setLocation(100, 100);
		this.setTitle("Sample Queue MVC");
		this.setResizable(false);

		myContentPane = getContentPane();
		myContentPane.setLayout(new BorderLayout());

		/*
		 * JTextAreas
		 */
		textArea1 = new JTextArea("Customer Statistics:");
		textArea1.setBounds(525, 0, 200, 175);
		textArea1.setLocation(525, 0);
		JScrollPane scrollPane1 = new JScrollPane(textArea1);
		textArea1.setEditable(false);
		scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		textArea2 = new JTextArea("Text Area");
		textArea2.setBounds(525, 45, 200, 175);
		textArea2.setLocation(525, 45);
		JScrollPane scrollPane2 = new JScrollPane(textArea2);
		textArea2.setEditable(false);
		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Customer Served Counter
		myTotalServed = new JLabel[MAX_NUM_OF_TELLERS];

		for (int i = 0; i < myTotalServed.length; i++) {
			myTotalServed[i] = new JLabel("0");
			myTotalServed[i].setSize(COUNTER_BOX_WIDTH, COUNTER_BOX_HEIGHT);
			myTotalServed[i].setLocation(40 + (CUSTOMER_WIDTH * i), ROW_2);
			myTotalServed[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			mySimPanel.add(myTotalServed[i]);
		}

		// Teller locations
		myTeller = new JLabel[MAX_NUM_OF_TELLERS];

		for (int i = 0; i < MAX_NUM_OF_TELLERS; i++) {
			myTeller[i] = new JLabel(new ImageIcon(TELLER_IMG));
			myTeller[i].setSize(TELLER_WIDTH, TELLER_HEIGHT);
			myTeller[i].setLocation(15 + (CUSTOMER_WIDTH * i), ROW_1);
			myTeller[i].setVisible(true);
			mySimPanel.add(myTeller[i]);
		}

		// Customer Lines
		myCustomer = new JLabel[MAX_NUM_OF_TELLERS][MAX_PEOPLE_IN_LINE];
		for (int i = 0; i < MAX_NUM_OF_TELLERS; i++) {
			for (int j = 0; j < MAX_PEOPLE_IN_LINE; j++) {
				myCustomer[i][j] = new JLabel();
				myCustomer[i][j].setSize(CUSTOMER_WIDTH, CUSTOMER_HEIGHT);
				myCustomer[i][j].setLocation(30 + (CUSTOMER_WIDTH * i), 395 - (50 * j));
				myCustomer[i][j].setVisible(true);
				mySimPanel.add(myCustomer[i][j]);
			}
		}

		// Background
		JLabel bg;
		bg = new JLabel(new ImageIcon("images/wood-floor.jpg"));
		bg.setSize(525, 600); // Don't change this size! It took me forever to get that white box at the
								// bottom in the right place lol
		bg.setLocation(0, 0);
		mySimPanel.add(bg);
		mySimPanel.add(myInfoPanel);
		mySimPanel.add(myInputPanel);
		mySimPanel.add(myStartPauseButton);
		myInfoPanel.add(textArea1);
		myInfoPanel.add(textArea2);
		myContentPane.add(mySimPanel, BorderLayout.CENTER);

		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	//////////////////////////////////////////
	// Methods //
	//////////////////////////////////////////

	/**
	 * This methods changes the counter under the cashiers.
	 */
	public void setCounters(int queue, int count) {

		String c = String.valueOf(count);
		myTotalServed[queue].setText(c);

	}
	
	/**
	 * This method gets the total served to put in the counters
	 */
	public int getCounters(int queue) {
		String s = myTotalServed[queue].getText();
		int value = Integer.parseInt(s);
		return value;
	}
	
	/**
	 * This method sets the total served.
	 * @return totalServed
	 */
	public int setTotalServed() {
		int totalServed = 0;
		for(int i = 0; i < 5; i++) {
			int count = getCounters(i);
			totalServed +=count;
		}
		
		return totalServed;
		
	}

	/**
	 * getGenerateTimeInput(), getNumCustomersInput(), getNumCashiersInput(), and getMaxServiceTimeInput() get the user inputs from the view
	 */
	public void getGenerateTimeInput() {
		try {
			String s = textFields[0].getText();
			generateTime = Integer.parseInt(s);
			// System.out.println(generateTime);
		} catch (NumberFormatException e) {
			System.out.print("Please input a number for every category.");
		}

	}

	public void getNumCustomersInput() {
		try {
			String s = textFields[1].getText();
			numCustomers = Integer.parseInt(s);
			// System.out.println(numCustomers);
		} catch (NumberFormatException e) {
			System.out.print("Please input a number for every category.");
		}

	}

	public void getNumCashiersInput() {
		try {
			String s = textFields[2].getText();
			numCashiers = Integer.parseInt(s);
			// System.out.println(numCashiers);

		} catch (NumberFormatException e) {
			System.out.print("Please input a number for every category.");
		}

	}

	public void getMaxServiceTimeInput() {
		try {
			String s = textFields[3].getText();
			maxServiceTime = Integer.parseInt(s);
			// System.out.println(maxServiceTime);

		} catch (NumberFormatException e) {
			System.out.print("Please input a number for every category.");
		}

	}

	/**
	 * This method changes the Button from "Start" to "Pause"
	 */
	public void changeStartPause() {
		if (myStartPauseButton.getText().equals("Start")) {
			getGenerateTimeInput();
			getNumCustomersInput();
			getNumCashiersInput();
			getMaxServiceTimeInput();
			myStartPauseButton.setText("Pause");
		} else {
			myStartPauseButton.setText("Start");
		}
	}

	/*
	 * Getters and Setters
	 *
	 */
	public int getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(int g) {
		this.generateTime = g;
	}

	public int getNumCustomers() {
		return numCustomers;
	}

	public void setNumCustomers(int c) {
		this.numCustomers = c;
	}

	public int getNumCashiers() {
		return numCashiers;
	}

	public void setNumCashiers(int n) {
		this.numCashiers = n;
	}

	public int getMaxServiceTime() {
		return maxServiceTime;
	}

	public void setMaxServiceTime(int maxServiceTime) {
		this.maxServiceTime = maxServiceTime;
	}

	
	/**
	 * This metho sets the customers into the view
	 * @param queue
	 * @param numInLine
	 */
	public void setCustomersInLine(int queue, int numInLine) {
		myTeller[queue].setIcon(new ImageIcon(TELLER_IMG));

		for (int i = 0; i < numCustomers; i++) {
			myCustomer[queue][i].setVisible(false);
			
		}
		try {
			for (int i = 0; i < numInLine && i < numCustomers; i++) {
				myCustomer[queue][i].setVisible(true);
				myCustomer[queue][i].setIcon(new ImageIcon(myScaledImage));
			}
		} catch (NullPointerException e) {

		}
		
	}
	
	/**
	 * This method sets the Customer Statistics.
	 */
	public void setStats(float totalServiceTime) {
		int totalCustomersServed = this.setTotalServed();
		float averageServiceTime = totalServiceTime / totalCustomersServed;
		
		textArea1.setText("Customer Statistics: " + "\n Total Customers Served: " + totalCustomersServed
				+ "\n Average Service Time: " + averageServiceTime + "\n Average Wait Time: "
				+ "");
}

	/**
	 * Associates the button with the appropriate method
	 * 
	 * @param controller
	 *            The controller in which the method is included.
	 */
	private void associateListeners() {


		Class<? extends SimulationController> controllerClass;
		Method startPauseMethod;


		controllerClass = myController.getClass();

		startPauseMethod = null;


		try {

			startPauseMethod = controllerClass.getDeclaredMethod("startPause", (Class<?>[]) null);
		

		} catch (SecurityException e) {
			String error;

			error = e.toString();
			System.out.println(error);
		} catch (NoSuchMethodException e) {
			String error;

			error = e.toString();
			System.out.println(error);
		}

		myStartPauseListener = new ButtonListener(myController, startPauseMethod, null);
		myStartPauseButton.addMouseListener(myStartPauseListener);

	}

}