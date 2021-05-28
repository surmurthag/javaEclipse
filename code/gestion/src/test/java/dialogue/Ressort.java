package dialogue;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

public class Ressort extends JFrame {

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
					Ressort frame = new Ressort();
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
	public Ressort() {
		createContents();
	}
	private void createContents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		int rows = 10;
		int cols = 10;
		for (int r = 0; r < rows; r++) {
		    for (int c = 0; c < cols; c++) {
		        int anInt = (int) Math.pow(r, c);
		        JButton textField =
		                new JButton(Integer.toString(anInt));
		        contentPane.add(textField);
		    }
		}

		//Lay out the panel.
		SpringUtilities.makeCompactGrid(contentPane, //parent
		                                rows, cols,
		                                3, 3,  //initX, initY
		                                3, 3); //xPad, yPad
	}

}
