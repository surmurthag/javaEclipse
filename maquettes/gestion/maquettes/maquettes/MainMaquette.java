package maquettes;

import java.awt.EventQueue;

/**
 * Point de départ de l'application.
 */
public class MainMaquette implements Runnable {

	public static void main(String[] args) {

		System.out.println("Lancement du programme");

		EventQueue.invokeLater(new MainMaquette());
	}

	@Override
	public void run() {
		FConnexion frame = new FConnexion();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		System.out.println("Fenêtre de Connexion visible");
	}
}
