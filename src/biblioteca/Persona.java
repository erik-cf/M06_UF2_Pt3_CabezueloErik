package biblioteca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "persona")
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "dni")
	private int dni;
	
	@Column(name = "nom")
	private String nom;
	
	@Column(name = "telefon")
	private int telefon;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "personallibre", joinColumns = { @JoinColumn(name = "dni_persona") }, inverseJoinColumns = {
			@JoinColumn(name = "id_llibre") })
	private Set<Llibre> llibres;

	public void addLlibre(Llibre l) {
		if (llibres == null) {
			llibres = new HashSet<Llibre>();
		}
		llibres.add(l);
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getTelefon() {
		return telefon;
	}

	public void setTelefon(int telefon) {
		this.telefon = telefon;
	}

	public Set<Llibre> getLlibres() {
		return llibres;
	}

	public void setLlibres(Set<Llibre> llibres) {
		this.llibres = llibres;
	}
	
	public String toString() {
		return "Persona amb dni " + dni + " i nom \"" + nom + "\":\n"
				+ "\ttelefon: " + telefon + "\n"
								+ "llibres: \n"
				+ printLlibres();
	}
	
	public String printLlibres() {
		if(llibres == null) return "";
		String text = "";
		for(Llibre l : llibres) {
			text = text + "\t\t" + l.getNom() + "\n";
		}
		return text;
	}


}
