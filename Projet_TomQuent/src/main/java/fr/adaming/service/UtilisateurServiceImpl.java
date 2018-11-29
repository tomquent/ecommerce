package fr.adaming.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IUtilisateurDao;
import fr.adaming.model.Utilisateur;

@Service("utService")
@Transactional
public class UtilisateurServiceImpl implements IUtilisateurService {

	//	association UML en JAVA
	@Autowired
	IUtilisateurDao utDao;

	public void setUtDao(IUtilisateurDao utDao) {
		this.utDao = utDao;
	}


	
	// Méthode isExist


	@Override
	public Utilisateur isExist(Utilisateur u) {
			return utDao.isExist(u);
	}
}
