package controle.modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entite.Client;
import entite.Commande;

public class ModeleCommandes extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private static final Comparator<? super Commande> COMPARATEUR	= Comparator
			// les commandes sont comparées par rapport à leur date
			.comparing(Commande::getInstant)
			// les plus récentes sont les premières à être affichées
			.reversed();

	private final List<Commande> lesDonnees;
	private final String[] lesTitres = {"Code", "Client", "Mode de paiement", "Total TTC", "Date"};

	public ModeleCommandes(List<Commande> lesCommandes){
		lesDonnees = new ArrayList<>(lesCommandes);
		Collections.sort(lesDonnees, COMPARATEUR);
	}
	
	public int getRowCount() {
		return lesDonnees.size();
	}
	
	public int getColumnCount() {
		return lesTitres.length;
	}
	
	public String getColumnName(int columnIndex) {
		return lesTitres[columnIndex];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Commande uneCommande = lesDonnees.get(rowIndex);
		if(columnIndex == 0) return uneCommande.getCode();
		if(columnIndex == 1) {
			Client client = uneCommande.getClient();
			return client.getNom() + " " + client.getPrenom();
		}
		if(columnIndex == 2) return uneCommande.getReglement().getType();
		if(columnIndex == 3) return uneCommande.getTotalTTC();
		else return uneCommande.getInstant();
	}

	public Class<?> getColumnClass(int columnIndex){
		Class<?> classe = Object.class;
		switch(columnIndex){
		case 0:
		case 1:
		case 2:
			classe = String.class;
			break;
		case 3:
			classe = Double.class;
			break;
		default:
		}
		return classe;
	}

	public void créé(Commande uneCommande) {
		if(lesDonnees.add(uneCommande)){
			int ligne = lesDonnees.size() -1;
			fireTableRowsInserted(ligne, ligne);
		}
	}
	
	public void supprimé(int rowIndex) {
		lesDonnees.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public void modifié(int numeroLigne, Commande uneCommande) {
		lesDonnees.set(numeroLigne, uneCommande);
		fireTableRowsUpdated(numeroLigne, numeroLigne);
	}
	
	public void lu(List<Commande> lesCommandes){
		lesDonnees.clear();
		lesDonnees.addAll(lesCommandes);
		Collections.sort(lesDonnees, COMPARATEUR);
		fireTableDataChanged();	
	}
	
	public List<Commande> getCommandes() {
		return Collections.unmodifiableList(lesDonnees);
	}
	
	public Commande getCommande(int numeroDeLigne) {
		return lesDonnees.get(numeroDeLigne);
	}
}
