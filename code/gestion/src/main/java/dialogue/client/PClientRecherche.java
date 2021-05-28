package dialogue.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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

import net.miginfocom.swing.MigLayout;
import controle.ControleClient;
import controle.utilitaires.GestionDates;
import dialogue.UI;
import dialogue.rendu.BooleenRenderer;
import dialogue.rendu.InstantRenderer;
import dialogue.rendu.GrasRenderer;
import entite.Client;

public class PClientRecherche extends JPanel implements TableModelListener {

	private static final long serialVersionUID = 1L;

	// propriétés non graphiques
	// -------------------------
	private ControleClient controleClient;

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

	private JCheckBox chckbxCartefidelite;

	private final Action actionPrincipale = new ActionPrincipale();

	private final Action actionApercu = new ActionApercu();
	private final Action actionImprimer = new ActionImprimer();

	private JButton btnAction;

	private JLabel lblTitre;

	private JComboBox<String> comboBoxTrie;

	private JButton btnAccueil;

	private JButton btnExport;

	PClientRecherche setControleClient(ControleClient controleClient) {
		this.controleClient = controleClient;

		table.setModel(controleClient.getModele());
		controleClient.getModele().addTableModelListener(this);

		// gestion du rendu des colonnes du JTables
		// ----------------------------------------
		table.getColumnModel().getColumn(1).setCellRenderer(new GrasRenderer());
		table.getColumnModel().getColumn(3)
				.setCellRenderer(new BooleenRenderer());
		table.getColumnModel().getColumn(4).setCellRenderer(new InstantRenderer());
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int ligneSelectionnee = table.getSelectedRow();
				System.out.println("ligne sélectionnée " +ligneSelectionnee);
				if( ligneSelectionnee != -1) {
					Client client = controleClient.getModele().getClient(ligneSelectionnee);
					txtCode.setText(client.getCode());
					txtDateCreation.setText(GestionDates.dateEnChaineFR(client.getDateCreation()));
					txtNom.setText(client.getNom());
					txtPrenom.setText(client.getPrenom());
				}
				super.mouseClicked(e);
			}
		});
		
		comboBoxTrie.addItem("Code");
		comboBoxTrie.addItem("Prénom");
		comboBoxTrie.addItem("Nom");
		comboBoxTrie.addItem("Date de Création");

		comboBoxTrie.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Tri sélectionné: " +comboBoxTrie.getSelectedItem());
			}
		});
		return this;
	}

	/**
	 * Create the frame.
	 * 
	 * @param parent
	 * @wbp.parser.constructor
	 */
	PClientRecherche() {
		createContents();
	}

	private void createContents() {
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

		lblTitre = new JLabel("");
		lblTitre.setIcon(new ImageIcon(PClientRecherche.class.getResource("/images/gestion/client/User-Find-64.png")));
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.anchor = GridBagConstraints.LINE_START;
		gbc_lblTitre.insets = new Insets(0, 0, 15, 20);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panel_menu.add(lblTitre, gbc_lblTitre);
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 25));

		btnAction = new JButton("Rechercher");
		btnAction.setAction(actionPrincipale);
		btnAction.setForeground(Color.BLACK);
		btnAction.setIcon(new ImageIcon(PClientRecherche.class.getResource("/images/gestion/Search-48.png")));
		UI.deshabillerBouton(btnAction, "Search");
		btnAction.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnAction = new GridBagConstraints();
		gbc_btnAction.anchor = GridBagConstraints.LINE_START;
		gbc_btnAction.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAction.insets = new Insets(0, 0, 5, 0);
		gbc_btnAction.gridx = 0;
		gbc_btnAction.gridy = 1;
		panel_menu.add(btnAction, gbc_btnAction);

		JButton btnApercu = new JButton("Apercu");
		btnApercu.setAction(actionApercu);
		btnApercu.setForeground(Color.BLACK);
		btnApercu.setHorizontalAlignment(SwingConstants.LEFT);
		btnApercu.setIcon(new ImageIcon(PClientRecherche.class
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

		btnExport = new JButton("Export");
		btnExport.setForeground(Color.BLACK);
		GridBagConstraints gbc_btnExport = new GridBagConstraints();
		gbc_btnExport.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExport.anchor = GridBagConstraints.LINE_START;
		gbc_btnExport.insets = new Insets(0, 0, 5, 0);
		gbc_btnExport.gridx = 0;
		gbc_btnExport.gridy = 7;
		panel_menu.add(btnExport, gbc_btnExport);
		UI.deshabillerBouton(btnExport, "Data-Export");

		btnAccueil = new JButton("Annuler");
		btnAccueil.setIcon(new ImageIcon(PClientRecherche.class.getResource("/images/gestion/Cancel-48.png")));
		btnAccueil.setForeground(Color.BLACK);
		GridBagConstraints gbc_btnAccueil = new GridBagConstraints();
		gbc_btnAccueil.weighty = 1.0;
		gbc_btnAccueil.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnAccueil.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAccueil.gridx = 0;
		gbc_btnAccueil.gridy = 8;
		panel_menu.add(btnAccueil, gbc_btnAccueil);
		UI.deshabillerBouton(btnAccueil, "Cancel");

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
		txtDateCreation.setEditable(false);
		lblDatecreation.setLabelFor(txtDateCreation);
		GridBagConstraints gbc_txtDateCreation = new GridBagConstraints();
		gbc_txtDateCreation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDateCreation.insets = new Insets(0, 0, 5, 5);
		gbc_txtDateCreation.gridx = 3;
		gbc_txtDateCreation.gridy = 0;
		panel_formulaire.add(txtDateCreation, gbc_txtDateCreation);
		txtDateCreation.setColumns(10);
		UI.habiller(txtDateCreation);

		chckbxCartefidelite = new JCheckBox("Carte de Fidélité");
		chckbxCartefidelite.setOpaque(false);
		GridBagConstraints gbc_chckbxCartefidelite = new GridBagConstraints();
		gbc_chckbxCartefidelite.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxCartefidelite.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxCartefidelite.gridx = 4;
		gbc_chckbxCartefidelite.gridy = 0;
		panel_formulaire.add(chckbxCartefidelite, gbc_chckbxCartefidelite);
		UI.habiller(chckbxCartefidelite);

		JLabel lblPrenom = new JLabel("Prenom");
		GridBagConstraints gbc_lblPrenom = new GridBagConstraints();
		gbc_lblPrenom.anchor = GridBagConstraints.EAST;
		gbc_lblPrenom.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrenom.gridx = 0;
		gbc_lblPrenom.gridy = 1;
		panel_formulaire.add(lblPrenom, gbc_lblPrenom);
		UI.habiller(lblPrenom);

		txtPrenom = new JTextField();
		lblPrenom.setLabelFor(txtPrenom);
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
		txtTelfixe.setEditable(false);
		lblFixe.setLabelFor(txtTelfixe);
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
		txtMobile.setEditable(false);
		lblMobile.setLabelFor(txtMobile);
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
		txtEmail.setEditable(false);
		lblEmail.setLabelFor(txtEmail);
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
		textArea.setEditable(false);
		Border cadre = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		textArea.setBorder(cadre);
		textArea.setBackground(SystemColor.control);
		lblRemarques.setLabelFor(textArea);
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

		JLabel lblTri = new JLabel("Trier la liste par");
		lblTri.setIcon(new ImageIcon(PClientRecherche.class
				.getResource("/images/gestion/Sort-Ascending-32.png")));
		panel_principal.add(lblTri, "cell 1 2,alignx trailing");
		UI.habiller(lblTri);

		comboBoxTrie = new JComboBox<>();
		UI.habiller(comboBoxTrie);
		panel_principal.add(comboBoxTrie, "cell 2 2,growx");
		
		SwingUtilities.invokeLater(
				() -> btnAccueil.requestFocusInWindow()		
		);
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
					+ "il y a eu une insertion !!!"
					+ "\nFaisons d'autres actions suite à cet événement.");
			break;
		case TableModelEvent.DELETE:
			System.out
					.println("La table a été mise à jour, il y a eu une suppression !!!"
							+ "\nFaisons d'autres actions suite à cet événement.");
			break;
		case TableModelEvent.UPDATE:
			System.out
					.println("La table a été mise à jour, il y a eu une modification !!!"
							+ "\nFaisons d'autres actions suite à cet événement.");
			break;
		default:
			break;
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

	private class ActionPrincipale extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionPrincipale() {
			putValue(NAME, "Rechercher");
			putValue(SHORT_DESCRIPTION, "Rechercher parmi les clients existants");
		}

		public void actionPerformed(ActionEvent e) {
			String code = txtCode.getText();
			String nom = txtNom.getText();
			String prenom = txtPrenom.getText();									

			controleClient.rechercher(code, nom, prenom);
		}
	}	
	
	void setActionAnnuler(Action action) {
		btnAccueil.setAction(action);
		UI.deshabillerBouton(btnAccueil, "Cancel");
	}

	void setActionExport(Action action) {
		btnExport.setAction(action);
		UI.deshabillerBouton(btnExport, "Data-Export");
	}
}
