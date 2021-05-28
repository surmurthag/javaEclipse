package depart;

import java.awt.EventQueue;

import dialogue.FConnexion;

/**
 * Point de départ de l'application.
 */
public class Main implements Runnable {

	public static void main(String[] args) {

		System.out.println("Lancement du programme Java-Eclipse");

		EventQueue.invokeLater(new Main());
	}

	@Override
	public void run() {
		FConnexion frame = new FConnexion();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		System.out.println("Fenêtre de Connexion visible");
	}
}
