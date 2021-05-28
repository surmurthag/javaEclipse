package dialogue.stats;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controle.connection.Connexion;
import dialogue.UI;

public class FTableauBord extends JDialog {
    private static final long serialVersionUID = 1L;
	private final Connexion connexion;
	private JButton btnCommandes;
	private JButton btnImpays;
	private JButton btnChiffreDaffaires;
	private JButton btnArticles;
	private JPanel panel;
	private JButton btnClients;
	private Action action;

    /**
     * Create the dialog.
     */
    public FTableauBord(Window parent, Connexion connexion) {
    	super(parent);
    	this.connexion = connexion;
		initialize();
    }
    
    private void initialize() {
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 739, 444);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getBtnImpays(), BorderLayout.EAST);
		getContentPane().add(getBtnChiffreDaffaires(), BorderLayout.CENTER);
		getContentPane().add(getPanel(), BorderLayout.WEST);
		setIconImage(UI.getLogo());
		setTitle("Tableau de bord");
    }    
    
    private void bouton(JButton bouton) {
		bouton.setOpaque(false);
		bouton.setContentAreaFilled(false);
		bouton.setBorderPainted(false);
    }
    
	private JButton getBtnCommandes() {
		if (btnCommandes == null) {
			btnCommandes = new JButton("Commandes");
			btnCommandes.setAction(getAction());
			btnCommandes.setIcon(new ImageIcon(FTableauBord.class.getResource("/images/gestion/commande/Shopping-Bag-64-actif.png")));
			bouton(btnCommandes);
		}
		return btnCommandes;
	}
	private JButton getBtnImpays() {
		if (btnImpays == null) {
			btnImpays = new JButton("Impayés");
			btnImpays.setEnabled(false);
			btnImpays.setIcon(new ImageIcon(FTableauBord.class.getResource("/images/stats/Pending-Payment-128.png")));
			bouton(btnImpays);
		}
		return btnImpays;
	}
	private JButton getBtnChiffreDaffaires() {
		if (btnChiffreDaffaires == null) {
			btnChiffreDaffaires = new JButton("Chiffre d'Affaires");
			btnChiffreDaffaires.setEnabled(false);
			btnChiffreDaffaires.setIcon(new ImageIcon(FTableauBord.class.getResource("/images/stats/Bar-Chart-128.png")));
			bouton(btnChiffreDaffaires);
		}
		return btnChiffreDaffaires;
	}
	private JButton getBtnArticles() {
		if (btnArticles == null) {
			btnArticles = new JButton("Articles");
			btnArticles.setEnabled(false);
			btnArticles.setIcon(new ImageIcon(FTableauBord.class.getResource("/images/gestion/article/Product-64-actif.png")));
			bouton(btnArticles);
		}
		return btnArticles;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			panel.add(getBtnArticles());
			panel.add(getBtnCommandes());
			panel.add(getBtnClients());
		}
		return panel;
	}
	private JButton getBtnClients() {
		if (btnClients == null) {
			btnClients = new JButton("Clients");
			btnClients.setEnabled(false);
			btnClients.setIcon(new ImageIcon(FTableauBord.class.getResource("/images/gestion/client/People-64-actif.png")));
			bouton(btnClients);
		}
		return btnClients;
	}
	
	private class ActionCommandes extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionCommandes() {
			putValue(NAME, "Commandes");
			putValue(SHORT_DESCRIPTION, "Afficher les statistiques des commandes");
		}
		public void actionPerformed(ActionEvent e) {
			String annee = JOptionPane.showInputDialog(null, "Entrez une année : ");
			FStats stats = new FStats(FTableauBord.this, connexion, annee);
			stats.setModal(true);
			stats.setLocationRelativeTo(stats.getParent());
			stats.setVisible(true);
		}
	}

	private Action getAction() {
		if (action == null) {
			action = new ActionCommandes();
		}
		return action;
	}
}
