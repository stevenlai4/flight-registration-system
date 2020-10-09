import java.sql.*;

class ConnectSQL {

	public static Connection dbConnector(){
		Connection con = null;
		String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=AirportDatabase;" +
							   "user=sa;password=Ss58824001";
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			System.out.println("Connection Established");
			return con;
		}
		catch(Exception e) {
			return null;
		}
	}
}
