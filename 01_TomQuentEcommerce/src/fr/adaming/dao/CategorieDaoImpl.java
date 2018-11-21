package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.adaming.model.Categorie;

@Stateless
public class CategorieDaoImpl implements ICategorieDao{

	//association UML en JAVA
	@PersistenceContext(unitName="pu_tq")
	private EntityManager em;

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
