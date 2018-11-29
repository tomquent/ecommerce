package fr.adaming.managedBean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fr.adaming.util.MailSender;

@ManagedBean(name="contacterMB")
@RequestScoped
public class ContacterMB implements Serializable {

	// UID
	private static final long serialVersionUID = 1L;
	// association UML en JAVA-----------------

	// Attribut-------------------------------

	// MAIL
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

			MailSender mailSender = new MailSender();
			
			mailSender.sendMailAdmin("michalbebert@gmail.com", objet, msg);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Message envoyé","Votre correspondant répondra au plus vite."));
			return "accueilCommande";	
	}
}
