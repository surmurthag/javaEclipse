package dialogue.article;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import controle.ControleArticleDecore;
import controle.connection.Connexion;
import dialogue.UI;
import dialogue.client.PClients;
import dialogue.rendu.GrasRenderer;
import entite.Article;

public class FArticles extends JDialog {

	private static final long serialVersionUID = 1L;

	// propriétés non graphiques
	// -------------------------
	private ControleArticleDecore controleArticle;

	private JPanel contentPane;
	private JTable table;
	private JTextField txtCode;
	private JTextField txtCategorie;

	private final Action actionAjouter = new ActionAjouter();
	private final Action actionModifier = new ActionModifier();
	private final Action actionSupprimer = new ActionSupprimer();
	private final Action actionEffacer = new ActionEffacer();

	private final Action actionApercu = new ActionApercu();
	private final Action actionImprimer = new ActionImprimer();
	private final Action actionExport = new ActionExport();

	private final Action actionAccueil = new ActionAccueil();
	private JTextField txtRecherche;
	private JTextField txtDesignation;

	private JFormattedTextField txtPrixUnitaire;

	private JSlider sliderQuantite;

	private ButtonGroup buttonGroup;

	private JRadioButton rdbtnCategorie;

	private JRadioButton rdbtnCode;
	private JTextField txtQuantite;

	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private final Chercheur chercheur = new Chercheur();
	private final class Chercheur implements Runnable {
		private Future<?> future;
		private String recherche;

		@Override
		public void run() {
			System.out.println("Recherche de " +recherche );
			controleArticle.rechercher(recherche);
			this.future = null;
			this.recherche = null;
		}
		
		public void planifier(String recherche) {
			if (this.future != null) {
				System.out.println("Annule la recherche planifiée de " +this.recherche +" pour " +recherche);
				this.future.cancel(false);
			} else {
				System.out.println("Recherche planifiée pour " +recherche);
			}
			
			this.recherche = recherche;
			Future<?> future = executor.schedule(this, 300, TimeUnit.MILLISECONDS);
			this.future = future;
		}
	}
	
	@Override
	public void dispose() {
		executor.shutdown();
		super.dispose();
	}
	
	public FArticles setConnexion(Connexion connexion) {
		controleArticle = new ControleArticleDecore(connexion);
		controleArticle.init();

		table.setModel(controleArticle.getModele());

		// gestion du rendu des colonnes du JTable
		// ----------------------------------------
		TableColumnModel colonnes = table.getColumnModel();
		TableColumn colonne = colonnes.getColumn(1);
		colonne.setCellRenderer(new GrasRenderer());

		table.setToolTipText("<html><img src=\""
				+ FArticles.class.getResource("/images/gestion/Data-Edit-48-actif.png")
				+ "\" />Pour <b>modifier</b> une ligne, vous devez <b>double-cliquez</b> sur <b>celle-ci</b></html>");
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int numeroLigne = table.getSelectedRow();
					Article article = controleArticle.getModele().getArticle(
							numeroLigne);
					article.getCode();
					article.getCategorie().getCode();
					article.getDesignation();
					article.getQuantite();
					article.getPrixUnitaire();

					txtCode.setText(String.valueOf(table.getValueAt(
							numeroLigne, 0)));
					txtCategorie.setText(String.valueOf(table.getValueAt(
							numeroLigne, 1)));
					txtDesignation.setText(String.valueOf(table.getValueAt(
							numeroLigne, 2)));
					sliderQuantite.setValue(article.getQuantite());
					txtPrixUnitaire.setValue(table.getValueAt(numeroLigne,
							4));

					bouton_mode_ajout_ou_edition(false);
				}
			}
		});

		txtRecherche.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				String recherche = txtRecherche.getText();
				chercheur.planifier(recherche);
			}
		});

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
			String valeur = Integer.toString(sliderQuantite.getValue());
			txtQuantite.setText(valeur);
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
		btnApercu.setAction(actionApercu);
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
		btnImprimer.setAction(actionImprimer);
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
		btnExport.setAction(actionExport);
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
		btnAjouter.setAction(actionAjouter);
		btnAjouter.setHorizontalAlignment(SwingConstants.LEFT);
		btnAjouter.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Add-New-48.png")));
		UI.deshabillerBouton(btnAjouter, "Add-New");
		btnAjouter.setForeground(Color.BLACK);

		JButton btnModifier = new JButton("Modifier");
		toolBar.add(btnModifier);
		btnModifier.setAction(actionModifier);
		btnModifier.setHorizontalAlignment(SwingConstants.LEFT);
		btnModifier.setIcon(new ImageIcon(PClients.class
				.getResource("/images/gestion/Data-Edit-48.png")));
		UI.deshabillerBouton(btnModifier, "Data-Edit");
		btnModifier.setForeground(Color.BLACK);

		JButton btnSupprimer = new JButton("Supprimer");
		toolBar.add(btnSupprimer);
		btnSupprimer.setAction(actionSupprimer);
		btnSupprimer.setHorizontalAlignment(SwingConstants.LEFT);
		btnSupprimer.setIcon(new ImageIcon(FArticles.class
				.getResource("/images/gestion/Garbage-Open-48.png")));
		UI.deshabillerBouton(btnSupprimer, "Garbage-Open");
		btnSupprimer.setForeground(Color.BLACK);

		JButton btnAnnuler = new JButton("Effacer");
		toolBar.add(btnAnnuler);
		btnAnnuler.setAction(actionEffacer);
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

	private class ActionExport extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionExport() {
			putValue(NAME, "Export");
			putValue(SHORT_DESCRIPTION,
					"Exporter les articles sous forme de fichier");
		}

		public void actionPerformed(ActionEvent e) {
			controleArticle.export(FArticles.this);
		}
	}

	private class ActionApercu extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionApercu() {
			putValue(NAME, "Aperçu");
			putValue(SHORT_DESCRIPTION, "Aperçu avant impression");
		}

		public void actionPerformed(ActionEvent e) {
			controleArticle.apercu();
		}
	}

	private class ActionImprimer extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionImprimer() {
			putValue(NAME, "Imprimer");
			putValue(SHORT_DESCRIPTION, "Imprimer les données des articles");
		}

		public void actionPerformed(ActionEvent e) {
			controleArticle.imprimer();
		}
	}

	private class ActionSupprimer extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionSupprimer() {
			putValue(NAME, "Supprimer");
			putValue(SHORT_DESCRIPTION, "Supprimer l'article sélectionné");
		}

		public void actionPerformed(ActionEvent e) {
			int numeroLigne = table.getSelectedRow();
			if (numeroLigne < 0) {
				// si aucune ligne sélectionnée
				JOptionPane.showMessageDialog(null,
						"Vous devez sélectionner "
						+ "une ligne pour la supprimer",
						"Avertissement", JOptionPane.WARNING_MESSAGE);
			} else {
				int choix = JOptionPane
						.showConfirmDialog(
								null,
								"Voulez-vous vraiment supprimer"
								+ " l'article sélectionné ?",
								"Confirmation", JOptionPane.YES_NO_OPTION);
				if (choix == JOptionPane.YES_OPTION) {
					Article article = controleArticle.getModele().getArticle(numeroLigne);
					
					String code = article.getCode();
					controleArticle.supprimer(numeroLigne, code);
				}
			}
		}
	}

	private class ActionEffacer extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionEffacer() {
			putValue(NAME, "Effacer");
			putValue(SHORT_DESCRIPTION, "Effacer la saisie en cours");
		}

		public void actionPerformed(ActionEvent e) {
			effacerSaisie();
			bouton_mode_ajout_ou_edition(true);
		}
	}

	private class ActionModifier extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionModifier() {
			putValue(NAME, "Modifier");
			putValue(SHORT_DESCRIPTION, "Modifier l'article sélectionné");
		}

		public void actionPerformed(ActionEvent e) {
			String code = txtCode.getText();
			if ("".equals(code)) {
				JOptionPane.showMessageDialog(null,
						"Le code article est obligatoire", "Avertissement",
						JOptionPane.WARNING_MESSAGE);
			} else {
				String categorie = txtCategorie.getText();
				String designation = txtDesignation.getText();
				int quantite = (Integer) sliderQuantite.getValue();
				double prix = ((Number) txtPrixUnitaire.getValue())
						.doubleValue();
				int numeroLigne = table.getSelectedRow();
				if (numeroLigne < 0) {
					JOptionPane.showMessageDialog(null,
							"Sélectionnez un article", "Avertissement",
							JOptionPane.WARNING_MESSAGE);
				} else {
					boolean modif = controleArticle.modifier(numeroLigne,
							code, categorie, designation, quantite, prix);
					if (modif) {
						effacerSaisie();
						
						bouton_mode_ajout_ou_edition(true);
					}
				}
			}
		}
	}

	private class ActionAjouter extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ActionAjouter() {
			putValue(NAME, "Ajouter");
			putValue(SHORT_DESCRIPTION, "Ajouter un nouvel article");
		}

		public void actionPerformed(ActionEvent e) {
			String code = txtCode.getText();
			if (!code.equals("")) {
				String reference = txtCategorie.getText();
				String designation = txtDesignation.getText();
				int quantite = (Integer) sliderQuantite.getValue();
				double prix = ((Number) txtPrixUnitaire.getValue())
						.doubleValue();
				boolean creation = 
							controleArticle.creer(code, reference,
													designation,
													quantite, prix);
				if (creation) {
					effacerSaisie();
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Le code article est obligatoire", "Avertissement",
						JOptionPane.WARNING_MESSAGE);
			}
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
		if (ajout) {
			actionAjouter.setEnabled(true);
			actionSupprimer.setEnabled(false);
			actionModifier.setEnabled(false);
			actionEffacer.setEnabled(true);
		} else {
			actionAjouter.setEnabled(true);
			actionSupprimer.setEnabled(true);
			actionModifier.setEnabled(true);
			actionEffacer.setEnabled(true);
		}
	}
}
