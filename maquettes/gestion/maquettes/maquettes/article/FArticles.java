package maquettes.article;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;

import maquettes.UI;
import maquettes.client.PClients;
import controle.connection.Connexion;

public class FArticles extends JDialog {

	private static final long serialVersionUID = 1L;

	// propriétés non graphiques
	// -------------------------

	private JPanel contentPane;
	private JTable table;
	private JTextField txtCode;
	private JTextField txtCategorie;

	private final Action actionAccueil = new ActionAccueil();
	private JTextField txtRecherche;
	private JTextField txtDesignation;

	private JFormattedTextField txtPrixUnitaire;

	private JSlider sliderQuantite;

	private ButtonGroup buttonGroup;

	private JRadioButton rdbtnCategorie;

	private JRadioButton rdbtnCode;
	private JTextField txtQuantite;

	public FArticles setConnexion(Connexion connexion) {

		buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnCode);
		buttonGroup.add(rdbtnCategorie);

		rdbtnCode.addActionListener((ActionEvent e) -> {
			System.out.println("Code sélectionné ? " + rdbtnCode.isSelected());
		});
		rdbtnCategorie.addActionListener((ActionEvent e) -> {
			System.out.println("Catégorie sélectionnée ? "
					+ rdbtnCategorie.isSelected());
		});

		txtQuantite.setText(Integer.toString(sliderQuantite.getValue()));
		sliderQuantite.addChangeListener((ChangeEvent e) -> {
			txtQuantite.setText(Integer.toString(sliderQuantite.getValue()));
		});

		return this;
	}

	/**
	 * Create the frame.
	 * 
	 * @param parent
	 */
	public FArticles(Window parent) {
		super(parent);

		setIconImage(UI.getLogo());
		setTitle("Gestion des Articles");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		setLocationRelativeTo(null);

		createContents();
	}

	private void createContents() {
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0x99, 0xCC, 0x00));
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel_menu = new JPanel();
		panel_menu.setBackground(new Color(0x66, 0x99, 0x00));
		contentPane.add(panel_menu, BorderLayout.WEST);
		panel_menu.setBorder(new CompoundBorder(null, new EmptyBorder(5, 5, 5,
				5)));
		GridBagLayout gbl_panel_menu = new GridBagLayout();
		gbl_panel_menu.columnWidths = new int[] { 0, 0 };
		gbl_panel_menu.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_menu.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_menu.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_menu.setLayout(gbl_panel_menu);

		JLabel lblTitre = new JLabel("Articles");
		lblTitre.setIcon(new ImageIcon(FArticles.class
				.getResource("/images/gestion/article/Product-64-actif.png")));
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.insets = new Insets(0, 0, 15, 20);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panel_menu.add(lblTitre, gbc_lblTitre);
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 25));

		JButton btnApercu = new JButton("Apercu");
		btnApercu.setForeground(Color.WHITE);
		btnApercu.setHorizontalAlignment(SwingConstants.LEFT);
		btnApercu.setIcon(new ImageIcon(FArticles.class
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
		btnImprimer.setForeground(Color.WHITE);
		GridBagConstraints gbc_btnImprimer = new GridBagConstraints();
		gbc_btnImprimer.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnImprimer.anchor = GridBagConstraints.LINE_START;
		gbc_btnImprimer.insets = new Insets(0, 0, 5, 0);
		gbc_btnImprimer.gridx = 0;
		gbc_btnImprimer.gridy = 6;
		panel_menu.add(btnImprimer, gbc_btnImprimer);
		UI.deshabillerBouton(btnImprimer, "Printer");

		JButton btnExport = new JButton("Export");
		btnExport.setForeground(Color.WHITE);
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
		btnAccueil.setForeground(Color.WHITE);
		GridBagConstraints gbc_btnAccueil = new GridBagConstraints();
		gbc_btnAccueil.weighty = 1.0;
		gbc_btnAccueil.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnAccueil.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAccueil.gridx = 0;
		gbc_btnAccueil.gridy = 8;
		panel_menu.add(btnAccueil, gbc_btnAccueil);
		UI.deshabillerBouton(btnAccueil, "Home");

		JPanel panel_principal = new JPanel();
		panel_principal.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel_principal.setBackground(new Color(0xE2, 0xF0, 0xB6));
		contentPane.add(panel_principal, BorderLayout.CENTER);
		GridBagLayout gbl_panel_principal = new GridBagLayout();
		gbl_panel_principal.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_principal.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_principal.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0 };
		gbl_panel_principal.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		panel_principal.setLayout(gbl_panel_principal);

		JPanel panel_formulaire = new JPanel();
		panel_formulaire.setOpaque(false);
		panel_formulaire.setBorder(new CompoundBorder(new LineBorder(new Color(
				0, 0, 0), 2, true), new EmptyBorder(5, 5, 5, 5)));
		GridBagConstraints gbc_panel_formulaire = new GridBagConstraints();
		gbc_panel_formulaire.weightx = 1.0;
		gbc_panel_formulaire.fill = GridBagConstraints.BOTH;
		gbc_panel_formulaire.insets = new Insets(0, 0, 5, 0);
		gbc_panel_formulaire.gridwidth = 5;
		gbc_panel_formulaire.gridx = 0;
		gbc_panel_formulaire.gridy = 0;
		panel_principal.add(panel_formulaire, gbc_panel_formulaire);
		GridBagLayout gbl_panel_formulaire = new GridBagLayout();
		gbl_panel_formulaire.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_formulaire.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_formulaire.columnWeights = new double[] { 1.0, 1.0, 1.0, 0.0,
				1.0, 0.0 };
		gbl_panel_formulaire.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				1.0 };
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
		gbc_txtCode.gridwidth = 2;
		gbc_txtCode.weightx = 1.0;
		gbc_txtCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCode.insets = new Insets(0, 0, 5, 5);
		gbc_txtCode.gridx = 1;
		gbc_txtCode.gridy = 0;
		panel_formulaire.add(txtCode, gbc_txtCode);
		txtCode.setColumns(10);
		UI.habiller(txtCode);

		JLabel lblCategorie = new JLabel("Catégorie");
		GridBagConstraints gbc_lblCategorie = new GridBagConstraints();
		gbc_lblCategorie.anchor = GridBagConstraints.EAST;
		gbc_lblCategorie.insets = new Insets(0, 20, 5, 5);
		gbc_lblCategorie.gridx = 3;
		gbc_lblCategorie.gridy = 0;
		panel_formulaire.add(lblCategorie, gbc_lblCategorie);
		UI.habiller(lblCategorie);

		txtCategorie = new JTextField();
		lblCategorie.setLabelFor(txtCategorie);
		GridBagConstraints gbc_txtCategorie = new GridBagConstraints();
		gbc_txtCategorie.gridwidth = 2;
		gbc_txtCategorie.weightx = 1.0;
		gbc_txtCategorie.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCategorie.insets = new Insets(0, 0, 5, 0);
		gbc_txtCategorie.gridx = 4;
		gbc_txtCategorie.gridy = 0;
		panel_formulaire.add(txtCategorie, gbc_txtCategorie);
		txtCategorie.setColumns(10);
		UI.habiller(txtCategorie);

		JLabel lblDesignation = new JLabel("Désignation");
		UI.habiller(lblDesignation);
		GridBagConstraints gbc_lblDesignation = new GridBagConstraints();
		gbc_lblDesignation.anchor = GridBagConstraints.EAST;
		gbc_lblDesignation.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesignation.gridx = 0;
		gbc_lblDesignation.gridy = 1;
		panel_formulaire.add(lblDesignation, gbc_lblDesignation);

		txtDesignation = new JTextField();
		GridBagConstraints gbc_txtDesignation = new GridBagConstraints();
		gbc_txtDesignation.gridwidth = 5;
		gbc_txtDesignation.insets = new Insets(0, 0, 5, 0);
		gbc_txtDesignation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDesignation.gridx = 1;
		gbc_txtDesignation.gridy = 1;
		panel_formulaire.add(txtDesignation, gbc_txtDesignation);
		txtDesignation.setColumns(10);
		UI.habiller(txtDesignation);

		JLabel lblQuantite = new JLabel("Quantité");
		GridBagConstraints gbc_lblQuantite = new GridBagConstraints();
		gbc_lblQuantite.anchor = GridBagConstraints.EAST;
		gbc_lblQuantite.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantite.gridx = 0;
		gbc_lblQuantite.gridy = 2;
		panel_formulaire.add(lblQuantite, gbc_lblQuantite);
		UI.habiller(lblQuantite);

		sliderQuantite = new JSlider(1, 20, 5);
		sliderQuantite.setOpaque(false);
		GridBagConstraints gbc_slider_quantite = new GridBagConstraints();
		gbc_slider_quantite.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider_quantite.insets = new Insets(0, 0, 5, 5);
		gbc_slider_quantite.gridx = 1;
		gbc_slider_quantite.gridy = 2;
		panel_formulaire.add(sliderQuantite, gbc_slider_quantite);
		UI.habiller(sliderQuantite);

		txtQuantite = new JTextField();
		txtQuantite.setEditable(false);
		GridBagConstraints gbc_quantite = new GridBagConstraints();
		gbc_quantite.insets = new Insets(0, 0, 5, 5);
		gbc_quantite.fill = GridBagConstraints.HORIZONTAL;
		gbc_quantite.gridx = 2;
		gbc_quantite.gridy = 2;
		panel_formulaire.add(txtQuantite, gbc_quantite);
		txtQuantite.setColumns(2);
		UI.habiller(txtQuantite);

		JLabel lblPrixUnitaire = new JLabel("Prix Unitaire");
		GridBagConstraints gbc_lblPrixUnitaire = new GridBagConstraints();
		gbc_lblPrixUnitaire.anchor = GridBagConstraints.EAST;
		gbc_lblPrixUnitaire.insets = new Insets(0, 20, 5, 5);
		gbc_lblPrixUnitaire.gridx = 3;
		gbc_lblPrixUnitaire.gridy = 2;
		panel_formulaire.add(lblPrixUnitaire, gbc_lblPrixUnitaire);
		UI.habiller(lblPrixUnitaire);

		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(2);
		txtPrixUnitaire = new JFormattedTextField(format);
		txtPrixUnitaire.setText("0,00");
		txtPrixUnitaire.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_spinner_prix = new GridBagConstraints();
		gbc_spinner_prix.weightx = 1.0;
		gbc_spinner_prix.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_prix.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_prix.gridx = 4;
		gbc_spinner_prix.gridy = 2;
		txtPrixUnitaire.setColumns(10);
		panel_formulaire.add(txtPrixUnitaire, gbc_spinner_prix);
		UI.habiller(txtPrixUnitaire);

		JLabel lblMoney = new JLabel("€");
		GridBagConstraints gbc_lblMoney = new GridBagConstraints();
		gbc_lblMoney.anchor = GridBagConstraints.LINE_START;
		gbc_lblMoney.insets = new Insets(0, 0, 5, 0);
		gbc_lblMoney.gridx = 5;
		gbc_lblMoney.gridy = 2;
		panel_formulaire.add(lblMoney, gbc_lblMoney);
		UI.habiller(lblMoney);

		JToolBar toolBar = new JToolBar();
		toolBar.setOpaque(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.gridwidth = 6;
		gbc_toolBar.insets = new Insets(0, 0, 5, 5);
		gbc_toolBar.gridx = 0;
		gbc_toolBar.gridy = 3;
		panel_formulaire.add(toolBar, gbc_toolBar);

		JButton btnAjouter = new JButton("Ajouter");
		toolBar.add(btnAjouter);
		btnAjouter.setHorizontalAlignment(SwingConstants.LEFT);
		btnAjouter.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Add-New-48.png")));
		UI.deshabillerBouton(btnAjouter, "Add-New");
		btnAjouter.setForeground(Color.BLACK);

		JButton btnModifier = new JButton("Modifier");
		toolBar.add(btnModifier);
		btnModifier.setHorizontalAlignment(SwingConstants.LEFT);
		btnModifier.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Data-Edit-48.png")));
		UI.deshabillerBouton(btnModifier, "Data-Edit");
		btnModifier.setForeground(Color.BLACK);

		JButton btnSupprimer = new JButton("Supprimer");
		toolBar.add(btnSupprimer);
		btnSupprimer.setHorizontalAlignment(SwingConstants.LEFT);
		btnSupprimer.setIcon(new ImageIcon(FArticles.class
				.getResource("/images/gestion/Garbage-Open-48.png")));
		UI.deshabillerBouton(btnSupprimer, "Garbage-Open");
		btnSupprimer.setForeground(Color.BLACK);

		JButton btnAnnuler = new JButton("Effacer");
		toolBar.add(btnAnnuler);
		btnAnnuler.setHorizontalAlignment(SwingConstants.LEFT);
		btnAnnuler.setIcon(new ImageIcon(FArticles.class
				.getResource("/images/gestion/Cancel-48.png")));
		UI.deshabillerBouton(btnAnnuler, "Cancel");
		btnAnnuler.setForeground(Color.BLACK);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 6;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 4;
		panel_formulaire.add(panel, gbc_panel);

		table = new JTable();
		UI.habiller(table);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.weighty = 1.0;
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;

		panel_principal.add(scrollPane, gbc_scrollPane);

		JLabel lblTri = new JLabel("Trier par");
		lblTri.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Sort-Ascending-32.png")));
		GridBagConstraints gbc_lblTri = new GridBagConstraints();
		gbc_lblTri.anchor = GridBagConstraints.EAST;
		gbc_lblTri.insets = new Insets(0, 0, 5, 5);
		gbc_lblTri.gridx = 0;
		gbc_lblTri.gridy = 2;
		panel_principal.add(lblTri, gbc_lblTri);
		UI.habiller(lblTri);

		rdbtnCode = new JRadioButton("Code");
		rdbtnCode.setOpaque(false);
		UI.habiller(rdbtnCode);
		GridBagConstraints gbc_rdbtnCode = new GridBagConstraints();
		gbc_rdbtnCode.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnCode.gridx = 1;
		gbc_rdbtnCode.gridy = 2;
		panel_principal.add(rdbtnCode, gbc_rdbtnCode);

		rdbtnCategorie = new JRadioButton("Catégorie");
		rdbtnCategorie.setOpaque(false);
		UI.habiller(rdbtnCategorie);
		GridBagConstraints gbc_rdbtnCatgorie = new GridBagConstraints();
		gbc_rdbtnCatgorie.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnCatgorie.gridx = 2;
		gbc_rdbtnCatgorie.gridy = 2;
		panel_principal.add(rdbtnCategorie, gbc_rdbtnCatgorie);

		JLabel lblRecherche = new JLabel("Recherche");
		lblRecherche.setIcon(new ImageIcon(FArticles.class
				.getResource("/images/gestion/Search-32.png")));
		GridBagConstraints gbc_lblRecherche = new GridBagConstraints();
		gbc_lblRecherche.anchor = GridBagConstraints.WEST;
		gbc_lblRecherche.insets = new Insets(0, 20, 5, 5);
		gbc_lblRecherche.gridx = 3;
		gbc_lblRecherche.gridy = 2;
		panel_principal.add(lblRecherche, gbc_lblRecherche);
		UI.habiller(lblRecherche);

		txtRecherche = new JTextField();
		txtRecherche.setText("");
		GridBagConstraints gbc_txtRecherche = new GridBagConstraints();
		gbc_txtRecherche.insets = new Insets(0, 0, 5, 0);
		gbc_txtRecherche.weightx = 5.0;
		gbc_txtRecherche.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRecherche.gridx = 4;
		gbc_txtRecherche.gridy = 2;
		panel_principal.add(txtRecherche, gbc_txtRecherche);
		txtRecherche.setColumns(10);
		UI.habiller(txtRecherche);

		bouton_mode_ajout_ou_edition(true);

		SwingUtilities.invokeLater(() -> btnAccueil.requestFocusInWindow());
	}

	private class ActionAccueil extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionAccueil() {
			putValue(NAME, "Accueil");
			putValue(SHORT_DESCRIPTION, "Retourner sur l'écran d'accueil");
		}

		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	private void effacerSaisie() {
		txtCode.setText("");
		txtCategorie.setText("");
		txtDesignation.setText("");
		sliderQuantite.setValue(Integer.valueOf(1));
		txtPrixUnitaire.setValue(Double.valueOf(0));
		txtCode.requestFocus();
	}

	/*
	 * Methode permettant de basculer les boutons lors d'un ajout ou d'une
	 * modification
	 */
	private void bouton_mode_ajout_ou_edition(boolean ajout) {
	}
}
