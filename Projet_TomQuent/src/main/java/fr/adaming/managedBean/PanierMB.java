package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.Produit;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean
@SessionScoped
public class PanierMB implements Serializable {

	// Association UML en JAVA
	@ManagedProperty(value="#{comService}")
	private ICommandeService comService;
	
	public void setComService(ICommandeService comService) {
		this.comService = comService;
	}

	@ManagedProperty(value="#{prodService}")
	private IProduitService prodService;
	
	public void setProdService(IProduitService prodService) {
		this.prodService = prodService;
	}

	// Attributs
	private Commande commandeEnCours;
	private Produit produitSuppr;
	private List<Commande> commandesListeSelectionne;
	private List<Produit> produitsListeSelectionne;
	private List<Produit> produitsListeTampon;
	HttpSession maSession;

	// Constructeur vide
	public PanierMB() {
		super();
	}

	@PostConstruct
	public void init() {
		this.commandesListeSelectionne = new ArrayList<Commande>();
		this.produitsListeSelectionne = new ArrayList<Produit>();
		this.produitsListeTampon = new ArrayList<Produit>();
		this.produitSuppr =  new Produit();
		maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.commandeEnCours = new Commande();
	}

	// G&S

	public List<Commande> getCommandesListeSelectionne() {
		return commandesListeSelectionne;
	}

	public void setCommandesListeSelectionne(List<Commande> commandesListeSelectionne) {
		this.commandesListeSelectionne = commandesListeSelectionne;
	}

	public Produit getProduitSuppr() {
		return produitSuppr;
	}

	public void setProduitSuppr(Produit produitSuppr) {
		this.produitSuppr = produitSuppr;
	}

	public List<Produit> getProduitsListeTampon() {
		return produitsListeTampon;
	}

	public void setProduitsListeTampon(List<Produit> produitsListeTampon) {
		this.produitsListeTampon = produitsListeTampon;
	}

	public Commande getCommandeEnCours() {
		return commandeEnCours;
	}

	public void setCommandeEnCours(Commande commandeEnCours) {
		this.commandeEnCours = commandeEnCours;
	}

	public List<Produit> getProduitsListeSelectionne() {
		return produitsListeSelectionne;
	}

	public void setProduitsListeSelectionne(List<Produit> produitsListeSelectionne) {
		this.produitsListeSelectionne = produitsListeSelectionne;
	}

	// autres méthodes

	// Méthode lien panier

	public String lienPanier() {
		return "panier";
	}

	// Méthode ajout produits au panier
	public void ajoutLignesCommande() {
		
		commandeEnCours.setClient((Client) maSession.getAttribute("clientSession"));
		// verification de l'attribution d'un client non vide à la commande
				if (commandeEnCours.getClient() != null) {
		
		//	stocker une listetampon et la mettre à jour à chaque ajout
		if(this.produitsListeTampon.size()!=0) {
			//MISE A JOUR AVEC LA NOUVELLE SELECTION DE PRODUITS
			for(int i=0;i<this.produitsListeSelectionne.size();i++) {
				this.produitsListeTampon.add(this.produitsListeSelectionne.get(i));
			}			
		}else {
			//CREATION
			this.produitsListeTampon=this.produitsListeSelectionne;
		}
		
			// update de la commande et ses produits dans la BD

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("L'ajout des produits à la commande est un succès !"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					"L'ajout a échoué, vous devez être inscrit pour pouvoir commander ! BOUTON S'INSCRIRE !"));
		}

	}
	
	//Méthode supprimerProduitPanier
	
	public void supprimerProduitPanier() {
		for(int i=0; i<this.produitsListeTampon.size();i++) {			
			if(this.produitsListeTampon.get(i).getIdProduit()==this.produitSuppr.getIdProduit()) {
				this.produitsListeTampon.remove(i);
			}
		}
	}

}
