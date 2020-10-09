import java.sql.*;
import java.sql.Date;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import net.proteanit.sql.DbUtils;
import java.util.*;

public class ViewPassengerFlightInstance extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewPassengerFlightInstance frame = new ViewPassengerFlightInstance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection conn = null;
	private JTable table;
	private JComboBox flightCodeBox;
	private JComboBox departDateBox;
	
	/**
	 * Create the frame.
	 */
	public ViewPassengerFlightInstance() {
		setResizable(true);
		conn = ConnectSQL.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 678, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(236, 11, 426, 368);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		//////////////////Get Flight Code From Database///////////////
		flightCodeBox = new JComboBox();
		flightCodeBox.setBounds(48, 100, 130, 30);	
		contentPane.add(flightCodeBox);
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT flight_code FROM Flight");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				flightCodeBox.addItem(rs.getString(1));
			}
			
			pstmt.close();
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//////////////////////////////////////////////////////////////

		
		/////////////////flightCodeList ActionListener/////////////////
		departDateBox = new JComboBox();
		departDateBox.setBounds(48, 198, 130, 30);
		contentPane.add(departDateBox);
		flightCodeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try {
					departDateBox.removeAllItems();
					
					PreparedStatement pstmt = conn.prepareStatement("SELECT depart_date " +
																	"FROM flight_Instance " +
																	"WHERE flight_code = ?");
					pstmt.setString(1, (String)flightCodeBox.getSelectedItem());
					
					ResultSet rs = pstmt.executeQuery();
					
					while(rs.next()) {
						departDateBox.addItem(rs.getDate(1));
					}
					
					pstmt.close();
					rs.close();
					
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		///////////////////////////////////////////////////////////////
		
		JLabel lblFlightCodel = new JLabel("Flight Code:");
		lblFlightCodel.setBounds(48, 72, 83, 30);
		contentPane.add(lblFlightCodel);
		
		JLabel lblDepartureDate = new JLabel("Departure Date:");
		lblDepartureDate.setBounds(48, 170, 96, 25);
		contentPane.add(lblDepartureDate);
		
		JLabel lblSeatsAvailable = new JLabel("Seats Available:");
		lblSeatsAvailable.setBounds(328, 404, 96, 30);
		contentPane.add(lblSeatsAvailable);
		
		JLabel lblSeatsNum = new JLabel("");
		lblSeatsNum.setBounds(434, 404, 83, 30);
		contentPane.add(lblSeatsNum);
		
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String passenger = "SELECT P.passenger_id, P.first_name, P.last_name, P.miles " + 
							  	  	   "FROM Booking B, Passenger P " +
							  	  	   "WHERE B.flight_code = ? AND " +
							  	  	   		 "B.depart_date = ? AND " +
							  	  	   		 "B.passenger_id = P.passenger_id";
					
					String seats = "SELECT available_seats " +
							 	   "FROM Flight_Instance " +
							 	   "WHERE flight_code = ? AND " +
					  	  	   		 	 "depart_date = ?";
					
					PreparedStatement passengerTable = conn.prepareStatement(passenger);
					PreparedStatement seatsLable = conn.prepareStatement(seats);
					
					passengerTable.setString(1, (String)flightCodeBox.getSelectedItem());
					passengerTable.setDate(2, (Date)departDateBox.getSelectedItem());
					ResultSet rs = passengerTable.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					seatsLable.setString(1, (String)flightCodeBox.getSelectedItem());
					seatsLable.setDate(2, (Date)departDateBox.getSelectedItem());
					ResultSet rs1 = seatsLable.executeQuery();
						
					int avaSeats = 0;
						
					while(rs1.next()){
						avaSeats = rs1.getInt(1);
					}
						
					lblSeatsNum.setText(String.valueOf(avaSeats));
					
					passengerTable.close();
					seatsLable.close();
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnSearch.setBounds(48, 291, 130, 50);
		contentPane.add(btnSearch);
	}
}
