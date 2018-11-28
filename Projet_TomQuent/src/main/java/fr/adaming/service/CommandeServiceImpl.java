package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.dao.ICommandeDao;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Service("coService")
@Transactional
public class CommandeServiceImpl implements ICommandeService {
	
	// Asso UML en JAVA
	@Autowired
	private ICommandeDao comDao;
	
	
	// Asso UML en JAVA
	@Autowired
	private IClientDao clDao;

	public void setComDao(ICommandeDao comDao) {
		this.comDao = comDao;
	}

	public void setClDao(IClientDao clDao) {
		this.clDao = clDao;
	}
	

	//  METHODES
	
	// Méthode getAllCommande
	@Override
	public List<Commande> getAllCom(Client c) {    
		return comDao.getAllCom(c);
	}

	// Méthode getCommande
	@Override
	public Commande getCom(Commande com, Client c) {
		Client cl1 = clDao.getClient(c);
		Client cl2 = com.getClient();
		if(cl1 == cl2) {
			Commande comOut=comDao.getCom(com);
			return comDao.getCom(comOut);
		}else {
			return null;
		}	
	}

	// Méthode addCommande
	@Override
	public Commande addCom(Commande com, Client c) {   
		// Lier la commande au client
		com.setClient(c);
		return comDao.addCom(com);
	}
	
	// Méthode updateCommande
	@Override
	public int updateCom(Commande com, Client c) {     
		Client cl = clDao.getClient(c);
		if(cl.getId() == com.getClient().getId()) {
			Commande comOut=comDao.getCom(com);
			return comDao.updateCom(comOut);
		}else {
			return 0;
		}	
	}

	// Méthode deleteCommande
	@Override
	public int deleteCom(Commande com, Client c) {    // Faire que le client ne peut supprimer sa commande que 1j après dans MB
		Client cl1 = clDao.getClient(c);
		Client cl2 = com.getClient();
		if(cl1 == cl2) {
			Commande comOut = comDao.getCom(com);
			return comDao.deleteCom(comOut);
		}else {
			return 0;
		}
	}
	
	

}
