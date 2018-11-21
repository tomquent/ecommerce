package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Stateful
public class ProduitServiceImpl implements IProduitService{

	//association UML en JAVA
	@EJB
	private IProduitDao pDao;

	@Override
	public List<Produit> getAllProduits(Categorie cat) {

		return null;
	}

	@Override
	public Produit getProduit(Produit p, Categorie cat) {

		return null;
	}

	@Override
	public Produit addProduit(Produit p, Categorie cat) {

		return null;
	}

	@Override
	public int updateProduit(Produit p, Categorie cat) {

		return 0;
	}

	@Override
	public int deleteProduit(Produit p, Categorie cat) {

		return 0;
	}
	
}
