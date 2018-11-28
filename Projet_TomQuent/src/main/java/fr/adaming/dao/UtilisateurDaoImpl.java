package fr.adaming.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Utilisateur;

@Repository
public class UtilisateurDaoImpl implements IUtilisateurDao {

	// association UML en JAVA
	@Autowired
	private SessionFactory sf;
	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	// Méthode

	// Méthode isExist

	@Override
	public Utilisateur isExist(Utilisateur u) {

		Session s = sf.getCurrentSession();

		String req = "FROM Utilisateur u WHERE u.mail=:pMail AND u.mdp=:pMdp";
		Query query = s.createQuery(req);
		query.setParameter("pMail", u.getMail());
		query.setParameter("pMdp", u.getMdp());
		return (Utilisateur) query.uniqueResult();
	}

}
