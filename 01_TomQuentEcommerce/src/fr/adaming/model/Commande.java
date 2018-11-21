package fr.adaming.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="commandes")
public class Commande {
	
	// Attributs
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_commande")
	private long idCom;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	// Transformation de l'association UML en JAVA
	@ManyToOne
	@JoinColumn(name="client_id", referencedColumnName="id_client")
	private Client client;

	
	// Constructeurs
	public Commande() {
		super();
	}

	public Commande(Date date) {
		super();
		this.date = date;
	}

	public Commande(long idCom, Date date) {
		super();
		this.idCom = idCom;
		this.date = date;
	}


	// G et S
	public long getIdCom() {
		return idCom;
	}

	public void setIdCom(long idCom) {
		this.idCom = idCom;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}


	// toString
	@Override
	public String toString() {
		return "Commande [idCom=" + idCom + ", date=" + date + ", client=" + client + "]";
	}
	
	

}
