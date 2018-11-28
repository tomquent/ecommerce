package fr.adaming.dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import fr.adaming.model.LigneCommande;


@Repository
public class LigneCommandeImpl implements ILigneCommandeDao {

	@PersistenceContext(unitName="pu_tq")
	private EntityManager em;
	
	
//	M�thodes 

//	m�thode d'ajout de la ligne de commande
	@Override
	public LigneCommande addLigneCommande(LigneCommande lc) {

		em.persist(lc);
		
		return lc;
	}

}
