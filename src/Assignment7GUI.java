import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Assignment7GUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Assignment7GUI window = new Assignment7GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection conn = null;
	
	/**
	 * Create the application.
	 */
	public Assignment7GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		conn = ConnectSQL.dbConnector();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 442, 21);
		frame.getContentPane().add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Options");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmInserNewPassenger = new JMenuItem("Insert New Passenger");
		mntmInserNewPassenger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InsertNewPassenger newPassenger = new InsertNewPassenger();
				newPassenger.setVisible(true);
			}
		});
		mnNewMenu.add(mntmInserNewPassenger);
		
		
		JMenuItem mntmViewFlightInstance = new JMenuItem("View Passenger Flight Instance ");
		mntmViewFlightInstance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewPassengerFlightInstance viewFI = new ViewPassengerFlightInstance();
				viewFI.setVisible(true);
			}
		});
		mnNewMenu.add(mntmViewFlightInstance);
		
		JMenuItem mntmAddBookingRecord = new JMenuItem("Add Booking Record ");
		mntmAddBookingRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddBookingRecord addBooking = new AddBookingRecord();
				addBooking.setVisible(true);
			}
		});
		mnNewMenu.add(mntmAddBookingRecord);
	}
}
