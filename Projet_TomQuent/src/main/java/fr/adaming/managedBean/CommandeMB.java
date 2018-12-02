package fr.adaming.managedBean;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;
import fr.adaming.service.IProduitService;
import fr.adaming.util.MailSender;

@ManagedBean(name = "coMB")
@RequestScoped
public class CommandeMB implements Serializable {

	// ClÃ© RÃ©seau
	private static final long serialVersionUID = 1L;

	// ASSO UML EN JAVA
	@ManagedProperty(value = "#{comService}")
	ICommandeService comService;

	@ManagedProperty(value = "#{clService}")
	IClientService clService;

	@ManagedProperty(value = "#{prodService}")
	IProduitService prodService;

	@ManagedProperty(value = "#{lcService}")
	ILigneCommandeService lcService;

	// Setters pour l'injection de dÃ©pendance des @ManagedProperty
	public void setComService(ICommandeService comService) {
		this.comService = comService;
	}

	public void setClService(IClientService clService) {
		this.clService = clService;
	}

	public void setProdService(IProduitService prodService) {
		this.prodService = prodService;
	}

	public void setLcService(ILigneCommandeService lcService) {
		this.lcService = lcService;
	}

	// Attributs
	private List<LigneCommande> listeLignesCommandes;
	private LigneCommande lc;
	private Commande commande;
	private Client client;
	private List<Produit> produitsListe;
	HttpSession session;

	private boolean viewCommande = true;

	// Constructeurs
	public CommandeMB() {
		super();
		this.session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.listeLignesCommandes = new ArrayList<LigneCommande>();
		this.lc = new LigneCommande();
		this.produitsListe = new ArrayList<Produit>();
		this.client = (Client) session.getAttribute("clientSession");
		this.commande = new Commande();
	}

	@PostConstruct
	public void init() {
		this.session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.listeLignesCommandes = new ArrayList<LigneCommande>();
		this.lc = new LigneCommande();
		this.produitsListe = new ArrayList<Produit>();
		this.client = (Client) session.getAttribute("clientSession");
		this.commande = new Commande();
	}

	// Getters et Setters

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public boolean isViewCommande() {
		return viewCommande;
	}

	public void setViewCommande(boolean viewCommande) {
		this.viewCommande = viewCommande;
	}

	public List<Produit> getProduitsListe() {
		return produitsListe;
	}

	public void setProduitsListe(List<Produit> produitsListe) {
		this.produitsListe = produitsListe;
	}

	public LigneCommande getLc() {
		return lc;
	}

	public void setLc(LigneCommande lc) {
		this.lc = lc;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public List<LigneCommande> getListeLignesCommandes() {
		return listeLignesCommandes;
	}

	public void setListeLignesCommandes(List<LigneCommande> listeLignesCommandes) {
		this.listeLignesCommandes = listeLignesCommandes;
	}

	// METHODES******************************************************

	// Commencer une commande

	public void startCommande() {

		// Verification si commande en cours
		this.viewCommande = (boolean) this.session.getAttribute("viewCommande");
		if (this.viewCommande != false) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Impossible !", "Une commande est déjà  en cours"));
		} else {

			this.viewCommande = true;
			this.session.setAttribute("viewCommande", this.viewCommande);
		}
	}

	// Passer une commande via l'action du client

	public String passerCommande() {

		// Démarrage d'une commande et ajout d'une commande
		Date date = new Date();
		date.getTime();
		this.commande.setDate(date);
		// Client client = (Client) session.getAttribute("clientSession");
		this.commande.setClient(this.client = (Client) session.getAttribute("clientSession"));

		// vérification de la présence d'un client en session
		if (this.commande.getClient().getId() != 0) {

			// ajout de la commande dans la DB
			this.commande = comService.addCom(this.commande, this.commande.getClient());

			if (this.commande.getIdCom() != 0) {

				for (int i = 0; i < this.produitsListe.size(); i++) {
					int quantite = this.produitsListe.get(i).getQuantiteDesire();
					double prix = this.produitsListe.get(i).getPrix() * quantite;
					this.lc = new LigneCommande(quantite, prix);
					
					this.produitsListe.get(i).setPrixTotal(prix); 
					
					// Mettre à jour le stock de produit
					this.produitsListe.get(i).setQuantite(this.produitsListe.get(i).getQuantite() - quantite);
					prodService.updateProduit(this.produitsListe.get(i), this.produitsListe.get(i).getpCategorie());
					
					// Lier la commande et la LC et la LC et le produit
					this.lc.setCommande(this.commande);
					this.lc.setProduit(this.produitsListe.get(i));
					
					// ajouter les LC a la BD et les lier à une commande
					this.lc = lcService.addLigneCommande(this.lc);
					this.listeLignesCommandes.add(lc);
					this.commande.setLignesCommandes(this.listeLignesCommandes);
				}

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "La commande a échoué!", ""));
				return "panier";
			}

			this.detailsCommandePdf(this.commande, this.listeLignesCommandes);

			String msg = "Bonjour\n\n Une nouvelle commande a été passée par le client : "+this.commande.getClient().getNom() +
					" ! \n La commande est à traiter ; voir la PJ pour le détail.";

			MailSender.sendMail("michalbebert@gmail.com", "Nouvelle Commande", msg, this.commande);

			// RÃ©initialiser la vue de l'accueilCommande + panier
			this.viewCommande = false;
			this.session.setAttribute("viewCommande", this.viewCommande);
			this.session.setAttribute("choixViewProduits", false);
			this.session.setAttribute("choixViewRecherche", false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Commande enregistrée !", ""));
			
			// Réinitialisation du panier
			PanierMB panierMB = new PanierMB();
			panierMB.setProduitsListeTampon(null);
			panierMB.setProduitsListeSelectionne(null);

			return "recapitulatifCommande";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La commande a échoué!", "Vous devez vous connecter pour passer commande"));
			return "panier";
		}

	}

	public String retourRecap() {

		PanierMB panierMB = new PanierMB();
		panierMB.setProduitsListeTampon(null);
		panierMB.setProduitsListeSelectionne(null);

		return "accueilCommande";
	}

	// Modifier une commande // Faire que le client ne peut modifier sa commande que
	// 1j après la commande ?!

	public String lienModifCommande() {

		return "modificationCommande";
	}

	public String modifCommande() {
		// modification d'une commande
		int verif = comService.updateCom(this.commande, this.client);
		if (verif != 0) {
			// maj de la liste
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("commandeListe",
					comService.getAllComByClient(this.client));
			return "accueilCommande";

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("La modification de la commande a échoué"));
			return "modificationCommande";
		}
	}

	// Supprimer une commande // Faire que le client ne peut supprimer sa commande
	// que 1j après la commande ?!

	public String supprCommandeClient() {

		long dateAjd = new Date().getTime();

		//System.out.println(dateAjd - this.commande.getDate().getTime());

		if (dateAjd - this.commande.getDate().getTime() <= 86400000) {
			
			int a = comService.deleteCom(this.commande, this.client);
			
			if (a != 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Ok", "La commande a été supprimée"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Impossible", "Supprimer la commande n'est pas possible"));
			}
			return "espaceClient";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Impossible", "Supprimer la commande n'est pas possible, votre délai de 24h est dépassée"));
			return "espaceClient";
		}

	}

	public String supprCommandeAdmin() {
		
		int a =comService.deleteCom(this.commande, this.client);
		
		if (a != 0) {

			String subject = "Suppression de votre commande";

			String msg = "Bonjour M./Mme " + this.client.getNom()
					+ ",\n\nNous avons constaté une anomalie dans votre commande du " + this.commande.getDate().toGMTString()
					+".\n\nCette commande est donc annulée et vous serez remboursé(e) dans les plus brefs délais.\n\nNous vous prions d'excuser ce désagrément et nous vous offrons une remise de 50% sur le prochain article que vous souhaiterez acquérir sur notre site.\n\nSachez accueillir nos sincères excuses.\n\nLe service client.";

			MailSender.sendMailAdmin(this.client.getMail(), subject, msg);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Ok", "La commande a été supprimée"));
			return "espaceGestionCommande";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Impossible", "Supprimer la commande n'est pas possible"));
			return "espaceGestionCommande";
		}

	}



	// Generer le pdf de ré	capitulatif de la commande

	public void detailsCommandePdf(Commande commande, List<LigneCommande> listeLignesCommandes) {

		double montantLigne = 0;
		double montantTotal = 0;

		String chemin = "C:\\Users\\formi\\Desktop\\PDF\\Commande N°" + commande.getIdCom() + ".pdf";

		Document document = new Document();

		PdfPTable tableListe = new PdfPTable(4);

		Header header = new Header("Recapitulatif Commande", "Recapitulatif Commande");

		try {
			PdfWriter.getInstance(document, new FileOutputStream(chemin));
			document.open();

			// On crÃ©er l'objet cellule.
			PdfPCell cell;

			Paragraph paragraph = new Paragraph();

			paragraph.add("Récapitulatif de la commande du " + commande.getDate() + "\n de M/Mme "
					+ commande.getClient().getNom() + ".\n\n");
			paragraph
					.setFont(FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLDITALIC, new BaseColor(0, 0, 255)));
			paragraph.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(paragraph);

			document.addTitle(header.getName());

			cell = new PdfPCell(new Phrase("Produits de la commande"));
			cell.setColspan(4);
			tableListe.addCell(cell);

			cell = new PdfPCell(new Phrase("Designation"));
			tableListe.addCell(cell);
			cell = new PdfPCell(new Phrase("Prix Unitaire"));
			tableListe.addCell(cell);
			cell = new PdfPCell(new Phrase("Quantite"));
			tableListe.addCell(cell);
			cell = new PdfPCell(new Phrase("Total"));
			tableListe.addCell(cell);

			for (int i = 0; i < listeLignesCommandes.size(); i++) {

				cell = new PdfPCell(new Phrase(listeLignesCommandes.get(i).getProduit().getDesignation()));
				tableListe.addCell(cell);
				cell = new PdfPCell(
						new Phrase((String.valueOf(listeLignesCommandes.get(i).getProduit().getPrix() + "€"))));
				tableListe.addCell(cell);
				cell = new PdfPCell(new Phrase(String.valueOf(listeLignesCommandes.get(i).getQuantite())));
				tableListe.addCell(cell);
				montantLigne = listeLignesCommandes.get(i).getPrix();
				montantTotal += montantLigne;
				cell = new PdfPCell(new Phrase(String.valueOf(montantLigne) + "€"));
				tableListe.addCell(cell);
			}

			cell = new PdfPCell(new Phrase("Total"));
			cell.setColspan(2);
			tableListe.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(montantTotal) + "€"));
			cell.setColspan(2);
			tableListe.addCell(cell);

			document.add(tableListe);

		} catch (Exception e) {
			// TODO: handle exception
		}

		document.close();
	}

}