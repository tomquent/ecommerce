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

	// M�thode getAllProduit
	@Override
	public List<Produit> getAllProduits(Categorie cat) {
		return pDao.getAllProduits(cat);
	}

	// M�thode getAllProduit toustoustous
	@Override
	public List<Produit> getAllProduits() {
		return pDao.getAllProduits();

	}

	// M�thode getProduit
	@Override
	public Produit getProduit(Produit p, Categorie cat) {
		Produit pOut = pDao.getProduit(p);
		if (pOut.getpCategorie().getIdCategorie() == cat.getIdCategorie()) {
			return pOut;
		} else {
			return null;
		}
	}

	// M�thode searchProduitWithName

	@Override
	public List<Produit> searchProduits(Produit p) {
		return pDao.searchProduits(p);
	}

	// M�thode addProduit
	@Override
	public Produit addProduit(Produit p, Categorie cat) {
		p.setpCategorie(cat);
		return pDao.addProduit(p);
	}

	// M�thode updateProduit
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

	// M�thode deleteProduit
	@Override
	public int deleteProduit(Produit p, Categorie cat) {
		return pDao.deleteProduit(p);
	}

}
