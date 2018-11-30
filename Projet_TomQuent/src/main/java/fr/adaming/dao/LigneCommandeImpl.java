package fr.adaming.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;


@Repository
public class LigneCommandeImpl implements ILigneCommandeDao {

	// Transformation de l'association UML en JAVA
	@Autowired
	private SessionFactory sf;
	
	// Setter pour l'injection de dépendance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}
	
	
//	Méthodes 

//	méthode d'ajout de la ligne de commande
	@Override
	public LigneCommande addLigneCommande(LigneCommande lc) {
		Session s =sf.getCurrentSession();

		s.save(lc);
		return lc;
	}

//	méthode dde recherche des lignescommandes d'une commande

	@Override
	public List<LigneCommande> getListeLignesCommandes(Commande c) {
		Session s =sf.getCurrentSession();
		
		String req = "FROM LigneCommande lc WHERE lc.commande.idCom=:pIdCom";
		
		Query query = s.createQuery(req);
		
		query.setParameter("pIdCom", c.getIdCom());

		return query.list();
	}

}
