package dialogue;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Bordure extends JFrame {

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
					Bordure frame = new Bordure();
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
	public Bordure() {
		createContents();
	}
	private void createContents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnNord = new JButton("Nord");
		contentPane.add(btnNord, BorderLayout.NORTH);
		
		JButton btnOuest = new JButton("Ouest");
		contentPane.add(btnOuest, BorderLayout.WEST);
		
		JButton btnEst = new JButton("Est");
		contentPane.add(btnEst, BorderLayout.EAST);
		
		JButton btnSud = new JButton("Sud");
		contentPane.add(btnSud, BorderLayout.SOUTH);
		
		JButton btnCentre = new JButton("Centre");
		contentPane.add(btnCentre, BorderLayout.CENTER);
	}

}
