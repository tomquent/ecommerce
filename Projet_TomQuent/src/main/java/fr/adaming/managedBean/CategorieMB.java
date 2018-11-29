package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IProduitService;

@ManagedBean(name="categorieMB")
@RequestScoped
public class CategorieMB implements Serializable {

//	Clé réseau
	private static final long serialVersionUID = 1L;

	// association UML en JAVA
	@ManagedProperty(value = "#{catService}")
	private ICategorieService catService;

	public void setCatService(ICategorieService catService) {
		this.catService = catService;
	}

	@ManagedProperty(value = "#{prodService}")
	private IProduitService prodService;

	public void setProdService(IProduitService prodService) {
		this.prodService = prodService;
	}

	// attributs
	private Categorie cat;
	private int nombreCategorie;
	private boolean indiceViewProduits = false;
	private List<Produit> listeProduits;
	HttpSession maSession;

	private UploadedFile file;

	// Constructeurs vide
	public CategorieMB() {
		super();
	}

	@PostConstruct
	public void init() {
		this.cat = new Categorie();
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.file = null;
	}

	// G&S

	public Categorie getCat() {
		return cat;
	}

	public void setCat(Categorie cat) {
		this.cat = cat;
	}

	public boolean isIndiceViewProduits() {
		return indiceViewProduits;
	}

	public void setIndiceViewProduits(boolean indiceViewProduits) {
		this.indiceViewProduits = indiceViewProduits;
	}

	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	// Autres méthodes

	// Méthode ajouterCatégorie + lien

	public String lienAjouterCategorie() {

		return "ajoutCategorie";
	}

	public String ajouterCategorie() {

		this.cat.setPhoto(file.getContents());
		Categorie c = catService.addCategorie(cat);
		if (c.getIdCategorie() != 0) {
			// maj de la liste
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("catListe",
					catService.getAllCategories());
			return "accueilGestionAdmin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Il manque des informations"));
			return "ajoutCategorie";
		}
	}

	// Méthode supprimerCategorie

	public void supprimerCategorie() {
		int verif = catService.deleteCategorie(cat);
		if (verif != 0) {
			// maj de la liste et de la page
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("catListe",
					catService.getAllCategories());
			this.indiceViewProduits = false;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("indiceViewProduits",
					this.indiceViewProduits);

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention",
					"Impossible de supprimer cette catégorie"));
		}
	}

	// Méthode modifierCategorie + lien

	public String lienModifierCategorie() {

		return "modificationCategorie";
	}

	public String modifierCategorie() {
		if (this.file.getSize() == 0) {
			this.cat.setPhoto(catService.getCategorie(this.cat).getPhoto());
		} else {
			this.cat.setPhoto(file.getContents());
		}

		int verif = catService.updateCategorie(cat);
		if (verif != 0) {
			// maj de la liste
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("catListe",
					catService.getAllCategories());
			return "accueilGestionAdmin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention",
					"Impossible de modifier cette catégorie, il manque des informations"));
			return "modificationCategorie";
		}
	}

	// Méthode LookProduit

	public String LookProduit() {

//		Ajout de la categorie choisie à la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("catSession", this.cat);

//		ajout de la liste de produit de la categorie selectionnée
		this.listeProduits = prodService.getAllProduits(this.cat);

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitsListe", listeProduits);

//		affichage de l'espace gestion des produits
		this.indiceViewProduits = true;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("indiceViewProduits",
				this.indiceViewProduits);

		return "accueilGestionAdmin";

	}

}
