package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.service.ICategorieService;
import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;

@Stateful
public class CategorieServiceImpl implements ICategorieService{

	@EJB
	private ICategorieDao catDao;

	@Override
	public List<Categorie> getAllCategories() {

		return null;
	}

	@Override
	public Categorie getCategorie(Categorie cat) {

		return null;
	}

	@Override
	public Categorie addCategorie(Categorie cat) {

		return null;
	}

	@Override
	public int updateCategorie(Categorie cat) {

		return 0;
	}

	@Override
	public int deleteCategorie(Categorie cat) {

		return 0;
	}
	
}
