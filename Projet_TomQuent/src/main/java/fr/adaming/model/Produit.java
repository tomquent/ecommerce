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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "produits")
public class Produit implements Serializable {

	// Clé Réseau
	private static final long serialVersionUID = 1L;

	// Attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_p")
	private long idProduit;
	@Column(name = "designation_p")
	private String designation;
	@Column(name = "description_p")
	private String description;
	@Column(name = "prix_p")
	private double prix;
	@Column(name = "quantite_p")
	private int quantite;
	@Column(name = "selectionne_p")
	private boolean selectionne = false;
	@Lob
	@Column(name = "photo_p")
	private byte[] photo;

	@Transient
	private int quantiteDesire;
	
	@Transient
	private double prixTotal;
	
	@Transient
	private String image;

	// Association UML en JAVA
	@ManyToOne
	@JoinColumn(name = "cat_id", referencedColumnName = "id_cat")
	Categorie pCategorie;

	@OneToMany(mappedBy = "produit",fetch = FetchType.EAGER)
	private List<LigneCommande> lignesCommandes;

	// Constructeurs

	public Produit() {
		super();
	}

	public Produit(String designation, String description, double prix, int quantite, boolean selectionne,
			byte[] photo) {
		super();
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photo = photo;
	}

	public Produit(long idProduit, String designation, String description, double prix, int quantite,
			boolean selectionne, byte[] photo) {
		super();
		this.idProduit = idProduit;
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photo = photo;
	}

	// G&S

	public long getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(long idProduit) {
		this.idProduit = idProduit;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public boolean isSelectionne() {
		return selectionne;
	}

	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public Categorie getpCategorie() {
		return pCategorie;
	}

	public void setpCategorie(Categorie pCategorie) {
		this.pCategorie = pCategorie;
	}

	public List<LigneCommande> getLignesCommandes() {
		return lignesCommandes;
	}

	public void setLignesCommandes(List<LigneCommande> lignesCommandes) {
		this.lignesCommandes = lignesCommandes;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getQuantiteDesire() {
		return quantiteDesire;
	}

	public void setQuantiteDesire(int quantiteDesire) {
		this.quantiteDesire = quantiteDesire;
	}

	public double getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(double prixTotal) {
		this.prixTotal = prixTotal;
	}

	@Override
	public String toString() {
		return "Produit [idProduit=" + idProduit + ", designation=" + designation + ", description=" + description
				+ ", prix=" + prix + ", quantite=" + quantite + ", selectionne=" + selectionne + "]";
	}

}
