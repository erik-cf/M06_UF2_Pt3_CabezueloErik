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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
/*
 * Classe que defineix les biblioteques
 */
@Entity
@Table(name = "biblioteca")
public class Biblioteca implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Mapegem la classe amb anotacions
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "nom")
	private String nom;

	/*
	 * En el cas biblioteca-llibre, és una relació 'molts a molts', pel que utilitzem la relació
	 * ManyToMany, i l'indiquem que la tabla que es fa servir per la relació és la de 
	 * bibliotecallibre
	 */
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "bibliotecallibre", joinColumns = { @JoinColumn(name = "id_biblio") }, inverseJoinColumns = {
			@JoinColumn(name = "id_llibre") })
	private Set<Llibre> llibres;

	/*
	 * Mètode que afegeix llibres a la biblioteca
	 */
	public void addLlibre(Llibre l) {
		if (llibres == null) {
			llibres = new HashSet<Llibre>();
		}
		llibres.add(l);
	}

	/*
	 * Getters i setters
	 */
	public int getId() {
		return id;
	}

	/*
	 * El setId es privat ja que és Hibernate qui ha de gestionar els IDs de les
	 * classes persistents.
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

	public String toString() {
		return "Biblioteca amb id " + id + " i nom \"" + nom + "\":\n" + "\tllibres: \n" + printLlibres();
	}

	public String printLlibres() {
		if (llibres == null)
			return "";
		String text = "";
		for (Llibre l : llibres) {
			text = text + "\t\t" + l.getNom() + "\n";
		}
		return text;
	}

}
