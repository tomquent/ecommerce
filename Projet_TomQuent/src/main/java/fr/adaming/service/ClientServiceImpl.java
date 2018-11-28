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
	
	//M�thode getAllClient
	@Override
	public List<Client> getAllClient() {
		return clDao.getAllClient();
	}

	//M�thode getClient
	@Override
	public Client getClient(Client cl) {
		return clDao.getClient(cl);
	}

	//M�thode addClient
	@Override
	public Client addClient(Client cl) {
		return clDao.addClient(cl);
	}

	// M�thode updateCategorie

	@Override
	public int updateClient(Client cl) {
		
		return clDao.updateClient(cl);
	}

	// M�thode deleteCategorie

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
