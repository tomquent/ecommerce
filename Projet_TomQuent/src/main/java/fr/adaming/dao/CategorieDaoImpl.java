package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.codec.binary.Base64;

import fr.adaming.model.Categorie;

@Stateless
public class CategorieDaoImpl implements ICategorieDao {

	// association UML en JAVA
	@PersistenceContext(unitName = "pu_tq")
	private EntityManager em;

	// M�thodes

	// M�thode getAllCategorie

	@Override
	public List<Categorie> getAllCategories() {

		String req = "SELECT c FROM Categorie c";
		Query query = em.createQuery(req);
		List<Categorie> liste = query.getResultList();
		for (Categorie cat : liste) {
			cat.setImage("data:image/png;base64," + Base64.encodeBase64String(cat.getPhoto()));
		}
		return liste;
	}

	// M�thode getCategorie

	@Override
	public Categorie getCategorie(Categorie cat) {

		Categorie catOut = em.find(Categorie.class, cat.getIdCategorie());
		catOut.setImage("data:image/png;base64," + Base64.encodeBase64String(cat.getPhoto()));

		return catOut;
	}

	// M�thode addCategorie

	@Override
	public Categorie addCategorie(Categorie cat) {
		em.persist(cat);
		return cat;

	}

	// M�thode updateCategorie

	@Override
	public int updateCategorie(Categorie cat) {
		Categorie cOut = em.find(Categorie.class, cat.getIdCategorie());
		if (cOut.getIdCategorie() != 0) {
			cOut = cat;
			em.merge(cOut);
			return 1;
		} else {
			return 0;
		}
	}

	// M�thode deleteCategorie

	@Override
	public int deleteCategorie(Categorie cat) {
		Categorie cOut = em.find(Categorie.class, cat.getIdCategorie());
		if (cOut.getIdCategorie() != 0) {
			em.remove(cOut);
			return 1;
		} else {
			return 0;
		}
	}

}
