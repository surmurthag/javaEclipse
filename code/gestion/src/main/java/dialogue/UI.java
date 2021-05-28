package dialogue;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

public class UI {

	private static final String LOGO = "/images/Inventory-32.png";

	public static Image getLogo() {
		return Toolkit.getDefaultToolkit().getImage(UI.class.getResource(LOGO));
	}

	private static ImageIcon getIcone(String chemin) {
		return new ImageIcon(UI.class.getResource(chemin));
	}

	private static void deshabillerBouton(JButton bouton) {
		bouton.setOpaque(false);
		bouton.setContentAreaFilled(false);
		bouton.setBorderPainted(false);
	}

	public static void deshabillerBouton(JButton bouton, String icone) {
		deshabillerBouton(bouton, "gestion", icone);
		bouton.setForeground(Color.WHITE);
	}

	public static void deshabillerBouton(JButton bouton, String dossier,
			String icone) {
		deshabillerBouton(bouton, dossier, icone, 48);
	}

	public static void deshabillerBouton(JButton bouton, String dossier,
			String icone, int size) {
		deshabillerBouton(bouton);
		String nom = "/images/" + dossier + "/" + icone + "-" + size;
		bouton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				bouton.setIcon(getIcone(nom + "-actif.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				bouton.setIcon(getIcone(nom + ".png"));
			}
		});
		bouton.setHorizontalAlignment(SwingConstants.LEFT);
		bouton.setIcon(getIcone(nom +".png"));
		bouton.setFont(bouton.getFont().deriveFont(14f));
	}

	public static void habiller(JComponent composant) {
		composant.setFont(composant.getFont().deriveFont(17f));
	}
}
