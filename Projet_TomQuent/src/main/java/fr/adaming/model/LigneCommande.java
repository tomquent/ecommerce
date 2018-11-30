package fr.adaming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lignescommandes")
public class LigneCommande implements Serializable {

//	cle reseau
	private static final long serialVersionUID = 1L;

//	Attributs

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_lc;

	@Column(name = "quantite")
	private double quantite;

	@Column(name = "prix")
	private double prix;  

//	Associations UML en JAVA 

	@ManyToOne
	@JoinColumn(name = "produit_id", referencedColumnName = "id_p")
	private Produit produit;

	@ManyToOne
	@JoinColumn(name = "commande_id", referencedColumnName = "id_commande")
	private Commande commande;

//	Constructeurs

	public LigneCommande() {
		super();
	}

	public LigneCommande(double quantite, double prix) {
		super();
		this.quantite = quantite;
		this.prix = prix;
	}

	public LigneCommande(int id_lc, double quantite, double prix) {
		super();
		this.id_lc = id_lc;
		this.quantite = quantite;
		this.prix = prix;
	}

//	G&S

	public int getId_lc() {
		return id_lc;
	}

	public void setId_lc(int id_lc) {
		this.id_lc = id_lc;
	}

	public double getQuantite() {
		return quantite;
	}

	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

}
