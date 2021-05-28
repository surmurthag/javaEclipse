package entite;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/*
 * La classe fournisseur est à compléter lorsque vous aurez réalisé
 * l'ensemble du projet de gestion tel que proposé dans le livre.
 * Nous vous laissons donc le soin de créer vous mêmes
 * les méthodes CRUD en vous aidant de tout ce qui a été vu
 * et mis en oeuvre pour bâtir le projet de gestion.
 * Pensez alors à créer aussi un modèle et une IHM
 * selon la même démarche suivie pour les classes entité
 * Client, Article, Commandes et Ligne  
 */
@Entity
public class Fournisseur implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
    private long code;
	
	private String societe;
	
	@OneToOne
	private Adresse adresse;
	
	public long getCode() {
		return this.code;
	}
	
	public String getSociete() {
		return this.societe;
	}
	
	public Adresse getAdresse() {
		return this.adresse;
	}
	
	public void setSociete(String societe) {
		this.societe = societe;
	}
	
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}	
}
