package entite.crud;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import controle.connection.Connexion;
import entite.Article;

public class ArticleCrud {

	private final Connexion laConnexion;

	/*
	 * Constructeur
	 */
	public ArticleCrud(Connexion connexion) {
		this.laConnexion = connexion;
	}

	/**
	 * Ajout d'un nouvel article dans la BD.
	 * 
	 * @throws IllegalArgumentException si il est impossible de créer l'article.
	 */
	public void creer(Article article) {

		laConnexion.modifier((gestionnaireEntite) -> {
			gestionnaireEntite.persist(article);
			return article;
		});
	}

	/*
	 * Lecture et récupération des enregistrements de la BD
	 */
	@SuppressWarnings("unchecked")
	public List<Article> lire() {
		return laConnexion.chercher((gestionnaireEntite) -> {
			Query query = gestionnaireEntite
					.createQuery("SELECT a FROM Article a");
			
			return query.getResultList();			
		});
	}

	// lire un article particulier de la BdD
	public Optional<Article> lire(String code) {
		return laConnexion.chercher((gestionnaireEntite) -> {			
			Query query = gestionnaireEntite
					.createQuery("SELECT a FROM Article a WHERE a.code = :code");
			query.setParameter("code", code);
			try {
				Article article = (Article) query.getSingleResult();
				return Optional.ofNullable(article);
			} catch (RuntimeException e) {
				return Optional.<Article>empty();
			}
		});
	}

	/*
	 * Modification d'un article dans la BD
	 */
	public boolean modifier(String code, String codeCategorie,
			String designation, int quantite, double prixUnitaire) {
		Article article = new Article(code, codeCategorie, designation, quantite, prixUnitaire, Instant.now());
		return modifier(article);
	}

	public boolean modifier(Article unArticle) {
		return laConnexion.modifier((gestionnaireEntite) -> {				
			String code = unArticle.getCode();
			Article article = gestionnaireEntite.find(Article.class, code);
			
			if (article == null) {
				throw new IllegalArgumentException("Pas d'article pour le code " + code);
			} else {
				article.setReference(unArticle.getCategorie());
				article.setDesignation(unArticle.getDesignation());
				article.setQuantite(unArticle.getQuantite());
				article.setPrixUnitaire(unArticle.getPrixUnitaire());
			}
			return true;
		});
	}
	/*
	 * Suppression d'un client dans la BD
	 */
	public boolean supprimer(String vCode) {
		return laConnexion.modifier((gestionnaireEntite) -> {
			Article article = gestionnaireEntite.find(Article.class, vCode);
			if (article == null) {
				throw new IllegalArgumentException("Pas d'article pour le code " + vCode);
			} else {				
				gestionnaireEntite.remove(article);				
			}
			return true;
		});
	}

	/*
	 * Recherche dans la BD
	 */
	@SuppressWarnings("unchecked")
	public List<Article> chercherTous(String recherche) {
		return laConnexion.chercher((gestionnaireEntite) -> {
			String requete = ""
			+ "SELECT a "
			+ "FROM Article a "
			+ "WHERE a.code LIKE '%" + recherche + "%' "
			+ "OR a.categorie.code LIKE '%" + recherche + "%' "
			+ "OR a.designation LIKE '%" + recherche + "%' ";
			
			Query query = gestionnaireEntite.createQuery(requete);
			return query.getResultList();			
		});
	}

	/*
	 * Recherche rapide juste sur le code
	 */
	@SuppressWarnings("unchecked")
	public List<Article> chercherParCode(String vCode) {
		return laConnexion.chercher((gestionnaireEntite) -> {			
			Query requete = gestionnaireEntite.createQuery("SELECT a FROM Article a WHERE a.code LIKE ?1");
			requete.setParameter(1, vCode);
			
			return requete.getResultList();
		});
	}
}
