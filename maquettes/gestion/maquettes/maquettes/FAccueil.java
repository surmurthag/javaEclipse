package maquettes;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import maquettes.article.FArticles;
import maquettes.client.PClients;
import maquettes.commande.FCommandes;
import net.miginfocom.swing.MigLayout;
import controle.connection.Connexion;

public class FAccueil extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final Connexion connexion;

	private JPanel contentPane;
	
	private final Action actionQuitter = new ActionQuitter();
	private final Action actionClients = new ActionClients();
	private final Action actionArticles = new ActionArticles();

	/**
	 * Create the frame.
	 */
	public FAccueil(Connexion connexion) {
		this.connexion = connexion;
		
		setIconImage(UI.getLogo());
		setTitle("Accueil");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 926, 686);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		UI.habiller(menuBar);
		
		JMenu mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);
		UI.habiller(mnFichier);
		
		JMenuItem mntmParamtres = new JMenuItem("Paramètres");
		mnFichier.add(mntmParamtres);
		UI.habiller(mntmParamtres);

		JSeparator separator = new JSeparator();
		mnFichier.add(separator);
		
		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.setAction(actionQuitter);
		mnFichier.add(mntmQuitter);
		UI.habiller(mntmQuitter);

		JMenu mnVues = new JMenu("Vues");
		mnVues.setMnemonic(KeyEvent.VK_V);
		menuBar.add(mnVues);
		UI.habiller(mnVues);

		JMenuItem mntmClients = new JMenuItem("Clients");
		mntmClients.setAction(actionClients);
		mnVues.add(mntmClients);
		UI.habiller(mntmClients);

		JMenuItem mntmArticles = new JMenuItem("Articles");
		mntmArticles.setAction(actionArticles);
		mnVues.add(mntmArticles);
		UI.habiller(mntmArticles);

		JMenuItem mntmCommandes = new JMenuItem("Commandes");
		mnVues.add(mntmCommandes);

		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_menu = new JPanel();
		panel_menu.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel_menu.setBackground(SystemColor.controlShadow);
		panel_menu.setBounds(0, 0, 200, 300);
		contentPane.add(panel_menu, BorderLayout.WEST);
		
		JLabel lblTitre = new JLabel("SARL Gestion");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(lblTitre.getFont().deriveFont(33f));
		
		JLabel lblInfos = new JLabel("Affichage option");
		lblInfos.setForeground(SystemColor.control);
		lblInfos.setBackground(SystemColor.controlShadow);
		lblInfos.setOpaque(true);
		lblInfos.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfos.setFont(lblInfos.getFont().deriveFont(21f));

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setAction(actionQuitter);
		btnQuitter.setIcon(new ImageIcon(FAccueil.class.getResource("/images/connection/Stop-48.png")));
		UI.deshabillerBouton(btnQuitter, "connection", "Stop");
		btnQuitter.setForeground(SystemColor.control);
		
		GroupLayout gl_panel_menu = new GroupLayout(panel_menu);
		gl_panel_menu.setHorizontalGroup(
			gl_panel_menu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_menu.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblInfos, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
					.addContainerGap())
				.addComponent(btnQuitter, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
				.addComponent(lblTitre, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
		);
		gl_panel_menu.setVerticalGroup(
			gl_panel_menu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_menu.createSequentialGroup()
					.addGap(7)
					.addComponent(lblTitre)
					.addGap(30)
					.addComponent(lblInfos)
					.addGap(348)
					.addComponent(btnQuitter)
					.addContainerGap(81, Short.MAX_VALUE))
		);
		panel_menu.setLayout(gl_panel_menu);
		
		JPanel panel_principal = new JPanel();
		panel_principal.setBorder(new EmptyBorder(5, 0, 5, 5));
		contentPane.add(panel_principal, BorderLayout.CENTER);
		panel_principal.setLayout(new MigLayout("", "[33%][33%][33%]", "[33%][33%][33%]"));

		JButton btnArticles = new JButton();
		btnArticles.setIcon(new ImageIcon(FAccueil.class.getResource("/images/accueil/Product-128.png")));
		UI.deshabillerBouton(btnArticles, "accueil", "Product", 128);
		btnArticles.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				afficherArticles();
			}
		});
		btnArticles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblInfos.setText("Articles");
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				lblInfos.setText(" ");
			}
		});				
		btnArticles.setCursor(new Cursor(Cursor.HAND_CURSOR));			
		panel_principal.add(btnArticles, "cell 1 0,alignx center");
		
		JButton btnClients = new JButton();
		btnClients.setIcon(new ImageIcon(FAccueil.class.getResource("/images/accueil/People-128-actif.png")));
		UI.deshabillerBouton(btnClients, "accueil", "People", 128);
		btnClients.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				afficherClients();
			} 
		});
		btnClients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblInfos.setText("Clients");
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				lblInfos.setText(" ");
			}
		});		
		btnClients.setCursor(new Cursor(Cursor.HAND_CURSOR));			
		panel_principal.add(btnClients, "cell 0 1,alignx center");

		JButton btnStats = new JButton("");
		UI.deshabillerBouton(btnStats, "accueil", "Diagram", 128);
		btnStats.addActionListener(e -> afficherStats());
		btnStats.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblInfos.setText("Statistiques");
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				lblInfos.setText(" ");
			}
		});
		btnStats.setIcon(new ImageIcon(FAccueil.class.getResource("/images/accueil/Diagram-128.png")));
		btnStats.setCursor(new Cursor(Cursor.HAND_CURSOR));			
		panel_principal.add(btnStats, "cell 1 1,alignx center");
		
		JButton btnCommandes = new JButton("");
		UI.deshabillerBouton(btnCommandes, "accueil", "Shopping-Bag", 128);
		btnCommandes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCommandes.setIcon(new ImageIcon(FAccueil.class.getResource("/images/accueil/Shopping-Bag-128-actif.png")));
				lblInfos.setText("Commandes");
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnCommandes.setIcon(new ImageIcon(FAccueil.class.getResource("/images/accueil/Shopping-Bag-128.png")));
				lblInfos.setText(" ");
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				afficherCommandes();
			}
		});
		btnCommandes.setIcon(new ImageIcon(FAccueil.class.getResource("/images/accueil/Shopping-Bag-128.png")));
		btnCommandes.setCursor(new Cursor(Cursor.HAND_CURSOR));			
		panel_principal.add(btnCommandes, "cell 2 1,alignx center");
		
		JButton btnParametres = new JButton ();
		btnParametres.setIcon(new ImageIcon(FAccueil.class.getResource("/images/accueil/Settings-02-128.png")));
		UI.deshabillerBouton(btnParametres, "accueil", "Settings-02", 128);
		btnCommandes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblInfos.setText("Paramètres");
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				lblInfos.setText(" ");
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				afficherParametres();
			}
		});
		btnParametres.setCursor(new Cursor(Cursor.HAND_CURSOR));			
		panel_principal.add(btnParametres, "cell 1 2,alignx center");
		
		
		SwingUtilities.invokeLater(
				() -> btnQuitter.requestFocusInWindow()		
		);
	}

	protected void afficherParametres() {
		// a faire soi-même
	}

	protected void afficherCommandes() {
	    FCommandes laFenetre = new FCommandes(this).setConnexion(connexion);
	    laFenetre.setVisible(true);
	}

	protected void afficherStats() {
	}

	protected void afficherArticles() {
	    FArticles laFenetre = new FArticles(this).setConnexion(connexion);
	    laFenetre.setVisible(true);
	}

	protected void afficherClients() {
		JDialog dialog = new JDialog(this);
		PClients clients = new PClients();
		clients.setFenetre(dialog);
		dialog.setContentPane(clients);
		dialog.setIconImage(UI.getLogo());
		dialog.setTitle("Gestion des Clients");
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setBounds(100, 100, 1000, 700);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	private class ActionQuitter extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionQuitter() {
			putValue(NAME, "Quitter");
			putValue(SHORT_DESCRIPTION, "Quitter l'application");
		}
		
		public void actionPerformed(ActionEvent e) {
			Connexion.getConnexion().fermeture();
			FAccueil.this.dispose();
		}
	}
	
	
	private class ActionClients extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionClients() {
			putValue(NAME, "Clients");
			putValue(SHORT_DESCRIPTION, "Afficher la liste des clients");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("C"));
			putValue(MNEMONIC_KEY, KeyEvent.VK_C);
		}
		
		public void actionPerformed(ActionEvent e) {
			afficherClients();
		}
	}
	
	private class ActionArticles extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionArticles() {
			putValue(NAME, "Articles");
			putValue(SHORT_DESCRIPTION, "Afficher la liste des articles");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
			putValue(MNEMONIC_KEY, KeyEvent.VK_A);
		}
		
		public void actionPerformed(ActionEvent e) {
			afficherArticles();
		}
	}
}
