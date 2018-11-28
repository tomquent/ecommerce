package fr.adaming.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IUtilisateurDao;
import fr.adaming.model.Utilisateur;

@Service("uService")
@Transactional
public class UtilisateurServiceImpl implements IUtilisateurService {

	//	association UML en JAVA
	@Autowired
	IUtilisateurDao uDao;

	public void setuDao(IUtilisateurDao uDao) {
		this.uDao = uDao;
	}
	
	// Méthode isExist

	@Override
	public Utilisateur isExist(Utilisateur u) {
			return uDao.isExist(u);
	}
}
