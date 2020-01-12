package persistencia;

import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import biblioteca.Autor;
import biblioteca.Biblioteca;
import biblioteca.Llibre;
import biblioteca.Persona;

public class HibernateManager {

	private static Session session = HibernateUtil.getSessionFactory().openSession();

	public static Autor createAutor(String nom) {
		Transaction t = session.beginTransaction();
		Autor a = new Autor();
		a.setNom(nom);
		a = session.get(Autor.class, session.save(a));
		t.commit();
		return a;
	}

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

	public static Integer createEmptyBiblioteca(String nom) {
		Transaction t = session.beginTransaction();
		Biblioteca b = new Biblioteca();
		b.setNom(nom);
		Integer n = (Integer) session.save(b);
		t.commit();
		return n;
	}

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

	public static void addLlibreToBiblioteca(Biblioteca b, Llibre l) {
		b.addLlibre(l);
		l.addBiblioteca(b);
		updateObject(b);
		updateObject(l);
	}

	public static void addLlibreToPersona(Persona p, Llibre l) {
		p.addLlibre(l);
		l.addPersona(p);
		updateObject(p);
		updateObject(l);
	}

	public static <T>ArrayList<T> getInstances(Class<T> classType) {
		Query q = session.createQuery("from " + classType.getName());
		return (ArrayList<T>) q.getResultList();
	}
	
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
	
	public static Autor getAutorFromName(String nom) {
		ArrayList<Autor> aIns = getInstances(Autor.class);
		for(Autor a : aIns) {
			if(a.getNom().equalsIgnoreCase(nom))
				return a;
		}		
		return null;
	}
	
	public static <T> T getInstaceFromId(Class<T> classType, int id) {
		return (T)session.get(classType, id);
	}
	
	public static <T> void updateObject(T t) {
		Transaction trx = session.beginTransaction();
		session.update(t);
		trx.commit();
	}
	
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
	
	public static void finish() {
		session.close();
		HibernateUtil.closeFactory();
	}

}
