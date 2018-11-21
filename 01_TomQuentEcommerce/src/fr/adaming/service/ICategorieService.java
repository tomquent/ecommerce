package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;

@Local
public interface ICategorieService {
	
	public List<Categorie> getAllCategories();

	public Categorie getCategorie(Categorie cat);

	public Categorie addCategorie(Categorie cat);

	public int updateCategorie(Categorie cat);

	public int deleteCategorie(Categorie cat);
	
}
