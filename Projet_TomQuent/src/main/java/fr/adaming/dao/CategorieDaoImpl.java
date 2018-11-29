package fr.adaming.dao;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;

@Repository
public class CategorieDaoImpl implements ICategorieDao {

	// association UML en JAVA
	@Autowired
	private SessionFactory sf;

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	// Méthodes

	// Méthode getAllCategorie

	@Override
	public List<Categorie> getAllCategories() {

		Session s = sf.getCurrentSession();

		String req = "FROM Categorie c";
		Query query = s.createQuery(req);

		List<Categorie> liste = (List<Categorie>) query.list();
		for (Categorie cat:liste) {
			cat.setImage("data:image/png;base64," + Base64.encodeBase64String(cat.getPhoto()));
		}
		return liste;
	}

	// Méthode getCategorie

	@Override
	public Categorie getCategorie(Categorie cat) {

		Session s = sf.getCurrentSession();

		String req = "FROM Categorie c WHERE c.idCategorie=:pIdC";

		Query query = s.createQuery(req);

		query.setParameter("pIdC", cat.getIdCategorie());

		Categorie catOut = (Categorie) query.uniqueResult();
		catOut.setImage("data:image/png;base64," + Base64.encodeBase64String(catOut.getPhoto()));

		return catOut;
	}

	// Méthode addCategorie

	@Override
	public Categorie addCategorie(Categorie cat) {

		Session s = sf.getCurrentSession();

		s.save(cat);

		return cat;
	}

	// Méthode updateCategorie

	@Override
	public int updateCategorie(Categorie cat) {

		Session s = sf.getCurrentSession();

		String req = "UPDATE Categorie c SET c.nomCategorie=:pNom,c.photo=:pPhoto,c.description=:pDescription WHERE c.idCategorie=:pIdC";

		Query query = s.createQuery(req);

		query.setParameter("pNom", cat.getNomCategorie());
		query.setParameter("pPhoto", cat.getPhoto());
		query.setParameter("pDescription", cat.getDescription());
		query.setParameter("pIdC", cat.getIdCategorie());

		return query.executeUpdate();
	}

	// Méthode deleteCategorie

	@Override
	public int deleteCategorie(Categorie cat) {

		Session s = sf.getCurrentSession();

		String req = "DELETE Categorie c WHERE c.idCategorie=:pIdC";

		Query query = s.createQuery(req);

		query.setParameter("pIdC", cat.getIdCategorie());

		return query.executeUpdate();

	}

}
