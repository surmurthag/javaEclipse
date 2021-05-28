package entite;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import controle.utilitaires.GestionDates;

@Entity
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	private Categorie categorie;

	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	private Set<Fournisseur> fournisseurs = new HashSet<>();
	
	@Basic
	private String designation;

	@Basic
	private int quantite;

	@Column(name="prix_unitaire")
	private double prixUnitaire;

	private LocalDate date;

	/*
	 * Constructeur
	 */
	public Article(String code, String codeCategorie,
			String designation,
			int quantite, double prixUnitaire,
			Instant date) {
		this(code, 
				new Categorie().setCode(codeCategorie),
				designation, 
				quantite, prixUnitaire,
				date);
	}
	
	public Article(String code, Categorie categorie,
			String designation,
			int quantite, double prixUnitaire,
			Instant date) {
		this.code = code;
		this.categorie = categorie;
		this.designation = designation;
		this.quantite = quantite;
		this.prixUnitaire = prixUnitaire;
		setDate(date);
	}

	/*
	 * Constructeur 2
	 */
	public Article() {
	}

	/*
	 * Accesseurs
	 */
	public String getCode() {
		return code;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public String getDesignation() {
		return designation;
	}

	public int getQuantite() {
		return quantite;
	}

	public double getPrixUnitaire() {
		return prixUnitaire;
	}

	public Instant getDate() {
		return GestionDates.instant(date);
	}
	
	public Set<Fournisseur> getFournisseurs() {
		return this.fournisseurs;
	}

	/*
	 * Mutateurs
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public void setReference(Categorie categorie) {
		this.categorie = categorie;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public void setPrixUnitaire(double prix_unitaire) {
		this.prixUnitaire = prix_unitaire;
	}

	public void setFournisseurs(Set<Fournisseur> fournisseurs) {
		this.fournisseurs = fournisseurs;
	}
	
	public void setDate(Instant date) {
		this.date = GestionDates.date(date);
	}
}
