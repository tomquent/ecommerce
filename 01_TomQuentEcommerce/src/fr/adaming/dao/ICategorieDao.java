package fr.adaming.dao;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Categorie;

@Local
public interface ICategorieDao {

	public List<Categorie> getAllCategories();

	public Categorie getCategorie(Categorie cat);

	public Categorie addCategorie(Categorie cat);

	public int updateCategorie(Categorie cat);

	public int deleteCategorie(Categorie cat);

	
}
