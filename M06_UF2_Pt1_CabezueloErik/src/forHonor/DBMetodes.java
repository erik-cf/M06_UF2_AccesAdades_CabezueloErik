package forHonor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * Clase que contiene todos los metodos que gestionan la base de datos
 */
public class DBMetodes implements ConnectInterface {

	/*
	 * Metodo de conexion a la base de datos
	 */
	public Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection(connection);
		return conn;
	}

	/*
	 * Metodo que inicializa la base de datos
	 */
	public void inicialitza() throws SQLException {
		// Creamos la tabla de faccion
		String creaTaulaFaccion = "CREATE TABLE IF NOT EXISTS faccion (" + "faccion_id INTEGER PRIMARY KEY,"
				+ "nombre_faccion VARCHAR(15)," + "lore VARCHAR(200)" + ");";
		Statement stmnt = connect().createStatement();
		stmnt.execute(creaTaulaFaccion);

		// Creamos la tabla de personaje
		String creaTaulaPersonaje = "CREATE TABLE IF NOT EXISTS personaje (personaje_id INTEGER PRIMARY KEY,"
				+ "nombre_personaje VARCHAR(15)," + "ataque INTEGER," + "defensa INTEGER," + "faccion_id INTEGER,"
				+ "FOREIGN KEY (faccion_id) REFERENCES faccion(faccion_id)" + ");";
		stmnt.execute(creaTaulaPersonaje);

		// Llamamos a los metodos que insertan los primeros valores en las tablas
		creaPrimeresFaccions();
		creaPrimersPersonatges();
	}

	/*
	 * Metodo que inserta las primeras facciones en la tabla faccion
	 */
	public void creaPrimeresFaccions() throws SQLException {
		String lore, insertaDadesFaccions;
		Statement stmnt = connect().createStatement();
		lore = "Los caballeros de Ashfeld son paradigmas del poder. La Legi�n de Hierro los envi� para llevar la paz a esas tierras. Desde entonces disfrutan de la libertad y han hecho de Ashfeld su hogar.";
		insertaDadesFaccions = "INSERT INTO faccion VALUES(1, 'Caballeros', '" + lore + "');";
		stmnt.execute(insertaDadesFaccions);

		lore = "Los vikingos desaparecieron hace siglos, tras escapar de sus derruidas tierras natales en busca de costas desconocidas. Los caballeros conquistaron a aquellos que se quedaron atr�s y los incorporaron a su cultura.";
		insertaDadesFaccions = "INSERT INTO faccion VALUES(2, 'Vikingos', '" + lore + "');";
		stmnt.execute(insertaDadesFaccions);

		lore = "La historia no ha sido amable con los samur�is. Originarios de una tierra muy lejana, allende los mares, cuentan la historia de un emperador y una patria que desaparecieron entre el mar y el fuego.";
		insertaDadesFaccions = "INSERT INTO faccion VALUES(3, 'Samur�is', '" + lore + "');";
		stmnt.execute(insertaDadesFaccions);

		lore = "Los Wu Lin son una facci�n de guerreros del interior de las murallas de la antigua China. Ahora, cuatro guerreros Wu Lin viajan al oeste en busca de venganza por las guerras, las traiciones y las tragedias personales. Combaten para reclamar su sitio en la pr�xima dinast�a.";
		insertaDadesFaccions = "INSERT INTO faccion VALUES(4, 'Wu Lin', '" + lore + "');";
		stmnt.execute(insertaDadesFaccions);
	}

	/*
	 * Metodo que inserta los primeros personajes en la tabla personaje
	 */
	public void creaPrimersPersonatges() throws SQLException {
		Statement stmnt = connect().createStatement();
		String insertaDadesPersonatge;

		insertaDadesPersonatge = "INSERT INTO personaje VALUES (1, 'Aramusha', 35, 20, 3);";
		stmnt.execute(insertaDadesPersonatge);

		insertaDadesPersonatge = "INSERT INTO personaje VALUES (2, 'Orochi', 80, 45, 3);";
		stmnt.execute(insertaDadesPersonatge);

		insertaDadesPersonatge = "INSERT INTO personaje VALUES (3, 'Berserker', 30, 30, 2);";
		stmnt.execute(insertaDadesPersonatge);

		insertaDadesPersonatge = "INSERT INTO personaje VALUES (4, 'The Raider', 100, 95, 2);";
		stmnt.execute(insertaDadesPersonatge);

		insertaDadesPersonatge = "INSERT INTO personaje VALUES (5, 'The Warden', 90, 120, 1);";
		stmnt.execute(insertaDadesPersonatge);

		insertaDadesPersonatge = "INSERT INTO personaje VALUES (6, 'Ademar', 70, 80, 1);";
		stmnt.execute(insertaDadesPersonatge);

		insertaDadesPersonatge = "INSERT INTO personaje VALUES (7, 'Julius', 80, 105, 1);";
		stmnt.execute(insertaDadesPersonatge);

		insertaDadesPersonatge = "INSERT INTO personaje VALUES (8, 'Shidou', 50, 40, 4);";
		stmnt.execute(insertaDadesPersonatge);

		insertaDadesPersonatge = "INSERT INTO personaje VALUES (9, 'Wei Chang', 70, 55, 4);";
		stmnt.execute(insertaDadesPersonatge);
	}

	/*
	 * Metodo que comprueba si existe la tabla facciones. Es muy util para saber si esta inicializada la base de datos o no.
	 */
	public boolean tableExists() throws SQLException {
		ResultSet rs;
		DatabaseMetaData dbMeta = connect().getMetaData();
		String[] types = { "TABLE" };
		// Cogemos las tablas en un resultset que coincidan con 'faccion'
		rs = dbMeta.getTables("", "", "faccion", types);
		// Si el resultset tiene algun campo, quiere decir que existe la tabla
		if (rs.next()) {
			return true;
		}
		return false;
	}

	/*
	 * Metodo que crea los objetos del programa a partir de los valores de la base de datos
	 */
	public Main retornaObjectes() throws SQLException {
		Main m = new Main();
		ArrayList<Faccion> aLF = new ArrayList<Faccion>();
		Faccion f;
		ArrayList<Personaje> aLP = new ArrayList<Personaje>();
		Personaje p;
		String select;
		ResultSet rs;
		Statement stmnt = connect().createStatement();

		// Seleccionamos todas las facciones
		select = "SELECT * FROM faccion";
		rs = stmnt.executeQuery(select);
		while (rs.next()) {
			f = new Faccion();
			f.setId(rs.getInt("faccion_id"));
			f.setNombre(rs.getString("nombre_faccion"));
			f.setLore(rs.getString("lore"));
			aLF.add(f);
		}
		// Le hacemos set al arrayList de facciones del Main
		m.setFacciones(aLF);

		// Seleccionamos todos los personajes
		select = "SELECT * FROM personaje";
		rs = stmnt.executeQuery(select);
		while (rs.next()) {
			p = new Personaje();
			p.setId(rs.getInt("personaje_id"));
			p.setNombre(rs.getString("nombre_personaje"));
			p.setAtaque(rs.getInt("ataque"));
			p.setDefensa(rs.getInt("defensa"));
			p.setFaccion_id(rs.getInt("faccion_id"));
			aLP.add(p);
		}
		// Le hacemos set al arrayList de personajes del Main
		m.setPersonajes(aLP);
		
		return m;
	}

	/*
	 * Metodo que inserta una nueva faccion en la base de datos con los datos del objeto que se le pasa por parametro
	 */
	public void insertaNuevaFaccion(Faccion f) throws SQLException {
		String insertaDatosFaccion;
		Statement stmnt = connect().createStatement();
		insertaDatosFaccion = "INSERT INTO faccion VALUES(" + f.getId() + ", '" + f.getNombre() + "', '" + f.getLore()
				+ "');";
		stmnt.execute(insertaDatosFaccion);
	}

	/*
	 * Metodo que inserta un nuevo personaje en la base de datos con los datos del objeto que se le pasa por parametro
	 */
	public void insertaNuevoPersonaje(Personaje p) throws SQLException {
		String insertaDatosPersonaje;
		Statement stmnt = connect().createStatement();
		insertaDatosPersonaje = "INSERT INTO personaje VALUES(" + p.getId() + ", '" + p.getNombre() + "', "
				+ p.getAtaque() + ", " + p.getDefensa() + ", " + p.getFaccion_id() + ");";
		stmnt.execute(insertaDatosPersonaje);
	}

	/*
	 * Metodo que imprime todos los caballeros que contenga la base de datos
	 */
	public void muestraCaballeros() throws SQLException {
		ResultSet rs;
		String select;
		select = "SELECT * FROM personaje WHERE faccion_id = (SELECT faccion_id FROM faccion WHERE LOWER(nombre_faccion) = 'caballeros')";
		
		Statement stmnt;
		stmnt = connect().createStatement();
		
		rs = stmnt.executeQuery(select);
		while (rs.next()) {
			System.out.println("\n/////////////////////////////////////////\n");
			System.out.println("ID del personaje: " + rs.getInt("personaje_id"));
			System.out.println("Nombre del personaje: " + rs.getString("nombre_personaje"));
			System.out.println("Ataque del personaje: " + rs.getInt("ataque"));
			System.out.println("Defensa del personaje: " + rs.getInt("defensa"));
			System.out.println("ID de la faccion caballeros: " + rs.getInt("faccion_id"));
			System.out.println("\n/////////////////////////////////////////\n");
		}
	}

	/*
	 * Metodo que muestra el samurai con mas ataque de la base de datos
	 */
	public void muestraSamuraiMasAtaque() throws SQLException {
		ResultSet rs;
		String select;
		select = "SELECT personaje_id, nombre_personaje, ataque, defensa, faccion_id FROM personaje WHERE faccion_id = (SELECT faccion_id FROM faccion WHERE LOWER(nombre_faccion) = 'samur�is') AND ataque = (SELECT max(ataque) FROM personaje WHERE faccion_id = (SELECT faccion_id FROM faccion WHERE LOWER(nombre_faccion) = 'samur�is'))";
		
		Statement stmnt;
		stmnt = connect().createStatement();
		rs = stmnt.executeQuery(select);
		
		while (rs.next()) {
			System.out.println("\n/////////////////////////////////////////\n");
			System.out.println("ID del personaje: " + rs.getInt("personaje_id"));
			System.out.println("Nombre del personaje: " + rs.getString("nombre_personaje"));
			System.out.println("Ataque del personaje: " + rs.getInt("ataque"));
			System.out.println("Defensa del personaje: " + rs.getInt("defensa"));
			System.out.println("ID de la faccion samurais: " + rs.getInt("faccion_id"));
			System.out.println("\n/////////////////////////////////////////\n");
		}
	}
}
