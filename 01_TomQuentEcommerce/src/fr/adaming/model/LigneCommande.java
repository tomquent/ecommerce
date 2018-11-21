package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

public class LigneCommande implements Serializable {
	
	//	Clé Réseau
	private static final long serialVersionUID = 1L;

	// Attributs
	private int qte;
	private int px;
	
	// Association UML JAVA
	private List<Commande> commande;
	
	
	
	
}
