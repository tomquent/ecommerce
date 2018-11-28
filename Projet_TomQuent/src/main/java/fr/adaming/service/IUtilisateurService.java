package fr.adaming.service;

import javax.ejb.Local;

import fr.adaming.model.Utilisateur;

@Local
public interface IUtilisateurService {

	public Utilisateur isExist(Utilisateur u);

}
