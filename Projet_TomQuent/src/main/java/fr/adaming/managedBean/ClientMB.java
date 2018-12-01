package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "clMB")
@SessionScoped
public class ClientMB implements Serializable {

	// Clé réseau
	private static final long serialVersionUID = 1L;

	// Asso UML en JAVA
	@ManagedProperty(value = "#{clService}")
	IClientService clService;

	@ManagedProperty(value = "#{comService}")
	ICommandeService comService;

	@ManagedProperty(value ="#{lcService}")
	ILigneCommandeService lcService;
	
	@ManagedProperty(value = "#{prodService}")
	IProduitService prodService;

	// Getters et Setters pour l'injection de dépendance des @ManagedProperty
	public void setClService(IClientService clService) {
		this.clService = clService;
	}

	public void setComService(ICommandeService comService) {
		this.comService = comService;
	}

	public void setLcService(ILigneCommandeService lcService) {
		this.lcService = lcService;
	}

	public void setProdService(IProduitService prodService) {
		this.prodService = prodService;
	}

	// Attributs
	private Client client;
	private boolean viewClient = false;
	private Commande commande;
	private List<Commande> listeCommandesActuelles;
	private List<LigneCommande> listeLignesCommande;
	HttpSession session;

	// Constructeurs
	public ClientMB() {
		super();
	}

	@PostConstruct
	public void init() {
		this.session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if ((Client) session.getAttribute("sessionClient") != null) {
			this.client = (Client) session.getAttribute("sessionClient");
			if (comService.getAllComByClient(this.client).size() != 0) {
				this.listeCommandesActuelles = comService.getAllComByClient(this.client);
			} else {
				this.listeCommandesActuelles = new ArrayList<Commande>();
			}
		} else {
			this.client = new Client();
			this.listeCommandesActuelles = new ArrayList<Commande>();
		}
		this.listeLignesCommande = new ArrayList<LigneCommande>();
	}

	// Getters et setters
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}


	public List<LigneCommande> getListeLignesCommande() {
		return listeLignesCommande;
	}

	public void setListeLignesCommande(List<LigneCommande> listeLignesCommande) {
		this.listeLignesCommande = listeLignesCommande;
	}

	public List<Commande> getListeCommandesActuelles() {
		return listeCommandesActuelles;
	}

	public void setListeCommandesActuelles(List<Commande> listeCommandesActuelles) {
		this.listeCommandesActuelles = listeCommandesActuelles;
	}

	// METHODES

	// ESPACE
	// MENU-------------------------------------------------------------------

	// Ajouter un client (qui s'ajoute lui même, il s'abonne)

	public String lienAddClient() {

		return "ajoutClient";
	}

	public String addClient() {

		Client cl = clService.addClient(this.client);

		if (cl.getId() != 0) {

			// maj client dans session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientSession", cl);
			this.viewClient = true;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewClient", this.viewClient);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Abonnement réussi ! Voici nos "
					+ "articles, vous pouvez dès maintenant en ajouter à votre panier !"));

			return "accueilCommande";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Vous n'avez pas pu vous ajouter, retenter ça devrait marcher !"));
			return "ajoutClient";
		}
	}

	// Modifier un client (il se modifie tout seul)

	public String lienModifClient() {
		this.client = (Client) session.getAttribute("clientSession");
		return "modificationClient";
	}

	public String modifClient() {

		// modification d'un client
		int verif = clService.updateClient(this.client);
		if (verif != 0) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modification(s) réussie(s) !",
					" Voici nos " + "articles, vous pouvez dès maintenant en ajouter à votre panier !"));
			return "accueilCommande";

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Vous n'avez pas pu vous modifier"));
		}
		return "modificationClient";
	}

	// Supprimer un client (qui se supprime tout seul, il se désabonne)

	public String supprClient() {

		// suppression d'un client
		this.client = (Client) session.getAttribute("clientSession");
		int verif = clService.deleteClient(this.client);
		if (verif != 0) {
			// maj de view
			this.viewClient = false;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewClient", this.viewClient);
			return "accueilCommande";

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("La suppression a échoué, êtes vous bien abonné(e) ?"));
			return "accueilCommande";
		}
	}

	// Authentifier un client pour qu'il commande

	public String lienAuthentificationClient() {

		return "authentificationClient";
	}

	public String authentificationClient() {
		// chercher le client dans la BD
		Client cOut = clService.getClient(this.client);
		if (cOut != null) {
			// mise du client et de la liste dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientSession", cOut);
			this.viewClient = true;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewClient", this.viewClient);
			return "accueilCommande";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Le mail ou l'identifiant n'est pas valide"));
			return "authentificationClient";

		}
	}

	// Deconnexion d'un client

	public String deconnexionClient() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientSession", null);
		this.viewClient = false;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewClient", this.viewClient);
		return "accueilCommande";
	}

	// ESPACE CLIENT---------------------------------------------------------------

	// Méthode accès Espace Client

	public String espaceClient() {
		if (this.client != null) {
			if (comService.getAllComByClient(this.client).size() != 0) {
				this.listeCommandesActuelles = comService.getAllComByClient(this.client);
			} else {
				this.listeCommandesActuelles = new ArrayList<Commande>();
			}
			return "espaceClient";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Connexion impossible", "Vous devez vous connecter pour accéder à votre espace Client"));
			return "accueilCommande";
		}

	}
	
	public void onRowToggle() {
		//recuperation des lignes de commandes de la commande à visualiser

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewDetail", true);
	}

}
