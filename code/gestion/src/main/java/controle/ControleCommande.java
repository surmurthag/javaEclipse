package controle;

import java.awt.Window;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

import javax.swing.table.TableModel;

import controle.connection.Connexion;
import controle.etat.JasperFacade;
import controle.modele.ModeleCommandes;
import controle.modele.ModeleLignes;
import dialogue.FExport;
import entite.Article;
import entite.Commande;
import entite.Ligne;
import entite.ModeReglements;
import entite.crud.ArticleCrud;
import entite.crud.CommandeCrud;
import entite.crud.EniException;
import entite.crud.ModeReglementsCrud;

public class ControleCommande {

	private final CommandeCrud commandeCrud;
	private final ArticleCrud articleCrud;
	private final ModeReglementsCrud reglementsCrud;
	
	private final ModeleLignes leModeleLigneCommande;
	private final ModeleCommandes leModeleCommande;

	private Commande commande;
	
	public ControleCommande(Connexion connexion) {
		commandeCrud = new CommandeCrud(connexion);
		articleCrud = new ArticleCrud(connexion);
		reglementsCrud = new ModeReglementsCrud(connexion);
		leModeleLigneCommande = new ModeleLignes();
		List<Commande> commandes = Collections.emptyList();
		leModeleCommande = new ModeleCommandes(commandes);
	}

	public void init() {
		List<Commande> commandes = commandeCrud.lire();
		leModeleCommande.lu(commandes);
		if (getModeReglements().isEmpty()) {
			reglementsCrud.creer("Carte Bleue");
			reglementsCrud.creer("Chèque");
			reglementsCrud.creer("Virement");
		}
	}
	
	public List<ModeReglements> getModeReglements() {
		return reglementsCrud.lire();
	}

	public boolean creerLigne(String codeArticle, int quantite) {
		Optional<Article> peutEtreUnArticle = articleCrud.lire(codeArticle);
		return peutEtreUnArticle.map(new Function<Article, Boolean>() {

			@Override
			public Boolean apply(Article article) {
				Ligne uneLigne = new Ligne(article,
						quantite);
				commande.ajouter(uneLigne);
				leModeleLigneCommande.créée(uneLigne);
				return true;
			}
		}).orElse(Boolean.FALSE);
//		return peutEtreUnArticle.map((article) -> {
//			LigneCommande uneLigne = new LigneCommande(article,
//					quantite);
//			commande.ajouter(uneLigne);
//			leModeleLigneCommande.créée(uneLigne);
//			return true;
//		}).orElse(Boolean.FALSE);
	}

	private static String randomCommandeNumber() {
		Random rand = new Random();
		String num = "COM" + rand.nextInt(999); //$NON-NLS-1$
		return num;
	}

	public Commande nouvelleCommande() {
		String code	= randomCommandeNumber();
		commande = new Commande(code);
		leModeleLigneCommande.lu(commande.getLignes());
		return commande;
	}
	
	public void creerCommande() throws EniException {
		if (commande.getClient() == null) {
			throw new EniException("Pas de client "
					+ "pour la commande " +commande.getCode());
		}
		commande.maintenant();
		commandeCrud.creer(commande);
		JasperFacade.apercu("commande.jrxml", commande.getLignes());
	}

	public void supprimer(int numeroLigne) {
		leModeleLigneCommande.supprimée(numeroLigne);
	}

	public void supprimerToutesLesLignes() {
		leModeleLigneCommande.supprimées();
	}

	public TableModel getModeleLignes() {
		return leModeleLigneCommande;
	}

	public ModeleCommandes getModeleCommande() {
		return leModeleCommande;
	}

	public void rechercher(String recherche) {
		List<Commande> lesCommandes = commandeCrud.chercher(recherche);
		leModeleCommande.lu(lesCommandes);
	}

	public boolean supprimerCommande(String codeCommande, int ligne_a_supprimer) throws EniException {
		commandeCrud.supprimer(codeCommande);
		leModeleCommande.supprimé(ligne_a_supprimer);
		return true;
	}

	public void imprimer(Commande commande) {
		JasperFacade.imprimer("commande.jrxml", commande.getLignes());
	}

	public void apercu(Commande commande) {
		List<Ligne> lignes = commande.getLignes();
		JasperFacade.apercu("commande.jrxml", lignes);
	}

	public void export(Commande commande, Window parent) {
		FExport export = new FExport(parent, "commandes", "commande.jrxml",
					commande.getLignes());
		export.setLocationRelativeTo(export.getParent());
		export.setModal(true);
		export.setVisible(true);
	}

	public Commande getCommande() {
		return commande;
	}
}
