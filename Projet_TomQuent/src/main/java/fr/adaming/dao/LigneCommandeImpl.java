package fr.adaming.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

}
