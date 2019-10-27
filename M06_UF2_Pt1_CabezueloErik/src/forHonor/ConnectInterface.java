package forHonor;

import java.sql.Connection;
import java.sql.SQLException;

/*
 * Interfaz que contiene el String de conexion y la definicion del metodo connect()
 */
public interface ConnectInterface {
	// String de conexion
	String connection = "jdbc:sqlite:forHonor.db";
	
	// Definicion del metodo connect()
	public Connection connect() throws SQLException;

}
