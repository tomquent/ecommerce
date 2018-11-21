package fr.adaming.model;

import java.util.List;

public class Panier {

	private List<LigneCommande> listeLignesCommandes;
	
	public Panier() {
		super();
	}

	public Panier(List<LigneCommande> listeLignesCommandes) {
		super();
		this.listeLignesCommandes = listeLignesCommandes;
	}

	public List<LigneCommande> getListeLignesCommandes() {
		return listeLignesCommandes;
	}

	public void setListeLignesCommandes(List<LigneCommande> listeLignesCommandes) {
		this.listeLignesCommandes = listeLignesCommandes;
	}
	
}
