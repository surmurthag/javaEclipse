package dialogue;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GridBag extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GridBag frame = new GridBag();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GridBag() {
		createContents();
	}
	private void createContents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JButton btnBouton = new JButton("Bouton");
		GridBagConstraints gbc_btnBouton = new GridBagConstraints();
		gbc_btnBouton.gridheight = 2;
		gbc_btnBouton.insets = new Insets(0, 0, 5, 5);
		gbc_btnBouton.gridx = 0;
		gbc_btnBouton.gridy = 0;
		contentPane.add(btnBouton, gbc_btnBouton);
		
		JButton btnBouton_1 = new JButton("Bouton 2");
		GridBagConstraints gbc_btnBouton_1 = new GridBagConstraints();
		gbc_btnBouton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnBouton_1.gridx = 1;
		gbc_btnBouton_1.gridy = 0;
		contentPane.add(btnBouton_1, gbc_btnBouton_1);
		
		JButton btnBouton_3 = new JButton("Bouton 3");
		GridBagConstraints gbc_btnBouton_3 = new GridBagConstraints();
		gbc_btnBouton_3.insets = new Insets(0, 0, 5, 0);
		gbc_btnBouton_3.gridx = 2;
		gbc_btnBouton_3.gridy = 0;
		contentPane.add(btnBouton_3, gbc_btnBouton_3);
		
		JButton btnBouton_4 = new JButton("Bouton 5");
		GridBagConstraints gbc_btnBouton_4 = new GridBagConstraints();
		gbc_btnBouton_4.insets = new Insets(0, 0, 5, 0);
		gbc_btnBouton_4.gridwidth = 2;
		gbc_btnBouton_4.gridx = 1;
		gbc_btnBouton_4.gridy = 1;
		contentPane.add(btnBouton_4, gbc_btnBouton_4);
		
		JButton btnBouton_2 = new JButton("Bouton 4");
		GridBagConstraints gbc_btnBouton_2 = new GridBagConstraints();
		gbc_btnBouton_2.fill = GridBagConstraints.VERTICAL;
		gbc_btnBouton_2.gridheight = 2;
		gbc_btnBouton_2.insets = new Insets(0, 0, 0, 5);
		gbc_btnBouton_2.gridx = 0;
		gbc_btnBouton_2.gridy = 2;
		contentPane.add(btnBouton_2, gbc_btnBouton_2);
		
		JButton btnBouton_5 = new JButton("Bouton 6");
		GridBagConstraints gbc_btnBouton_5 = new GridBagConstraints();
		gbc_btnBouton_5.fill = GridBagConstraints.BOTH;
		gbc_btnBouton_5.gridwidth = 2;
		gbc_btnBouton_5.insets = new Insets(0, 0, 5, 0);
		gbc_btnBouton_5.gridx = 1;
		gbc_btnBouton_5.gridy = 2;
		contentPane.add(btnBouton_5, gbc_btnBouton_5);
		
		JButton btnBouton_6 = new JButton("Bouton 7");
		GridBagConstraints gbc_btnBouton_6 = new GridBagConstraints();
		gbc_btnBouton_6.anchor = GridBagConstraints.EAST;
		gbc_btnBouton_6.gridwidth = 2;
		gbc_btnBouton_6.gridx = 1;
		gbc_btnBouton_6.gridy = 3;
		contentPane.add(btnBouton_6, gbc_btnBouton_6);
	}

}
