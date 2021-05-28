package priseEnMain.premier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.SwingWorker;

public class Sequentiel {
	public static void main(String[] args) {
		System.out.println("1ere ligne");
		System.out.println("2eme ligne");
		System.out.println("3eme ligne");
		
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("ligne ind�pendante");				
			}
		};
		Thread fil = new Thread(run, "parall�le");
		fil.start();
		try {
			fil.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
//		ExecutorService executor = Executors.newSingleThreadExecutor();
//		ExecutorService executor = Executors.newCachedThreadPool();
		ExecutorService executor = Executors.newScheduledThreadPool(4);
		executor.execute(run);
		executor.shutdown();
	}

	private void impression(JButton bouton) {
		// dans le thread graphique
		// initialiser une barre de progression
		
		// d�sactiver le bouton pour �viter les impressions multiples
		bouton.setEnabled(false);
		SwingWorker<?,?> travailleur = new SwingWorker<List<Object>, Integer>() {

			@Override
			protected List<Object> doInBackground() throws Exception {
				// dans le thread d'arri�re-plan
				// ... op�ration longue ...
				// ... op�ration tr�s longue ...
				// une page a �t� imprim�e, alerter le thread graphique
				publish(Integer.valueOf(1));
				// ... op�ration longue ...
				
				return new ArrayList<Object>();
			}

			@Override
			protected void done() {
				// de retour dans le thread graphique
				
				try {
					// r�cup�rer la liste des pages imprim�es
					List<Object> list = get();
					// afficher quelque chose avec
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					Throwable cause = e.getCause();
					e.printStackTrace();
				}
				// r�activer le bouton
				bouton.setEnabled(true);
			}
			@Override
			protected void process(List<Integer> chunks) {
				// dans le thread graphique
				// mettre � jour une barre de progression
				super.process(chunks);
			}
		};
		travailleur.execute();		
	}

}