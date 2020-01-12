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

@Entity
@Table(name = "autor")
public class Autor implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "nom")
	private String nom;
	
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Llibre> llibres;
	
	public void addLlibre(Llibre l) {
		if(llibres == null) {
			llibres = new HashSet<Llibre>();
		}
		llibres.add(l);
	}
	
	public int getId() {
		return id;
	}
	
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
	
	public String toString() {
		return "Autor amb id " + id + " i nom \"" + nom + "\":\n"
				+ "\tllibres: \n"
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
