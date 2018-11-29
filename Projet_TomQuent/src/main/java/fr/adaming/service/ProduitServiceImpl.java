package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Service("prodService")
@Transactional
public class ProduitServiceImpl implements IProduitService {

	// association UML en JAVA
	@Autowired
	private IProduitDao pDao;

	public void setpDao(IProduitDao pDao) {
		this.pDao = pDao;
	}

	// Méthode getAllProduit
	@Override
	public List<Produit> getAllProduits(Categorie cat) {
		return pDao.getAllProduits(cat);
	}

	// Méthode getAllProduit toustoustous
	@Override
	public List<Produit> getAllProduits() {
		return pDao.getAllProduits();

	}

	// Méthode getProduit
	@Override
	public Produit getProduit(Produit p, Categorie cat) {
		p.setpCategorie(cat);
		return pDao.getProduit(p);
	}

	// Méthode searchProduitWithName

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
		p.setpCategorie(cat);
		return pDao.updateProduit(p);
	}

	// Méthode deleteProduit
	@Override
	public int deleteProduit(Produit p, Categorie cat) {
		return pDao.deleteProduit(p);
	}

}
