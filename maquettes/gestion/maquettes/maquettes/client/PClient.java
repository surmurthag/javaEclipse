package maquettes.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.time.Instant;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import maquettes.UI;
import net.miginfocom.swing.MigLayout;
import controle.utilitaires.GestionDates;
import entite.Client;

public class PClient extends JPanel {

	private static final long serialVersionUID = 1L;

	// propriétés non graphiques
	// -------------------------

	// propriétés graphiques
	// -------------------------
	private JTextField txtCode;
	private JFormattedTextField dateCreation;
	private JTextField txtPrenom;
	private JTextField txtNom;
	private JTextField txtAdresse;
	private JTextField txtTelfixe;
	private JTextField txtMobile;
	private JTextField txtEmail;

	private JTextArea textArea;

	private JCheckBox chckbxCartefidelite;

	private final Action actionPrincipale = new ActionPrincipale();

	private JLabel lblTitre;
	private JTextField txtCodepostal;
	private JTextField txtVille;

	private JButton btnAccueil;

	private JButton btnExport;

	/**
	 * Create the frame.
	 * 
	 * @param parent
	 * @wbp.parser.constructor
	 */
	PClient() {
		this(null);
	}

	PClient(Client client) {

		JFormattedTextField.AbstractFormatter formatter = new Formateur();
		dateCreation = new JFormattedTextField(formatter);

		createContents();
		if (client != null) {
			txtCode.setText(client.getCode());
			// on empèche la modification des champs code et date création
			txtCode.setEditable(false);
			dateCreation.setValue(client.getDateCreation());
			dateCreation.setEditable(false);

			txtNom.setText(client.getNom());
			txtPrenom.setText(client.getPrenom());
			chckbxCartefidelite.setSelected(client.isCarteFidele());
		}
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

		lblTitre = new JLabel(getTitre());
		lblTitre.setIcon(new ImageIcon(PClient.class.getResource(getIcone())));
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.anchor = GridBagConstraints.LINE_START;
		gbc_lblTitre.insets = new Insets(0, 0, 15, 20);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panel_menu.add(lblTitre, gbc_lblTitre);
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 25));

		JButton btnAction = new JButton("Sauvegarder");
		configureAction(btnAction);
		btnAction.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnAction = new GridBagConstraints();
		gbc_btnAction.anchor = GridBagConstraints.LINE_START;
		gbc_btnAction.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAction.insets = new Insets(0, 0, 5, 0);
		gbc_btnAction.gridx = 0;
		gbc_btnAction.gridy = 1;
		panel_menu.add(btnAction, gbc_btnAction);

		JButton btnApercu = new JButton("Apercu");
		btnApercu.setForeground(Color.BLACK);
		btnApercu.setHorizontalAlignment(SwingConstants.LEFT);
		btnApercu.setIcon(new ImageIcon(PClient.class
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
		btnAccueil.setIcon(new ImageIcon(PClient.class
				.getResource("/images/gestion/Cancel-48.png")));
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
		panel_principal.setLayout(new MigLayout("", "[grow]", "[][][][grow]"));

		JPanel panel_formulaire = new JPanel();
		panel_formulaire.setOpaque(false);
		panel_formulaire.setBorder(new CompoundBorder(
				new CompoundBorder(	new EmptyBorder(5, 5, 5, 5), 
									new TitledBorder(
											new LineBorder(new Color(0, 0, 0), 1, true),
											"Client", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY)),
				new EmptyBorder(5, 5, 5, 5)));
		panel_principal.add(panel_formulaire, "cell 0 0 4 1,grow");
		GridBagLayout gbl_panel_formulaire = new GridBagLayout();
		gbl_panel_formulaire.columnWidths = new int[] { 79, 182, 91, 182, 119 };
		gbl_panel_formulaire.rowHeights = new int[] { 26 };
		gbl_panel_formulaire.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0,
				0.0 };
		gbl_panel_formulaire.rowWeights = new double[] { 1.0 };
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

		lblDatecreation.setLabelFor(dateCreation);
		GridBagConstraints gbc_txtDateCreation = new GridBagConstraints();
		gbc_txtDateCreation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDateCreation.insets = new Insets(0, 0, 5, 5);
		gbc_txtDateCreation.gridx = 3;
		gbc_txtDateCreation.gridy = 0;
		panel_formulaire.add(dateCreation, gbc_txtDateCreation);
		dateCreation.setColumns(10);
		UI.habiller(dateCreation);

		chckbxCartefidelite = new JCheckBox("Carte de Fidélité");
		chckbxCartefidelite.setOpaque(false);
		GridBagConstraints gbc_chckbxCartefidelite = new GridBagConstraints();
		gbc_chckbxCartefidelite.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxCartefidelite.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxCartefidelite.gridx = 4;
		gbc_chckbxCartefidelite.gridy = 0;
		panel_formulaire.add(chckbxCartefidelite, gbc_chckbxCartefidelite);
		UI.habiller(chckbxCartefidelite);

		JPanel panel_etatCivil = new JPanel();
		panel_etatCivil.setOpaque(false);
		panel_etatCivil.setBorder(new CompoundBorder(
				new CompoundBorder(	new EmptyBorder(5, 5, 5, 5), 
									new TitledBorder(
											new LineBorder(new Color(0, 0, 0), 1, true),
											"Etat Civil", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY)),
				new EmptyBorder(5, 5, 5, 5)));
		panel_principal.add(panel_etatCivil, "cell 0 1,grow");
		GridBagLayout gbl_panel_etatCivil = new GridBagLayout();
		gbl_panel_etatCivil.columnWidths = new int[] { 56, 146, 34, 146, 0 };
		gbl_panel_etatCivil.rowHeights = new int[] { 26, 26, 0, 0 };
		gbl_panel_etatCivil.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel_etatCivil.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel_etatCivil.setLayout(gbl_panel_etatCivil);

		JLabel lblPrenom = new JLabel("Prénom");
		GridBagConstraints gbc_lblPrenom = new GridBagConstraints();
		gbc_lblPrenom.anchor = GridBagConstraints.WEST;
		gbc_lblPrenom.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrenom.gridx = 0;
		gbc_lblPrenom.gridy = 0;
		panel_etatCivil.add(lblPrenom, gbc_lblPrenom);
		UI.habiller(lblPrenom);
		lblPrenom.setLabelFor(txtPrenom);

		txtPrenom = new JTextField();
		GridBagConstraints gbc_txtPrenom = new GridBagConstraints();
		gbc_txtPrenom.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrenom.weightx = 1.0;
		gbc_txtPrenom.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtPrenom.insets = new Insets(0, 0, 5, 20);
		gbc_txtPrenom.gridx = 1;
		gbc_txtPrenom.gridy = 0;
		panel_etatCivil.add(txtPrenom, gbc_txtPrenom);
		txtPrenom.setColumns(10);
		UI.habiller(txtPrenom);

		JLabel lblNom = new JLabel("Nom");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.WEST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 2;
		gbc_lblNom.gridy = 0;
		panel_etatCivil.add(lblNom, gbc_lblNom);
		UI.habiller(lblNom);

		txtNom = new JTextField();
		lblNom.setLabelFor(txtNom);
		GridBagConstraints gbc_txtNom = new GridBagConstraints();
		gbc_txtNom.weightx = 1.0;
		gbc_txtNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNom.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtNom.insets = new Insets(0, 0, 5, 0);
		gbc_txtNom.gridx = 3;
		gbc_txtNom.gridy = 0;
		panel_etatCivil.add(txtNom, gbc_txtNom);
		txtNom.setColumns(10);
		UI.habiller(txtNom);

		JLabel lblAdresse = new JLabel("Adresse");
		GridBagConstraints gbc_lblAdresse = new GridBagConstraints();
		gbc_lblAdresse.anchor = GridBagConstraints.WEST;
		gbc_lblAdresse.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdresse.gridx = 0;
		gbc_lblAdresse.gridy = 1;
		panel_etatCivil.add(lblAdresse, gbc_lblAdresse);
		UI.habiller(lblAdresse);

		txtAdresse = new JTextField();
		lblAdresse.setLabelFor(txtAdresse);
		GridBagConstraints gbc_txtAdresse = new GridBagConstraints();
		gbc_txtAdresse.insets = new Insets(0, 0, 5, 0);
		gbc_txtAdresse.gridwidth = 3;
		gbc_txtAdresse.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAdresse.weightx = 1.0;
		gbc_txtAdresse.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtAdresse.gridx = 1;
		gbc_txtAdresse.gridy = 1;
		panel_etatCivil.add(txtAdresse, gbc_txtAdresse);
		txtAdresse.setColumns(10);
		UI.habiller(txtAdresse);
		
		JLabel lblCodepostal = new JLabel("Code Postal");
		GridBagConstraints gbc_lblCodepostal = new GridBagConstraints();
		gbc_lblCodepostal.anchor = GridBagConstraints.EAST;
		gbc_lblCodepostal.insets = new Insets(0, 0, 0, 5);
		gbc_lblCodepostal.gridx = 0;
		gbc_lblCodepostal.gridy = 2;
		panel_etatCivil.add(lblCodepostal, gbc_lblCodepostal);
		UI.habiller(lblCodepostal);
		
		txtCodepostal = new JTextField();
		lblCodepostal.setLabelFor(txtCodepostal);
		GridBagConstraints gbc_txtCodepostal = new GridBagConstraints();
		gbc_txtCodepostal.insets = new Insets(0, 0, 0, 20);
		gbc_txtCodepostal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCodepostal.gridx = 1;
		gbc_txtCodepostal.gridy = 2;
		panel_etatCivil.add(txtCodepostal, gbc_txtCodepostal);
		txtCodepostal.setColumns(10);
		UI.habiller(txtCodepostal);
		
		JLabel lblVille = new JLabel("Ville");
		GridBagConstraints gbc_lblVille = new GridBagConstraints();
		gbc_lblVille.anchor = GridBagConstraints.EAST;
		gbc_lblVille.insets = new Insets(0, 0, 0, 5);
		gbc_lblVille.gridx = 2;
		gbc_lblVille.gridy = 2;
		panel_etatCivil.add(lblVille, gbc_lblVille);
		UI.habiller(lblVille);
		
		txtVille = new JTextField();
		lblVille.setLabelFor(txtVille);
		GridBagConstraints gbc_txtVille = new GridBagConstraints();
		gbc_txtVille.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVille.gridx = 3;
		gbc_txtVille.gridy = 2;
		panel_etatCivil.add(txtVille, gbc_txtVille);
		txtVille.setColumns(10);
		UI.habiller(txtVille);
		
		JPanel panel_coordonnees = new JPanel();
		panel_coordonnees.setOpaque(false);
		panel_coordonnees.setBorder(new CompoundBorder(
				new CompoundBorder(	new EmptyBorder(5, 5, 5, 5), 
									new TitledBorder(
											new LineBorder(new Color(0, 0, 0), 1, true),
											"Coordonnées", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY)),
				new EmptyBorder(5, 5, 5, 5)));

		panel_principal.add(panel_coordonnees, "cell 0 2,grow");
		GridBagLayout gbl_panel_coordonnees = new GridBagLayout();
		gbl_panel_coordonnees.columnWidths = new int[] { 39, 146, 46, 146, 0 };
		gbl_panel_coordonnees.rowHeights = new int[] { 26, 26, 0 };
		gbl_panel_coordonnees.columnWeights = new double[] { 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		gbl_panel_coordonnees.rowWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		panel_coordonnees.setLayout(gbl_panel_coordonnees);

		JLabel lblFixe = new JLabel("Fixe");
		GridBagConstraints gbc_lblFixe = new GridBagConstraints();
		gbc_lblFixe.anchor = GridBagConstraints.WEST;
		gbc_lblFixe.insets = new Insets(0, 0, 5, 5);
		gbc_lblFixe.gridx = 0;
		gbc_lblFixe.gridy = 0;
		panel_coordonnees.add(lblFixe, gbc_lblFixe);
		UI.habiller(lblFixe);

		txtTelfixe = new JTextField();
		lblFixe.setLabelFor(txtTelfixe);
		GridBagConstraints gbc_txtTelfixe = new GridBagConstraints();
		gbc_txtTelfixe.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelfixe.weightx = 1.0;
		gbc_txtTelfixe.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtTelfixe.insets = new Insets(0, 0, 5, 20);
		gbc_txtTelfixe.gridx = 1;
		gbc_txtTelfixe.gridy = 0;
		panel_coordonnees.add(txtTelfixe, gbc_txtTelfixe);
		txtTelfixe.setColumns(10);
		UI.habiller(txtTelfixe);

		JLabel lblMobile = new JLabel("Mobile");
		GridBagConstraints gbc_lblMobile = new GridBagConstraints();
		gbc_lblMobile.anchor = GridBagConstraints.WEST;
		gbc_lblMobile.insets = new Insets(0, 0, 5, 5);
		gbc_lblMobile.gridx = 2;
		gbc_lblMobile.gridy = 0;
		panel_coordonnees.add(lblMobile, gbc_lblMobile);
		UI.habiller(lblMobile);
		lblMobile.setLabelFor(txtMobile);

		txtMobile = new JTextField();
		GridBagConstraints gbc_txtMobile = new GridBagConstraints();
		gbc_txtMobile.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMobile.weightx = 1.0;
		gbc_txtMobile.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtMobile.insets = new Insets(0, 0, 5, 0);
		gbc_txtMobile.gridx = 3;
		gbc_txtMobile.gridy = 0;
		panel_coordonnees.add(txtMobile, gbc_txtMobile);
		txtMobile.setColumns(10);
		UI.habiller(txtMobile);

		JLabel lblEmail = new JLabel("Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 0, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 1;
		panel_coordonnees.add(lblEmail, gbc_lblEmail);
		UI.habiller(lblEmail);

		txtEmail = new JTextField();
		lblEmail.setLabelFor(txtEmail);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.gridwidth = 3;
		gbc_txtEmail.weightx = 1.0;
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtEmail.gridx = 1;
		gbc_txtEmail.gridy = 1;
		panel_coordonnees.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
		UI.habiller(txtEmail);

		JPanel panel_remarques = new JPanel();
		panel_remarques.setOpaque(false);
		panel_remarques.setBorder(new CompoundBorder(
				new CompoundBorder(	new EmptyBorder(5, 5, 5, 5), 
									new TitledBorder(
											new LineBorder(new Color(0, 0, 0), 1, true),
											"Remarques", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY)),
				new EmptyBorder(5, 5, 5, 5)));
		panel_principal.add(panel_remarques, "cell 0 3,grow");
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 79};
		gbl_panel.rowHeights = new int[] { 210 };
		gbl_panel.columnWeights = new double[] { 0.0};
		gbl_panel.rowWeights = new double[] { 0.0 };
		panel_remarques.setLayout(gbl_panel);

		textArea = new JTextArea();
		Border cadre = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		textArea.setBorder(cadre);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.weighty = 1.0;
		gbc_textArea.weightx = 1.0;
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.anchor = GridBagConstraints.NORTHWEST;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 0;
		panel_remarques.add(textArea, gbc_textArea);
		UI.habiller(textArea);
		
		SwingUtilities.invokeLater(
				() -> btnAccueil.requestFocusInWindow()		
		);
	}

	void configureAction(JButton bouton) {
		bouton.setAction(actionPrincipale);
		bouton.setForeground(Color.BLACK);
		bouton.setIcon(new ImageIcon(PClient.class
				.getResource("/images/gestion/Save-48.png")));
		UI.deshabillerBouton(bouton, "Save");
	}

	String getIcone() {
		return "/images/gestion/client/User-Add-64.png";
	}

	String getTitre() {
		return "Ajout";
	}

	private final class Formateur extends
			JFormattedTextField.AbstractFormatter {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String valueToString(Object value) throws ParseException {
			String text = "";
			if (value instanceof Instant) {
				Instant instant = (Instant) value;					
				text = GestionDates.dateEnChaineFR(instant);
			}
			return text;
		}

		@Override
		public Object stringToValue(String text) throws ParseException {
			try {
				return GestionDates.chaineFRenDate(text);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	private class ActionPrincipale extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionPrincipale() {
			configurerAction(this);
		}

		public void actionPerformed(ActionEvent e) {
			String code = txtCode.getText();
			String nom = txtNom.getText();
			String prenom = txtPrenom.getText();
			boolean carteFidelite = chckbxCartefidelite.isSelected();
			Instant crééLe = (Instant) dateCreation.getValue();

			actionPrincipale(code, nom, prenom, carteFidelite, crééLe);
		}
	}

	protected void configurerAction(AbstractAction action) {
		action.putValue(AbstractAction.NAME, "Sauvegarder");
		action.putValue(AbstractAction.SHORT_DESCRIPTION, "Sauvegarder le nouveau client");
	}

	protected void actionPrincipale(String code, String nom, String prenom,
			boolean fidelite, Instant crééLe) {
	}

	JLabel getLblTitre() {
		return lblTitre;
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
