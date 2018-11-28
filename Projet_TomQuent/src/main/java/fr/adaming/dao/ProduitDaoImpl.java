package fr.adaming.dao;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Repository
public class ProduitDaoImpl implements IProduitDao {

	// association UML en JAVA
	@Autowired
	SessionFactory sf;

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	// Méthodes

	// Méthode getAllProduit

	@Override
	public List<Produit> getAllProduits(Categorie cat) {

		Session s = sf.getCurrentSession();

		String req = "FROM Produit p WHERE p.pCategorie.idCategorie=:pIdCat";
		Query query = s.createQuery(req);
		query.setParameter("pIdCat", cat.getIdCategorie());

		List<Produit> liste = query.list();

		for (Produit p : liste) {
			p.setImage("data:image/png;base64," + Base64.encodeBase64String(p.getPhoto()));
		}

		return liste;
	}

	// Méthode getAllProduit without Categorie
	@Override
	public List<Produit> getAllProduits() {

		Session s = sf.getCurrentSession();

		String req = "SELECT p FROM Produit p";
		Query query = s.createQuery(req);

		List<Produit> liste = query.list();

		for (Produit p : liste) {
			p.setImage("data:image/png;base64," + Base64.encodeBase64String(p.getPhoto()));
		}

		return liste;
	}

	// Méthode getProduit

	@Override
	public Produit getProduit(Produit p) {

		Session s = sf.getCurrentSession();

		String req = "FROM Produit p WHERE p.idProduit=:pIdP AND p.pCategorie.idCategorie=:pIdCat";

		Query query = s.createQuery(req);

		query.setParameter("pIdP", p.getIdProduit());
		query.setParameter("pIdCat", p.getpCategorie().getIdCategorie());

		Produit pOut = (Produit) query.uniqueResult();

		pOut.setImage("data:image/png;base64," + Base64.encodeBase64String(pOut.getPhoto()));

		return pOut;

	}
	// Méthode searchProduitWithName

	public List<Produit> searchProduits(Produit p) {

		Session s = sf.getCurrentSession();

		String req = "FROM Produit p WHERE p.designation LIKE :pDesignation";
		Query query = s.createQuery(req);
		query.setParameter("pDesignation", "%" + p.getDesignation() + "%");

		List<Produit> liste = query.list();

		for (Produit pdt : liste) {
			pdt.setImage("data:image/png;base64," + Base64.encodeBase64String(pdt.getPhoto()));
		}
		return liste;
	}

	// Méthode addProduit

	@Override
	public Produit addProduit(Produit p) {

		Session s = sf.getCurrentSession();

		s.save(p);

		return p;
	}

	// Méthode updateProduit

	@Override
	public int updateProduit(Produit p) {

		Session s = sf.getCurrentSession();

		String req = "UPDATE Produit p SET p.designation=:pDesignation,p.description=:pDescription,p.prix=:pPrix,p.quantite=:pQuantite,p.photo=:pPhoto WHERE p.idProduit=:pIdP AND p.pCategorie.idCategorie=:pIdCat";

		Query query = s.createQuery(req);

		query.setParameter("pDesignation", p.getDesignation());
		query.setParameter("pDescription", p.getDescription());
		query.setParameter("pPrix", p.getPrix());
		query.setParameter("pQuantite", p.getQuantite());
		query.setParameter("pPhoto", p.getPhoto());
		query.setParameter("pIdP", p.getIdProduit());
		query.setParameter("pIdCat", p.getpCategorie().getIdCategorie());

		return query.executeUpdate();

	}

	// Méthode deleteProduit
	@Override
	public int deleteProduit(Produit p) {

		Session s = sf.getCurrentSession();
		
		String req = "DELETE FROM Produit p WHERE p.idProduit=:pIdP AND p.pCategorie.idCategorie=:pIdCat";
		Query query = s.createQuery(req);
		query.setParameter("pIdP", p.getIdProduit());
		query.setParameter("pIdCat", p.getpCategorie().getIdCategorie());

		return query.executeUpdate();

	}

}
