package programa;

import java.util.InputMismatchException;
import java.util.Scanner;

import biblioteca.*;
import persistencia.HibernateManager;

public class Main {

	public static final Scanner sc = new Scanner(System.in);

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

	public static void main(String[] args) {
		boolean end;
		if (HibernateManager.getInstances(Biblioteca.class).size() == 0)
			BridgeClass.initialize();
		do{
			end = menu();
		}while(end);
		HibernateManager.finish();
		sc.close();
	}
}
