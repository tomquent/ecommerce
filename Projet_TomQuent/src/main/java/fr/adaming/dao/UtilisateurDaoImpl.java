package fr.adaming.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Utilisateur;

@Stateless
public class UtilisateurDaoImpl implements IUtilisateurDao {

	// association UML en JAVA
	@PersistenceContext(unitName = "pu_tq")
	private EntityManager em;

	// Méthode

	// Méthode isExist

	@Override
	public Utilisateur isExist(Utilisateur u) {
			String req="SELECT u FROM Utilisateur u WHERE u.mail=:pMail AND u.mdp=:pMdp";
			Query query=em.createQuery(req);
			query.setParameter("pMail", u.getMail());
			query.setParameter("pMdp", u.getMdp());
			return (Utilisateur) query.getSingleResult();	
	}

}
