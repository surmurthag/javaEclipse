package priseEnMain.graphique;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PremiereFenetre extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PremiereFenetre frame = new PremiereFenetre();
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
	public PremiereFenetre() {
		createContents();
	}
	private void createContents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PremiereFenetre.class.getResource("/priseEnMain/graphique/Moon-32.png")));
		setTitle("1\u00E8re fen\u00EAtre");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSaisissezUnMessage = new JLabel("Saisissez un message");
		lblSaisissezUnMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaisissezUnMessage.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 21));
		lblSaisissezUnMessage.setForeground(Color.RED);
		lblSaisissezUnMessage.setBounds(15, 16, 398, 36);
		contentPane.add(lblSaisissezUnMessage);
		
		textField = new JTextField();
		textField.setBounds(15, 68, 398, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Action!");
			}
		});
		textField.setText("");
		btnValider.addActionListener(
			(ActionEvent e) -> {
				System.out.println("Action!");
			}
		);
		btnValider.addActionListener(
				e -> {
					System.out.println("Action!");
				}
			);
		btnValider.addActionListener(
				e -> System.out.println("Action!")
		);
//		btnValider.addActionListener(PremiereFenetre::action);
//		btnValider.addActionListener(this::action);
		btnValider.setBounds(160, 186, 115, 42);
		contentPane.add(btnValider);
	}
	private void action(ActionEvent e) {
		
	}
}

