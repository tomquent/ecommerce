package fr.adaming.dao;

import javax.ejb.Local;

import fr.adaming.model.Utilisateur;

@Local
public interface IUtilisateurDao {

	public Utilisateur isExist(Utilisateur u);
	
}
