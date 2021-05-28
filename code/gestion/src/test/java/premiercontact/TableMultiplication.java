package premiercontact;
import javax.swing.JOptionPane;


public class TableMultiplication {

	public static void main(String[] args) {
		int tab [][] = new int[10][2];
		
		String message = "Table de multiplication du nombre";
		
		Integer saisie = Integer.valueOf(JOptionPane.showInputDialog(message));
		
		int nombre = saisie.intValue();
		
		for (int colonne = 0; colonne < 2; ++colonne) {
			for (int ligne = 0; ligne < tab.length; ++ligne) {
				if (colonne == 0) {
					tab[ligne][colonne] = ligne + 1;
				} else {
					tab[ligne][colonne] = nombre * (ligne + 1);					
				}
			}
		}
		
		System.out.println("Ta");
	}
}
