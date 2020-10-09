import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.sql.*;

public class InsertNewPassenger extends JFrame {

	private JPanel contentPane;
	private JTextField firstNameText;
	private JTextField lastNameText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertNewPassenger frame = new InsertNewPassenger();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection conn = null;

	/**
	 * Create the frame.
	 */
	public InsertNewPassenger() {
		conn = ConnectSQL.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(96, 50, 81, 29);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(96, 90, 81, 29);
		contentPane.add(lblLastName);
		
		firstNameText = new JTextField();
		firstNameText.setBounds(186, 54, 156, 20);
		contentPane.add(firstNameText);
		firstNameText.setColumns(10);
		
		lastNameText = new JTextField();
		lastNameText.setBounds(186, 94, 156, 20);
		contentPane.add(lastNameText);
		lastNameText.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String maxIDQuery = "SELECT MAX(passenger_id) AS MaxID FROM Passenger";
					PreparedStatement findMaxID = conn.prepareStatement(maxIDQuery);
					ResultSet maxID = findMaxID.executeQuery();
					
					int maxIDNum = 0;
					
					while(maxID.next()){
						maxIDNum = maxID.getInt(1);
					}
					
					String insertQuery = "INSERT INTO Passenger(passenger_id,first_name,last_name, miles)" + 
										 "VALUES(?,?,?,0)";
					PreparedStatement insertPass = conn.prepareStatement(insertQuery);
					
					if(firstNameText.getText().equals("") || lastNameText.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Please don't leave text field blank!");
					}
					else{
						insertPass.setInt(1, maxIDNum + 1);
						insertPass.setString(2, firstNameText.getText());
						insertPass.setString(3, lastNameText.getText());
						insertPass.executeUpdate();
						JOptionPane.showMessageDialog(null,  "The profile for passenger " + 
															 (maxIDNum + 1) + " " + firstNameText.getText() + 
															 " " + lastNameText.getText() + " was created.");
					}
					findMaxID.close();
					insertPass.close();
					maxID.close();
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnSubmit.setBounds(176, 164, 91, 23);
		contentPane.add(btnSubmit);
	}
}
