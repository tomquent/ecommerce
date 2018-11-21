package fr.adaming.dao;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Local
public interface IClientDao {
	
	public List<Client> getAllClient();
	
	public Client getCom(Client cl);
	
	public Client addClient(Client cl);
	
	public int updateClient(Commande cl);
	
	public int deleteClient(Commande cl);

}
