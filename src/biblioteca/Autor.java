package biblioteca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/*
 * Classe que defineix els autors
 */
@Entity
@Table(name = "autor")
public class Autor implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// Fem servir anotacions pel mapejat:
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "nom")
	private String nom;
	
	// En el cas d'autor-llibre, es una relació 'un a molts', fem servir el OneToMany:
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Llibre> llibres;
	
	/*
	 * Metode que afegeix llibres al autor
	 */
	public void addLlibre(Llibre l) {
		if(llibres == null) {
			llibres = new HashSet<Llibre>();
		}
		llibres.add(l);
	}
	
	/*
	 * Getters i Setters
	 */
	public int getId() {
		return id;
	}
	
	/*
	 * El setId es privat ja que és Hibernate qui ha de gestionar els IDs de les classes persistents.
	 */
	private void setId(int id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}

	public Set<Llibre> getLlibres() {
		return llibres;
	}

	public void setAutors(Set<Llibre> llibres) {
		this.llibres = llibres;
	}
	
	/*
	 * Mètode toString
	 */
	public String toString() {
		return "Autor amb id " + id + " i nom \"" + nom + "\":\n"
				+ "\tllibres: \n"
				+ printLlibres();
	}
	
	/*
	 * Mètode que imprimeix els llibres d'un autor
	 */
	public String printLlibres() {
		if(llibres == null) return "";
		String text = "";
		for(Llibre l : llibres) {
			text = text + "\t\t" + l.getNom() + "\n";
		}
		return text;
	}
	
	
	
}
