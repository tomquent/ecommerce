package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.codec.binary.Base64;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Stateless
public class ProduitDaoImpl implements IProduitDao {

	// association UML en JAVA
	@PersistenceContext(unitName = "pu_tq")
	private EntityManager em;

	// Méthodes

	// Méthode getAllProduit

	@Override
	public List<Produit> getAllProduits(Categorie cat) {

		String req = "SELECT p FROM Produit p WHERE p.pCategorie.idCategorie=:pIdCat";
		Query query = em.createQuery(req);
		query.setParameter("pIdCat", cat.getIdCategorie());

		List<Produit> liste = query.getResultList();

		for (Produit p : liste) {
			p.setImage("data:image/png;base64," + Base64.encodeBase64String(p.getPhoto()));
		}

		return liste;
	}

	// Méthode getAllProduit without Categorie
	@Override
	public List<Produit> getAllProduits() {
		String req = "SELECT p FROM Produit p";
		Query query = em.createQuery(req);
		
		List<Produit> liste = query.getResultList();

		for (Produit p : liste) {
			p.setImage("data:image/png;base64," + Base64.encodeBase64String(p.getPhoto()));
		}
		
		return liste;
	}

	// Méthode getProduit

	@Override
	public Produit getProduit(Produit p) {

		Produit pOut = em.find(Produit.class, p.getIdProduit());
		pOut.setImage("data:image/png;base64," + Base64.encodeBase64String(p.getPhoto()));
		return pOut;

	}
	//Méthode searchProduitWithName

	public List<Produit> searchProduits(Produit p){
		
		String req = "SELECT p FROM Produit p WHERE p.designation LIKE :pDesignation";
		Query query = em.createQuery(req);
		query.setParameter("pDesignation", "%"+p.getDesignation()+"%");
		
		List<Produit> liste = query.getResultList();

		for (Produit pdt : liste) {
			pdt.setImage("data:image/png;base64," + Base64.encodeBase64String(pdt.getPhoto()));
		}
		return liste;
	}


	// Méthode addProduit

	@Override
	public Produit addProduit(Produit p) {
		em.persist(p);
		return p;
	}

	// Méthode updateProduit

	@Override
	public int updateProduit(Produit p) {
		Produit pOut = em.find(Produit.class, p.getIdProduit());
		if (pOut.getIdProduit() != 0) {
			pOut = p;
			em.merge(pOut);
			return 1;
		} else {
			return 0;
		}
	}

	// Méthode deleteProduit
	@Override
	public int deleteProduit(Produit p) {

		String req = "DELETE FROM Produit p WHERE p.idProduit=:pId";
		Query query = em.createQuery(req);
		query.setParameter("pId", p.getIdProduit());
		return query.executeUpdate();

	}

}
