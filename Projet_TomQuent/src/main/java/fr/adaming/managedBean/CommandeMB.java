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

	// Clé Réseau
	private static final long serialVersionUID = 1L;

	// ASSO UML EN JAVA
	@ManagedProperty(value="#{comService}")
	ICommandeService comService;

	@ManagedProperty(value="#{clService}")
	IClientService clService;

	@ManagedProperty(value="#{prodService}")
	IProduitService prodService;
	
	@ManagedProperty(value="#{lcService}")
	ILigneCommandeService lcService;

	// Attributs
	private List<LigneCommande> ligneCommande;
	private LigneCommande lc;
	private Commande commande;
	private Client client;
	private List<Produit> produitsListe;
	HttpSession session;

	private boolean viewCommande = true;

	// Constructeurs
	public CommandeMB() {
		super();
	}

	@PostConstruct    // bien de anotation ???
	public void init() {
		this.session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.ligneCommande = new ArrayList<LigneCommande>();
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

	public List<LigneCommande> getLigneCommande() {
		return ligneCommande;
	}

	public void setLigneCommande(List<LigneCommande> ligneCommande) {
		this.ligneCommande = ligneCommande;
	}

	// METHODES******************************************************

	// Commencer une commande

	public void startCommande() {

		// Verification si commande en cours
		this.viewCommande = (boolean) this.session.getAttribute("viewCommande");
		if (this.viewCommande != false) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Impossible !", "Une commande est déjà en cours"));
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
		this.commande.setClient((Client) session.getAttribute("clientSession"));

		// vérification de la présence d'un client en session
		if (this.commande.getClient().getId() != 0) {

			// ajout de la commande dans la DB
			this.commande = comService.addCom(this.commande, this.commande.getClient());

			if (this.commande.getIdCom() != 0) {

				for (int i = 0; i < this.produitsListe.size(); i++) {
					double quantite = this.produitsListe.get(i).getQuantite();
					double prix = this.produitsListe.get(i).getPrix() * quantite;
					this.lc = new LigneCommande(quantite, prix);
					this.lc.setCommande(this.commande);
					this.lc.setProduit(this.produitsListe.get(i));
					this.lc = lcService.addLigneCommande(this.lc);
					this.ligneCommande.add(lc);
				}
				
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "La commande a échoué!", ""));
				return "panier";
			}

			this.detailsCommandePdf(this.commande, this.produitsListe);

			MailSender mailsender = new MailSender();
			String msg = "Bonjour\n\n Une nouvelle commande à traiter a été passée, voir la PJ pour le détail.";

			mailsender.sendMail("michalbebert@gmail.com", "Nouvelle Commande", msg, this.commande);

			// Réinitialiser la vue de l'accueilCommande + panier
			this.viewCommande = false;
			this.session.setAttribute("viewCommande", this.viewCommande);
			this.session.setAttribute("choixViewProduits", false);
			this.session.setAttribute("choixViewRecherche", false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "La commande enregistrée !", ""));

			return "recapitulatifCommande";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La commande a échoué!", "Vous devez vous connecter pour passer commande"));
			return "panier";
		}

	}
	
	public String retourRecap() {
		
		PanierMB panierMB = new PanierMB();
		panierMB.setProduitsListeTampon(new ArrayList<Produit>());
		
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
					comService.getAllCom(this.client));
			return "accueilCommande";

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("La modification de la commande a échoué"));
			return "modificationCommande";
		}
	}

	// Supprimer une commande // Faire que le client ne peut supprimer sa commande
	// que 1j après la commande ?!

	public String lienSupprCommande() {

		return "supprCommande";
	}

	public String supprCommande() {

		try {
			// suppression d'une commande
			comService.deleteCom(this.commande, this.client);

			// maj de la liste
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("commandeListe",
					comService.getAllCom(this.client));

			return "accueilCommande";

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Il manque des informations"));
			return "supprCommande";
		}
	}

	// GetAllCommande pas à faire ??! et GetOneCOmmande non plus..? Surtout si on a
	// un <select> dans le tableau ?

	// Generer le pdf de récapitulatif de la commande

	public void detailsCommandePdf(Commande commande, List<Produit> listeProduitCommande) {

		double montantLigne = 0;
		double montantTotal = 0;

		String chemin = "C:\\Users\\formi\\Desktop\\PDF\\Commande N°" + commande.getIdCom() + ".pdf";

		Document document = new Document();

		PdfPTable tableListe = new PdfPTable(4);

		Header header = new Header("Recapitulatif Commande", "Recapitulatif Commande");

		try {
			PdfWriter.getInstance(document, new FileOutputStream(chemin));
			document.open();

			// On créer l'objet cellule.
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

			for (int i = 0; i < listeProduitCommande.size(); i++) {

				cell = new PdfPCell(new Phrase(listeProduitCommande.get(i).getDesignation()));
				tableListe.addCell(cell);
				cell = new PdfPCell(new Phrase((String.valueOf(listeProduitCommande.get(i).getPrix() + "€"))));
				tableListe.addCell(cell);
				cell = new PdfPCell(new Phrase(String.valueOf(listeProduitCommande.get(i).getQuantite())));
				tableListe.addCell(cell);
				montantLigne = listeProduitCommande.get(i).getPrix() * listeProduitCommande.get(i).getQuantite();
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
