package controle.modele;

/*
 *  Classe comportant le modèle de données des Clients.
 *  Doit étendre la classe abstraite AbstractTableModel
 */

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entite.Client;

public class ModeleClients extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	// représente les lignes du modèle
	private final List<Client> lesDonnees;

	// les entêtes de colonnes
	private static final String[] TITRES = {"Code", "Nom", "Prénom", "Carte Fidélité", "Date Création"};

	public ModeleClients(List<Client> lesClients) {
		lesDonnees = new ArrayList<>(lesClients);
	}
	
	public int getRowCount() {
		return lesDonnees.size();
	}

	public int getColumnCount() {
		return TITRES.length;
	}
	
	public String getColumnName(int columnIndex) {
		return TITRES[columnIndex];
	}

	// pour accéder à la valeur d'une cellule
	public Object getValueAt(int rowIndex, int columnIndex) {
		Client client = getClient(rowIndex);
		switch(columnIndex){
		case 0:
			return client.getCode();
		case 1:
			return client.getNom();
		case 2:
			return client.getPrenom();
		case 3:
			return client.isCarteFidele();
		case 4:
			return client.getDateCreation();
		default:
			return null;
		}
	}
	
	public Client getClient(int numeroLigne) {
		return lesDonnees.get(numeroLigne);
	}

	/*
	 * utiles pour les renderers par défaut qui vont appliquer
	 * un style de présentation des données en fonction de la classe
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex){
		Class<?> classe = null;
		switch(columnIndex){
		case 3:
			classe = Boolean.class;
			break;
		case 4:
			classe = Instant.class;
			break;
		default:
			classe = super.getColumnClass(columnIndex);
			break;
		}
		return classe;
	}

	// pour obtenir le numéro de ligne à partir du code
	// lors d'une recherche dans la liste
	private int getNumLigne(String unCode) {
		int numLigne = -1;

		for (int idx = 0; idx < lesDonnees.size(); ++idx) {
			String code = lesDonnees.get(idx).getCode();
			if(code.equals(unCode)) {
				numLigne = idx;
				break;
			}
		}    		
		return numLigne;    	
	}    

	// -------------------------
	public void créé(Client leClient) {
		lesDonnees.add(leClient);
		int index = lesDonnees.size() -1;
		fireTableRowsInserted(index, index);
	}

	public void supprimé(int indexLigne) {
		lesDonnees.remove(indexLigne);
		// notification de la suppression de la ligne indexLigne à la ligne indexLigne
		fireTableRowsDeleted(indexLigne, indexLigne);
	}

	public void modifié(Client leClient) {
		int numeroLigne = getNumLigne(leClient.getCode());
		if (numeroLigne >= 0) {			
			lesDonnees.set(numeroLigne, leClient);
			fireTableRowsUpdated(numeroLigne, numeroLigne);
		}
	}

	/* permet de mettre à jour le modèle suite à de nouvelles recherches
	 * et d'informer les vues affichant ce modèle
	 */
	public void lu(List<Client> nouvellesDonnees){
		lesDonnees.clear();
		lesDonnees.addAll(nouvellesDonnees);
		fireTableDataChanged();
	}
}
