package maquettes.commande;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.time.Instant;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import maquettes.UI;
import maquettes.client.PClients;
import controle.connection.Connexion;
import controle.utilitaires.GestionDates;
import entite.ModeReglements;

public class FCommandes extends JDialog {

	private static final long serialVersionUID = 1L;

	// propriétés non graphiques
	// -------------------------
	private Connexion connexion;

	// actions
	// -------------------------
	private final Action actionAccueil = new ActionAccueil();

	private final Action actionVoirCommandes = new ActionVoirCommandes();

	// propriétés graphiques
	// -------------------------
	private JPanel contentPane;
	private JTable table;
	private JTextField txtDate;

	private JTextField txtNumeroCommande;

	private JComboBox<ModeReglements> comboBoxReglement;
	private JTextField txtCode;
	private JTextField txtDesignation;
	private JTextField txtCategorie;
	private JTextField txtMontant;

	private JSpinner spinnerQuantite;

	private JLabel lblValeurTotale;
	private JButton btnChoixclient;

	public FCommandes setConnexion(Connexion connexion) {
		this.connexion = connexion;
		return this;
	}

	/**
	 * Create the frame.
	 * 
	 * @param parent
	 */
	public FCommandes(Window parent) {
		super(parent);

		setIconImage(UI.getLogo());
		setTitle("Gestion des Commandes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		setLocationRelativeTo(null);

		createContents();
	}

	private void createContents() {
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0xFF, 0xBB, 0x33));
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel_menu = new JPanel();
		panel_menu.setBackground(new Color(0xFF, 0x88, 0x00));
		contentPane.add(panel_menu, BorderLayout.WEST);
		panel_menu.setBorder(new CompoundBorder(null, new EmptyBorder(5, 5, 5,
				5)));
		GridBagLayout gbl_panel_menu = new GridBagLayout();
		gbl_panel_menu.columnWidths = new int[] { 0, 0 };
		gbl_panel_menu.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_menu.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_menu.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_menu.setLayout(gbl_panel_menu);

		JLabel lblTitre = new JLabel("Commande");
		lblTitre.setIcon(new ImageIcon(
				FCommandes.class
						.getResource("/images/gestion/commande/Shopping-Bag-64-actif.png")));
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.insets = new Insets(0, 0, 15, 20);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panel_menu.add(lblTitre, gbc_lblTitre);
		lblTitre.setFont(lblTitre.getFont().deriveFont(25f));

		JButton btnCommandes = new JButton("Commandes");
		btnCommandes.setAction(actionVoirCommandes);
		btnCommandes.setHorizontalAlignment(SwingConstants.LEFT);
		btnCommandes.setIcon(new ImageIcon(FCommandes.class
				.getResource("/images/gestion/commande/Receipt-48-actif.png")));
		GridBagConstraints gbc_btnCommandes = new GridBagConstraints();
		gbc_btnCommandes.anchor = GridBagConstraints.LINE_START;
		gbc_btnCommandes.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCommandes.insets = new Insets(0, 0, 5, 0);
		gbc_btnCommandes.gridx = 0;
		gbc_btnCommandes.gridy = 1;
		panel_menu.add(btnCommandes, gbc_btnCommandes);
		btnCommandes.setIcon(new ImageIcon(FCommandes.class
				.getResource("/images/gestion/commande/Receipt-48-actif.png")));
		UI.deshabillerBouton(btnCommandes, "gestion/commande", "Receipt");
		btnCommandes.setForeground(Color.WHITE);

		JButton btnSupprimerLigne = new JButton("SupprimerLigne");
		btnSupprimerLigne.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnSupprimerligne = new GridBagConstraints();
		gbc_btnSupprimerligne.anchor = GridBagConstraints.LINE_START;
		gbc_btnSupprimerligne.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSupprimerligne.insets = new Insets(0, 0, 5, 0);
		gbc_btnSupprimerligne.gridx = 0;
		gbc_btnSupprimerligne.gridy = 3;
		btnSupprimerLigne.setIcon(new ImageIcon(FCommandes.class
				.getResource("/images/gestion/Cancel-48.png")));
		UI.deshabillerBouton(btnSupprimerLigne, "Cancel");
		panel_menu.add(btnSupprimerLigne, gbc_btnSupprimerligne);

		JButton btnSupprimerToutesLignes = new JButton("SupprimerToutesLignes");
		btnSupprimerToutesLignes.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnSupprimertouteslignes = new GridBagConstraints();
		gbc_btnSupprimertouteslignes.anchor = GridBagConstraints.LINE_START;
		gbc_btnSupprimertouteslignes.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSupprimertouteslignes.insets = new Insets(0, 0, 5, 0);
		gbc_btnSupprimertouteslignes.gridx = 0;
		gbc_btnSupprimertouteslignes.gridy = 4;
		panel_menu.add(btnSupprimerToutesLignes, gbc_btnSupprimertouteslignes);
		btnSupprimerToutesLignes.setIcon(new ImageIcon(FCommandes.class
				.getResource("/images/gestion/Garbage-Open-48.png")));
		UI.deshabillerBouton(btnSupprimerToutesLignes, "Garbage-Open");

		JButton btnValider = new JButton("Valider");
		btnValider.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnValider = new GridBagConstraints();
		gbc_btnValider.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnValider.insets = new Insets(10, 0, 5, 0);
		gbc_btnValider.gridx = 0;
		gbc_btnValider.gridy = 5;
		panel_menu.add(btnValider, gbc_btnValider);
		btnValider
				.setIcon(new ImageIcon(
						FCommandes.class
								.getResource("/images/gestion/commande/Shopping-Cart-05-48-actif.png")));
		UI.deshabillerBouton(btnValider, "gestion/commande", "Shopping-Cart-05");
		btnValider.setForeground(Color.WHITE);

		JButton btnApercu = new JButton("Apercu");
		btnApercu.setForeground(Color.WHITE);
		btnApercu.setHorizontalAlignment(SwingConstants.LEFT);
		btnApercu.setIcon(new ImageIcon(FCommandes.class
				.getResource("/images/gestion/Preview-48.png")));
		GridBagConstraints gbc_btnApercu = new GridBagConstraints();
		gbc_btnApercu.weighty = 1.0;
		gbc_btnApercu.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnApercu.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnApercu.insets = new Insets(0, 0, 5, 0);
		gbc_btnApercu.gridx = 0;
		gbc_btnApercu.gridy = 6;
		panel_menu.add(btnApercu, gbc_btnApercu);
		UI.deshabillerBouton(btnApercu, "Preview");

		JButton btnImprimer = new JButton("Imprimer");
		btnImprimer.setForeground(Color.WHITE);
		GridBagConstraints gbc_btnImprimer = new GridBagConstraints();
		gbc_btnImprimer.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnImprimer.anchor = GridBagConstraints.LINE_START;
		gbc_btnImprimer.insets = new Insets(0, 0, 5, 0);
		gbc_btnImprimer.gridx = 0;
		gbc_btnImprimer.gridy = 7;
		panel_menu.add(btnImprimer, gbc_btnImprimer);
		UI.deshabillerBouton(btnImprimer, "Printer");

		JButton btnExport = new JButton("Export");
		btnExport.setForeground(Color.WHITE);
		GridBagConstraints gbc_btnExport = new GridBagConstraints();
		gbc_btnExport.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExport.anchor = GridBagConstraints.LINE_START;
		gbc_btnExport.insets = new Insets(0, 0, 5, 0);
		gbc_btnExport.gridx = 0;
		gbc_btnExport.gridy = 8;
		panel_menu.add(btnExport, gbc_btnExport);
		UI.deshabillerBouton(btnExport, "Data-Export");

		JButton btnAccueil = new JButton("Accueil");
		btnAccueil.setAction(actionAccueil);
		btnAccueil.setForeground(Color.WHITE);
		GridBagConstraints gbc_btnAccueil = new GridBagConstraints();
		gbc_btnAccueil.weighty = 1.0;
		gbc_btnAccueil.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnAccueil.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAccueil.gridx = 0;
		gbc_btnAccueil.gridy = 9;
		panel_menu.add(btnAccueil, gbc_btnAccueil);
		UI.deshabillerBouton(btnAccueil, "Home");

		JPanel panel_principal = new JPanel();
		panel_principal.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel_principal.setBackground(new Color(0xFF, 0xEC, 0xC0));
		contentPane.add(panel_principal, BorderLayout.CENTER);
		GridBagLayout gbl_panel_principal = new GridBagLayout();
		gbl_panel_principal.columnWidths = new int[] { 0 };
		gbl_panel_principal.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_principal.columnWeights = new double[] { 1.0 };
		gbl_panel_principal.rowWeights = new double[] { 0.0, 1.0, 0, 0.0 };
		panel_principal.setLayout(gbl_panel_principal);

		JPanel panel_informations = new JPanel();
		panel_informations.setOpaque(false);
		panel_informations.setBorder(new CompoundBorder(new TitledBorder(
				new LineBorder(new Color(0xFF, 0x88, 0x00), 2, true),
				"Informations g\u00E9n\u00E9rales", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(128, 128, 128)),
				new EmptyBorder(5, 5, 5, 5)));
		GridBagConstraints gbc_panel_formulaire = new GridBagConstraints();
		gbc_panel_formulaire.weightx = 1.0;
		gbc_panel_formulaire.fill = GridBagConstraints.BOTH;
		gbc_panel_formulaire.insets = new Insets(0, 0, 5, 0);
		gbc_panel_formulaire.gridwidth = 3;
		gbc_panel_formulaire.gridx = 0;
		gbc_panel_formulaire.gridy = 0;
		panel_principal.add(panel_informations, gbc_panel_formulaire);
		GridBagLayout gbl_panel_formulaire = new GridBagLayout();
		gbl_panel_formulaire.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_formulaire.rowHeights = new int[] { 0, 0 };
		gbl_panel_formulaire.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		gbl_panel_formulaire.rowWeights = new double[] { 0.0, 0. };
		panel_informations.setLayout(gbl_panel_formulaire);

		JLabel lblNumeroCommande = new JLabel("Commande en cours");
		UI.habiller(lblNumeroCommande);
		GridBagConstraints gbc_lblNumeroCommande = new GridBagConstraints();
		gbc_lblNumeroCommande.anchor = GridBagConstraints.EAST;
		gbc_lblNumeroCommande.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumeroCommande.gridx = 0;
		gbc_lblNumeroCommande.gridy = 0;
		panel_informations.add(lblNumeroCommande, gbc_lblNumeroCommande);

		txtNumeroCommande = new JTextField();
		txtNumeroCommande.setHorizontalAlignment(SwingConstants.CENTER);
		txtNumeroCommande.setEditable(false);
		GridBagConstraints gbc_numeroCommande = new GridBagConstraints();
		gbc_numeroCommande.weightx = 1.0;
		gbc_numeroCommande.insets = new Insets(0, 0, 5, 5);
		gbc_numeroCommande.fill = GridBagConstraints.HORIZONTAL;
		gbc_numeroCommande.gridx = 1;
		gbc_numeroCommande.gridy = 0;
		panel_informations.add(txtNumeroCommande, gbc_numeroCommande);
		txtNumeroCommande.setColumns(10);
		UI.habiller(txtNumeroCommande);

		JLabel lblDate = new JLabel("Date");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.anchor = GridBagConstraints.EAST;
		gbc_lblDate.insets = new Insets(0, 20, 5, 5);
		gbc_lblDate.gridx = 2;
		gbc_lblDate.gridy = 0;
		panel_informations.add(lblDate, gbc_lblDate);
		UI.habiller(lblDate);

		txtDate = new JTextField();
		txtDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setLabelFor(txtDate);
		txtDate.setEditable(false);
		txtDate.setText(GestionDates.dateEnChaineFR(Instant.now()));
		GridBagConstraints gbc_txtDate = new GridBagConstraints();
		gbc_txtDate.gridwidth = 2;
		gbc_txtDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDate.insets = new Insets(0, 0, 5, 0);
		gbc_txtDate.gridx = 3;
		gbc_txtDate.gridy = 0;
		panel_informations.add(txtDate, gbc_txtDate);
		txtDate.setColumns(10);
		UI.habiller(txtDate);

		JLabel lblNomClient = new JLabel("Nom du client");
		GridBagConstraints gbc_lblNomClient = new GridBagConstraints();
		gbc_lblNomClient.anchor = GridBagConstraints.EAST;
		gbc_lblNomClient.insets = new Insets(0, 0, 0, 5);
		gbc_lblNomClient.gridx = 0;
		gbc_lblNomClient.gridy = 1;
		panel_informations.add(lblNomClient, gbc_lblNomClient);
		UI.habiller(lblNomClient);

		btnChoixclient = new JButton("Sélectionner le client");
		GridBagConstraints gbc_btnChoixclient = new GridBagConstraints();
		gbc_btnChoixclient.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnChoixclient.gridwidth = 4;
		gbc_btnChoixclient.gridx = 1;
		gbc_btnChoixclient.gridy = 1;
		panel_informations.add(btnChoixclient, gbc_btnChoixclient);
		UI.deshabillerBouton(btnChoixclient, "Search");
		btnChoixclient.setOpaque(true);
		btnChoixclient.setBackground(new Color(0xFF, 0xBB, 0x33));
		// btnChoixclient.setBackground(new Color(0x33, 0xB5, 0xE5));

		JPanel panel_valeurs = new JPanel();
		panel_valeurs.setOpaque(false);
		panel_valeurs.setBorder(new CompoundBorder(new TitledBorder(
				new LineBorder(new Color(0xFF, 0x88, 0x00), 2, true),
				"Valeurs de la commande", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(128, 128, 128)),
				new EmptyBorder(5, 5, 5, 5)));
		GridBagConstraints gbc_panel_valeurs = new GridBagConstraints();
		gbc_panel_valeurs.weighty = 1.0;
		gbc_panel_valeurs.weightx = 1.0;
		gbc_panel_valeurs.fill = GridBagConstraints.BOTH;
		gbc_panel_valeurs.insets = new Insets(0, 0, 5, 0);
		gbc_panel_valeurs.gridwidth = 3;
		gbc_panel_valeurs.gridx = 0;
		gbc_panel_valeurs.gridy = 1;
		panel_principal.add(panel_valeurs, gbc_panel_valeurs);
		GridBagLayout gbl_panel_valeurs = new GridBagLayout();
		gbl_panel_valeurs.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_valeurs.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_valeurs.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				0.0, 1.0 };
		gbl_panel_valeurs.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		panel_valeurs.setLayout(gbl_panel_valeurs);

		JButton btnSelectionarticle = new JButton("SelectionArticle");
		btnSelectionarticle.setIcon(new ImageIcon(FCommandes.class
				.getResource("/images/gestion/Search-48.png")));
		GridBagConstraints gbc_btnSelectionarticle = new GridBagConstraints();
		gbc_btnSelectionarticle.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSelectionarticle.gridwidth = 2;
		gbc_btnSelectionarticle.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelectionarticle.gridx = 0;
		gbc_btnSelectionarticle.gridy = 0;
		UI.deshabillerBouton(btnSelectionarticle, "Search");
		panel_valeurs.add(btnSelectionarticle, gbc_btnSelectionarticle);
		btnSelectionarticle.setOpaque(true);
		btnSelectionarticle.setBackground(new Color(0xFF, 0xBB, 0x33));
		// btnSelectionarticle.setBackground(new Color(0x99, 0xCC, 0x00));

		JLabel lblCode = new JLabel("Code");
		lblCode.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_lblCode = new GridBagConstraints();
		gbc_lblCode.insets = new Insets(0, 20, 5, 5);
		gbc_lblCode.anchor = GridBagConstraints.LINE_END;
		gbc_lblCode.gridx = 2;
		gbc_lblCode.gridy = 0;
		panel_valeurs.add(lblCode, gbc_lblCode);
		UI.habiller(lblCode);

		txtCode = new JTextField();
		txtCode.setEditable(false);
		GridBagConstraints gbc_txtCode = new GridBagConstraints();
		gbc_txtCode.anchor = GridBagConstraints.LINE_END;
		gbc_txtCode.insets = new Insets(0, 0, 5, 5);
		gbc_txtCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCode.gridx = 3;
		gbc_txtCode.gridy = 0;
		panel_valeurs.add(txtCode, gbc_txtCode);
		txtCode.setColumns(10);
		UI.habiller(txtCode);

		JLabel lblCategorie = new JLabel("Catégorie");
		GridBagConstraints gbc_lblCategorie = new GridBagConstraints();
		gbc_lblCategorie.insets = new Insets(0, 20, 5, 5);
		gbc_lblCategorie.anchor = GridBagConstraints.LINE_END;
		gbc_lblCategorie.gridx = 4;
		gbc_lblCategorie.gridy = 0;
		panel_valeurs.add(lblCategorie, gbc_lblCategorie);
		UI.habiller(lblCategorie);

		txtCategorie = new JTextField();
		GridBagConstraints gbc_txtCategorie = new GridBagConstraints();
		gbc_txtCategorie.anchor = GridBagConstraints.LINE_END;
		gbc_txtCategorie.insets = new Insets(0, 0, 5, 0);
		gbc_txtCategorie.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCategorie.gridx = 5;
		gbc_txtCategorie.gridy = 0;
		panel_valeurs.add(txtCategorie, gbc_txtCategorie);
		txtCategorie.setColumns(10);
		txtCategorie.setEditable(false);
		UI.habiller(txtCategorie);

		JLabel lblDesignation = new JLabel("Désignation");
		GridBagConstraints gbc_lblDesignation = new GridBagConstraints();
		gbc_lblDesignation.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesignation.anchor = GridBagConstraints.EAST;
		gbc_lblDesignation.gridx = 0;
		gbc_lblDesignation.gridy = 1;
		panel_valeurs.add(lblDesignation, gbc_lblDesignation);
		UI.habiller(lblDesignation);

		txtDesignation = new JTextField();
		txtDesignation.setEditable(false);
		GridBagConstraints gbc_txtDesignation = new GridBagConstraints();
		gbc_txtDesignation.insets = new Insets(0, 0, 5, 5);
		gbc_txtDesignation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDesignation.gridx = 1;
		gbc_txtDesignation.gridy = 1;
		panel_valeurs.add(txtDesignation, gbc_txtDesignation);
		txtDesignation.setColumns(10);
		UI.habiller(txtDesignation);

		JLabel lblMontant = new JLabel("Montant");
		GridBagConstraints gbc_lblMontant = new GridBagConstraints();
		gbc_lblMontant.anchor = GridBagConstraints.EAST;
		gbc_lblMontant.insets = new Insets(0, 10, 5, 5);
		gbc_lblMontant.gridx = 2;
		gbc_lblMontant.gridy = 1;
		panel_valeurs.add(lblMontant, gbc_lblMontant);
		UI.habiller(lblMontant);

		txtMontant = new JTextField();
		GridBagConstraints gbc_txtMontant = new GridBagConstraints();
		gbc_txtMontant.insets = new Insets(0, 0, 5, 5);
		gbc_txtMontant.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMontant.gridx = 3;
		gbc_txtMontant.gridy = 1;
		panel_valeurs.add(txtMontant, gbc_txtMontant);
		txtMontant.setColumns(10);
		txtMontant.setEditable(false);
		UI.habiller(txtMontant);

		JLabel lblQuantite = new JLabel("Quantité");
		GridBagConstraints gbc_lblQuantit = new GridBagConstraints();
		gbc_lblQuantit.anchor = GridBagConstraints.LINE_END;
		gbc_lblQuantit.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantit.gridx = 4;
		gbc_lblQuantit.gridy = 1;
		panel_valeurs.add(lblQuantite, gbc_lblQuantit);
		UI.habiller(lblQuantite);

		spinnerQuantite = new JSpinner();
		GridBagConstraints gbc_spinnerQuantite = new GridBagConstraints();
		gbc_spinnerQuantite.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerQuantite.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerQuantite.gridx = 5;
		gbc_spinnerQuantite.gridy = 1;
		SpinnerNumberModel model = (SpinnerNumberModel) spinnerQuantite
				.getModel();
		model.setMinimum(1);
		model.setValue(1);
		panel_valeurs.add(spinnerQuantite, gbc_spinnerQuantite);
		UI.habiller(spinnerQuantite);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.anchor = GridBagConstraints.LINE_END;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridwidth = 6;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		panel_valeurs.add(panel, gbc_panel);
		panel.setOpaque(false);

		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.setHorizontalAlignment(SwingConstants.LEFT);
		btnAjouter.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Add-New-48.png")));
		panel.add(btnAjouter);
		UI.deshabillerBouton(btnAjouter, "Add-New");
		btnAjouter.setForeground(Color.BLACK);

		JButton btnModifier = new JButton("Modifier");
		btnModifier.setHorizontalAlignment(SwingConstants.LEFT);
		btnModifier.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Data-Edit-48.png")));
		panel.add(btnModifier);
		UI.deshabillerBouton(btnModifier, "Data-Edit");
		btnModifier.setForeground(Color.BLACK);

		JButton btnSupprimer = new JButton("Supprimer");
		panel.add(btnSupprimer);
		btnSupprimer.setHorizontalAlignment(SwingConstants.LEFT);
		btnSupprimer.setIcon(new ImageIcon(FCommandes.class
				.getResource("/images/gestion/Cancel-48.png")));
		UI.deshabillerBouton(btnSupprimer, "Cancel");
		btnSupprimer.setForeground(Color.BLACK);

		table = new JTable();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.weighty = 1.0;
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		panel_valeurs.add(scrollPane, gbc_scrollPane);

		JLabel lblReglement = new JLabel("Mode de règlement");
		lblReglement.setHorizontalAlignment(SwingConstants.RIGHT);
		lblReglement.setIcon(new ImageIcon(FCommandes.class
				.getResource("/images/gestion/commande/ATM-32.png")));
		GridBagConstraints gbc_lblReglement = new GridBagConstraints();
		gbc_lblReglement.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblReglement.anchor = GridBagConstraints.LINE_END;
		gbc_lblReglement.insets = new Insets(0, 0, 5, 5);
		gbc_lblReglement.gridx = 0;
		gbc_lblReglement.gridy = 2;
		panel_principal.add(lblReglement, gbc_lblReglement);
		UI.habiller(lblReglement);

		comboBoxReglement = new JComboBox<ModeReglements>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 20);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		panel_principal.add(comboBoxReglement, gbc_comboBox);
		UI.habiller(comboBoxReglement);

		JPanel panelTotal = new JPanel();
		panelTotal.setBackground(new Color(0xFF, 0xBB, 0x33));
		panelTotal.setBorder(BorderFactory.createLineBorder(new Color(0xFF,
				0x88, 0x00), 3, true));
		panelTotal.setLayout(new GridBagLayout());
		GridBagConstraints gbc_panelTotal = new GridBagConstraints();
		gbc_panelTotal.insets = new Insets(10, 0, 20, 0);
		gbc_panelTotal.weightx = 1.0;
		gbc_panelTotal.gridx = 2;
		gbc_panelTotal.gridy = 2;
		panel_principal.add(panelTotal, gbc_panelTotal);

		lblValeurTotale = new JLabel("0.00");
		lblValeurTotale.setHorizontalAlignment(SwingConstants.CENTER);
		lblValeurTotale.setBounds(30, 7, 108, 31);
		GridBagConstraints gbc_lblValeurTotale = new GridBagConstraints();
		gbc_lblValeurTotale.insets = new Insets(5, 5, 5, 10);
		gbc_lblValeurTotale.gridx = 0;
		gbc_lblValeurTotale.gridy = 0;
		panelTotal.add(lblValeurTotale, gbc_lblValeurTotale);
		UI.habiller(lblValeurTotale);
		lblValeurTotale.setFont(lblValeurTotale.getFont().deriveFont(Font.BOLD,
				25f));

		JLabel lblMonnaie = new JLabel("€");
		lblMonnaie.setBounds(143, 7, 24, 31);
		GridBagConstraints gbc_lblMonnaie = new GridBagConstraints();
		gbc_lblMonnaie.insets = new Insets(0, 0, 0, 10);
		gbc_lblMonnaie.gridx = 1;
		gbc_lblMonnaie.gridy = 0;
		panelTotal.add(lblMonnaie, gbc_lblMonnaie);
		UI.habiller(lblMonnaie);

		JButton btnValideCommande = new JButton("Valider la commande");
		btnValideCommande
				.setIcon(new ImageIcon(
						FCommandes.class
								.getResource("/images/gestion/commande/Shopping-Cart-05-48-actif.png")));
		GridBagConstraints gbc_btnValideCommande = new GridBagConstraints();
		gbc_btnValideCommande.anchor = GridBagConstraints.LAST_LINE_END;
		gbc_btnValideCommande.gridwidth = 2;
		gbc_btnValideCommande.insets = new Insets(0, 0, 0, 5);
		gbc_btnValideCommande.gridx = 1;
		gbc_btnValideCommande.gridy = 3;
		panel_principal.add(btnValideCommande, gbc_btnValideCommande);
		UI.deshabillerBouton(btnValideCommande, "gestion/commande",
				"Shopping-Cart-05");

		SwingUtilities.invokeLater(() -> btnAccueil.requestFocusInWindow());
	}

	private class ActionAccueil extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionAccueil() {
			putValue(NAME, "Accueil");
			putValue(SHORT_DESCRIPTION, "Retourner sur l'écran d'accueil");
		}

		public void actionPerformed(ActionEvent e) {
			if (JOptionPane
					.showConfirmDialog(
							null,
							"Abandon de la saisie ?",
							"Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
				dispose();
			}
		}
	}

	private class ActionVoirCommandes extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionVoirCommandes() {
			putValue(NAME, "Commandes existantes");
			putValue(SHORT_DESCRIPTION, "Voir les commandes existantes");
		}

		public void actionPerformed(ActionEvent e) {
			FCommandesExistantes laFenetre = new FCommandesExistantes(
					FCommandes.this).setConnexion(connexion);
			laFenetre.setVisible(true);
		}
	}
}
