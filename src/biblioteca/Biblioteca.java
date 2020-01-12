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

@Entity
@Table(name = "biblioteca")
public class Biblioteca implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "nom")
	private String nom;
	
	 @ManyToMany(cascade = { CascadeType.ALL })
	    @JoinTable(
	        name = "bibliotecallibre", 
	        joinColumns = { @JoinColumn(name = "id_biblio") }, 
	        inverseJoinColumns = { @JoinColumn(name = "id_llibre") }
	    )
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
	
	public String toString() {
		return "Biblioteca amb id " + id + " i nom \"" + nom + "\":\n"
				+ "\tllibres: \n" + printLlibres();
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
