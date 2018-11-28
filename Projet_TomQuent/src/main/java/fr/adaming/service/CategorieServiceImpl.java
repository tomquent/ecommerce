package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.service.ICategorieService;
import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;

@Service("catService")
@Transactional
public class CategorieServiceImpl implements ICategorieService {

	// Association UML en JAVA
	@Autowired
	private ICategorieDao catDao;

	public void setCatDao(ICategorieDao catDao) {
		this.catDao = catDao;
	}

	// Méthode

	// Méthode getAllCategorie
	@Override
	public List<Categorie> getAllCategories() {

		if (catDao.getAllCategories() != null) {
			return catDao.getAllCategories();
		} else {
			return null;
		}
	}

	// Méthode getCategorie
	@Override
	public Categorie getCategorie(Categorie cat) {
		return catDao.getCategorie(cat);
	}

	// Méthode addCategorie
	@Override
	public Categorie addCategorie(Categorie cat) {
		return catDao.addCategorie(cat);

	}

	// Méthode updateCategorie
	@Override
	public int updateCategorie(Categorie cat) {
		return catDao.updateCategorie(cat);
	}

	// Méthode deleteCategorie
	@Override
	public int deleteCategorie(Categorie cat) {
		return catDao.deleteCategorie(cat);
	}
}
