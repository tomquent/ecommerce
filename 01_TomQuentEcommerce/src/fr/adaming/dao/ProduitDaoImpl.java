package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Stateless
public class ProduitDaoImpl implements IProduitDao {

	//association UML en JAVA
	@PersistenceContext(unitName="pu_tq")
	private EntityManager em;
	
	@Override
	public List<Produit> getAllProduits(Categorie cat) {

		return null;
	}

	@Override
	public Produit getProduit(Produit p) {

		return null;
	}

	@Override
	public Produit addProduit(Produit p) {

		return null;
	}

	@Override
	public int updateProduit(Produit p) {

		return 0;
	}

	@Override
	public int deleteProduit(Produit p) {

		return 0;
	}

}
