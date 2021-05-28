package dialogue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;

public class FParametres extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtServeur;
	private JTextField txtBase;
	private JTextField txtUtilisateur;
	private JPasswordField pwdMotDePasse;
	private final Action actionAnnuler = new ActionAnnuler();
	private final Action actionValider = new ActionValider();

	/**
	 * Create the frame.
	 */
	public FParametres() {
		createContents();
	}

	private void createContents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(950, 400, 404, 454);

		setTitle("Paramètres");
		setIconImage(UI.getLogo());

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		panel.setLayout(gbl_panel);

		JLabel lblReseau = new JLabel("");
		lblReseau.setIcon(new ImageIcon(FParametres.class
				.getResource("/images/Data-Network-128.png")));
		GridBagConstraints gbc_lblReseau = new GridBagConstraints();
		gbc_lblReseau.insets = new Insets(0, 0, 5, 5);
		gbc_lblReseau.gridx = 0;
		gbc_lblReseau.gridy = 0;
		panel.add(lblReseau, gbc_lblReseau);
		UI.habiller(lblReseau);
		
		JLabel lblServeur = new JLabel("Serveur");
		GridBagConstraints gbc_lblServeur = new GridBagConstraints();
		gbc_lblServeur.insets = new Insets(0, 0, 5, 5);
		gbc_lblServeur.anchor = GridBagConstraints.EAST;
		gbc_lblServeur.gridx = 0;
		gbc_lblServeur.gridy = 1;
		panel.add(lblServeur, gbc_lblServeur);
		UI.habiller(lblServeur);

		txtServeur = new JTextField();
		txtServeur.setText("localhost");
		GridBagConstraints gbc_txtServeur = new GridBagConstraints();
		gbc_txtServeur.insets = new Insets(0, 0, 5, 0);
		gbc_txtServeur.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtServeur.gridx = 1;
		gbc_txtServeur.gridy = 1;
		panel.add(txtServeur, gbc_txtServeur);
		txtServeur.setColumns(10);
		UI.habiller(txtServeur);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.EAST;
		gbc_lblPort.insets = new Insets(0, 0, 20, 5);
		gbc_lblPort.gridx = 0;
		gbc_lblPort.gridy = 2;
		panel.add(lblPort, gbc_lblPort);
		UI.habiller(lblPort);
		
		JFormattedTextField frmtdtxtfldPort = new JFormattedTextField();
		frmtdtxtfldPort.setText("3306");
		GridBagConstraints gbc_frmtdtxtfldPort = new GridBagConstraints();
		gbc_frmtdtxtfldPort.insets = new Insets(0, 0, 20, 0);
		gbc_frmtdtxtfldPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_frmtdtxtfldPort.gridx = 1;
		gbc_frmtdtxtfldPort.gridy = 2;
		panel.add(frmtdtxtfldPort, gbc_frmtdtxtfldPort);
		UI.habiller(frmtdtxtfldPort);

		JLabel lblBase = new JLabel("Base");
		GridBagConstraints gbc_lblBase = new GridBagConstraints();
		gbc_lblBase.anchor = GridBagConstraints.EAST;
		gbc_lblBase.insets = new Insets(0, 0, 5, 5);
		gbc_lblBase.gridx = 0;
		gbc_lblBase.gridy = 3;
		panel.add(lblBase, gbc_lblBase);
		UI.habiller(lblBase);

		txtBase = new JTextField();
		txtBase.setText("gestion");
		GridBagConstraints gbc_txtBase = new GridBagConstraints();
		gbc_txtBase.insets = new Insets(0, 0, 5, 0);
		gbc_txtBase.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBase.gridx = 1;
		gbc_txtBase.gridy = 3;
		panel.add(txtBase, gbc_txtBase);
		txtBase.setColumns(10);
		UI.habiller(txtBase);

		JLabel lblUtilisateur = new JLabel("Utilisateur");
		GridBagConstraints gbc_lblUtilisateur = new GridBagConstraints();
		gbc_lblUtilisateur.anchor = GridBagConstraints.EAST;
		gbc_lblUtilisateur.insets = new Insets(0, 0, 5, 5);
		gbc_lblUtilisateur.gridx = 0;
		gbc_lblUtilisateur.gridy = 4;
		panel.add(lblUtilisateur, gbc_lblUtilisateur);
		UI.habiller(lblUtilisateur);

		txtUtilisateur = new JTextField();
		txtUtilisateur.setText("eni");
		GridBagConstraints gbc_txtUtilisateur = new GridBagConstraints();
		gbc_txtUtilisateur.insets = new Insets(0, 0, 5, 0);
		gbc_txtUtilisateur.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUtilisateur.gridx = 1;
		gbc_txtUtilisateur.gridy = 4;
		panel.add(txtUtilisateur, gbc_txtUtilisateur);
		txtUtilisateur.setColumns(10);
		UI.habiller(txtUtilisateur);

		JLabel lblMotdepasse = new JLabel("Mot De Passe");
		GridBagConstraints gbc_lblMotdepasse = new GridBagConstraints();
		gbc_lblMotdepasse.anchor = GridBagConstraints.EAST;
		gbc_lblMotdepasse.insets = new Insets(0, 0, 5, 5);
		gbc_lblMotdepasse.gridx = 0;
		gbc_lblMotdepasse.gridy = 5;
		panel.add(lblMotdepasse, gbc_lblMotdepasse);
		UI.habiller(lblMotdepasse);

		pwdMotDePasse = new JPasswordField();
		pwdMotDePasse.setText("Mot");
		GridBagConstraints gbc_pwdMotDePasse = new GridBagConstraints();
		gbc_pwdMotDePasse.insets = new Insets(0, 0, 5, 0);
		gbc_pwdMotDePasse.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwdMotDePasse.gridx = 1;
		gbc_pwdMotDePasse.gridy = 5;
		panel.add(pwdMotDePasse, gbc_pwdMotDePasse);
		UI.habiller(pwdMotDePasse);

		JPanel panelActions = new JPanel();
		panelActions.setBorder(new EmptyBorder(0, 10, 5, 10));
		getContentPane().add(panelActions, BorderLayout.SOUTH);
		GridBagLayout gbl_panelActions = new GridBagLayout();
		gbl_panelActions.columnWidths = new int[] { 0, 0 };
		gbl_panelActions.rowHeights = new int[] { 0 };
		gbl_panelActions.columnWeights = new double[] { 1.0, 1.0 };
		gbl_panelActions.rowWeights = new double[] { 0.0 };
		panelActions.setLayout(gbl_panelActions);

		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.setAction(actionAnnuler);
		btnAnnuler.setIcon(new ImageIcon(FParametres.class.getResource("/images/gestion/Cancel-48-actif.png")));
		GridBagConstraints gbc_btnAnnuler = new GridBagConstraints();
		gbc_btnAnnuler.weightx = 1.0;
		gbc_btnAnnuler.anchor = GridBagConstraints.LINE_START;
		gbc_btnAnnuler.insets = new Insets(0, 0, 0, 5);
		gbc_btnAnnuler.gridx = 0;
		gbc_btnAnnuler.gridy = 0;
		panelActions.add(btnAnnuler, gbc_btnAnnuler);
		UI.deshabillerBouton(btnAnnuler, "Cancel");
		btnAnnuler.setForeground(Color.BLACK);

		JButton btnValider = new JButton("Valider");
		btnValider.setAction(actionValider);
		btnValider.setIcon(new ImageIcon(FParametres.class.getResource("/images/gestion/Save-48-actif.png")));
		GridBagConstraints gbc_btnValider = new GridBagConstraints();
		gbc_btnValider.weightx = 1.0;
		gbc_btnValider.anchor = GridBagConstraints.LINE_END;
		gbc_btnValider.gridx = 1;
		gbc_btnValider.gridy = 0;
		panelActions.add(btnValider, gbc_btnValider);
		UI.deshabillerBouton(btnValider, "Save");
		btnValider.setForeground(Color.BLACK);
	}
	private class ActionAnnuler extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionAnnuler() {
			putValue(NAME, "Annuler");
		}
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	private class ActionValider extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionValider() {
			putValue(NAME, "Valider");
		}
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}
