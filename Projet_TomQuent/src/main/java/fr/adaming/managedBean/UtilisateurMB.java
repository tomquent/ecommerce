package fr.adaming.managedBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import fr.adaming.model.Categorie;
import fr.adaming.model.Commande;
import fr.adaming.model.Produit;
import fr.adaming.model.Utilisateur;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.IProduitService;
import fr.adaming.service.IUtilisateurService;

@ManagedBean(name = "utilisateurMB")
@RequestScoped
public class UtilisateurMB implements Serializable {

//	association UML en JAVA 
	@ManagedProperty(value = "#{utService}")
	private IUtilisateurService utService;

	public void setUtService(IUtilisateurService utService) {
		this.utService = utService;
	}

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

	@ManagedProperty(value = "#{comService}")
	ICommandeService comService;

	public void setComService(ICommandeService comService) {
		this.comService = comService;
	}

	@ManagedProperty(value = "#{clService}")
	IClientService clService;

	public void setClService(IClientService clService) {
		this.clService = clService;
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
	private List<Commande> listeCommandes;
	private Categorie categorie;
	private Produit produit;

//	Constructeur vide
	public UtilisateurMB() {
		super();
	}

	@PostConstruct
	public void init() {
		this.utilisateur = new Utilisateur();
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		this.categorie = new Categorie();
		this.produit = new Produit();
		this.listeCommandes = comService.getAllCom();
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

	public List<Commande> getListeCommandes() {
		return listeCommandes;
	}

	public void setListeCommandes(List<Commande> listeCommandes) {
		this.listeCommandes = listeCommandes;
	}

	public List<Categorie> getListeCategories() {
		return listeCategories;
	}

	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

//	Autres méthodes****************************************************************************

	// COTE
	// ADMIN__________________________________________________________________________

	// CONNEXION DECONNEXION------------------------

	// se connecter

	public String seConnecter() {
//		System.out.println("le mail de this.utilisateur est " +this.utilisateur.getMail());
		// chercher l'utilisateur dans la BD
		Utilisateur uOut = utService.isExist(this.utilisateur);

//		System.out.println("le mail de uOut est " +uOut.getMail());
		if (uOut != null) {
			// recup de la liste de categorie
			this.listeCategories = catService.getAllCategories();

			// mise de l'utilisateur et de la liste dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uSession", uOut);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("catListe",
					this.listeCategories);

			return "accueilGestionAdmin";

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Le login ou le mot de passe n'est pas valide"));
			return "login";
		}

	}

	// se déconnecter

	public String deconnecter() {
		
		try {
			this.speechOkay();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login";
	}

	// Gestion COMMANDES des Clients------------------------

	// COTE COMMANDE
	// (Client)______________________________________________________________

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

	public void rechercheProduit() throws Exception {
		
		// recuperation de la liste correspondant à la désignation du produit
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

	// reconnaissance vocal

	public void speechOkay() {

		Configuration configuration = new Configuration();

		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

		LiveSpeechRecognizer recognizer;
		try {
			recognizer = new LiveSpeechRecognizer(configuration);
			
			recognizer.startRecognition(true);
			SpeechResult result;
			// Pause recognition process. It can be resumed then with
			// startRecognition(false).
			String okay = "okay";
			while ((result = recognizer.getResult()) != null) {

				if (result.getHypothesis().toString().equals(okay)) {
					System.out.format(result.getHypothesis());
					break;
				}
			}
			recognizer.stopRecognition();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				"Vous vous êtes déconnecté en criant \"okay\" "));

		
		// Start recognition process pruning previously cached data.
		
	}

}
