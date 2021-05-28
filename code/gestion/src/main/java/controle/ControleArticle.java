package controle;

import java.awt.Window;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import controle.connection.Connexion;
import controle.etat.JasperFacade;
import controle.modele.ModeleArticles;
import dialogue.FExport;
import entite.Article;
import entite.crud.ArticleCrud;

public class ControleArticle {

	private final ArticleCrud crud;
	private final ModeleArticles leModeleArticle;

	public ControleArticle(Connexion laConnexion) {
		crud = new ArticleCrud(laConnexion);
		List<Article> vide = Collections.emptyList();
		leModeleArticle = new ModeleArticles(vide);
	}
	
	public void init() {
		List<Article> articles = crud.lire();
		leModeleArticle.lu(articles);
	}

	public boolean creer(String code, String reference, String designation,
			int quantite, double prixUnitaire) {
		Instant maintenant = Instant.now();

		Article unArticle = new Article(code, reference, designation,
										quantite, prixUnitaire,
										maintenant);

		crud.creer(unArticle);
		leModeleArticle.créé(unArticle);
		return true;
	}

	public boolean modifier(int numeroLigne, String code,
			String reference, String designation, int quantite,
			double prixUnitaire) {
		Instant maintenant = Instant.now();

		Article unArticle = new Article(code, reference, designation,
				quantite, prixUnitaire, maintenant);
		boolean modif = crud.modifier(code, reference, designation,
											quantite, prixUnitaire);
		if (modif) {
			leModeleArticle.modifié(numeroLigne, unArticle);
		}
		return modif;
	}

	public void supprimer(int numeroLigne, String code) {
		crud.supprimer(code);
		leModeleArticle.supprimé(numeroLigne);
	}

	public void apercu() {
		List<Article> articles = crud.lire();
		JasperFacade.apercu("Articles.jrxml", articles);
	}

	public void imprimer() {
		List<Article> articles = crud.lire();
		JasperFacade.imprimer("Articles.jrxml", articles);
	}

	public void export(Window parent) {
		List<Article> articles = crud.lire();
		FExport export = new FExport(parent, "articles", "Articles.jrxml", articles);
		export.setLocationRelativeTo(export.getParent());
		export.setVisible(true);
	}

	public void rechercher(String recherche) {
		List<Article> nouvelleListe = crud.chercherTous(recherche);
		leModeleArticle.lu(nouvelleListe);
	}

	public void rafraichir() {
		leModeleArticle.lu(crud.lire());
	}

	public ModeleArticles getModele() {
		return leModeleArticle;
	}
}
