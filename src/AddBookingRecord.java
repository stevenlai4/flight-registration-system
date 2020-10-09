import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class AddBookingRecord extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBookingRecord frame = new AddBookingRecord();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection conn = null;
	private JTextField passengerIDtextField;
	private JTextField firstFlightCodetextField;
	private JTextField firstDepartDatetextField;
	private JLabel lblfirstDepartureDate;
	private JTextField secondFlightCodetextField;
	private JLabel lblsecondFlightCode;
	private JLabel lblsecondDepartureDate;
	private JTextField secondDepartDatetextField;
	private JLabel lblPassengerId;
	private JLabel lblfirstFlightCode;
	/**
	 * Create the frame.
	 */
	public AddBookingRecord() {
		conn = ConnectSQL.dbConnector();
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 705, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		passengerIDtextField = new JTextField();
		passengerIDtextField.setBounds(220, 53, 151, 29);
		contentPane.add(passengerIDtextField);
		passengerIDtextField.setColumns(10);
		
		lblPassengerId = new JLabel("Passenger ID:");
		lblPassengerId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassengerId.setBounds(27, 52, 108, 29);
		contentPane.add(lblPassengerId);
		
		firstFlightCodetextField = new JTextField();
		firstFlightCodetextField.setBounds(220, 110, 151, 29);
		contentPane.add(firstFlightCodetextField);
		firstFlightCodetextField.setColumns(10);
		
		lblfirstFlightCode = new JLabel("Flight Code 1:");
		lblfirstFlightCode.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblfirstFlightCode.setBounds(27, 109, 108, 29);
		contentPane.add(lblfirstFlightCode);
		
		firstDepartDatetextField = new JTextField ();
		firstDepartDatetextField.setBounds(220, 168, 151, 29);
		contentPane.add(firstDepartDatetextField);
		firstDepartDatetextField.setColumns(10);
		
		lblfirstDepartureDate = new JLabel("Departure Date 1(YYYY-MM-DD):");
		lblfirstDepartureDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblfirstDepartureDate.setBounds(27, 170, 300, 22);
		contentPane.add(lblfirstDepartureDate);
		
		secondFlightCodetextField = new JTextField();
		secondFlightCodetextField.setEnabled(false);
		secondFlightCodetextField.setBounds(220, 224, 151, 29);
		contentPane.add(secondFlightCodetextField);
		secondFlightCodetextField.setColumns(10);
		
		lblsecondFlightCode = new JLabel("Flight Code 2:");
		lblsecondFlightCode.setEnabled(false);
		lblsecondFlightCode.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblsecondFlightCode.setBounds(27, 226, 108, 22);
		contentPane.add(lblsecondFlightCode);
		
		lblsecondDepartureDate = new JLabel("Departure Date 2(YYYY-MM-DD):");
		lblsecondDepartureDate.setEnabled(false);
		lblsecondDepartureDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblsecondDepartureDate.setBounds(27, 283, 300, 22);
		contentPane.add(lblsecondDepartureDate);
		
		secondDepartDatetextField = new JTextField();
		secondDepartDatetextField.setEnabled(false);
		secondDepartDatetextField.setBounds(220, 285, 151, 29);
		contentPane.add(secondDepartDatetextField);
		secondDepartDatetextField.setColumns(10);
		
		JButton btnSingleTrip = new JButton("Single Trip");
		btnSingleTrip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(secondFlightCodetextField.isEnabled()){
					secondFlightCodetextField.setEnabled(false);
					lblsecondFlightCode.setEnabled(false);
					lblsecondDepartureDate.setEnabled(false);
					secondDepartDatetextField.setEnabled(false);
					passengerIDtextField.setText("");
					firstFlightCodetextField.setText("");
					firstDepartDatetextField.setText("");
					secondFlightCodetextField.setText("");
					secondDepartDatetextField.setText("");
				}
			}
		});
		btnSingleTrip.setBounds(453, 56, 121, 48);
		contentPane.add(btnSingleTrip);
		
		JButton btnMultiTrip = new JButton("Multi-City Trip");
		btnMultiTrip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!secondFlightCodetextField.isEnabled()){
					secondFlightCodetextField.setEnabled(true);
					lblsecondFlightCode.setEnabled(true);
					lblsecondDepartureDate.setEnabled(true);
					secondDepartDatetextField.setEnabled(true);
					passengerIDtextField.setText("");
					firstFlightCodetextField.setText("");
					firstDepartDatetextField.setText("");
				}
			}
		});
		btnMultiTrip.setBounds(453, 129, 121, 48);
		contentPane.add(btnMultiTrip);
		
		JButton btnInsertBooking = new JButton("Insert Booking");
		btnInsertBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!secondFlightCodetextField.isEnabled()){
					if(passengerIDtextField.getText().trim().equals("")|| 
					   firstFlightCodetextField.getText().trim().equals("")|| 
					   firstDepartDatetextField.getText().trim().equals("")){
						JOptionPane.showMessageDialog(null,"Please do not leave the text fields blank!","Error",JOptionPane.ERROR_MESSAGE);
					}
					else{
						try{
							PreparedStatement checkSeats = conn.prepareStatement("SELECT available_seats " +
																				 "FROM Flight_Instance " + 
																				 "WHERE flight_code = ? AND depart_date = ?");
							checkSeats.setString(1,firstFlightCodetextField.getText().trim());
							checkSeats.setDate(2,Date.valueOf(firstDepartDatetextField.getText().trim()));
							ResultSet availableSeats = checkSeats.executeQuery();
							int seatsNum = -1;
							while(availableSeats.next()){
								seatsNum = availableSeats.getInt(1);
							}
							
							if(seatsNum == 0){
								JOptionPane.showMessageDialog(null,"No more available seats!","Alert",JOptionPane.ERROR_MESSAGE);
							}
							else{
							
								PreparedStatement singleFlight = conn.prepareStatement("INSERT INTO Booking VALUES(?,?,?)");
								singleFlight.setString(1,firstFlightCodetextField.getText().trim());
								singleFlight.setDate(2,Date.valueOf(firstDepartDatetextField.getText().trim()));
								int pid = Integer.parseInt(passengerIDtextField.getText().trim());
								singleFlight.setInt(3,pid);
								singleFlight.execute();
								JOptionPane.showMessageDialog(null,  "Passenger ID: " + passengerIDtextField.getText().trim() + "\n" +
																	 "Flight Code: " + firstFlightCodetextField.getText().trim() + "\n" +
																	 "Departure Date: " + firstDepartDatetextField.getText().trim() + "\n" +
																	 "has been successfully added to the booking!");
								singleFlight.close();
								availableSeats.close();
							}
							
							checkSeats.close();
						}
						catch(Exception e){
							JOptionPane.showMessageDialog(null, e);
						}
					}
				}
				else{
					if(passengerIDtextField.getText().trim().equals("")|| 
					   firstFlightCodetextField.getText().trim().equals("")|| 
					   firstDepartDatetextField.getText().trim().equals("")||
					   secondFlightCodetextField.getText().trim().equals("")||
					   secondDepartDatetextField.getText().trim().equals("")){
								JOptionPane.showMessageDialog(null,"Please do not leave the text fields blank!","Error",JOptionPane.ERROR_MESSAGE);
					}
					else{
					   Date firstDate = Date.valueOf(firstDepartDatetextField.getText().trim());
					   Date secondDate = Date.valueOf(secondDepartDatetextField.getText().trim());
					   
					   if(firstDate.after(secondDate)){
						   JOptionPane.showMessageDialog(null,"First date must be earlier than second date!","Alert",JOptionPane.ERROR_MESSAGE);
					   }
					   
					   try {
						   PreparedStatement dest = conn.prepareStatement("SELECT arrival_iata " +
						   												  "FROM Flight " +
								   										  "WHERE flight_code = ?");
						   dest.setString(1, firstFlightCodetextField.getText().trim());
						   ResultSet dest_iata = dest.executeQuery();
						   String destination = "";
						   while(dest_iata.next()){
							   destination = dest_iata.getString(1);
						   }
						   dest.close();
						   dest_iata.close();
						   
						   PreparedStatement depart = conn.prepareStatement("SELECT departure_iata " +
									  										"FROM Flight " +
									  										"WHERE flight_code = ?");
						   depart.setString(1, secondFlightCodetextField.getText().trim());
						   ResultSet depart_iata = depart.executeQuery();
						   String departure = "";
						   while(depart_iata.next()){
							   departure = depart_iata.getString(1);
						   }
						   depart.close();
						   depart_iata.close();
						   
						   if(!departure.equals(destination)){
							   JOptionPane.showMessageDialog(null,"This is not a feasible multi-city trip!","Alert",JOptionPane.ERROR_MESSAGE);
						   }
						   
					   } 
					   catch (Exception e) {
						   JOptionPane.showMessageDialog(null, e);
					   }
					   
					   try{
						   PreparedStatement firstSeats = conn.prepareStatement("SELECT available_seats " +
																				"FROM Flight_Instance " + 
																				"WHERE flight_code = ? AND depart_date = ?");
						   firstSeats.setString(1, firstFlightCodetextField.getText().trim());
						   firstSeats.setDate(2, Date.valueOf(firstDepartDatetextField.getText().trim()));
						   ResultSet firstAvaSeats = firstSeats.executeQuery();
						   
						   int firstAvailableSeats = -1;
						   while(firstAvaSeats.next()){
							   firstAvailableSeats = firstAvaSeats.getInt(1);
						   }
						   firstSeats.close();
						   firstAvaSeats.close();
						   
						   PreparedStatement secondSeats = conn.prepareStatement("SELECT available_seats " +
																				"FROM Flight_Instance " + 
																				"WHERE flight_code = ? AND depart_date = ?");
						   secondSeats.setString(1, secondFlightCodetextField.getText().trim());
						   secondSeats.setDate(2, Date.valueOf(secondDepartDatetextField.getText().trim()));
						   ResultSet secondAvaSeats = secondSeats.executeQuery();
						
						   int secondAvailableSeats = -1;
						   while(secondAvaSeats.next()){
							   secondAvailableSeats = secondAvaSeats.getInt(1);
						   }
						   secondSeats.close();
						   secondAvaSeats.close();
						   
						   if(firstAvailableSeats == 0 && secondAvailableSeats != 0){
							   JOptionPane.showMessageDialog(null,"No more available seats for first flight: " + firstFlightCodetextField.getText().trim(),
									   						 "Alert",JOptionPane.ERROR_MESSAGE);
						   }
						   else if(secondAvailableSeats == 0 && firstAvailableSeats != 0){
							   JOptionPane.showMessageDialog(null,"No more available seats for second flight: " + secondFlightCodetextField.getText().trim(),
				   						 						  "Alert",JOptionPane.ERROR_MESSAGE);
						   }
						   else if(secondAvailableSeats == 0 && firstAvailableSeats == 0){
							   JOptionPane.showMessageDialog(null,"No more available seats for both flights","Alert",JOptionPane.ERROR_MESSAGE);
						   }
						   else{
							   PreparedStatement firstFlight = conn.prepareStatement("INSERT INTO Booking VALUES(?,?,?)");
							   firstFlight.setString(1,firstFlightCodetextField.getText().trim());
							   firstFlight.setDate(2,Date.valueOf(firstDepartDatetextField.getText().trim()));
							   int pid = Integer.parseInt(passengerIDtextField.getText().trim());
							   firstFlight.setInt(3,pid);
							   firstFlight.execute();
							   
							   PreparedStatement secondFlight = conn.prepareStatement("INSERT INTO Booking VALUES(?,?,?)");
							   secondFlight.setString(1,secondFlightCodetextField.getText().trim());
							   secondFlight.setDate(2,Date.valueOf(secondDepartDatetextField.getText().trim()));
							   secondFlight.setInt(3,pid);
							   secondFlight.execute();
							   
							   JOptionPane.showMessageDialog(null,  "Passenger ID: " + passengerIDtextField.getText().trim() + "\n" +
																	"First Flight Code: " + firstFlightCodetextField.getText().trim() + "\n" +
																	"First Departure Date: " + firstDepartDatetextField.getText().trim() + "\n" +
																	"Second Flight Code: " + secondFlightCodetextField.getText().trim() + "\n" +
																	"Second Departure Date: " + secondDepartDatetextField.getText().trim() + "\n" +
																	"has been successfully added to the booking!");
							   
							   firstFlight.close();
							   secondFlight.close();
						   }
					   }
					   catch(Exception e){
						   JOptionPane.showMessageDialog(null, e);
					   }
					}
				}
			}
		});
		btnInsertBooking.setBounds(122, 357, 140, 48);
		contentPane.add(btnInsertBooking);
	}
}
