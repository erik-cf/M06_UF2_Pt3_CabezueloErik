package programa;

import biblioteca.Autor;
import biblioteca.Biblioteca;
import biblioteca.Llibre;
import biblioteca.Persona;
import persistencia.HibernateManager;

/*
 * Aquesta classe ens fa de pont entre la classe Main i la classe
 * HibernateManager, i d0aquí ve el nom de Bridge.
 * Per tal de no tenir la classe Main sobrecarregada de mètodes,
 * els separem aquí.
 */
public class BridgeClass {

	/*
	 * Mètode que s'executa a l'iniciar el programa per a crear una biblioteca, un llibre i un autor
	 * per aquest llibre.
	 */
	public static void initialize() {
		System.out.println("Primer hem de crear una biblioteca i un llibre per a aquesta biblioteca: ");
		System.out.println("Introdueix el nom de la biblioteca: ");
		String nomBiblio = Main.sc.nextLine();
		int idBiblio = HibernateManager.createEmptyBiblioteca(nomBiblio);
		System.out.println("Introdueix nom del llibre: ");
		String nomLlibre = Main.sc.nextLine();
		System.out.println("Introdueix l'editorial: ");
		String editorial = Main.sc.nextLine();
		System.out.println("Introdueix el nom de l'autor: ");
		String nomAutor = Main.sc.nextLine();
		System.out.println("Creant autor " + nomAutor + "...");
		Autor autor = checkAutor(nomAutor);
		int idLlibre = HibernateManager.createLlibre(nomLlibre, editorial, autor);
		autor.addLlibre(HibernateManager.getInstaceFromId(Llibre.class, idLlibre));
		HibernateManager.updateObject(autor);
		HibernateManager.addLlibreToBiblioteca(HibernateManager.getInstaceFromId(Biblioteca.class, idBiblio),
				HibernateManager.getInstaceFromId(Llibre.class, idLlibre));
		HibernateManager.updateObject(HibernateManager.getInstaceFromId(Biblioteca.class, idBiblio));
		HibernateManager.updateObject(HibernateManager.getInstaceFromId(Llibre.class, idLlibre));
		System.out.println("Inicialització finalitzada!");
	}

	/*
	 * Mètode que demana dades per crear l'autor i truca el mètode corresponent
	 * a l'HibernateManager
	 */
	public static void creaAutor() {
		System.out.println("Introdueix el nom de l'autor: ");
		checkAutor(Main.sc.nextLine());

	}

	/*
	 * Mètode que demana dades per crear la biblioteca i truca el mètode corresponent
	 * a l'HibernateManager
	 */
	public static void creaBiblio() {
		System.out.println("Introdueix el nom de la biblioteca: ");
		int id = HibernateManager.createEmptyBiblioteca(Main.sc.nextLine());
		System.out.println("Vols afegir un llibre a la biblioteca? (S per a Si, qualsevol altre tecla per a No)");
		String opt = Main.sc.next();
		if (opt.equalsIgnoreCase("s")) {
			addLlibresToInstance(HibernateManager.getInstaceFromId(Biblioteca.class, id));
		}
	}

	/*
	 * Mètode que demana dades per crear la persona i truca el mètode corresponent
	 * a l'HibernateManager
	 */
	public static void creaPersona() {
		System.out.println("Introdueix el dni de la persona: ");
		int dni = Main.validateIntInput();
		System.out.println("Introdueix el nom de la persona: ");
		String nomPersona = Main.sc.nextLine();
		System.out.println("Introdueix el telefon de la persona: ");
		int telefon = Main.validateIntInput();
		int id = HibernateManager.createEmptyPersona(dni, nomPersona, telefon);
		System.out.println("Vols afegir un llibre a la persona? (S per a Si, qualsevol altre tecla per a No)");
		String opt = Main.sc.next();
		if (opt.equalsIgnoreCase("s")) {
			addLlibresToInstance(HibernateManager.getInstaceFromId(Persona.class, id));
		}
	}

	/*
	 * Mètode que demana dades per crear el llibre i truca el mètode corresponent
	 * a l'HibernateManager
	 */
	public static void creaLlibre() {
		System.out.println("Introdueix nom del llibre: ");
		String nomLlibre = Main.sc.nextLine();
		System.out.println("Introdueix l'editorial: ");
		String editorial = Main.sc.nextLine();
		System.out.println("Introdueix el nom de l'autor: ");
		String nomAutor = Main.sc.nextLine();
		HibernateManager.createLlibre(nomLlibre, editorial, checkAutor(nomAutor));
	}

	/*
	 * Mètode que imprimeix els llibres disponibles i demana un id per afegir-ho a
	 * la instancia d'una classe generica que se li passa per paràmetre
	 */
	public static <T> void addLlibresToInstance(T t) {
		int idLlibre;
		HibernateManager.printInstances(Llibre.class);
		do {
			System.out.println("Introdueix els ID dels llibres a afegir (separats per salts de línia) (0 per sortir):");
			idLlibre = Main.validateIntInput();
			if (idLlibre != 0 && HibernateManager.getInstaceFromId(Llibre.class, idLlibre) != null) {
				if (t.getClass().equals(Persona.class))
					HibernateManager.addLlibreToPersona((Persona) t,
							HibernateManager.getInstaceFromId(Llibre.class, idLlibre));
				else if (t.getClass().equals(Biblioteca.class))
					HibernateManager.addLlibreToBiblioteca((Biblioteca) t,
							HibernateManager.getInstaceFromId(Llibre.class, idLlibre));
			} else {
				if (idLlibre != 0) {
					System.out.println("Aquest llibre no existeix!!!");
				}
			}
		} while (idLlibre != 0);
	}

	/*
	 * Mètode que demana quin llistat es vol imprimir
	 */
	public static void solicitaLlistat() {
		System.out.println("Quina informacio vols veure? ");
		System.out.println("1 - Autors");
		System.out.println("2 - Biblioteques");
		System.out.println("3 - Llibres");
		System.out.println("4 - Socis");
		switch (Main.validateIntInput()) {
		case 1:
			HibernateManager.printInstances(Autor.class);
			break;
		case 2:
			HibernateManager.printInstances(Biblioteca.class);
			break;
		case 3:
			HibernateManager.printInstances(Llibre.class);
			break;
		case 4:
			HibernateManager.printInstances(Persona.class);
			break;
		default:
			System.out.println("Has ficat un numero fora de rang!\n");
		}
	}

	/*
	 * Mètode que retorna un autor si existeix(el busca pel nom), i si no existeix, el crea
	 */
	public static Autor checkAutor(String nomAutor) {
		Autor autor;
		if ((autor = HibernateManager.getAutorFromName(nomAutor)) == null)
			return HibernateManager.createAutor(nomAutor);
		System.out.println("Ja existeix l'autor " + nomAutor + ", no fa falta crear-ho.");
		return autor;
	}

	/*
	 * Mètode que imprimeix un llistat i demana l'identificador d'un dels objectes impresos
	 * per a retornar-lo
	 */
	public static <T> int returnIdFromUserInput(Class<T> classType) {
		HibernateManager.printInstances(classType);
		String identificador;
		if (classType.equals(Persona.class)) {
			identificador = "DNI";
		} else {
			identificador = "ID";
		}
		System.out.println("Introdueix el " + identificador + " de " + classType.getName() + ":");
		return Main.validateIntInput();
	}

	/*
	 * Mètode que torna a demanar els camps de biblioteca per tal de modificar-los
	 * i actualitzar la instància persistida.
	 */
	public static void modificaBiblioteca() {
		int id = returnIdFromUserInput(Biblioteca.class);
		System.out.println("Introdueix el nou nom: ");
		Biblioteca b = (Biblioteca) HibernateManager.getInstaceFromId(Biblioteca.class, id);
		if(b == null) {
			System.out.println("Aquesta biblioteca no existeix!");
			return;
		}
		b.setNom(Main.sc.nextLine());
		System.out.println("Vols afegir un llibre a la biblioteca? (S per a Si, qualsevol altre tecla per a No)");
		String opt = Main.sc.next();
		if (opt.equalsIgnoreCase("s")) {
			addLlibresToInstance(HibernateManager.getInstaceFromId(Biblioteca.class, id));
		}
		HibernateManager.updateObject(b);
	}

	/*
	 * Mètode que torna a demanar els camps de persona per tal de modificar-los
	 * i actualitzar la instància persistida.
	 */
	public static void modificaPersona() {
		int id = returnIdFromUserInput(Persona.class);
		System.out.println("Introdueix el nou nom: ");
		Persona p = (Persona) HibernateManager.getInstaceFromId(Persona.class, id);
		if(p == null) {
			System.out.println("Aquest soci no existeix!");
			return;
		}
		p.setNom(Main.sc.nextLine());
		System.out.println("Introdueix el nou telefon: ");
		p.setTelefon(Main.validateIntInput());
		System.out.println("Vols afegir un llibre al soci? (S per a Si, qualsevol altre tecla per a No)");
		String opt = Main.sc.next();
		if (opt.equalsIgnoreCase("s")) {
			addLlibresToInstance(HibernateManager.getInstaceFromId(Persona.class, id));
		}
		HibernateManager.updateObject(p);
	}
	
	/*
	 * Mètode que torna a demanar els camps d'autor per tal de modificar-los
	 * i actualitzar la instància persistida.
	 */
	public static void modificaAutor() {
		int id = returnIdFromUserInput(Autor.class);
		System.out.println("Introdueix el nou nom: ");
		Autor a = (Autor) HibernateManager.getInstaceFromId(Autor.class, id);
		if(a == null) {
			System.out.println("Aquest autor no existeix!");
			return;
		}
		a.setNom(Main.sc.nextLine());
		HibernateManager.updateObject(a);
	}
	
	/*
	 * Mètode que torna a demanar els camps de llibre per tal de modificar-los
	 * i actualitzar la instància persistida.
	 */
	public static void modificaLlibre() {
		int id = returnIdFromUserInput(Llibre.class);
		System.out.println("Introdueix el nou nom: ");
		Llibre l = (Llibre) HibernateManager.getInstaceFromId(Llibre.class, id);
		if(l == null) {
			System.out.println("Aquest llibre no existeix!");
			return;
		}
		l.setNom(Main.sc.nextLine());
		System.out.println("Introdueix la nova editorial: ");
		l.setEditorial(Main.sc.nextLine());
		System.out.println("Introdueix el nou autor: ");
		l.setAutor(HibernateManager.getInstaceFromId(Autor.class, returnIdFromUserInput(Autor.class)));
		HibernateManager.updateObject(l);
	}
}