package dialogue.commande;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
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
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import controle.ControleCommande;
import controle.connection.Connexion;
import controle.utilitaires.GestionDates;
import dialogue.Messages;
import dialogue.UI;
import dialogue.client.PClients;
import dialogue.rendu.GrasRenderer;
import entite.Article;
import entite.Client;
import entite.Commande;
import entite.ModeReglements;
import entite.crud.EniException;

public class FCommandes extends JDialog {

	private static final long serialVersionUID = 1L;

	// propriétés non graphiques
	// -------------------------
	private ControleCommande controleCommande;

	private Connexion connexion;

	// actions
	// -------------------------
	private final Action actionAjouter = new ActionAjouter();
	private final Action actionModifier = new ActionModifier();
	private final Action actionSupprimer = new ActionSupprimer();
	private final Action actionSupprimerToutesLignes = new ActionSupprimerToutesLignes();

	private final Action actionApercu = new ActionApercu();
	private final Action actionImprimer = new ActionImprimer();
	private final Action actionExport = new ActionExport();

	private final Action actionAccueil = new ActionAccueil();

	private final Action actionChoixClient = new ActionChoixClient();
	private final Action actionChoixArticle = new ActionChoixArticle();

	private final Action actionVoirCommandes = new ActionVoirCommandes();
	private final Action actionValiderCommande = new ActionValiderCommande();

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
		controleCommande = new ControleCommande(connexion);
		controleCommande.init();
		
		Commande laCommande = controleCommande.nouvelleCommande();
		
		table.setModel(controleCommande.getModeleLignes());
		// gestion du rendu des colonnes du JTables
		// ----------------------------------------
		TableColumnModel colonnes = table.getColumnModel();
		TableColumn colonne = colonnes.getColumn(1);
		colonne.setCellRenderer(new GrasRenderer());

		txtNumeroCommande.setText(laCommande.getCode());

		for (ModeReglements unMode : controleCommande.getModeReglements()) {
			comboBoxReglement.addItem(unMode);
		}
		comboBoxReglement.addActionListener(evt -> {
			ModeReglements reglement = getReglement();
			Commande commandeActuelle = controleCommande.getCommande();
			commandeActuelle.setReglement(reglement);
		});
		comboBoxReglement.setSelectedIndex(0);

		actionValiderCommande.setEnabled(false);

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
		btnSupprimerLigne.setAction(actionSupprimer);
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
		btnSupprimerToutesLignes.setAction(actionSupprimerToutesLignes);
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
		btnValider.setAction(actionValiderCommande);
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
		btnApercu.setAction(actionApercu);
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
		btnImprimer.setAction(actionImprimer);
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
		btnExport.setAction(actionExport);
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
		btnChoixclient.setAction(actionChoixClient);
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
		btnSelectionarticle.setAction(actionChoixArticle);
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
		btnAjouter.setAction(actionAjouter);
		btnAjouter.setHorizontalAlignment(SwingConstants.LEFT);
		btnAjouter.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Add-New-48.png")));
		panel.add(btnAjouter);
		UI.deshabillerBouton(btnAjouter, "Add-New");
		btnAjouter.setForeground(Color.BLACK);

		JButton btnModifier = new JButton("Modifier");
		btnModifier.setAction(actionModifier);
		btnModifier.setHorizontalAlignment(SwingConstants.LEFT);
		btnModifier.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Data-Edit-48.png")));
		panel.add(btnModifier);
		UI.deshabillerBouton(btnModifier, "Data-Edit");
		btnModifier.setForeground(Color.BLACK);

		JButton btnSupprimer = new JButton("Supprimer");
		panel.add(btnSupprimer);
		btnSupprimer.setAction(actionSupprimer);
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
		btnValideCommande.setAction(actionValiderCommande);
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
							Messages.getString("FenCommande.67"), //$NON-NLS-1$
							Messages.getString("FenCommande.46"), JOptionPane.YES_NO_OPTION) == 0) { //$NON-NLS-1$
				controleCommande.supprimerToutesLesLignes();
				dispose();
			}
		}
	}

	private class ActionExport extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionExport() {
			putValue(NAME, "Export");
			putValue(SHORT_DESCRIPTION,
					"Exporter les clients sous forme de fichier");
		}

		public void actionPerformed(ActionEvent e) {
			controleCommande.export(controleCommande.getCommande(),
					FCommandes.this);
		}
	}

	private class ActionApercu extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionApercu() {
			putValue(NAME, "Aperçu");
			putValue(SHORT_DESCRIPTION, "Aperçu avant impression");
		}

		public void actionPerformed(ActionEvent e) {
			controleCommande.apercu(controleCommande.getCommande());
		}
	}

	private class ActionImprimer extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionImprimer() {
			putValue(NAME, "Imprimer");
			putValue(SHORT_DESCRIPTION, "Imprimer les données des clients");
		}

		public void actionPerformed(ActionEvent e) {
			controleCommande.imprimer(controleCommande.getCommande());
		}
	}

	private class ActionSupprimer extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionSupprimer() {
			putValue(NAME, "Supprimer");
			putValue(SHORT_DESCRIPTION,
					"Supprimer la ligne de commande sélectionnée");
		}

		public void actionPerformed(ActionEvent e) {
			int numeroLigne = table.getSelectedRow();
			if (numeroLigne < 0) {
				// si aucune ligne sélectionnée
				JOptionPane.showMessageDialog(null,
						"Vous devez sélectionner une ligne"
						+ " pour la supprimer",
						"Avertissement", JOptionPane.WARNING_MESSAGE);
			} else {
				int choix = JOptionPane
						.showConfirmDialog(
								null,
								"Voulez-vous vraiment supprimer"
								+ " la ligne sélectionnée ?",
								"Confirmation", JOptionPane.YES_NO_OPTION);
				if (choix == JOptionPane.YES_OPTION) {
					controleCommande.supprimer(numeroLigne);
				}
			}
		}
	}

	private class ActionModifier extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionModifier() {
			putValue(NAME, "Modifier");
			putValue(SHORT_DESCRIPTION,
					"Modifier la ligne de commande sélectionnée");
		}

		public void actionPerformed(ActionEvent e) {
			// à faire soi-même
		}

	}

	private class ActionAjouter extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionAjouter() {
			putValue(NAME, "Ajouter");
			putValue(SHORT_DESCRIPTION,
					"Ajouter une nouvelle ligne dans la commande");
		}

		public void actionPerformed(ActionEvent e) {
			String codeArticle = txtCode.getText();
			if (codeArticle.isEmpty()) {
				JOptionPane.showMessageDialog(null,
						Messages.getString("FenCommande.39")); //$NON-NLS-1$
			} else {
				int quantite = Integer.parseInt(spinnerQuantite.getValue()
						.toString());
				if (quantite > 0) {
					ajouterArticle(codeArticle, quantite);
				}
			}
		}

		private void ajouterArticle(String codeArticle, int quantite) {
			boolean creee = controleCommande.creerLigne(codeArticle, quantite);
			if (creee) {
				Commande enCours = controleCommande.getCommande();
				double total = enCours.getTotalTTC();
				lblValeurTotale.setText(formate(total));
				effacerSaisie();
				if (enCours.getClient() != null) {
					actionValiderCommande.setEnabled(true);
				}
			}
		}
	}

	private void effacerSaisie() {
		txtCode.setText("");
		txtCategorie.setText("");
		txtDesignation.setText("");
		txtMontant.setText("");
		spinnerQuantite.setValue(Integer.valueOf(1));
		btnChoixclient.requestFocus();
	}

	private ModeReglements getReglement() {
		return (ModeReglements) comboBoxReglement.getSelectedItem();
	}

	private String formate(double prix) {
		String total = "?";
		try {
			DecimalFormat format = new DecimalFormat(
					Messages.getString("FenCommande.72")); //$NON-NLS-1$

			total = format.format(prix);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null,
					Messages.getString("FenCommande.74")); //$NON-NLS-1$
		}
		return total;
	}

	private class ActionChoixArticle extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionChoixArticle() {
			putValue(NAME, "Sélectionner un article");
			putValue(SHORT_DESCRIPTION,
					"Parcourir les articles pour en choisir un");
		}

		public void actionPerformed(ActionEvent e) {
			ChoixArticle choix = new ChoixArticle(FCommandes.this, connexion);
			choix.setModal(true);
			choix.setLocationRelativeTo(choix.getParent());
			choix.setVisible(true);
			Article article = choix.getArticle();
			if (article != null) {
				choixArticle(article);
			}
		}

		private void choixArticle(Article article) {
			txtCode.setText(article.getCode());
			txtCategorie.setText(article.getCategorie().getCode());
			txtDesignation.setText(article.getDesignation());
			txtMontant.setText(formate(article.getPrixUnitaire()));
			SpinnerNumberModel model = (SpinnerNumberModel) spinnerQuantite
					.getModel();
			model.setMaximum(article.getQuantite());
		}
	}

	private class ActionChoixClient extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionChoixClient() {
			putValue(NAME, "Sélectionner le client");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			ChoixClient choix = new ChoixClient(FCommandes.this, connexion);
			choix.setModal(true);
			choix.setLocationRelativeTo(choix.getParent());
			choix.setVisible(true);

			Client client = choix.getClient();
			if (client != null) {
				Commande commande = controleCommande.getCommande();
				commande.setClient(client);
				btnChoixclient.setText(String.format("%s %s [%s]",
						client.getPrenom(), client.getNom(), client.getCode()));
				if (!commande.getLignes().isEmpty()) {
					actionValiderCommande.setEnabled(true);
				}
			}
		}
	}

	private class ActionValiderCommande extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionValiderCommande() {
			putValue(NAME, "Valider la commande");
			putValue(SHORT_DESCRIPTION,
					"Valide la commande et la sauvegarde dans la base de données");
		}

		public void actionPerformed(ActionEvent evt) {
			if (table.getRowCount() == 0) {
				JOptionPane.showMessageDialog(null,
						Messages.getString("FenCommande.55"), //$NON-NLS-1$
						Messages.getString("FenCommande.56"), //$NON-NLS-1$
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				creer();
			}
		}

		private void creer() {
			try {
				controleCommande.creerCommande();
				effacerSaisie();
				Commande laCommande = controleCommande.nouvelleCommande();
				txtNumeroCommande.setText(laCommande.getCode());
				lblValeurTotale.setText("0.00");
				btnChoixclient.setText((String) actionChoixClient
						.getValue(Action.NAME));
				actionValiderCommande.setEnabled(false);
				laCommande.setReglement(getReglement());
			} catch (EniException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),
						Messages.getString("FenCommande.56"), //$NON-NLS-1$
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class ActionSupprimerToutesLignes extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionSupprimerToutesLignes() {
			putValue(NAME, "Supprimer toutes les lignes");
			putValue(SHORT_DESCRIPTION,
					"Supprimer toutes les lignes de la commande en cours");
		}

		public void actionPerformed(ActionEvent e) {
			controleCommande.supprimerToutesLesLignes();
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
