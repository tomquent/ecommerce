package fr.adaming.managedBean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fr.adaming.util.MailSender;

@ManagedBean(name = "contacterMB")
@RequestScoped
public class ContacterMB implements Serializable {

	// UID
	private static final long serialVersionUID = 1L;
	// association UML en JAVA-----------------

	// Attribut-------------------------------

	// MAIL
	private String toMail;
	private String objet;
	private String msg;

	// constructeur vide----------------------

	public ContacterMB() {
		super();
	}

	// getters&setters-------------------------

	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}

	public String getToMail() {
		return toMail;
	}

	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	// autres méthodes--------------------------

	// CONTACTER LE GERANT

	public String contacterLienAdmin() {
		return "contactAdmin";
	}

	public String contacterAdmin() {

		MailSender.sendMailAdmin("michalbebert@gmail.com", objet, msg);

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Message envoyé", "Votre correspondant répondra au plus vite."));
		return "accueilCommande";
	}

	// CONTACTER LE CLIENT

	public String contacterLienClient() {

		return "contactClient";
	}

	public String contacterClient() {
		
		MailSender.sendMailAdmin(toMail, objet, msg);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Message envoyé", "Le client recevra le mail."));
		
		return "espaceGestionCommande";
	}
}
