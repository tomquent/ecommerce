package fr.adaming.service;

import javax.ejb.Local;

import fr.adaming.model.LigneCommande;

@Local
public interface ILigneCommandeService {

	public LigneCommande addLigneCommande(LigneCommande lc);
	
}
