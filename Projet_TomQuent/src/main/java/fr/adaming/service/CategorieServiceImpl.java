package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.service.ICategorieService;
import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;

@Stateful
public class CategorieServiceImpl implements ICategorieService {

	// Association UML en JAVA
	@EJB
	private ICategorieDao catDao;

	// M�thode

	// M�thode getAllCategorie
	@Override
	public List<Categorie> getAllCategories() {

		if (catDao.getAllCategories() != null) {
			return catDao.getAllCategories();
		} else {
			return null;
		}
	}

	// M�thode getCategorie
	@Override
	public Categorie getCategorie(Categorie cat) {
		if (catDao.getCategorie(cat) != null) {
			return catDao.getCategorie(cat);
		} else {
			return null;
		}
	}

	// M�thode addCategorie
	@Override
	public Categorie addCategorie(Categorie cat) {
		return catDao.addCategorie(cat);
		
	}

	// M�thode updateCategorie
	@Override
	public int updateCategorie(Categorie cat) {
		int verif = catDao.updateCategorie(cat);
		if (verif != 0) {
			return verif;
		} else {
			return verif;
		}
	}
	
	// M�thode deleteCategorie
	@Override
	public int deleteCategorie(Categorie cat) {
		int verif = catDao.deleteCategorie(cat);
		if (verif != 0) {
			return verif;
		} else {
			return verif;
		}
	}
}
