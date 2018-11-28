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

	// Méthodes

	// Méthode getAllClient

	@Override
	public List<Client> getAllClient() {
		String req = "SELECT c FROM Client c";
		Query query = em.createQuery(req);
		return query.getResultList();
	}

	// Méthode getClient
	@Override
	public Client getClient(Client cl) {
		return em.find(Client.class, cl.getId());
	}

	// Méthode addClient
	@Override
	public Client addClient(Client cl) {
		em.persist(cl);
		return cl;
	}

	// Méthode updateClientt
	@Override
	public int updateClient(Client cl) {
//		Client clOut = em.find(Client.class, cl.getId());
		
		String req = "UPDATE Client c SET c.nom=:pNom, c.adresse=:pAdresse, c.mail=:pMail, c.tel=:pTel WHERE c.id=:pCId";
		Query query=em.createQuery(req);		
//		if (clOut.getId() != 0) {
			query.setParameter("pNom" , cl.getNom());    
			query.setParameter("pAdresse" , cl.getAdresse());
			query.setParameter("pMail" , cl.getMail());
			query.setParameter("pTel" , cl.getTel());
			query.setParameter("pCId", cl.getId());
//			clOut = cl;
//			em.merge(clOut);

			return query.executeUpdate();
	}

	// Méthode deleteClient
	@Override
	public int deleteClient(Client cl) {
		Client clOut = em.find(Client.class, cl.getId());
		if (clOut.getId() != 0) {
			em.remove(clOut);
			return 1;
		} else {
			return 0;
		}
	}

}
