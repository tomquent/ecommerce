package fr.adaming.dao;


import java.util.List;

import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;


public interface ILigneCommandeDao {

	public LigneCommande addLigneCommande(LigneCommande lc);
	
	public List<LigneCommande> getListeLignesCommandes(Commande c);
}
