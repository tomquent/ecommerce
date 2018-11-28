package fr.adaming.service;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.model.LigneCommande;

@Stateful
public class LigneCommandeServiceImpl implements ILigneCommandeService {

//	Association UML en JAVA
	@EJB
	private ILigneCommandeDao lcDao;

//	Méthodes 

//	méthode d'ajout de la ligne de commande

	@Override
	public LigneCommande addLigneCommande(LigneCommande lc) {
		return lcDao.addLigneCommande(lc);
	}

}
