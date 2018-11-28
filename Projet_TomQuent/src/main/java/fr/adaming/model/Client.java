package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="clients")
public class Client implements Serializable {
	
	//	Clé Réseau
	private static final long serialVersionUID = 1L;
	
	// Attributs
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_client")
	private long id;
	@Column(name="nom_client")
	private String nom;
	@Column(name="adresse_client")
	private String adresse;
	@Column(name="mail_client")
	private String mail;
	@Column(name="tel_client")
	private String tel;
	
	
	//Transformation de l'asso UML en JAVA
	@OneToMany(mappedBy="client",fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Commande> listeCom;


	// Constructeurs
	public Client() {
		super();
	}

	public Client(String nom, String adresse, String mail, String tel) {
		super();
		this.nom = nom;
		this.adresse = adresse;
		this.mail = mail;
		this.tel = tel;
	}

	public Client(long id, String nom, String adresse, String mail, String tel) {
		super();
		this.id = id;
		this.nom = nom;
		this.adresse = adresse;
		this.mail = mail;
		this.tel = tel;
	}

	
	// Getters et setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public List<Commande> getListeCom() {
		return listeCom;
	}

	public void setListeCom(List<Commande> listeCom) {
		this.listeCom = listeCom;
	}

	// toString
	@Override
	public String toString() {
		return "Client [id=" + id + ", nom=" + nom + ", adresse=" + adresse + ", mail=" + mail + ", tel=" + tel + "]";
	}
	
}
