package bbdd_servidor;

public interface ConnectInterface {

	String HOST = "localhost";
	String PORT = "3306";
	String USER = "root";
	String PASSWORD = "P@ssw0rd";
	String DATABASE = "videoclub";
	String DRIVER = "com.mysql.jdbc.Driver";
	String CONNSTRING = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
	
		
}
