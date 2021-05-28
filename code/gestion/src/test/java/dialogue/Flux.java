package dialogue;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Flux extends JFrame {

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
					Flux frame = new Flux();
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
	public Flux() {
		createContents();
	}
	private void createContents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnBouton = new JButton("Bouton 1");
		contentPane.add(btnBouton);
		
		JButton btnBouton_1 = new JButton("Bouton 2 long texte");
		contentPane.add(btnBouton_1);
		
		JButton btnBouton_2 = new JButton("Bouton 3");
		contentPane.add(btnBouton_2);
		
		JButton btnBouton_3 = new JButton("Bouton 4");
		contentPane.add(btnBouton_3);
		
		JButton btnBouton_4 = new JButton("Bouton 5");
		contentPane.add(btnBouton_4);
	}

}
