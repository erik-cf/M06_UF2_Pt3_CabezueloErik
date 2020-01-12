package biblioteca;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/*
 * Classe que defineix els llibres
 */
@Entity
@Table(name = "llibre")
public class Llibre implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Mapegem els atributs amb anotacions
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "nom")
	private String nom;

	@Column(name = "editorial")
	private String editorial;

	/*
	 * Com que autor és una relació 'un a molts' util·litzem el ManyToOne
	 * ja que ja hem util·litzat el OneToMany en autor(un autor pot tenir molts llibres)
	 * (molts llibres poden tenir el mateix autor)
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Autor autor;

	/*
	 * Amb Persona i Biblioteca hi ha una relació ManyToMany que ja ha sigut mapejada en les seves classes,
	 * a llibre indicarem per quin camps d'aquestes classes ha estat mapejat
	 */
	@ManyToMany(mappedBy = "llibres")
	private Set<Persona> persones;

	@ManyToMany(mappedBy = "llibres")
	private Set<Biblioteca> biblioteques;

	/*
	 * Mètode que afegeix persones a un llibre
	 */
	public void addPersona(Persona p) {
		if (persones == null) {
			persones = new HashSet<Persona>();
		}
		persones.add(p);
	}

	/*
	 * Mètode que afegeix biblioteques a un llibre
	 */
	public void addBiblioteca(Biblioteca b) {
		if (biblioteques == null) {
			biblioteques = new HashSet<Biblioteca>();
		}
		biblioteques.add(b);
	}

	/*
	 * Getters i Setters
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

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Set<Persona> getPersones() {
		return persones;
	}

	public void setPersones(Set<Persona> persones) {
		this.persones = persones;
	}

	public Set<Biblioteca> getBiblioteques() {
		return biblioteques;
	}

	public void setBiblioteques(Set<Biblioteca> biblioteques) {
		this.biblioteques = biblioteques;
	}

	/*
	 * Mètode toString:
	 */
	public String toString() {
		return "Llibre amb id " + id + " i nom \"" + nom + "\":\n" + "\teditorial: " + editorial + "\n" + "\tautor:"
				+ autor.getNom() + "\n" + "\tpersones: \n" + printPersones() + "\tbiblioteques:\n "
				+ printBiblioteques();
	}

	/*
	 * Mètode que imprimeix les persones d'aquest llibre
	 */
	private String printPersones() {
		if (persones == null)
			return "";
		String text = "";
		for (Persona p : persones) {
			text = text + "\t\t" + p.getNom() + "\n";
		}
		return text;
	}

	/*
	 * Mètode que imprimeix les biblioteques d'aquest llibre
	 */
	private String printBiblioteques() {
		if (biblioteques == null)
			return "";
		String text = "";
		for (Biblioteca b : biblioteques) {
			text = text + "\t\t" + b.getNom() + "\n";
		}
		return text;
	}

}
