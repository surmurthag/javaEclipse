package controle;

import java.awt.Window;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import controle.connection.Connexion;
import controle.modele.ModeleCommandes;
import entite.Commande;
import entite.ModeReglements;
import entite.crud.EniException;

public class ControleCommandeDecore {

	private final ControleCommande controle;
	
	public ControleCommandeDecore(Connexion connexion) {
		this( new ControleCommande(connexion));
	}
	
	public ControleCommandeDecore(ControleCommande controle) {
		this.controle = controle;
	}

	public void init() {
		try {
			this.controle.init();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"ajout non effectuée dans la BD.\n\n" + e.getMessage(),
					"Problème rencontré", JOptionPane.ERROR_MESSAGE);
		}
	}

	public List<ModeReglements> getModeReglements() {
		try {
			return controle.getModeReglements();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Impossible de lire les modes de règlements.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
			return Collections.emptyList();
		}
	}

	public boolean creerLigne(String codeArticle, int quantite) {
		try {
			return controle.creerLigne(codeArticle, quantite);
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Aucune lecture effectuée dans la BD.\n\n"
									+ e.getMessage(), "Problème rencontré",
							JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	public Commande nouvelleCommande() {
		return controle.nouvelleCommande();
	}
	
	public void creerCommande() throws EniException {
		try {
			controle.creerCommande();
		} catch (Exception e) {
			throw new EniException("Aucune création effectuée dans la BD.\n\n"
							+ e.getMessage(), e);
		}
	}

	public void supprimer(int numeroLigne) {
		controle.supprimer(numeroLigne);
	}

	public void supprimerToutesLesLignes() {
		controle.supprimerToutesLesLignes();
	}

	public TableModel getModeleLignes() {
		return controle.getModeleLignes();
	}

	public ModeleCommandes getModeleCommande() {
		return controle.getModeleCommande();
	}

	public void rechercher(String recherche) {
		try {
			controle.rechercher(recherche);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Aucune recherche effectuée dans la BD.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean supprimerCommande(String codeCommande, int ligne_a_supprimer) {
		try {
			return controle.supprimerCommande(codeCommande, ligne_a_supprimer);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Aucune suppression effectuée dans la BD.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	public void imprimer(Commande commande) {
		try {			
			controle.imprimer(commande);
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Aucune lecture effectuée dans la BD.\n\n"
									+ e.getMessage(), "Problème rencontré",
							JOptionPane.ERROR_MESSAGE);
		}
	}

	public void apercu(Commande commande) {
		try {
			controle.apercu(commande);
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Aucune lecture effectuée dans la BD.\n\n"
									+ e.getMessage(), "Problème rencontré",
							JOptionPane.ERROR_MESSAGE);
		}

	}

	public void export(Commande commande, Window parent) {
		try {
			controle.export(commande, parent);
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Aucune lecture effectuée dans la BD.\n\n"
									+ e.getMessage(), "Problème rencontré",
							JOptionPane.ERROR_MESSAGE);
		}
	}

	public Commande getCommande() {
		return controle.getCommande();
	}
}
