package dialogue.stats;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import controle.connection.Connexion;
import controle.etat.JasperFacade;
import dialogue.UI;

public class FStats extends JDialog {

	private final class Travailleur extends SwingWorker<Collection<Stat>, Stat> {
		private final Connexion connexion;

		public Travailleur(Connexion connexion) {
			this.connexion = connexion;
		}

		@Override
		protected Collection<Stat> doInBackground() throws Exception {

			List<?> results = connexion.chercher((gerant) -> {
				Query query= gerant.createNativeQuery(requete);
				return query.getResultList();
			});
			statistiques = new ArrayList<>(results.size());

			for (Object obj : results) {
				// création de la donnée
				Stat stat = new Stat(obj);
				statistiques.add(stat);
				publish(stat);
			}
			return statistiques;
		}

		protected void process(List<Stat> chunks) {
			for (Stat stat : chunks) {
				// injection des données dans le tableau
				data.setValue(stat.getMois(),
							  stat.getCompteur());
			}
		}

		protected void done() {
//				if (data.getItemCount() == 0) {
			if (statistiques.isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"Aucune donnée trouvée pour cette année");
			} else {
				print.setEnabled(true);
			}
		}
	}

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	// tableau de données associé au JFreeChart
	private DefaultPieDataset data = new DefaultPieDataset();
	private JButton print;
	private String annee;
	private List<Stat> statistiques;

	private final String requete;

	public FStats(Window parent, Connexion connexion, String annee) {
		super(parent);
		this.annee = annee;

		requete = "SELECT MONTH(date) AS mois"
				+ ", COUNT(*) AS nbr "
				+ "FROM commande " 
				+ "WHERE YEAR(date) = '" 
				+ annee + "' "
				+ "GROUP BY MONTH(date)";
		habilleFenetre();

		new Travailleur(connexion).execute();
	}

	private void habilleFenetre() {
		setIconImage(UI.getLogo());
		setTitle("Graphique des Commandes pour l'année " + annee);
		setBounds(100, 100, 747, 632);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPanel());
		getContentPane().add(getPrint(), BorderLayout.SOUTH);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = genereGraphicStats();
			panel.setLayout(null);
		}
		return panel;
	}

	private JPanel genereGraphicStats() {
		JPanel panel = null;
		try {

			// classe pour création et importation des données pour générer le
			// graphique
			JFreeChart chart = ChartFactory.createPieChart(
					// 1er paramètre : titre du graphique
					// 2ème para : données
					"Commandes " + annee, data,
					// 3ème : toolTip
					// 4ème : url
					true, true, false);
			// ChartPanel : composant qui transforme le graphique en JPanel
			ChartPanel chartPane = new ChartPanel(chart);
			chartPane.setBorder(new LineBorder(Color.BLACK, 1, true));

			panel = chartPane;
		} catch (Exception e) {
			panel = new JPanel();
			JOptionPane
					.showMessageDialog(
							null,
							"Une erreur s'est produite lors "
							+ "de la génération du graphique",
							"Erreur", JOptionPane.ERROR_MESSAGE);
		}
		return panel;
	}

	private JButton getPrint() {
		if (print == null) {
			print = new JButton("Imprimer le graphique");
			print.setEnabled(false);
			print.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JasperFacade.apercu("stats.jrxml", statistiques);
				}
			});
			UI.deshabillerBouton(print, "Printer");
			print.setForeground(Color.BLACK);
		}
		return print;
	}
}