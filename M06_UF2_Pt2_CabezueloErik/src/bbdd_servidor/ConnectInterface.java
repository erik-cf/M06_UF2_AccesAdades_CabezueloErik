package bbdd_servidor;

/*
 * Interfaç on es desen totes les dades de connexio a MySQL
 */
public interface ConnectInterface {

	String HOST = "localhost";
	String PORT = "3306";
	String USER = "root";
	String PASSWORD = "P@ssw0rd";
	String DATABASE = "videoclub";
	String DRIVER = "com.mysql.cj.jdbc.Driver";
	String CONNSTRING = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
	
		
}
