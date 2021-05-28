package controle.etat;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
// pour la gestion du chemin et des différents OS
import controle.utilitaires.Systeme;

public class JasperFacade {

	// Etapes 1 à 3 du processus Jasper
	// --------------------------------
	public static JasperPrint chargeEtcompile(String rapport,
			Collection<?> elements, Object... paramètres) throws JRException {
		try {
			// Etape 1
			String dossierJasper = Systeme.getRepertoireCourant()
					+ Systeme.getSeparateur() + "jasper"
					+ Systeme.getSeparateur();
			JasperDesign design = JRXmlLoader.load(dossierJasper + rapport);
			// Etape 2
			JasperReport report = JasperCompileManager.compileReport(design);
			// Etape 3

			// les paramètres sont passés en alternance:
			// d'abord la clé puis la valeur,
			// ceci répété pour chaque paramètre
			Map<String, Object> mesParametres = new HashMap<String, Object>();
			if (paramètres != null) {
				for (int i = 0; i < paramètres.length; i += 2) {
					mesParametres
							.put((String) paramètres[i], paramètres[i + 1]);
				}
			}
			mesParametres.put("imagesDir", dossierJasper + "images");

			JRDataSource source = new JRBeanCollectionDataSource(elements);
			return JasperFillManager.fillReport(report, mesParametres, source);
		} catch (JRException e) {
			throw e;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"La compilation du rapport a échouée : \n" + e.getMessage()
							+ "\nVeuillez contacter votre administrateur",
					"Erreur", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	// apercu avant impression
	public static void apercu(String rapport, Collection<?> elements,
			Object... paramètres) {
		try {
			JasperPrint print = chargeEtcompile(rapport, elements, paramètres);
			apercu(print);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erreur lors de l'aperçu : \n"
					+ e.getMessage()
					+ "\nVeuillez contacter votre administrateur", "Erreur",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void apercu(JasperPrint print) {
		JasperViewer.viewReport(print, false);
	}

	// Impression du rapport
	public static void imprimer(String rapport, Collection<?> elements,
			Object... paramètres) {
		try {
			JasperPrint print = chargeEtcompile(rapport, elements, paramètres);
			imprimer(print);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "L'impression a échouée : \n"
					+ e.getMessage()
					+ "\nVeuillez contacter votre administrateur", "Erreur",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void imprimer(JasperPrint print) throws JRException {
		JasperPrintManager.printReport(print, true);
	}

	public static void export(FormatExport format, String prefixe, String rapport,
			Collection<?> elements, Object... paramètres) {
		JFileChooser save = new JFileChooser();
		save.setSelectedFile(new File(prefixe + "." + format.name().toLowerCase()));
		int retour = save.showSaveDialog(save);
		if (retour == JFileChooser.APPROVE_OPTION) {
			try {
				JasperPrint print = chargeEtcompile(rapport, elements,
						paramètres);
				format.export(print, save.getSelectedFile());
			} catch (Exception e) {
				JOptionPane
						.showMessageDialog(
								null,
								String.format(
										"L'export %s a rencontré une erreur : "
										+ "\n%s\n"
										+ "Veuillez contacter votre administrateur",
										format.name(), e.getMessage()), "Erreur",
								JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
