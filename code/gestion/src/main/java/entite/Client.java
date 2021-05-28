package entite;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import controle.utilitaires.GestionDates;

@Entity
@NamedQueries({
	
		@NamedQuery(name = "chercherClient", query = 
				"SELECT c FROM Client c "
				+ "WHERE c.code LIKE :recherche "
				+ "OR c.nom LIKE :recherche "
				+ "OR c.prenom LIKE :recherche"),
				
		@NamedQuery(name = "chercherPrecis", query = 
				"SELECT c FROM Client c "
				+ "WHERE c.code LIKE ?1 "
				+ "AND c.nom LIKE ?2 "
				+ "AND c.prenom LIKE ?3")
		
})
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	// Propriétés de base de la classe
	// -------------------------------
	// l'identifiant en base de données
	@Id
	private String code;

	@Basic
	private String nom;

	private String prenom;

	private boolean carte_fidele;

	private LocalDate date;

	@OneToOne(cascade=CascadeType.ALL)
	private Adresse adresse;

	// Getters de base
	// ---------------
	public String getCode() {
		return code;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public boolean isCarteFidele() {
		return carte_fidele;
	}

	public Instant getDateCreation() {
		return GestionDates.instant(date);
	}

	// Setters
	public void setCode(String code) {
		this.code = code;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setCarteFidele(boolean carte_fidele) {
		this.carte_fidele = carte_fidele;
	}

	public void setDateCreation(Instant date_creation) {
		this.date = GestionDates.date(date_creation);
	}
	
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
	public Adresse getAdresse() {
		return this.adresse;
	}

	// CONSTRUCTEURS
	// -------------
	// 1er Constructeur
	// pour la création complète d'un client
	// limitée ici à 5 propriétés pour alléger le code
	public Client(String code, 
			String nom, String prenom,
			boolean carteFidelite, Instant creation) {
		this.code = code;
		this.nom = nom;
		this.prenom = prenom;
		carte_fidele = carteFidelite;
		setDateCreation(creation);
//		LocalDate.of(date.getYear() +1900, date.getMonth(), date.getDate());
	}

	/**
	 * Utilisé par JPA.
	 */
	public Client() {
		super();
	}

	@Override
	public String toString() {
		return "Client [code=" + this.code + ", nom=" + this.nom + ", prenom="
				+ this.prenom + ", carte_fidele=" + this.carte_fidele
				+ ", date=" + this.date + "]";
	}

	// ------------------------------------
	// Méthodes utilisées par Jasper
	// ------------------------------------
	@Deprecated
	public Date getCreation() {
		return Date.from(getDateCreation());
	}
	// ------------------------------------
}
