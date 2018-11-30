package fr.adaming.service;


import java.util.List;

import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;


public interface ILigneCommandeService {

	public LigneCommande addLigneCommande(LigneCommande lc);
	
	public List<LigneCommande> getListeLignesCommandes(Commande c);

}
