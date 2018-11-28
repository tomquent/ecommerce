package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.Produit;

@Stateless
public class CommandeDaoImpl implements ICommandeDao {

	// association UML en JAVA
	@PersistenceContext(unitName = "pu_tq")
	private EntityManager em;

	// M�thodes

	// M�thode getAllCommande

	@Override
	public List<Commande> getAllCom(Client c) {
		String req = "SELECT c FROM Commande c WHERE c.client.id=:pIdCl";
		Query query = em.createQuery(req);
		query.setParameter("pIdCl", c.getId());
		return query.getResultList();
	}

	// M�thode getCommande
	@Override
	public Commande getCom(Commande com) {
		return em.find(Commande.class, com.getIdCom());
	}

	// M�thode addCommande
	@Override
	public Commande addCom(Commande com) {
		em.persist(com);
		return com;
	}

	// M�thode updateCommande
	@Override
	public int updateCom(Commande com) {
		Commande comExistant = em.find(Commande.class, com.getIdCom());
		
		if (comExistant.getIdCom() != 0) {
			comExistant = com;
			em.merge(comExistant);
			return 1;
		} else {
			return 0;
		}
	}

	// M�thode deleteCommande
	@Override
	public int deleteCom(Commande com) {
		Commande comOut = em.find(Commande.class, com.getIdCom());
		if (comOut.getIdCom() != 0) {
			em.remove(comOut);
			return 1;
		} else {
			return 0;
		}
	}

}
