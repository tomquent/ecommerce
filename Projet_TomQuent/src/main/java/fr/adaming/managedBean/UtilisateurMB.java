package fr.adaming.managedBean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Categorie;
import fr.adaming.model.Commande;
import fr.adaming.model.Produit;
import fr.adaming.model.Utilisateur;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IProduitService;
import fr.adaming.service.IUtilisateurService;

@ManagedBean(name = "utilisateurMB")
@RequestScoped
public class UtilisateurMB {

//	association UML en JAVA 
	@ManagedProperty("#{utService}")
	private IUtilisateurService utService;

	public void setUtService(IUtilisateurService utService) {
		this.utService = utService;
	}

	@ManagedProperty("#{catService}")
	private ICategorieService catService;

	public void setCatService(ICategorieService catService) {
		this.catService = catService;
	}

	@ManagedProperty("#{prodService}")
	private IProduitService prodService;

	public void setProdService(IProduitService prodService) {
		this.prodService = prodService;
	}

//	Attributs
	private Utilisateur utilisateur;
	private List<Categorie> listeCategories;
	HttpSession maSession;

	// Attributs Coté Commande
	private String choixView;
	private boolean choixViewCategories = false;
	private boolean choixViewProduits = false;
	private boolean choixViewRecherche = false;
	private List<Produit> listeProduits;
	private Categorie categorie;
	private Produit produit;

//	Constructeur vide
	public UtilisateurMB() {
		super();
		this.utilisateur = new Utilisateur();
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		this.categorie = new Categorie();
		this.produit = new Produit();
	}

	@PostConstruct
	public void init() {

	}

//	G&S

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getChoixView() {
		return choixView;
	}

	public void setChoixView(String choixView) {
		this.choixView = choixView;
	}

	public boolean isChoixViewCategories() {
		return choixViewCategories;
	}

	public void setChoixViewCategories(boolean choixViewCategories) {
		this.choixViewCategories = choixViewCategories;
	}

	public boolean isChoixViewProduits() {
		return choixViewProduits;
	}

	public void setChoixViewProduits(boolean choixViewProduits) {
		this.choixViewProduits = choixViewProduits;
	}

	public boolean isChoixViewRecherche() {
		return choixViewRecherche;
	}

	public void setChoixViewRecherche(boolean choixViewRecherche) {
		this.choixViewRecherche = choixViewRecherche;
	}

	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public List<Categorie> getListeCategories() {
		return listeCategories;
	}

	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

//	Autres méthodes****************************************************************************

	// COTE
	// ADMIN______________________________________________________________________________

	// se connecter

	public String seConnecter() {
		// chercher l'utilisateur dans la BD
		Utilisateur uOut = utService.isExist(this.utilisateur);

		if (uOut != null) {
			// recup de la liste de categorie
			// this.listeCategories = catService.getAllCategories();

			// mise de l'utilisateur et de la liste dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uSession", uOut);
			// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("catListe",
			// this.listeCategories);

			return "accueilGestionAdmin";

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Le login ou le mot de passe n'est pas valide"));
			return "login";
		}

	}

	// se déconnecter

	public String deconnecter() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login";
	}

	// COTE
	// COMMANDE____________________________________________________________________________________

	// Méthode lien vers la page

	public String lienAccueilCommande() {

		// gestion des vues
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewCommande", false);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewClient", false);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("commandeEnCours", new Commande());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientSession", null);

		// mise de la liste de categorie dans la session
		this.listeCategories = catService.getAllCategories();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("catListe", this.listeCategories);

		// mise de la liste totale de produits dans la session
		this.listeProduits = prodService.getAllProduits();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitsListe", this.listeProduits);

		return "accueilCommande";
	}

	// Méthode choix de sélection des produits pour la commande

	public void choisirViewSelection(ValueChangeEvent event) {

		// mise à jour de la selection
		this.choixView = (String) event.getNewValue();

		// affichage selon le choix de selection du produit

		if (this.choixView.equals("none")) {

			// Affichage ou cache des blocs

			this.choixViewProduits = false;
			maSession.setAttribute("choixViewProduits", this.choixViewProduits);
			this.choixViewCategories = false;
			maSession.setAttribute("choixViewCategories", this.choixViewCategories);
			this.choixViewRecherche = false;
			maSession.setAttribute("choixViewRecherche", this.choixViewRecherche);

		} else if (this.choixView.equals("choixCategories")) {

			// Affichage ou cache des blocs
			this.choixViewCategories = true;
			maSession.setAttribute("choixViewCategories", this.choixViewCategories);
			this.choixViewProduits = false;
			maSession.setAttribute("choixViewProduits", this.choixViewProduits);
			this.choixViewRecherche = false;
			maSession.setAttribute("choixViewRecherche", this.choixViewRecherche);

		} else if (this.choixView.equals("choixProduits")) {

			// MAJ liste produit all
			this.listeProduits = prodService.getAllProduits();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitsListe",
					this.listeProduits);
			// Affichage ou cache des blocs

			this.choixViewCategories = false;
			maSession.setAttribute("choixViewCategories", this.choixViewCategories);
			this.choixViewProduits = true;
			maSession.setAttribute("choixViewProduits", this.choixViewProduits);
			this.choixViewRecherche = false;
			maSession.setAttribute("choixViewRecherche", this.choixViewRecherche);

		} else if (this.choixView.equals("choixRecherche")) {

			// Affichage ou cache des blocs
			this.choixViewCategories = false;
			maSession.setAttribute("choixViewCategories", this.choixViewCategories);
			this.choixViewProduits = false;
			maSession.setAttribute("choixViewProduits", this.choixViewProduits);
			this.choixViewRecherche = true;
			maSession.setAttribute("choixViewRecherche", this.choixViewRecherche);
		}

	}

	// Méthode choix de sélection des produits par Categorie

	public void choisirCategorie(ValueChangeEvent event) {

		// Selection de la categorie
		long select = (long) event.getNewValue();
		if (select != 0) {
			// Mise à jour de la liste des produits de la categorie
			this.categorie.setIdCategorie((Long) event.getNewValue());

			this.listeProduits = prodService.getAllProduits(this.categorie);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitsListe",
					this.listeProduits);

			// Affichage de la table des produits de la categorie
			this.choixViewProduits = true;
			maSession.setAttribute("choixViewProduits", this.choixViewProduits);
		} else {
			this.choixViewProduits = false;
			maSession.setAttribute("choixViewProduits", this.choixViewProduits);
		}
	}

	// Méthode choix de sélection des produits par Recherche

	public void rechercheProduit() {

		// recuperation de la liste correspondant à la désignation du produit
		System.out.println(this.produit.getDesignation());

		this.listeProduits = prodService.searchProduits(this.produit);

		// Verification de la contenance de la liste
		if (this.listeProduits.size() != 0) {

			// Mise à jour de la liste des produits après recherche
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitsListe",
					this.listeProduits);
			// Affichage de la table des produits après recherche
			this.choixViewProduits = true;
			maSession.setAttribute("choixViewProduits", this.choixViewProduits);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("La designation de votre produit n'existe pas dans nos offres"));
			this.choixViewProduits = false;
			maSession.setAttribute("choixViewProduits", this.choixViewProduits);
		}
	}

}
