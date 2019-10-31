package bbdd_servidor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DBMetodes implements ConnectInterface {

	/*
	 * Creem els atributs de la classe
	 */
	private Connection conn;
	private Scanner sc = new Scanner(System.in);
	private int lastCodSoci;

	/*
	 * Un constructor que fara la connexio i comprovara l'últim cod_soc
	 */
	public DBMetodes() throws SQLException, ClassNotFoundException {

		Class.forName(DRIVER);
		conn = DriverManager.getConnection(CONNSTRING, USER, PASSWORD);

		lastCodSoci = getNextCodSoc();
		if (lastCodSoci == 0) {
			System.out.println("S'ha arribat al maxim de socis...");
			System.out.println("Tancant aplicacio...");
			System.exit(0);
		}
	}

	/*
	 * Metode que inserta un nou soci a la base de dades demanant per teclat la
	 * informacio del nou soci
	 */
	public boolean nouSoci() throws SQLException {
		// Creem l'insert i el preparem per al 'PreparedStatement'
		String insert = "INSERT INTO socio VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// Creem el prepared statement amb l'insert anterior
		PreparedStatement pstmnt = conn.prepareStatement(insert);

		// Afegim el nou codi de soci que agafem de la classe
		pstmnt.setInt(1, lastCodSoci);
		lastCodSoci += 1;

		// Agafem la resta de dades per teclat
		System.out.println("Introdueix el nom del nou soci: (max. 20 caracters)");
		pstmnt.setString(2, comprovaLengthParaula(20));

		System.out.println("Introdueix els cognoms del nou soci: (max. 20 caracters)");
		pstmnt.setString(3, comprovaLengthParaula(20));

		System.out.println("Introdueix la direccio del nou soci: (max. 20 caracters)");
		pstmnt.setString(4, comprovaLengthParaula(20));

		System.out.println("Introdueix el telefon (max. 10 caracters)");
		pstmnt.setString(5, comprovaLengthParaula(10));

		System.out.println("Introdueix la poblacio del soci (max. 30 caracters)");
		pstmnt.setString(6, comprovaLengthParaula(30));

		System.out.println("Introdueix el CP del soci: (max. 5 caracters)");
		pstmnt.setString(7, comprovaLengthParaula(5));

		System.out.println("Introdueix la provincia del soci: (max. 20 caracters)");
		pstmnt.setString(8, comprovaLengthParaula(20));

		System.out.println("Introdueix el pais del soci: (max. 10 caracters)");
		pstmnt.setString(9, comprovaLengthParaula(10));

		System.out.println("Introdueix l'edat del soci: (Maxim 11 xifres)");
		pstmnt.setInt(10, comprovaXifres(11));

		// La data d'alta serà la d'avui
		pstmnt.setDate(11, new Date(new java.util.Date().getTime()));

		System.out.println("Introdueix la cuota del soci: (Maxim 11 xifres)");
		pstmnt.setInt(12, comprovaXifres(11));

		// Si no s'ha fet cap insert, retornarem false
		if (pstmnt.executeUpdate() == 0)
			return false;

		// En cap altre cas, retornem true
		return true;
	}

	/*
	 * Metode que actualitza la cuota del soci que se li passi per parametre
	 */
	public boolean canviarCuota(int cod_soc) throws SQLException {
		// Preparem l'String amb l'update
		String update = "UPDATE socio SET cuota = ? WHERE cod_soc = ?";
		PreparedStatement pstmnt = conn.prepareStatement(update);

		System.out.println("Introdueix la nova cuota: ");
		pstmnt.setInt(1, comprovaXifres(11));

		pstmnt.setInt(2, cod_soc);

		// Si no s'actualitza cap camp, retornem false
		if (pstmnt.executeUpdate() == 0)
			return false;

		// En cap altre cas, retornem true
		return true;
	}

	/*
	 * Metode que elimina un soci de la base de dades
	 */
	public boolean eliminaSoci(int cod_soc) throws SQLException {
		// Preparem l'String amb el delete
		String delete = "DELETE FROM socio WHERE cod_soc = ?";

		// Creem el preparedStatement amb el delete creat
		PreparedStatement pstmnt = conn.prepareStatement(delete);
		pstmnt.setInt(1, cod_soc);

		// Si no s'elimina cap soci, retornem false
		if (pstmnt.executeUpdate() == 0)
			return false;

		// En cap altre cas, retornem true
		return true;
	}

	/*
	 * Metode que mostra els socis per pantalla
	 */
	public void showSocis() throws SQLException {
		// Fem el SELECT dels camps necessaris
		String select = "SELECT cod_soc, nombre, apellidos, cuota FROM socio";
		ResultSet rs;
		Statement stmnt = conn.createStatement();
		rs = stmnt.executeQuery(select);

		// Recorrem la llista de resultats per imprimir les dades
		while (rs.next()) {
			System.out.println("\nSoci amb codi " + rs.getInt("cod_soc"));
			System.out.println("---------------------------------------");
			System.out.println("\tNom: " + rs.getString("nombre"));
			System.out.println("\tCognoms: " + rs.getString("apellidos"));
			System.out.println("\tCuota: " + rs.getInt("cuota"));
			System.out.println("---------------------------------------\n");
		}
	}

	/*
	 * Metode que comprova si el que s'ha ficat per teclat es un numero Integer
	 */
	public int comprovaInt() {
		boolean inputOK = false;
		int num = 0;

		do {
			try {
				num = sc.nextInt();
				sc.nextLine();

				// Com que no gestionem numeros negatius, tambe ho comprovem
				if (num < 0) {
					System.out.println("El numero no pot ser negatiu!");
				} else {
					inputOK = true;
				}

			} catch (InputMismatchException e) {
				System.out.println("Has d'afegir un numero enter!");
				sc.nextLine();
			}
		} while (!inputOK);

		// Retornem el num si es un Integer
		return num;
	}

	/*
	 * Metode que comprova la llargada de les paraules, si son mes gran del que se
	 * li passa per parametre, mostra un error
	 */
	public String comprovaLengthParaula(int max) {
		String paraula = "";
		boolean inputOK = false;

		do {
			paraula = sc.nextLine();

			// Si la llargada es mes gran que el maxim, o és buida
			if (paraula.length() > max || paraula.equals("")) {

				if (max != 1)
					System.out.println("La paraula no pot tenir mes de " + max + " caracters!");
				else
					System.out.println("La paraula no pot tenir mes d'" + max + " caracter!");

			} else {

				inputOK = true;

			}
		} while (!inputOK);

		// Retornem la paraula o frase
		return paraula;

	}

	/*
	 * Metode que comprova quantes xifres te un numero per si no cap a la base de dades
	 * Se li passa per parametre el maxim de xifres del numero
	 */
	public int comprovaXifres(int max) {
		int num;
		int numMaxim = 1;
		boolean inputOK = false;

		// Multipliquem per 10 tantes vegades com sigui el maxim de xifres
		for (int i = 0; i < max; i++) {
			numMaxim = numMaxim * 10;
		}

		do {

			num = comprovaInt();

			if (num < numMaxim) {
				inputOK = true;
			} else {

				if (max != 1)
					System.out.println("El numero no pot sobrepassar les " + max + " xifres.");
				else
					System.out.println("El numero no pot sobrepassar d'" + max + " xifra.");

			}
		} while (!inputOK);

		// Si el numero de xifres es correcte, retornem true
		return num;
	}

	/*
	 * Metode que comprova si ja existeix el codi de soci que se li passa per parametre
	 */
	public boolean existeixID(int ID) throws SQLException {
		String select = "SELECT * FROM socio WHERE COD_SOC = " + ID;
		ResultSet rs;
		Statement stmnt = conn.createStatement();
		rs = stmnt.executeQuery(select);
		
		// Si troba cap resultat retorna true com que si que existeix
		if (rs.next())
			return true;
		
		// Si no, retorna false
		return false;
	}

	/*
	 * Metode que agafa el següent numero disponible del camp cod_soc de la base de dades
	 */
	public int getNextCodSoc() throws SQLException {
		// Fem un SELECT i ordenem de major a petit, perquè el primer resultat sigui el numero mes gran
		String select = "SELECT cod_soc FROM socio ORDER BY cod_soc DESC";
		ResultSet rs;
		Statement stmnt = conn.createStatement();
		int lastCod;
		rs = stmnt.executeQuery(select);

		// Si hi ha cap resultat, seguim comprovacions
		if (rs.next()) {
			lastCod = rs.getInt("COD_SOC");
			
			// Si el codi te mes de 3 xifres, no hi cabra al camp cod_soc de la base de dades (longitud 2)
			if ((lastCod + 1) >= 100) {
				// Retornem 0 com valor comodi
				return 0;
				
			} else {
				// Si no, retornem el valor més 1, perquè és el següent disponible
				return lastCod + 1;
			}
		}
		
		// Si no existeix cap codi de soci, retornem 1
		return 1;
	}
	
	/*
	 * Metode que tanca l'Scanner de la classe
	 */
	public void tancaScanner() {
		sc.close();
	}
}
