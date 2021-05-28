package dialogue;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Boite extends JFrame {

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
					Boite frame = new Boite();
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
	public Boite() {
		createContents();
	}
	private void createContents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel, BorderLayout.NORTH);
		
		JButton btnBouton = new JButton("Bouton 1");
		panel.add(btnBouton);
		
		JButton btnBouton_1 = new JButton("Bouton long");
		panel.add(btnBouton_1);
		
		JButton btnBouton_2 = new JButton("Bouton 3");
		panel.add(btnBouton_2);
		
		JButton btnBoutonLong = new JButton("Bouton long");
		panel.add(btnBoutonLong);
		
		JButton btnBouton_3 = new JButton("Bouton 5");
		panel.add(btnBouton_3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(5, 5, 5, 5)));
		contentPane.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JButton btnBouton_4 = new JButton("Bouton 6");
		panel_1.add(btnBouton_4);
		
		JButton btnBoutonLong_1 = new JButton("Bouton long");
		panel_1.add(btnBoutonLong_1);
		
		JButton btnBouton_5 = new JButton("Bouton 8");
		panel_1.add(btnBouton_5);
	}

}
