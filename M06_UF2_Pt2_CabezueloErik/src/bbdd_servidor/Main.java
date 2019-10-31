package bbdd_servidor;

import java.sql.SQLException;

public class Main {
	
	static DBMetodes dbM;
	
	public static void menu() {
		
	}
	
	public static boolean accions() {
		return true;
	}
	
	public static void main(String[] args) {
		try {
			dbM = new DBMetodes();
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: No s'ha pogut carregar el driver.");
		} catch (SQLException e) {
			System.out.println("ERROR: No s'ha pogut fer la connexio");
		}
		
		do {
			menu();
		}while(accions());
		
	}

}
