package persistencia;

/*
 * Aquesta classe cont� els m�todes que gestionen les accions d'Hibernate
 * com desar classes, eliminar-les, o actualitzar-les.
 * Tot el que s'hagi de fer mitjan�ant una sessi� d'Hibernate
 * estar� en aquesta classe.
 */
import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import biblioteca.Autor;
import biblioteca.Biblioteca;
import biblioteca.Llibre;
import biblioteca.Persona;

public class HibernateManager {
	
	// Inicialitzem la sessi�, que ser� l'encarregada de gestionar totes les accions d'Hibernate
	private static Session session = HibernateUtil.getSessionFactory().openSession();

	/*
	 * M�tode que desa un autor a la base de dades
	 */
	public static Autor createAutor(String nom) {
		Transaction t = session.beginTransaction();
		Autor a = new Autor();
		a.setNom(nom);
		a = session.get(Autor.class, session.save(a));
		t.commit();
		return a;
	}

	/*
	 * M�tode que desa un llibre a la base de dades
	 */
	public static Integer createLlibre(String nom, String editorial, Autor autor) {
		Transaction t = session.beginTransaction();
		Llibre l = new Llibre();
		l.setAutor(autor);
		l.setEditorial(editorial);
		l.setNom(nom);
		Integer n = (Integer) session.save(l);
		t.commit();
		return n;
	}

	/*
	 * M�tode que desa una biblioteca (sense llibres) a la base de dades
	 */
	public static Integer createEmptyBiblioteca(String nom) {
		Transaction t = session.beginTransaction();
		Biblioteca b = new Biblioteca();
		b.setNom(nom);
		Integer n = (Integer) session.save(b);
		t.commit();
		return n;
	}

	/*
	 * M�tode que desa una Persona (sense llibres) a la base de dades
	 */
	public static Integer createEmptyPersona(int dni, String nom, int telefon) {
		Transaction t = session.beginTransaction();
		Persona p = new Persona();
		p.setDni(dni);
		p.setTelefon(telefon);
		p.setNom(nom);
		Integer n = (Integer) session.save(p);
		t.commit();
		return n;
	}

	/*
	 * M�tode que afegeix un llibre a una biblioteca i actualitza els objectes
	 */
	public static void addLlibreToBiblioteca(Biblioteca b, Llibre l) {
		b.addLlibre(l);
		l.addBiblioteca(b);
		updateObject(b);
		updateObject(l);
	}

	/*
	 * M�tode que afegeix un llibre a una persona i actualitza els objectes
	 */
	public static void addLlibreToPersona(Persona p, Llibre l) {
		p.addLlibre(l);
		l.addPersona(p);
		updateObject(p);
		updateObject(l);
	}

	/*
	 * M�tode que retorna un array amb totes les inst�ncies d'una classe
	 * que se li passa per par�metre
	 */
	public static <T>ArrayList<T> getInstances(Class<T> classType) {
		Query q = session.createQuery("from " + classType.getName());
		return (ArrayList<T>) q.getResultList();
	}
	
	/*
	 * M�tode que imprimeix tots els objectes d'una inst�ncia.
	 */
	public static <T> void printInstances(Class<T> classType) {
		ArrayList<T> aL = getInstances(classType);
		System.out.println("\n////////////////////////////////////////\n");
		for(T t : aL) {
			System.out.println("----------------------");
			System.out.println("\n" + t.toString() + "\n");
			System.out.println("----------------------");
			System.out.println();
		}
		System.out.println("\n////////////////////////////////////////\n");
	}
	
	/*
	 * M�tode que busca un autor pel nom que t�.
	 * Segons la l�gica del programa, no hi pot haver dos
	 * autors amb el mateix nom.
	 */
	public static Autor getAutorFromName(String nom) {
		ArrayList<Autor> aIns = getInstances(Autor.class);
		for(Autor a : aIns) {
			if(a.getNom().equalsIgnoreCase(nom))
				return a;
		}		
		return null;
	}
	
	/*
	 * M�tode que ens retorna una instancia de la classe que li indiquem segons
	 * l'ID que li passem per parametre.
	 */
	public static <T> T getInstaceFromId(Class<T> classType, int id) {
		return (T)session.get(classType, id);
	}
	
	/*
	 * M�tode que fa la transacci� per actualitzar un objecte
	 */
	public static <T> void updateObject(T t) {
		Transaction trx = session.beginTransaction();
		session.update(t);
		trx.commit();
	}
	
	/*
	 * M�tode que elimina un objecte de la classe que se li passa per parametre,
	 * segons l'ID que se li passa tamb� per parametre
	 */
	public static <T> void deleteInstance(Class<T> classType, int id) {
		Object o = session.get(classType, id);
		if(o != null) {
			Transaction trx = session.beginTransaction();
			session.remove(o);
			trx.commit();
		}else {
			System.out.println("Error! No s'ha trobat aquest objecte!");
		}
	}
	
	/*
	 * M�tode que tanca l'Hibernate i tanca la SessionFactory i la Session
	 */
	public static void finish() {
		session.close();
		HibernateUtil.closeFactory();
	}

}
