package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.model.Client;

@Service("clService")
@Transactional
public class ClientServiceImpl implements IClientService {

	// Asso UML en JAVA
	@Autowired
	private IClientDao clDao;
	
	// le setter pour l'injection de dependance
	public void setClDao(IClientDao clDao) {
		this.clDao = clDao;
	}

	

	// ******************* METHODES ******************

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

		return clDao.deleteClient(cl);
	}
	
	
	

}
