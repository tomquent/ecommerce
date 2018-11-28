package fr.adaming.service;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IUtilisateurDao;
import fr.adaming.model.Utilisateur;

@Stateful
public class UtilisateurServiceImpl implements IUtilisateurService {

	//	association UML en JAVA
	@EJB
	IUtilisateurDao uDao;

	// Méthode isExist

	@Override
	public Utilisateur isExist(Utilisateur u) {
			return uDao.isExist(u);
	}
}
