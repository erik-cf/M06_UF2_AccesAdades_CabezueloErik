package bbdd_servidor;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	// Objectes que necessitem per a realitzar accions a la classe principal
	static DBMetodes dbM;
	static Scanner sc = new Scanner(System.in);

	/*
	 * Metode que imprimeix el menú de l'aplicacio
	 */
	public static void menu() {
		System.out.println("Benvingut al videoclub millenium. Que vols fer?");
		System.out.println("\t1 - Afegir un nou soci");
		System.out.println("\t2 - Canviar la cuota d'un soci");
		System.out.println("\t3 - Eliminar un soci");
		System.out.println("\t4 - Mostra tots els socis");
		System.out.println("\t5 - Sortir");
	}

	/*
	 * Programa que fa les accions depenent de l'input que faci l'usuari amb les opcions del menu
	 * Retorn un boolea depenent de si l'usuari vol sortir o no
	 */
	public static boolean accions() throws SQLException {
		int opcio, cod_soci;
		String Siono;
		opcio = dbM.comprovaInt();
		switch(opcio) {
		
		default:
			System.out.println("El número ha d'estar entre l'1 i el 5!");
			break;
		
		case 1:
			// Truquem al metode que afegeix un nou soci
			if(dbM.nouSoci())
				// Si s'ha fet, informem
				System.out.println("Afegit nou soci");
			else
				// Si no, informem tambe
				System.out.println("No s'ha pogut afegir el nou soci...");
			break;
		
		case 2:
			// Mostrem els socis
			dbM.showSocis();
			do {
				System.out.println("Vols canviar la cuota d'algun soci? (S/n)");
				Siono = sc.nextLine();
				if(Siono.equalsIgnoreCase("s")) {
					System.out.println("Introdueix el codi del soci al que li vols canviar la cuota: ");
					// Agafem el codi de soci
					cod_soci = dbM.comprovaInt();
					
					// Comprovem si existeix l'ID a la base de dades
					if(dbM.existeixID(cod_soci)) {
					
						// Si existeix truquem al metode de canviar cuota
						if(dbM.canviarCuota(cod_soci))
							System.out.println("Cuota de soci actualitzada");
						else
							System.out.println("No s'ha pogut canviar a cuota del soci...");
					
					}else {
						System.out.println("Aquest soci no existeix!");
					}
					
				}else if(!Siono.equalsIgnoreCase("n")) {
					System.out.println("Has d'escriure 's' per a SI o 'n' per a NO.");
				}
				
			}while(!(Siono.equalsIgnoreCase("s") || Siono.equalsIgnoreCase("n")));
			break;
		
		case 3:
			// Mostrem socis 
			dbM.showSocis();
			System.out.println("Introdueix el codi del soci a eliminar: ");
			
			cod_soci = dbM.comprovaInt();
			if(dbM.existeixID(cod_soci)) {
				// Truquem al metode que elimina el soci
				if(dbM.eliminaSoci(cod_soci))
					System.out.println("Soci eliminat.");
				else
					System.out.println("No s'ha pogut eliminar el soci...");
			
			}else {
				System.out.println("Aquest soci no existeix!");
			}
			break;
			
		case 4:
			dbM.showSocis();
			break;
		
		case 5:
			System.out.println("Sortint de l'aplicacio...");
			sc.close();
			dbM.tancaScanner();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		// Creem l'objecte que executara els metodes per a gestionar la BBDD
		try {
			dbM = new DBMetodes();
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: No s'ha pogut carregar el driver.");
		} catch (SQLException e) {
			System.out.println("ERROR: No s'ha pogut fer la connexio" + e.toString());
			
		}

		// Executem menu i accions
		try {
			do {
				menu();
			} while (accions());
		} catch (SQLException e) {
			System.out.println("Error SQL: " + e.toString());
		}

	}

}
