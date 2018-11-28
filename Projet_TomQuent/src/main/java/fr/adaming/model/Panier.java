package fr.adaming.model;

public class Panier {

//	Attributs
	
	private Commande commande;
	private Produit produit;
	
//	Constructeur
	
	public Panier() {
	super();
}
	
	
//	G&S
	
	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	public Produit getProduit() {
		return produit;
	}
	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	
	
	
}
