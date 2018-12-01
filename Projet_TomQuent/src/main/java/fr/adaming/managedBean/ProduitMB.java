package fr.adaming.managedBean;

import java.io.Serializable;

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

@ManagedBean(name = "produitMB")
@RequestScoped
public class ProduitMB implements Serializable {

	// Clé réseau
	private static final long serialVersionUID = 1L;

	// association UML en JAVA
	@ManagedProperty(value = "#{catService}")
	private ICategorieService catService;

	public void setCatService(ICategorieService catService) {
		this.catService = catService;
	}

	@ManagedProperty(value = "#{prodService}")
	private IProduitService prodService;

	// Attributs

	public void setProdService(IProduitService prodService) {
		this.prodService = prodService;
	}

	private Produit produit;
	private Categorie categorie;
	private int nombreProduit;
	HttpSession maSession;
	private UploadedFile file;

	// Constructeur vide
	public ProduitMB() {
		super();
	}

	@PostConstruct
	public void init() {
		this.produit = new Produit();
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.categorie = (Categorie) maSession.getAttribute("catSession");
	}

	// G&S

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public int getNombreProduit() {
		return nombreProduit;
	}

	public void setNombreProduit(int nombreProduit) {
		this.nombreProduit = nombreProduit;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	// Autres méthodes

	// Méthode ajouterProduit + lien

	public String lienAjouterProduit() {
		return "ajoutProduit";
	}

	public String ajouterProduit() {

		this.produit.setPhoto(file.getContents());
		Produit pOut = prodService.addProduit(this.produit, this.categorie);
		if (pOut.getIdProduit() != 0) {
			// maj de la liste
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitsListe",
					prodService.getAllProduits(this.categorie));
			return "accueilGestionAdmin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Il manque des informations"));
			return "ajoutProduit";
		}
	}

	// Méthode supprimerProduit

	public void supprimerProduit() {
		int verif = prodService.deleteProduit(this.produit, this.categorie);
		if (verif != 0) {
			// maj de la liste
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitsListe",
					prodService.getAllProduits(this.categorie));
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Impossible de supprimer ce produit"));
		}
	}

	// Méthode modifierProduit + lien

	public String lienModifierProduit() {
		return "modificationProduit";
	}

	public String modifierProduit() {

		if (this.file.getSize() == 0) {
			this.produit.setPhoto(prodService.getProduit(this.produit, this.categorie).getPhoto());
		} else {
			this.produit.setPhoto(file.getContents());
		}

		int verif = prodService.updateProduit(this.produit, this.categorie);
		if (verif != 0) {
			// maj de la liste
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitsListe",
					prodService.getAllProduits(this.categorie));
			return "espaceGestionCatalogue";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention",
					"Impossible de modifier ce produit, il manque des informations"));
			return "modificationProduit";
		}
	}

	// Visualiser le produit + lien

	public String lienProduitView() {

		this.produit = prodService.getProduit(this.produit, this.produit.getpCategorie());

		return "ficheProduit";
	}

}
