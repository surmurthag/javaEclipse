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
				System.out.println("ligne indépendante");				
			}
		};
		Thread fil = new Thread(run, "parallèle");
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
		
		// désactiver le bouton pour éviter les impressions multiples
		bouton.setEnabled(false);
		SwingWorker<?,?> travailleur = new SwingWorker<List<Object>, Integer>() {

			@Override
			protected List<Object> doInBackground() throws Exception {
				// dans le thread d'arrière-plan
				// ... opération longue ...
				// ... opération très longue ...
				// une page a été imprimée, alerter le thread graphique
				publish(Integer.valueOf(1));
				// ... opération longue ...
				
				return new ArrayList<Object>();
			}

			@Override
			protected void done() {
				// de retour dans le thread graphique
				
				try {
					// récupérer la liste des pages imprimées
					List<Object> list = get();
					// afficher quelque chose avec
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					Throwable cause = e.getCause();
					e.printStackTrace();
				}
				// réactiver le bouton
				bouton.setEnabled(true);
			}
			@Override
			protected void process(List<Integer> chunks) {
				// dans le thread graphique
				// mettre à jour une barre de progression
				super.process(chunks);
			}
		};
		travailleur.execute();		
	}

}