package controle.modele;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entite.Article;
import entite.Ligne;

public class ModeleLignes extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    Ligne instanceLigneCommande = null;
    private final List<Ligne> lesDonnees;
    private final String[] lesTitres = {"Code", "Code catégorie", "Désignation", "Quantité", "Prix unitaire", "Total"};

    public ModeleLignes(){
    	lesDonnees = new ArrayList<Ligne>();
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
		Ligne uneLigne = lesDonnees.get(rowIndex);
		Article article = uneLigne.getArticle();
		if(columnIndex == 0) return article.getCode();
		if(columnIndex == 1) {
			return article.getCategorie().getCode();
		}
		if(columnIndex == 2) return article.getDesignation();
		if(columnIndex == 3) return uneLigne.getQuantite();
		if(columnIndex == 4) return uneLigne.getPrixUnitaire();
		else return uneLigne.getTotal();
    }

    public Class<?> getColumnClass(int columnIndex){
		switch(columnIndex){
			case 0:
			    return String.class;
			case 1:
			    return Character.class;
			case 2:
			    return String.class;
			case 3:
			    return Integer.class;  		
			case 4:
			    return Double.class;
			case 5:
			    return Double.class;
			default:
			    return Object.class;
		}
    }

	// -------------------------
    public void créée(Ligne uneLigne) {
    	// TODO merger les lignes par article
		if(lesDonnees.add(uneLigne)){
		    int ligne = lesDonnees.size() -1;
			fireTableRowsInserted(ligne, ligne);
		}
    }

    public void supprimée(int rowIndex) {
		lesDonnees.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
    }
    
    public void supprimées(){
		lesDonnees.clear();
		fireTableDataChanged();
    }
    
    public void modifiée(int numeroLigne, Ligne uneLigne) {
		lesDonnees.set(numeroLigne, uneLigne);
		fireTableRowsUpdated(numeroLigne, numeroLigne);
    }
    
    public void lu(List<Ligne> nouvellesDonnees){
    	lesDonnees.clear();
    	lesDonnees.addAll(nouvellesDonnees);
		fireTableDataChanged();	
    }

	public List<Ligne> getLignes() {
		return lesDonnees;
	}
}
