package forHonor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Clase principal que ejecuta las acciones
 */
public class Main {

	/*
	 * Objetos necesarios y atributos de la clase principal
	 */
	ArrayList<Faccion> facciones;
	ArrayList<Personaje> personajes;
	static DBMetodes dbM;
	Scanner sc = new Scanner(System.in);

	/*
	 * Metodo que imprime el menu de la aplicacion
	 */
	public void menu() {
		System.out.println("Que quieres hacer?");
		System.out.println("1 - Agregar nueva faccion");
		System.out.println("2 - Agregar nuevo personaje");
		System.out.println("3 - Muestra personajes de la faccion Caballeros");
		System.out.println("4 - Muestra el samurai con mas ataque");
		System.out.println("5 - Salir");
	}

	/*
	 * Metodo que realiza las acciones del menu dependiendo de lo que el usuario
	 * haya escogido
	 */
	public boolean acciones() throws SQLException {
		int opcio = 0;
		System.out.println("Introduce la opcion:");

		try {
			opcio = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Tiene que ser un numero!");
			sc.nextLine();
			return false;
		}

		switch (opcio) {
		default:
			System.out.println("Tiene que ser un numero entre el 1 y el 5");
			break;

		case 1:
			insertaFaccion();
			break;

		case 2:
			insertaPersonaje();
			break;

		case 3:
			dbM.muestraCaballeros();
			break;

		case 4:
			dbM.muestraSamuraiMasAtaque();
			break;

		case 5:
			System.out.println("Cerrando aplicacion...");
			sc.close();
			return true;
		}
		return false;
	}

	/*
	 * Metodo que inserta una faccion en el arraylist de facciones y crea una
	 * instancia de la faccion para poder meterla en la base de datos.
	 */
	public void insertaFaccion() throws SQLException {
		int id = 0;
		String nombre, lore;
		boolean ok = false;

		while (!ok) {
			System.out.println("Introduce el id de la faccion:");
			id = compruebaNumerico();

			if (existeIDFaccion(id))
				System.out.println("Este id de faccion ya existe!");
			else
				ok = true;
		}

		System.out.println("Introduce el nombre de la faccion:");
		nombre = sc.nextLine();

		System.out.println("Introduce el lore de la faccion: ");
		lore = sc.nextLine();

		Faccion f = new Faccion(id, nombre, lore);
		facciones.add(f);
		// Llamamos al metodo que inserta una nueva faccion
		dbM.insertaNuevaFaccion(f);
	}

	/*
	 * Metodo que inserta un personaje en el arraylist de personajes y crea una
	 * instancia del personaje para poder meterlo en la base de datos.
	 */
	public void insertaPersonaje() throws SQLException {
		int id = 0, ataque = 0, defensa = 0, faccion_id = 0;
		String nombre;
		boolean ok = false;

		while (!ok) {
			System.out.println("Introduce el id del personaje:");
			id = compruebaNumerico();

			if (existeIDPersonaje(id)) {
				System.out.println("Este id de personaje ya existe!");
			} else {
				ok = true;
			}
		}
		ok = false;

		System.out.println("Introduce el nombre del personaje: ");
		nombre = sc.nextLine();

		System.out.println("Introduce el ataque del personaje: ");
		ataque = compruebaNumerico();

		System.out.println("Introduce la defensa del personaje: ");
		defensa = compruebaNumerico();

		while (!ok) {
			System.out.println("Introduce el id de la faccion: ");
			faccion_id = compruebaNumerico();
			if (!existeIDFaccion(faccion_id)) {
				System.out.println("Esa faccion no existe!");
			} else {
				ok = true;
			}
		}

		Personaje p = new Personaje(id, nombre, ataque, defensa, faccion_id);
		personajes.add(p);
		// Llamamos al metodo que inserta el personaje en la base de datos
		dbM.insertaNuevoPersonaje(p);
	}

	/*
	 * Metodo que comprueba si existe el ID dentro de las facciones. Muy util para
	 * revisar que no se repita un ID a la hora de crear una faccion o para
	 * comprobar que la faccion que se le asigna a un personaje existe.
	 */
	public boolean existeIDFaccion(int id) {
		for (Faccion f : facciones) {
			if (f.getId() == id) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Metodo que comprueba si existe el ID del personaje. Muy util para no permitir
	 * que se repita el ID entre distintos personajes.
	 */
	public boolean existeIDPersonaje(int id) {
		for (Personaje p : personajes) {
			if (p.getId() == id) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Metodo que comprueba si el valor introducido es numerico o no.
	 */
	public int compruebaNumerico() {
		int num;
		while (true) {
			try {
				num = sc.nextInt();
				sc.nextLine();
				return num;
			} catch (InputMismatchException e) {
				System.out.println("Tiene que ser un valor numerico!");
				sc.nextLine();
			}
		}
	}

	/*
	 * Metodo main principal que llama a los distintos metodos que realizan las acciones de la aplicacion
	 */
	public static void main(String[] args) {
		Main m = null;
		dbM = new DBMetodes();
		
		try {
			if (!dbM.tableExists()) {
				dbM.inicialitza();
			}
			m = dbM.retornaObjectes();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			do {
				m.menu();
			} while (!m.acciones());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Getters y setters de la clase principal (Para las arrayLists)
	 */
	public ArrayList<Faccion> getFacciones() {
		return facciones;
	}

	public void setFacciones(ArrayList<Faccion> facciones) {
		this.facciones = facciones;
	}

	public ArrayList<Personaje> getPersonajes() {
		return personajes;
	}

	public void setPersonajes(ArrayList<Personaje> personajes) {
		this.personajes = personajes;
	}

}
