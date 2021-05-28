package maquettes.commande;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import maquettes.UI;
import controle.connection.Connexion;
import entite.Article;

public class ChoixArticle extends JDialog {
	private static final long serialVersionUID = 1L;
    private JLabel lab_recherche;
    private JTextField rechercher;
	private JLabel lblVeuillezChoisirDans;
    private JScrollPane jScrollPane = null;
    private JTable jTable = null;
    
	private Article article;

    public ChoixArticle(Window parent, Connexion connexion) {
    	super(parent);
    	initialize();
    }
    
    private void initialize() {
    	JPanel panel = new JPanel();
    	panel.setBorder(new EmptyBorder(5, 5, 5, 5));
    	setContentPane(panel);
    	
		panel.setBackground(new Color(0x66, 0x99, 0x00));
		setBounds(100, 100, 698, 468);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 2.0, 0.0};
		panel.setLayout(gridBagLayout);
		GridBagConstraints gbc_lblVeuillezChoisirDans = new GridBagConstraints();
		gbc_lblVeuillezChoisirDans.fill = GridBagConstraints.BOTH;
		gbc_lblVeuillezChoisirDans.insets = new Insets(0, 0, 5, 0);
		gbc_lblVeuillezChoisirDans.gridwidth = 2;
		gbc_lblVeuillezChoisirDans.gridx = 0;
		gbc_lblVeuillezChoisirDans.gridy = 0;
		panel.add(getLblVeuillezChoisirDans(), gbc_lblVeuillezChoisirDans);
		GridBagConstraints gbc_jScrollPane = new GridBagConstraints();
		gbc_jScrollPane.fill = GridBagConstraints.BOTH;
		gbc_jScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_jScrollPane.gridwidth = 2;
		gbc_jScrollPane.gridx = 0;
		gbc_jScrollPane.gridy = 1;
		panel.add(getJScrollPane(), gbc_jScrollPane);
		GridBagConstraints gbc_lab_recherche = new GridBagConstraints();
		gbc_lab_recherche.anchor = GridBagConstraints.EAST;
		gbc_lab_recherche.fill = GridBagConstraints.VERTICAL;
		gbc_lab_recherche.insets = new Insets(0, 0, 5, 5);
		gbc_lab_recherche.gridx = 0;
		gbc_lab_recherche.gridy = 2;
		panel.add(getLab_recherche(), gbc_lab_recherche);
		GridBagConstraints gbc_rechercher = new GridBagConstraints();
		gbc_rechercher.insets = new Insets(0, 0, 5, 0);
		gbc_rechercher.anchor = GridBagConstraints.NORTH;
		gbc_rechercher.fill = GridBagConstraints.BOTH;
		gbc_rechercher.gridx = 1;
		gbc_rechercher.gridy = 2;
		panel.add(getRechercher(), gbc_rechercher);
		setIconImage(UI.getLogo());
    }

    private JLabel getLblVeuillezChoisirDans() {
		if (lblVeuillezChoisirDans == null) {
		    lblVeuillezChoisirDans = new JLabel("Double-cliquez sur la ligne de l'article.");
		    lblVeuillezChoisirDans.setForeground(Color.WHITE);
		}
		return lblVeuillezChoisirDans;
    }
    
    private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
		    jScrollPane = new JScrollPane();
		    jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
    }
    
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}
    
	Article getArticle() {
		return article;
	}
    
    private JLabel getLab_recherche() {
		if (lab_recherche == null) {
		    lab_recherche = new JLabel("Rechercher");
		    lab_recherche.setForeground(Color.WHITE);
		    lab_recherche.setIcon(new ImageIcon(ChoixArticle.class.getResource("/images/gestion/Search-48.png")));
		}
		return lab_recherche;
    }
    
    private JTextField getRechercher() {
		if (rechercher == null) {
		    rechercher = new JTextField();
		    rechercher.setColumns(10);
		}
		return rechercher;
    }
}
