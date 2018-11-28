package fr.adaming.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.adaming.model.LigneCommande;

@Stateless
public class LigneCommandeImpl implements ILigneCommandeDao {

	@PersistenceContext(unitName="pu_tq")
	private EntityManager em;
	
	
//	Méthodes 

//	méthode d'ajout de la ligne de commande
	@Override
	public LigneCommande addLigneCommande(LigneCommande lc) {

		em.persist(lc);
		
		return lc;
	}

}
