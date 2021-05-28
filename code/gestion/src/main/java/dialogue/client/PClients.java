package dialogue.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.Instant;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;
import controle.ControleClient;
import controle.connection.Connexion;
import controle.modele.ModeleClients;
import controle.utilitaires.GestionDates;
import dialogue.UI;
import dialogue.rendu.BooleenRenderer;
import dialogue.rendu.GrasRenderer;
import dialogue.rendu.InstantRenderer;
import entite.Client;

public class PClients extends JPanel implements TableModelListener {

	private static final long serialVersionUID = 1L;


	// propriétés non graphiques
	// -------------------------
	private ControleClient controleClient;
	private Connexion connexion;

	private JTable table;
	private JTextField txtCode;
	private JTextField txtDateCreation;
	private JTextField txtPrenom;
	private JTextField txtNom;
	private JTextField txtAdresse;
	private JTextField txtTelfixe;
	private JTextField txtMobile;
	private JTextField txtEmail;

	private JTextArea textArea;

	private JCheckBox cartefidelite;

	private final Action actionAnnuler = new ActionAnnuler();
	private final Action actionAjouter = new ActionAjouter();
	private final Action actionRechercher = new ActionRechercher();
	private final Action actionModifier = new ActionModifier();
	private final Action actionSupprimer = new ActionSupprimer();

	private final Action actionApercu = new ActionApercu();
	private final Action actionImprimer = new ActionImprimer();
	private final Action actionExport = new ActionExport();

	private final Action actionAccueil = new ActionAccueil();


	private JDialog fenetre;


	private JButton btnModifier;


	private JButton btnRechercher;


	private JButton btnAjouter;

	private void ouvrirPageWeb(String adresse) {
		if(Desktop.isDesktopSupported()) {
			Desktop lanceur = Desktop.getDesktop();
			if(lanceur.isSupported(Desktop.Action.BROWSE)) {
				try {
					lanceur.browse(new URI(adresse));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public PClients setConnexion(Connexion connexion) {
		this.connexion = connexion;
		controleClient = new ControleClient(connexion);

		table.setModel(controleClient.getModele());
		controleClient.getModele().addTableModelListener(this);
		controleClient.addPropertyChangeListener(evt -> {
			System.out.println("Changement " +evt.getPropertyName());
			System.out.println("Ancienne valeur " +evt.getOldValue());
			System.out.println("Nouvelle valeur " +evt.getNewValue());
			ouvrirPageWeb("www.google.fr");
		});
		// gestion du rendu des colonnes du JTables
		// ----------------------------------------
		TableColumnModel modeleColonne = table.getColumnModel();
		TableColumn noms = modeleColonne.getColumn(1);
		noms.setCellRenderer(new GrasRenderer());
		TableColumn cartes = modeleColonne.getColumn(3);
		cartes.setCellRenderer(new BooleenRenderer());
		TableColumn dates = modeleColonne.getColumn(4);
		dates.setCellRenderer(new InstantRenderer());

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// gestion du SIMPLE-CLIC sur une ligne de la table
				// pour un report des données
				// dans les champs correspondants
				int numLigne = table.getSelectedRow();
				if (numLigne >= 0) {					
					ModeleClients modele = controleClient.getModele();
					Client client = modele.getClient(numLigne);
					txtCode.setText(client.getCode());
					txtNom.setText(client.getNom());
					txtPrenom.setText(client.getPrenom());
					cartefidelite.setSelected(client.isCarteFidele());
					// on met la date de création au format dd-MM-yyyy
					String strDate = GestionDates.dateEnChaineFR(client
							.getDateCreation());
					txtDateCreation.setText(strDate);
				}

				// gestion du DOUBLE-CLIC sur une ligne de la table
				// pour une modif en mode fiche
				if (e.getClickCount() == 2) {
					modification();
				}
			}
		});

		return this;
	}

	/**
	 * Create the frame.
	 * 
	 * @param parent
	 */
	public PClients() {
		setBackground(new Color(0x33, 0xB5, 0xE5));
		setBorder(null);
		setLayout(new BorderLayout(0, 0));

		JPanel panel_menu = new JPanel();
		panel_menu.setBackground(new Color(0x00, 0x99, 0xCC));
		add(panel_menu, BorderLayout.WEST);
		panel_menu.setBorder(new CompoundBorder(null, new EmptyBorder(5, 5, 5,
				5)));
		GridBagLayout gbl_panel_menu = new GridBagLayout();
		gbl_panel_menu.columnWidths = new int[] { 0, 0 };
		gbl_panel_menu.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_menu.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_menu.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_menu.setLayout(gbl_panel_menu);

		JLabel lblTitre = new JLabel("Clients");
		lblTitre.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/client/People-64-actif.png")));
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.insets = new Insets(0, 0, 15, 20);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panel_menu.add(lblTitre, gbc_lblTitre);
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 25));

		btnAjouter = new JButton("Ajouter");
		btnAjouter.setAction(actionAjouter);
		btnAjouter.setForeground(Color.BLACK);
		btnAjouter.setHorizontalAlignment(SwingConstants.LEFT);
		btnAjouter.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Add-New-48.png")));
		GridBagConstraints gbc_btnAjouter = new GridBagConstraints();
		gbc_btnAjouter.anchor = GridBagConstraints.LINE_START;
		gbc_btnAjouter.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAjouter.insets = new Insets(0, 0, 5, 0);
		gbc_btnAjouter.gridx = 0;
		gbc_btnAjouter.gridy = 1;
		panel_menu.add(btnAjouter, gbc_btnAjouter);
		UI.deshabillerBouton(btnAjouter, "Add-New");

		btnRechercher = new JButton("Rechercher");
		btnRechercher.setAction(actionRechercher);
		btnRechercher.setForeground(Color.BLACK);
		btnRechercher.setHorizontalAlignment(SwingConstants.LEFT);
		btnRechercher.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Search-48.png")));
		GridBagConstraints gbc_btnRechercher = new GridBagConstraints();
		gbc_btnRechercher.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRechercher.anchor = GridBagConstraints.LINE_START;
		gbc_btnRechercher.insets = new Insets(0, 0, 5, 0);
		gbc_btnRechercher.gridx = 0;
		gbc_btnRechercher.gridy = 2;
		panel_menu.add(btnRechercher, gbc_btnRechercher);
		UI.deshabillerBouton(btnRechercher, "Search");

		btnModifier = new JButton("Modifier");
		btnModifier.setAction(actionModifier);
		btnModifier.setForeground(Color.BLACK);
		btnModifier.setHorizontalAlignment(SwingConstants.LEFT);
		btnModifier.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Data-Edit-48.png")));
		GridBagConstraints gbc_btnModifier = new GridBagConstraints();
		gbc_btnModifier.anchor = GridBagConstraints.LINE_START;
		gbc_btnModifier.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnModifier.insets = new Insets(0, 0, 5, 0);
		gbc_btnModifier.gridx = 0;
		gbc_btnModifier.gridy = 3;
		panel_menu.add(btnModifier, gbc_btnModifier);
		UI.deshabillerBouton(btnModifier, "Data-Edit");

		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.setAction(actionSupprimer);
		btnSupprimer.setForeground(Color.BLACK);
		btnSupprimer.setHorizontalAlignment(SwingConstants.LEFT);
		btnSupprimer.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Garbage-Open-48.png")));
		GridBagConstraints gbc_btnSupprimer = new GridBagConstraints();
		gbc_btnSupprimer.anchor = GridBagConstraints.LINE_START;
		gbc_btnSupprimer.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSupprimer.insets = new Insets(0, 0, 5, 0);
		gbc_btnSupprimer.gridx = 0;
		gbc_btnSupprimer.gridy = 4;
		panel_menu.add(btnSupprimer, gbc_btnSupprimer);
		UI.deshabillerBouton(btnSupprimer, "Garbage-Open");

		JButton btnApercu = new JButton("Aperçu");
		btnApercu.setAction(actionApercu);
		btnApercu.setForeground(Color.BLACK);
		btnApercu.setHorizontalAlignment(SwingConstants.LEFT);
		btnApercu.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Preview-48.png")));
		GridBagConstraints gbc_btnApercu = new GridBagConstraints();
		gbc_btnApercu.weighty = 1.0;
		gbc_btnApercu.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnApercu.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnApercu.insets = new Insets(0, 0, 5, 0);
		gbc_btnApercu.gridx = 0;
		gbc_btnApercu.gridy = 5;
		panel_menu.add(btnApercu, gbc_btnApercu);
		UI.deshabillerBouton(btnApercu, "Preview");

		JButton btnImprimer = new JButton("Imprimer");
		btnImprimer.setAction(actionImprimer);
		btnImprimer.setForeground(Color.BLACK);
		GridBagConstraints gbc_btnImprimer = new GridBagConstraints();
		gbc_btnImprimer.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnImprimer.anchor = GridBagConstraints.LINE_START;
		gbc_btnImprimer.insets = new Insets(0, 0, 5, 0);
		gbc_btnImprimer.gridx = 0;
		gbc_btnImprimer.gridy = 6;
		panel_menu.add(btnImprimer, gbc_btnImprimer);
		UI.deshabillerBouton(btnImprimer, "Printer");

		JButton btnExport = new JButton("Export");
		btnExport.setAction(actionExport);
		btnExport.setForeground(Color.BLACK);
		GridBagConstraints gbc_btnExport = new GridBagConstraints();
		gbc_btnExport.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExport.anchor = GridBagConstraints.LINE_START;
		gbc_btnExport.insets = new Insets(0, 0, 5, 0);
		gbc_btnExport.gridx = 0;
		gbc_btnExport.gridy = 7;
		panel_menu.add(btnExport, gbc_btnExport);
		UI.deshabillerBouton(btnExport, "Data-Export");

		JButton btnAccueil = new JButton("Accueil");
		btnAccueil.setAction(actionAccueil);
		btnAccueil.setForeground(Color.BLACK);
		GridBagConstraints gbc_btnAccueil = new GridBagConstraints();
		gbc_btnAccueil.weighty = 1.0;
		gbc_btnAccueil.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnAccueil.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAccueil.gridx = 0;
		gbc_btnAccueil.gridy = 8;
		panel_menu.add(btnAccueil, gbc_btnAccueil);
		UI.deshabillerBouton(btnAccueil, "Home");

		JPanel panel_principal = new JPanel();
		panel_principal.setBackground(new Color(197, 234, 248));
		add(panel_principal, BorderLayout.CENTER);
		panel_principal.setLayout(new MigLayout("", "[][10%][10%][grow]",
				"[grow][42%,grow][]"));

		JPanel panel_formulaire = new JPanel();
		panel_formulaire.setOpaque(false);
		panel_formulaire.setBorder(new CompoundBorder(new LineBorder(new Color(
				0, 0, 0), 2, true), new EmptyBorder(5, 5, 5, 5)));
		panel_principal.add(panel_formulaire, "cell 0 0 4 1,grow");
		GridBagLayout gbl_panel_formulaire = new GridBagLayout();
		gbl_panel_formulaire.columnWidths = new int[] { 79, 182, 91, 182, 119 };
		gbl_panel_formulaire.rowHeights = new int[] { 26, 26, 26, 26, 0, 75 };
		gbl_panel_formulaire.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0,
				0.0 };
		gbl_panel_formulaire.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 1.0 };
		panel_formulaire.setLayout(gbl_panel_formulaire);

		JLabel lblCode = new JLabel("Code");
		GridBagConstraints gbc_lblCode = new GridBagConstraints();
		gbc_lblCode.anchor = GridBagConstraints.EAST;
		gbc_lblCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblCode.gridx = 0;
		gbc_lblCode.gridy = 0;
		panel_formulaire.add(lblCode, gbc_lblCode);
		UI.habiller(lblCode);

		txtCode = new JTextField();
		lblCode.setLabelFor(txtCode);
		txtCode.setEditable(false);
		GridBagConstraints gbc_txtCode = new GridBagConstraints();
		gbc_txtCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCode.insets = new Insets(0, 0, 5, 5);
		gbc_txtCode.gridx = 1;
		gbc_txtCode.gridy = 0;
		panel_formulaire.add(txtCode, gbc_txtCode);
		txtCode.setColumns(10);
		UI.habiller(txtCode);

		JLabel lblDatecreation = new JLabel("Créé le");
		GridBagConstraints gbc_lblDatecreation = new GridBagConstraints();
		gbc_lblDatecreation.anchor = GridBagConstraints.LINE_END;
		gbc_lblDatecreation.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatecreation.gridx = 2;
		gbc_lblDatecreation.gridy = 0;
		panel_formulaire.add(lblDatecreation, gbc_lblDatecreation);
		UI.habiller(lblDatecreation);

		txtDateCreation = new JTextField();
		lblDatecreation.setLabelFor(txtDateCreation);
		txtDateCreation.setEditable(false);
		GridBagConstraints gbc_txtDateCreation = new GridBagConstraints();
		gbc_txtDateCreation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDateCreation.insets = new Insets(0, 0, 5, 5);
		gbc_txtDateCreation.gridx = 3;
		gbc_txtDateCreation.gridy = 0;
		panel_formulaire.add(txtDateCreation, gbc_txtDateCreation);
		txtDateCreation.setColumns(10);
		UI.habiller(txtDateCreation);

		cartefidelite = new JCheckBox("Carte de Fidélité");
		cartefidelite.setOpaque(false);
		GridBagConstraints gbc_chckbxCartefidelite = new GridBagConstraints();
		gbc_chckbxCartefidelite.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxCartefidelite.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxCartefidelite.gridx = 4;
		gbc_chckbxCartefidelite.gridy = 0;
		panel_formulaire.add(cartefidelite, gbc_chckbxCartefidelite);
		UI.habiller(cartefidelite);

		JLabel lblPrenom = new JLabel("Prénom");
		GridBagConstraints gbc_lblPrenom = new GridBagConstraints();
		gbc_lblPrenom.anchor = GridBagConstraints.EAST;
		gbc_lblPrenom.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrenom.gridx = 0;
		gbc_lblPrenom.gridy = 1;
		panel_formulaire.add(lblPrenom, gbc_lblPrenom);
		UI.habiller(lblPrenom);

		txtPrenom = new JTextField();
		lblPrenom.setLabelFor(txtPrenom);
		txtPrenom.setEditable(false);
		GridBagConstraints gbc_txtPrenom = new GridBagConstraints();
		gbc_txtPrenom.anchor = GridBagConstraints.NORTH;
		gbc_txtPrenom.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrenom.insets = new Insets(0, 0, 5, 5);
		gbc_txtPrenom.gridx = 1;
		gbc_txtPrenom.gridy = 1;
		panel_formulaire.add(txtPrenom, gbc_txtPrenom);
		txtPrenom.setColumns(10);
		UI.habiller(txtPrenom);

		JLabel lblNom = new JLabel("Nom");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 2;
		gbc_lblNom.gridy = 1;
		panel_formulaire.add(lblNom, gbc_lblNom);
		UI.habiller(lblNom);

		txtNom = new JTextField();
		lblNom.setLabelFor(txtNom);
		txtNom.setEditable(false);
		GridBagConstraints gbc_txtNom = new GridBagConstraints();
		gbc_txtNom.anchor = GridBagConstraints.NORTH;
		gbc_txtNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNom.insets = new Insets(0, 0, 5, 0);
		gbc_txtNom.gridwidth = 2;
		gbc_txtNom.gridx = 3;
		gbc_txtNom.gridy = 1;
		panel_formulaire.add(txtNom, gbc_txtNom);
		txtNom.setColumns(10);
		UI.habiller(txtNom);

		JLabel lblAdresse = new JLabel("Adresse");
		GridBagConstraints gbc_lblAdresse = new GridBagConstraints();
		gbc_lblAdresse.anchor = GridBagConstraints.EAST;
		gbc_lblAdresse.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdresse.gridx = 0;
		gbc_lblAdresse.gridy = 2;
		panel_formulaire.add(lblAdresse, gbc_lblAdresse);
		UI.habiller(lblAdresse);

		txtAdresse = new JTextField();
		lblAdresse.setLabelFor(txtAdresse);
		txtAdresse.setEditable(false);
		GridBagConstraints gbc_txtAdresse = new GridBagConstraints();
		gbc_txtAdresse.anchor = GridBagConstraints.NORTH;
		gbc_txtAdresse.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAdresse.insets = new Insets(0, 0, 5, 0);
		gbc_txtAdresse.gridwidth = 4;
		gbc_txtAdresse.gridx = 1;
		gbc_txtAdresse.gridy = 2;
		panel_formulaire.add(txtAdresse, gbc_txtAdresse);
		txtAdresse.setColumns(10);
		UI.habiller(txtAdresse);

		JLabel lblFixe = new JLabel("Fixe");
		GridBagConstraints gbc_lblFixe = new GridBagConstraints();
		gbc_lblFixe.anchor = GridBagConstraints.EAST;
		gbc_lblFixe.insets = new Insets(0, 0, 5, 5);
		gbc_lblFixe.gridx = 0;
		gbc_lblFixe.gridy = 3;
		panel_formulaire.add(lblFixe, gbc_lblFixe);
		UI.habiller(lblFixe);

		txtTelfixe = new JTextField();
		lblFixe.setLabelFor(txtTelfixe);
		txtTelfixe.setEditable(false);
		GridBagConstraints gbc_txtTelfixe = new GridBagConstraints();
		gbc_txtTelfixe.anchor = GridBagConstraints.NORTH;
		gbc_txtTelfixe.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelfixe.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelfixe.gridx = 1;
		gbc_txtTelfixe.gridy = 3;
		panel_formulaire.add(txtTelfixe, gbc_txtTelfixe);
		txtTelfixe.setColumns(10);
		UI.habiller(txtTelfixe);

		JLabel lblMobile = new JLabel("Mobile");
		GridBagConstraints gbc_lblMobile = new GridBagConstraints();
		gbc_lblMobile.anchor = GridBagConstraints.EAST;
		gbc_lblMobile.insets = new Insets(0, 0, 5, 5);
		gbc_lblMobile.gridx = 2;
		gbc_lblMobile.gridy = 3;
		panel_formulaire.add(lblMobile, gbc_lblMobile);
		UI.habiller(lblMobile);

		txtMobile = new JTextField();
		lblMobile.setLabelFor(txtMobile);
		txtMobile.setEditable(false);
		GridBagConstraints gbc_txtMobile = new GridBagConstraints();
		gbc_txtMobile.anchor = GridBagConstraints.NORTH;
		gbc_txtMobile.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMobile.insets = new Insets(0, 0, 5, 0);
		gbc_txtMobile.gridwidth = 2;
		gbc_txtMobile.gridx = 3;
		gbc_txtMobile.gridy = 3;
		panel_formulaire.add(txtMobile, gbc_txtMobile);
		txtMobile.setColumns(10);
		UI.habiller(txtMobile);

		JLabel lblEmail = new JLabel("Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 4;
		panel_formulaire.add(lblEmail, gbc_lblEmail);
		UI.habiller(lblEmail);

		txtEmail = new JTextField();
		lblEmail.setLabelFor(txtEmail);
		txtEmail.setEditable(false);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.anchor = GridBagConstraints.NORTH;
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.insets = new Insets(0, 0, 5, 0);
		gbc_txtEmail.gridwidth = 4;
		gbc_txtEmail.gridx = 1;
		gbc_txtEmail.gridy = 4;
		panel_formulaire.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
		UI.habiller(txtEmail);

		JLabel lblRemarques = new JLabel("Remarques");
		lblRemarques.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_lblRemarques = new GridBagConstraints();
		gbc_lblRemarques.anchor = GridBagConstraints.NORTH;
		gbc_lblRemarques.insets = new Insets(0, 0, 5, 5);
		gbc_lblRemarques.gridx = 0;
		gbc_lblRemarques.gridy = 5;
		panel_formulaire.add(lblRemarques, gbc_lblRemarques);
		UI.habiller(lblRemarques);

		textArea = new JTextArea();
		textArea.setBackground(SystemColor.control);
		lblRemarques.setLabelFor(textArea);
		Border cadre = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		textArea.setBorder(cadre);
		textArea.setEditable(false);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridwidth = 4;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 5;
		panel_formulaire.add(textArea, gbc_textArea);
		UI.habiller(textArea);

		table = new JTable();
		UI.habiller(table);

		JScrollPane scrollPane = new JScrollPane(table);
		panel_principal.add(scrollPane, "cell 0 1 4 1,grow");
		scrollPane.setOpaque(false);
		
		JLabel lblTri = new JLabel("Trier la liste par");
		lblTri.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Sort-Ascending-32.png")));
		panel_principal.add(lblTri, "cell 1 2,alignx trailing");
		UI.habiller(lblTri);

		JComboBox<String> comboBox = new JComboBox<>();
		panel_principal.add(comboBox, "cell 2 2,growx");
		UI.habiller(comboBox);

		SwingUtilities.invokeLater(
				() -> btnAccueil.requestFocusInWindow()		
		);
	}

	private ImageIcon getImage(String image) {
		return new ImageIcon(getClass().getResource(image));
	}
	
	// méthode de modification appelée si clic sur Modification
	// ou si double-clic sur une ligne de la table
	private void modification() {
		int numeroLigne = table.getSelectedRow();
		if (numeroLigne < 0) {
			// si aucune ligne sélectionnée
			JOptionPane.showMessageDialog(null, "Sélectionnez auparavant"
					+ " la ligne à modifier" + '\n'
					+ "ou effectuez un double-clic sur la ligne",
					"MODIFICATION", JOptionPane.INFORMATION_MESSAGE);
		} else {
			// on récupère avant les données à partir d'une ligne sélectionnée
			Client client = controleClient.getModele().getClient(numeroLigne);

			// on crée la fenêtre Fiche Client
			PClient edition = new PClient(client) {
				
				private static final long serialVersionUID = 1L;

				@Override
				protected void configurerAction(AbstractAction action) {
					action.putValue(Action.NAME, "Modifier");
					action.putValue(Action.LARGE_ICON_KEY,
							getImage("/images/gestion/Save-48.png"));
				}

				@Override
				protected void actionPrincipale(String code, String nom,
						String prenom, boolean fidelite, Instant crééLe) {
					try {
						boolean modifié = controleClient.modifier(code, 
																nom, prenom,
																fidelite, crééLe);
						if (modifié) {
							actionAnnuler.actionPerformed(null);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			};
			JLabel titre = edition.getLblTitre();
			titre.setText("Edition");
			titre.setIcon(getImage("/images/gestion/client/User-Modify-64.png"));
			edition.setActionAnnuler(actionAnnuler);
			edition.setActionExport(actionExport);
			
			btnModifier.setIcon(getImage("/images/gestion/Data-Edit-48.png"));
			changerPanneau(edition, "Modification du client " 
						+client.getPrenom() +" " +client.getNom() 
						+"["+client.getCode() +"]");
		}
	}

	/* ************************************************************
	 * Gestion des événements concernant le modèle en redéfinissant la méthode
	 * tableChanged() de l'interface TableModelListener
	 * ***********************************************************
	 */
	public void tableChanged(TableModelEvent unEvenement) {
		switch (unEvenement.getType()) {
		case TableModelEvent.INSERT:
			System.out.println("La table a été mise à jour, "
					+ "il y a eu une insertion !!!");
			break;
		case TableModelEvent.DELETE:
			System.out
					.println("La table a été mise à jour, "
							+ "il y a eu une suppression !!!");
			break;
		case TableModelEvent.UPDATE:
			System.out
					.println("La table a été mise à jour,"
							+ " il y a eu une modification !!!");
			break;
		default:
			break;
		}
	}

	private class ActionAccueil extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionAccueil() {
			putValue(NAME, "Accueil");
			putValue(SHORT_DESCRIPTION, "Retourner sur l'écran d'accueil");
		}

		public void actionPerformed(ActionEvent e) {
			getFenetre().dispose();
		}
	}

	private class ActionAnnuler extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionAnnuler() {
			putValue(NAME, "Annuler");
			putValue(SHORT_DESCRIPTION, "Annuler l'action en cours");
		}

		public void actionPerformed(ActionEvent e) {
			changerPanneau(PClients.this, "Gestion des Clients");
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
			controleClient.export(getFenetre());
		}
	}

	private class ActionApercu extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionApercu() {
			putValue(NAME, "Aperçu");
			putValue(SHORT_DESCRIPTION, "Aperçu avant impression");
		}

		public void actionPerformed(ActionEvent e) {
			controleClient.apercu();
		}
	}

	private class ActionImprimer extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionImprimer() {
			putValue(NAME, "Imprimer");
			putValue(SHORT_DESCRIPTION, "Imprimer les données des clients");
		}

		public void actionPerformed(ActionEvent e) {
			controleClient.imprimer();
		}
	}

	private class ActionSupprimer extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionSupprimer() {
			putValue(NAME, "Supprimer");
			putValue(SHORT_DESCRIPTION, "Supprimer le client sélectionné");
		}

		public void actionPerformed(ActionEvent e) {
			int numeroLigne = table.getSelectedRow();
			if (numeroLigne < 0) {
				// si aucune ligne sélectionnée
				JOptionPane.showMessageDialog(null,
						"Sélectionnez une ligne avant.", "Suppression",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				int choix = JOptionPane.showConfirmDialog(
						null,
						String.format("Voulez-vous supprimer la fiche du client ?"
								+ "\ncode : %s"
								+ "\nnom  : %s",
								table.getValueAt(numeroLigne, 0),								
								table.getValueAt(numeroLigne, 1))
						,
						"SUPPRESSION", JOptionPane.YES_NO_OPTION);
				// 0 : oui 1 : non
				if (choix == JOptionPane.YES_OPTION) {
					// ***********************************************
					// suppression de l'enregistrement dans la BD
					// ***********************************************
					controleClient.supprimer(numeroLigne);
				}
			}
		}
	}

	private class ActionModifier extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionModifier() {
			putValue(NAME, "Modifier");
			putValue(SHORT_DESCRIPTION, "Modifier le client sélectionné");
		}

		public void actionPerformed(ActionEvent e) {
			modification();
		}
	}

	private class ActionRechercher extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionRechercher() {
			putValue(NAME, "Rechercher");
			putValue(SHORT_DESCRIPTION, "Rechercher parmi les clients");
		}

		public void actionPerformed(ActionEvent e) {

			// --- RECHERCHE EN MODE FICHE ---
			// création de la fenêtre
			PClientRecherche recherche = new PClientRecherche();
			recherche.setActionAnnuler(actionAnnuler);
			recherche.setActionExport(actionExport);
			ControleClient controle = new ControleClient(connexion);
			recherche.setControleClient(controle);

			btnRechercher.setIcon(getImage("/images/gestion/Search-48.png"));
			changerPanneau(recherche, "Recherche de client(s)");
		}
	}

	private class ActionAjouter extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionAjouter() {
			putValue(NAME, "Ajouter");
			putValue(SHORT_DESCRIPTION, "Ajouter un nouveau client");
		}

		public void actionPerformed(ActionEvent e) {
			// --- AJOUT EN MODE FICHE ---
			PClient ajout = new PClient();
			ajout.setActionAnnuler(actionAnnuler);
			ajout.setActionExport(actionExport);
			ajout.setControleClient(controleClient);
			
			btnAjouter.setIcon(getImage("/images/gestion/Add-New-48.png"));
			changerPanneau(ajout ,"Ajouter un nouveau client");
		}
	}

	public void setFenetre(JDialog fenetre) {
		this.fenetre = fenetre;
	}
	
	private JDialog getFenetre() {
//		Window window = SwingUtilities.getWindowAncestor(this);
		return fenetre;
	}
	
	private void changerPanneau(JPanel panneau, String titre) {
		Window fenetre = getFenetre();
		if (fenetre instanceof JDialog) {
			JDialog dialogue = (JDialog) fenetre;
			dialogue.setContentPane(panneau);
			dialogue.setTitle(titre);
		}
		fenetre.revalidate();
	}
}
