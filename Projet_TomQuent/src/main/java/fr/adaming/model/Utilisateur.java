package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur implements Serializable {

	//	Clé Réseau
	private static final long serialVersionUID = 1L;

	// Attributs

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_u")
	private long idUtilisateur;
	@Column(name = "role_u")
	private String role;
	@Column(name = "mail_u")
	private String mail;
	@Column(name = "mdp_u")
	private String mdp;

	//Association UML en JAVA
	@ManyToMany(mappedBy="listeUtilisateurs",targetEntity=Categorie.class)
	List<Categorie> listeCategories;
	
	//	Constructeurs d'entité

	public Utilisateur() {
		super();
	}

	public Utilisateur(String role, String mail, String mdp) {
		super();
		this.role = role;
		this.mail = mail;
		this.mdp = mdp;
	}

	public Utilisateur(long idUtilisateur, String role, String mail, String mdp) {
		super();
		this.idUtilisateur = idUtilisateur;
		this.role = role;
		this.mail = mail;
		this.mdp = mdp;
	}

	//	G&S

	public long getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public List<Categorie> getListeCategories() {
		return listeCategories;
	}

	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

}
