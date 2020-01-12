package programa;

import java.util.InputMismatchException;
import java.util.Scanner;

import biblioteca.*;
import persistencia.HibernateManager;
/*
 * Aquesta és la classe principal del programa on és
 * el mètode Main
 */
public class Main {

	// Necessitarem d'un Scanner per a demanar input de l'usuari
	public static final Scanner sc = new Scanner(System.in);

	/*
	 * Aquest mètode ens imprimeix el menú de les accions que pot fer el programa
	 */
	private static boolean menu() {
		System.out.println("Benvingut a la biblioteca. Què vols fer?");
		System.out.println("\t 1 - Crear un nou Autor.");
		System.out.println("\t 2 - Crear una nova biblioteca.");
		System.out.println("\t 3 - Crear un nou soci.");
		System.out.println("\t 4 - Crear un nou llibre.");
		System.out.println("\t 5 - Esborrar un autor.");
		System.out.println("\t 6 - Esborrar una biblioteca.");
		System.out.println("\t 7 - Esborrar un soci.");
		System.out.println("\t 8 - Esborrar un llibre.");
		System.out.println("\t 9 - Modificar un autor.");
		System.out.println("\t 10 - Modificar una biblioteca.");
		System.out.println("\t 11 - Modificar un soci.");
		System.out.println("\t 12 - Modificar un llibre.");
		System.out.println("\t 13 - Imprimir llistats.");
		System.out.println("\t 14 - Sortir.");
		return opcio();
	}

	/*
	 * Aquest mètode truca a altres mètodes depenent de la opció escollida
	 */
	private static boolean opcio() {
		int opcio = validateIntInput();

		switch (opcio) {
		case 1:
			BridgeClass.creaAutor();
			break;
		case 2:
			BridgeClass.creaBiblio();
			break;
		case 3:
			BridgeClass.creaPersona();
			break;
		case 4:
			BridgeClass.creaLlibre();
			break;
		case 5:
			HibernateManager.deleteInstance(Autor.class, BridgeClass.returnIdFromUserInput(Autor.class));
			break;
		case 6:
			HibernateManager.deleteInstance(Biblioteca.class, BridgeClass.returnIdFromUserInput(Biblioteca.class));
			break;
		case 7:
			HibernateManager.deleteInstance(Persona.class, BridgeClass.returnIdFromUserInput(Persona.class));
			break;
		case 8:
			HibernateManager.deleteInstance(Llibre.class, BridgeClass.returnIdFromUserInput(Llibre.class));
			break;
		case 9:
			BridgeClass.modificaAutor();
			break;
		case 10:
			BridgeClass.modificaBiblioteca();
			break;
		case 11:
			BridgeClass.modificaPersona();
			break;
		case 12:
			BridgeClass.modificaLlibre();
			break;			
		case 13:
			BridgeClass.solicitaLlistat();
			break;
		case 14:
			System.out.println("Fins aviat!");
			return false;
		default:
			System.out.println("Aquesta opcio no es a la llista!");
		}
		return true;
	}

	/*
	 * Aquest mètode ens comprova si s'ha introduit un integer o no quan esperem un.
	 */
	public static int validateIntInput() {
		int n;
		while (true) {
			try {
				n = sc.nextInt();
				sc.nextLine();
				return n;
			} catch (InputMismatchException ime) {
				System.out.println("Introdueix un número enter!");
				sc.nextLine();
			}
		}
	}

	/*
	 * Mètode main de l'aplicació
	 */
	public static void main(String[] args) {
		boolean end;
		// Comprovem si existeix cap instancia, he posat biblioteca, però podria
		// haver posat altra classe persistida també
		if (HibernateManager.getInstances(Biblioteca.class).size() == 0)
			// Si no hi ha cap instancia, truquem a initialize
			// Sempre es trucarà a aquest mètode ja que Hibernate sempre fa un drop i create
			// cada cop que inicia l'aplicació
			BridgeClass.initialize();
		do{
			end = menu();
		}while(end);
		HibernateManager.finish();
		sc.close();
	}
}
