package fr.adaming.managedBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;
import fr.adaming.service.ILigneCommandeService;

@ManagedBean(name = "lcMB")
@RequestScoped
public class LigneCommandeMB implements Serializable{

	//Cle reseau
	private static final long serialVersionUID = 1L;
	
	// Asso UML en JAVA
	@ManagedProperty(value="#{lcService}")
	private ILigneCommandeService lcService;
	
	public void setLcService(ILigneCommandeService lcService) {
		this.lcService = lcService;
	}
	
	
	//Attributs
	private LigneCommande lc;

	
	
	public LigneCommandeMB() {
		super();
		this.lc = new LigneCommande();
	}


	public LigneCommande getLc() {
		return lc;
	}


	public void setLc(LigneCommande lc) {
		this.lc = lc;
	}
	
	
	

}
