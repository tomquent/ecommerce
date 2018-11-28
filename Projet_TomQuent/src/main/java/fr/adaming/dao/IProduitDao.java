package fr.adaming.dao;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
 
@Local
public interface IProduitDao {
	
	public List<Produit> getAllProduits(Categorie cat);
	
	public List<Produit> getAllProduits();

	public Produit getProduit(Produit p);
	
	public List<Produit> searchProduits(Produit p); 

	public Produit addProduit(Produit p);

	public int updateProduit(Produit p);

	public int deleteProduit(Produit p);
	
}
