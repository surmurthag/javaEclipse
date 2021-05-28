package controle;


import java.awt.Window;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.ParseException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import controle.connection.Connexion;
import controle.etat.JasperFacade;
import controle.modele.ModeleClients;
import dialogue.FExport;
import entite.Client;
import entite.crud.ClientCrud;

public class ControleClient {

	private final ClientCrud crud;
	private final ModeleClients leModeleClients;

	private final PropertyChangeSupport notifications = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.notifications.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.notifications.removePropertyChangeListener(listener);
	}

	public ControleClient(Connexion connexion) {
		crud = new ClientCrud(connexion);
		List<Client> clients = null;
		try { 
			clients = crud.lire();
		} catch (Exception e){
			JOptionPane.showMessageDialog(null, 
					"Aucune lecture effectuée dans la BD.\n\n" +e.getMessage(),
					"Problème rencontré", 
					JOptionPane.ERROR_MESSAGE);        
			clients = Collections.emptyList();
		}

		leModeleClients = new ModeleClients(clients);
	}

	public ModeleClients getModele() {
		return leModeleClients;
	}

	public Client creer(String code, String nom, String prenom,
			boolean carte, Instant crééLe) {
		if (crééLe == null ) {
			crééLe = Instant.now();
		}
		// création d'une instance client pour obtenir le CRUD
		Client leClient = new Client(code, nom, prenom, carte, crééLe);
		try {
			// 1. sauvegarde d'abord dans la BD
			crud.creer(leClient);

			// 2. puis ajout dans le modèle
			leModeleClients.créé(leClient);
			return leClient;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Aucune création effectuée dans la BD.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}

		return null;
	}

	public boolean modifier(String code, String nom, String prenom,
			boolean carteFidelite, Instant creation) throws ParseException {

		// création d'une instance client pour abriter 
		// les données à modifier par le CRUD
		Client leClient = new Client(code, nom, prenom,
									carteFidelite, creation);
		try {
			Client ancienClient = crud.lire(code);
			// 1. sauvegarde d'abord dans la BD
			leClient = crud.modifier(leClient);
			notifications.firePropertyChange("client", ancienClient, leClient);
			// 2. puis ajout dans le modèle --> MAJ du JTable auto
			if (leClient != null) {
				leModeleClients.modifié(leClient);
				return true;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Aucune modification effectuée dans la BD.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	public void supprimer(int numeroLigne) {
		Client client = leModeleClients.getClient(numeroLigne);
		try {
			crud.supprimer(client.getCode());

			// suppression de la ligne dans le modèle
			leModeleClients.supprimé(numeroLigne);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Aucune suppression effectuée dans la BD.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void rechercher(String code, String nom, String prenom) {
		try {
			List<Client> lus = crud.chercher(code, nom, prenom);
			leModeleClients.lu(lus);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Aucune recherche effectuée dans la BD.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void rechercher(String text) {
		try {
			List<Client> lus = crud.chercher(text);
			leModeleClients.lu(lus);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"Aucune recherche effectuée dans la BD.\n\n"
							+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void apercu() {
		new SwingWorker<JasperPrint, Void>() {

			@Override
			protected JasperPrint doInBackground() throws Exception {
				List<Client> clients = crud.lire();
				return JasperFacade.chargeEtcompile("Clients.jrxml", clients);
			}

			@Override
			protected void done() {
				try {
					JasperPrint print = get();
					JasperFacade.apercu(print);

				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				} catch (ExecutionException e) {
					JOptionPane.showMessageDialog(null, "Aucun aperçu.\n\n"
							+ e.getCause().getMessage(), "Problème rencontré",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}.execute();
	}

	public void apercuDirect() {
		try {
			JasperPrint print = JasperFacade.chargeEtcompile("Clients.jrxml", crud.lire());
			JasperFacade.apercu(print);
		} catch (JRException e) {
			JOptionPane.showMessageDialog(null, "Aucun aperçu.\n\n"
					+ e.getMessage(), "Problème rencontré",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}


	public void imprimer() {
		System.out.println(System.currentTimeMillis() + ": impression en préparation");
		new SwingWorker<JasperPrint, Void>() {

			@Override
			protected JasperPrint doInBackground() throws Exception {
				List<Client> données = crud.lire();
				return JasperFacade.chargeEtcompile("Clients.jrxml", données);
			}

			@Override
			protected void done() {
				try {
					JasperPrint print = get();
					JasperFacade.imprimer(print);

				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				} catch (ExecutionException e) {
					JOptionPane.showMessageDialog(null,
							"Impression impossible.\n\n"
									+ e.getCause().getMessage(),
							"Problème rencontré", JOptionPane.ERROR_MESSAGE);
				} catch (JRException e) {
					JOptionPane.showMessageDialog(null,
							"Impression impossible.\n\n" + e.getMessage(),
							"Problème rencontré", JOptionPane.ERROR_MESSAGE);
				}
			}
		}.execute();
	}

	public void export(Window parent) {
		System.out.println(System.currentTimeMillis() + ": export en préparation");
		new SwingWorker<List<Client>, Void>() {

			@Override
			protected List<Client> doInBackground() throws Exception {
				return crud.lire();
			}

			@Override
			protected void done() {
				try {
					List<Client> elements = get();
					FExport laFenetre = new FExport(parent,	"clients", "Clients.jrxml", elements);
					laFenetre.setModal(true);
					laFenetre.setLocationRelativeTo(null);
					laFenetre.setVisible(true);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				} catch (ExecutionException e) {
					JOptionPane.showMessageDialog(null,
							"Lecture impossible pour export.\n\n"
									+ e.getCause().getMessage(),
							"Problème rencontré", JOptionPane.ERROR_MESSAGE);
				}
			}
		}.execute();
	}
}
