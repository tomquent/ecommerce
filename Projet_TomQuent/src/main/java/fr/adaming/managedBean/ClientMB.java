package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.Utilisateur;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "clMB")
@RequestScoped
public class ClientMB implements Serializable {

	// Asso UML en JAVA
	@EJB
	IClientService clService;

	@EJB
	ICommandeService comService;

	@EJB
	IProduitService prodService;

	// Attributs
	private Client client;
	private boolean viewClient = false;
	private Commande commande;
	private List<Commande> listeCommandesActuelles;
	HttpSession session;

	// Constructeurs
	public ClientMB() {
		super();
	}

	@PostConstruct
	public void init() {
		this.client = new Client();
		this.session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
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

	public List<Commande> getListeCommandesActuelles() {
		return listeCommandesActuelles;
	}

	public void setListeCommandesActuelles(List<Commande> listeCommandesActuelles) {
		this.listeCommandesActuelles = listeCommandesActuelles;
	}

	// METHODES

	// Ajouter un client (qui s'ajoute lui m�me, il s'abonne)

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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Abonnement r�ussi ! Voici nos "
					+ "articles, vous pouvez d�s maintenant en ajouter � votre panier !"));
			
			return "accueilCommande";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Vous n'avez pas pu vous ajouter, retenter !"));
			return "ajoutClient";
		}
	}

	// Modifier un client (il se modifie tout seul)

	public String lienModifClient() {
		this.client=(Client) session.getAttribute("clientSession");
		return "modificationClient";
	}

	public String modifClient() {

		// modification d'un client
		int verif = clService.updateClient(this.client);
		if (verif != 0) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modification(s) r�ussie(s) !"," Voici nos "
					+ "articles, vous pouvez d�s maintenant en ajouter � votre panier !"));
			return "accueilCommande";

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Vous n'avez pas pu vous modifier"));
		}
		return "modificationClient";
	}

	// Supprimer un client (qui se supprime tout seul, il se d�sabonne)

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
					new FacesMessage("La suppression a �chou�, �tes vous bien abonn�(e) ?"));
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

}
