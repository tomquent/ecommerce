package fr.adaming.service;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Local
public interface IProduitService {

	public List<Produit> getAllProduits(Categorie cat);
	
	public List<Produit> getAllProduits();

	public Produit getProduit(Produit p,Categorie cat);
	
	public List<Produit> searchProduits(Produit p); 

	public Produit addProduit(Produit p,Categorie cat);

	public int updateProduit(Produit p,Categorie cat);

	public int deleteProduit(Produit p,Categorie cat);
	
}
