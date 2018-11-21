package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Stateless
public class ClientDaoImpl implements IClientDao {
	
	// Transformation de l'association UML en JAVA
	@PersistenceContext(unitName = "pu_tq")
	private EntityManager em;

	@Override
	public List<Client> getAllClient() {
		try {
			String req = "SELECT c FROM Client c ALLLLLL";			//??
			Query query = em.createQuery(req);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	
	@Override
	public Client getClient(Client cl) {
		try {
			return em.find(Client.class, cl);
		} catch (Exception e) {
			return null;
		}	
	}

	
	@Override
	public Client addClient(Client cl) {
		
		try {
			em.persist(cl);
			return cl;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int updateClient(Client cl) {
		try {
			Client clOut = em.find(Client.class, cl.getId());
			clOut = cl;  											//??
			em.merge(cl);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int deleteClient(Client cl) {
		try {
			em.remove(cl);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

}
