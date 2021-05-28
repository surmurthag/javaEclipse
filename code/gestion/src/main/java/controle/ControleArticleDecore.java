package controle;

import java.awt.Window;
import java.util.function.Function;

import javax.swing.JOptionPane;

import controle.connection.Connexion;
import controle.modele.ModeleArticles;

public class ControleArticleDecore {

	private final ControleArticle controle;

	public ControleArticleDecore(Connexion laConnexion) {
		this(new ControleArticle(laConnexion));
	}
	
	public ControleArticleDecore(ControleArticle controle) {
		this.controle = controle;
	}
	
	public void init() {
		try { 
			controle.init();
		} catch (Exception e){
			JOptionPane.showMessageDialog(null, 
					"Aucune lecture effectuée dans la BD.\n\n"
					+e.getMessage(),
					"Problème rencontré", 
					JOptionPane.ERROR_MESSAGE);        
		}
	}

	public ModeleArticles getModele() {
		return this.controle.getModele();
	}

	public boolean creer(String code, String reference,
			String designation,
			int quantite, double prixUnitaire) {
		try {
			return controle.creer(code, reference,
									designation, 
									quantite, prixUnitaire);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Aucun ajout effectué dans la BD.\n\n"
					+ e.getMessage(),
					"Problème rencontré", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public boolean modifier(int numeroLigne, String code,
							String reference,
							String designation,
							int quantite, double prixUnitaire) {
		try {
			return controle.modifier(numeroLigne, code,
									reference, designation,
									quantite, prixUnitaire);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Aucune modification effectuée dans la BD.\n\n"
					+ e.getMessage(),
					"Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private void operation(Runnable operation, Function<Exception, String> message) {
		try {
			operation.run();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null,
							message.apply(exception),
							"Problème rencontré",
							JOptionPane.ERROR_MESSAGE);
		}
	}

	public void supprimer(int numLigne, String code) {
		operation(() -> controle.supprimer(numLigne, code), 
				   e -> "Aucune suppression effectuée dans la BD.\n\n"
						+ e.getMessage());
	}

	public void supprimer2(int numLigne, String code) {
		operation(new Runnable() {
			public void run() {
				controle.supprimer(numLigne, code);
			}
		}, new Function<Exception, String>() {

			@Override
			public String apply(Exception e) {
				return "Aucune suppression effectuée "
						+ "dans la BD.\n\n"
						+ e.getMessage();
			}
		});
	}

	public void apercu() {
		operation(() -> controle.apercu(), 
				   e -> "Aucune lecture effectuée dans la BD.\n\n"
						+ e.getMessage());
	}

	public void imprimer() {
		operation(() -> controle.imprimer(), 
				   e -> "Aucune lecture effectuée dans la BD.\n\n"
						+ e.getMessage());
	}

	public void export(Window parent) {
		operation(() -> controle.export(parent), 
				   e -> "Aucune lecture effectuée dans la BD.\n\n"
						+ e.getMessage());
	}

	public void rechercher(String recherche) {
		operation(() -> controle.rechercher(recherche), 
				   e -> "Aucune recherche effectuée dans la BD.\n\n"
						+ e.getMessage());
	}

	public void rafraichir() {
		operation(() -> controle.rafraichir(), 
				   e -> "Aucune lecture effectuée dans la BD.\n\n"
						+ e.getMessage());
	}
}
