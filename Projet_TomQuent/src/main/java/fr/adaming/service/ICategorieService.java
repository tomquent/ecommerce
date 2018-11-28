package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Categorie;

public interface ICategorieService {

	public List<Categorie> getAllCategories();

	public Categorie getCategorie(Categorie cat);

	public Categorie addCategorie(Categorie cat);

	public int updateCategorie(Categorie cat);

	public int deleteCategorie(Categorie cat);

}
