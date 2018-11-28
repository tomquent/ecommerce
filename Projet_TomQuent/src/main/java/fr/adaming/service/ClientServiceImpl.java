package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IClientDao;
import fr.adaming.model.Client;

@Stateful
public class ClientServiceImpl implements IClientService {

	// Asso UML en JAVA
	@EJB
	private IClientDao clDao;

	// METHODES
	
	//Méthode getAllClient
	@Override
	public List<Client> getAllClient() {
		return clDao.getAllClient();
	}

	//Méthode getClient
	@Override
	public Client getClient(Client cl) {
		return clDao.getClient(cl);
	}

	//Méthode addClient
	@Override
	public Client addClient(Client cl) {
		return clDao.addClient(cl);
	}

	// Méthode updateCategorie

	@Override
	public int updateClient(Client cl) {
		
		return clDao.updateClient(cl);
	}

	// Méthode deleteCategorie

	@Override
	public int deleteClient(Client cl) {
		try {
			Client clOut = clDao.getClient(cl);
			return clDao.deleteClient(clOut);
		} catch (Exception e) {
			return 0;
		}
	}

}
