package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Stateful
public class ProduitServiceImpl implements IProduitService {

	// association UML en JAVA
	@EJB
	private IProduitDao pDao;

	// Méthode getAllProduit
	@Override
	public List<Produit> getAllProduits(Categorie cat) {
		return pDao.getAllProduits(cat);
	}

	// Méthode getAllProduit toustoustous
	@Override
	public List<Produit> getAllProduits() {
		if (pDao.getAllProduits() != null) {
			return pDao.getAllProduits();
		} else {
			return null;
		}
	}

	// Méthode getProduit
	@Override
	public Produit getProduit(Produit p, Categorie cat) {
		Produit pOut = pDao.getProduit(p);
		if (pOut.getpCategorie().getIdCategorie() == cat.getIdCategorie()) {
			return pOut;
		} else {
			return null;
		}
	}
	
	//Méthode searchProduitWithName
	
	@Override
	public List<Produit> searchProduits(Produit p) {
		return pDao.searchProduits(p);
	}


	// Méthode addProduit
	@Override
	public Produit addProduit(Produit p, Categorie cat) {
		p.setpCategorie(cat);
		return pDao.addProduit(p);
	}

	// Méthode updateProduit
	@Override
	public int updateProduit(Produit p, Categorie cat) {
		Produit pDB = this.getProduit(p, cat);
		if (pDB != null) {
			p.setpCategorie(cat);
			if (pDao.updateProduit(p) != 0) {
				return pDao.updateProduit(p);
			} else {
				return pDao.updateProduit(p);
			}
		} else {
			return 0;
		}
	}

	// Méthode deleteProduit
	@Override
	public int deleteProduit(Produit p, Categorie cat) {
			return pDao.deleteProduit(p);
	}

}
