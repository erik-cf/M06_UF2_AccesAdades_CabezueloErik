package bbdd_servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBMetodes implements ConnectInterface {
	
	private Connection conn;
	
	public DBMetodes() throws SQLException, ClassNotFoundException {
		Class.forName(DRIVER);
		conn = DriverManager.getConnection(CONNSTRING, USER, PASSWORD);
	}
	
	public Connection getConnection() {
		return conn;
	}
}
