package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Repository
public class ClientDaoImpl implements IClientDao {

	// Transformation de l'association UML en JAVA
	@Autowired
	private SessionFactory sf;
	
	// Setter pour l'injection de dépendance (même si avec @autowired c'est pas nécessaire normalement)
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	// ****************** Méthodes *****************

	// Méthode getAllClient

	@Override
	public List<Client> getAllClient() {
		Session s = sf.getCurrentSession();

		// Requete HQL (et donc Query de HQL!!)
		String req = "FROM Client c";
		Query query = s.createQuery(req);
		
		return query.list();
	}

	// Méthode getClient
	@Override
	public Client getClient(Client cl) {
		Session s = sf.getCurrentSession();
		
		// Requete HQL (et donc Query de HQL!!) et passage des params
		String req = "FROM Client c WHERE c.id=:pId";
		Query query = s.createQuery(req);
		query.setParameter("pId", cl.getId());
		
		return (Client) query.uniqueResult();
		
	}

	// Méthode addClient
	@Override
	public Client addClient(Client cl) {
		Session s =sf.getCurrentSession();
		
		s.save(cl);
	
		return cl;
	}

	// Méthode updateClientt
	@Override
	public int updateClient(Client cl) {
		Session s =sf.getCurrentSession();
		
		//s.update(cl);
		
		// Requete HQL (et donc Query de HQL!!) et passage des params
		String req = "UPDATE Client c SET c.nom=:pNom, c.adresse=:pAdresse, c.mail=:pMail, c.tel=:pTel WHERE c.id=:pCId";
		Query query=s.createQuery(req);		

		query.setParameter("pNom" , cl.getNom());    
		query.setParameter("pAdresse" , cl.getAdresse());
		query.setParameter("pMail" , cl.getMail());
		query.setParameter("pTel" , cl.getTel());
		query.setParameter("pCId", cl.getId());
		
		return query.executeUpdate();
	}

	// Méthode deleteClient
	@Override
	public int deleteClient(Client cl) {
		Session s =sf.getCurrentSession();
		
		// Requete HQL (et donc Query de HQL!!) et passage des params // REQUETE HQL obligatoire pour delete CAR s.delete retourne void donc on peut pas savoir si il le fait
		String req="DELETE Client c WHERE c.id=:pCId";
		Query query=s.createQuery(req);		

		query.setParameter("pCId" , cl.getId());  

		return query.executeUpdate();
	}

	
	
}
