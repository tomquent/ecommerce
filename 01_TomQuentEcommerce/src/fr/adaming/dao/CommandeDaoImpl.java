package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Stateless
public class CommandeDaoImpl implements ICommandeDao {
	
	// Transformation de l'association UML en JAVA
	@PersistenceContext(unitName = "pu_tq")
	private EntityManager em;

	@Override
	public List<Commande> getAllCom(Client c) {
		try {
			String req = "SELECT c FROM Commande c WHERE c.client.id=:pIdCl";	 //c.client.id=:pIdCl??
			Query query = em.createQuery(req);
			query.setParameter("pIdCl", c.getId());
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Commande getCom(Commande com) {
		try {
			return em.find(Commande.class, com);
		} catch (Exception e) {
			return null;
		}	
	}

	@Override
	public Commande addCom(Commande com) {
		try {
			em.persist(com);
			return com;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int updateCom(Commande com) {
		try {
			Commande comOut = em.find(Commande.class, com.getIdCom());
			comOut = com;  											//??
			em.merge(com);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int deleteCom(Commande com) {
		try {
			em.remove(com);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

}
