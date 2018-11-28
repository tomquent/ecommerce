package fr.adaming.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.model.LigneCommande;

@Service("lcService")
@Transactional
public class LigneCommandeServiceImpl implements ILigneCommandeService {

//	Association UML en JAVA
	@Autowired
	private ILigneCommandeDao lcDao;

	public void setLcDao(ILigneCommandeDao lcDao) {
		this.lcDao = lcDao;
	}

	
	
	
//	Méthodes 

//	méthode d'ajout de la ligne de commande

	@Override
	public LigneCommande addLigneCommande(LigneCommande lc) {
		return lcDao.addLigneCommande(lc);
	}

}
