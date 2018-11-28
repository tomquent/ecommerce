package fr.adaming.dao;

import java.util.List;


import fr.adaming.model.Client;
import fr.adaming.model.Commande;


public interface ICommandeDao {
	
	public List<Commande> getAllCom(Client c);
	
	public Commande getCom(Commande com);
	
	public Commande addCom(Commande com);
	
	public int updateCom(Commande com);
	
	public int deleteCom(Commande com);

}
