package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IClientDao;
import fr.adaming.dao.ICommandeDao;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Stateful
public class CommandeServiceImpl implements ICommandeService {
	
	// Asso UML en JAVA
	@EJB
	private ICommandeDao comDao;
	
	
	// Asso UML en JAVA
	@EJB 
	private IClientDao clDao;
	

	//  METHODES
	
	// M�thode getAllCommande
	@Override
	public List<Commande> getAllCom(Client c) {    
		return comDao.getAllCom(c);
	}

	// M�thode getCommande
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

	// M�thode addCommande
	@Override
	public Commande addCom(Commande com, Client c) {   
		// Lier la commande au client
		com.setClient(c);
		return comDao.addCom(com);
	}
	
	// M�thode updateCommande
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

	// M�thode deleteCommande
	@Override
	public int deleteCom(Commande com, Client c) {    // Faire que le client ne peut supprimer sa commande que 1j apr�s dans MB
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
