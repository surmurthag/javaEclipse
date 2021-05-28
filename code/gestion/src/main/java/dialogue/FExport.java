package dialogue;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controle.etat.JasperFacade;
import controle.etat.FormatExport;
import dialogue.rendu.ExportRenderer;

public class FExport extends JDialog {
	private static final long serialVersionUID = 1L;
	private JList<FormatExport> type_export;
	private JPanel contentPane = null;
	private String modeleJasper = null;
	private JButton btnExport;
	private JLabel lblInfo;
	private Collection<?> elements;
	private JButton btnAnnuler;
	private String type;

	/**
	 * Create the dialog.
	 */
	public FExport(Window parent, String type, String modele, Collection<?> elements) {		
		super(parent);
		this.type = type;
		modeleJasper = modele;
		this.elements = elements;
		setSize(490, 498);
		setContentPane(createContentPane());
	}

	private JPanel createContentPane() {
		if (contentPane == null) {
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			GridBagLayout gbl_jContentPane = new GridBagLayout();
			gbl_jContentPane.columnWidths = new int[] {0, 0};
			gbl_jContentPane.rowHeights = new int[] {0, 0, 0};
			gbl_jContentPane.columnWeights = new double[]{0.0, 0.0};
			gbl_jContentPane.rowWeights = new double[]{0.0, 0.0, 0.0};
			contentPane.setLayout(gbl_jContentPane);
			GridBagConstraints gbc_jLabel1 = new GridBagConstraints();
			gbc_jLabel1.fill = GridBagConstraints.BOTH;
			gbc_jLabel1.insets = new Insets(5, 0, 25, 0);
			gbc_jLabel1.gridwidth = 2;
			gbc_jLabel1.gridx = 0;
			gbc_jLabel1.gridy = 0;
			contentPane.add(getJLabel1(), gbc_jLabel1);
			GridBagConstraints gbc_type_export = new GridBagConstraints();
			gbc_type_export.gridwidth = 2;
			gbc_type_export.insets = new Insets(0, 0, 20, 5);
			gbc_type_export.gridx = 0;
			gbc_type_export.gridy = 1;
			contentPane.add(getType_export(), gbc_type_export);
			GridBagConstraints gbc_btnAnnuler = new GridBagConstraints();
			gbc_btnAnnuler.weightx = 1.0;
			gbc_btnAnnuler.fill = GridBagConstraints.BOTH;
			gbc_btnAnnuler.insets = new Insets(0, 0, 0, 5);
			gbc_btnAnnuler.gridx = 0;
			gbc_btnAnnuler.gridy = 2;
			contentPane.add(getBtnAnnuler(), gbc_btnAnnuler);
			GridBagConstraints gbc_export = new GridBagConstraints();
			gbc_export.weightx = 1.0;
			gbc_export.fill = GridBagConstraints.BOTH;
			gbc_export.gridx = 1;
			gbc_export.gridy = 2;
			contentPane.add(getBtnExport(), gbc_export);
		}
		return contentPane;
	}
	
	private JList<FormatExport> getType_export() {
		if(type_export == null) {
			ExportRenderer renderer = new ExportRenderer();
			DefaultListModel<FormatExport> model = new DefaultListModel<FormatExport>();
			for( FormatExport type : FormatExport.values()) {
				model.addElement(type);
			}
			type_export = new JList<FormatExport>();
			type_export.setModel( model);
			type_export.setCellRenderer(renderer);
			type_export.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			type_export.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		}
		return type_export;
	}
	
	private JLabel getJLabel1() {
		if(lblInfo == null) {
			lblInfo = new JLabel();
			lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
			lblInfo.setText("Choisissez dans la liste le type de document à exporter.");
			UI.habiller(lblInfo);
		}
		return lblInfo;
	}
	
	private JButton getBtnExport() {
		if(btnExport == null) {
			btnExport = new JButton();
			btnExport.setIcon(new ImageIcon(FExport.class.getResource("/images/gestion/Data-Export-48.png")));
			btnExport.setText("Exporter");
			UI.deshabillerBouton(btnExport, "Data-Export");
			btnExport.setForeground(Color.BLACK);
			
			btnExport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					FormatExport typeExport = type_export.getSelectedValue();
					JasperFacade.export(typeExport, type, modeleJasper, elements);
					FExport.this.dispose();
				}				
			});
		}
		return btnExport;
	}	
	
	private JButton getBtnAnnuler() {
		if (btnAnnuler == null) {
			btnAnnuler = new JButton("Annuler");
			UI.deshabillerBouton(btnAnnuler, "Cancel");
			btnAnnuler.setForeground(Color.BLACK);
			btnAnnuler.addActionListener((ActionEvent e) -> dispose());
		}
		return btnAnnuler;
	}
}
