package controle.modele;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entite.Article;

public class ModeleArticles extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private final List<Article> lesDonnees;
    private final String[] lesTitres = {"Code", "Code Catégorie", "Désignation", "Quantité", "Prix unitaire"};

    public ModeleArticles(List<Article> lesArticles) {
    	lesDonnees = new ArrayList<>(lesArticles);
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
    	Article unArticle = getArticle(rowIndex);
		switch(columnIndex){
			case 0:
			    return unArticle.getCode();
			case 1:
			    return unArticle.getCategorie().getCode();
			case 2:
			    return unArticle.getDesignation();
			case 3:
			    return unArticle.getQuantite();
			case 4:
			    return unArticle.getPrixUnitaire();
			default:
			    return null;
		}
    }

    public void créé(Article unArticle) {
		lesDonnees.add(unArticle);
		int ligne = lesDonnees.size() -1;
		fireTableRowsInserted(ligne, ligne);
    }

    public void supprimé(int numeroLigne) {
		lesDonnees.remove(numeroLigne);
		fireTableRowsDeleted(numeroLigne, numeroLigne);
    }

    public void modifié(int numeroLigne, Article unArticle) {
		lesDonnees.set(numeroLigne, unArticle);
		fireTableRowsUpdated(numeroLigne, numeroLigne);
    }

    public void lu(List<Article> nouvellesDonnees){
    	lesDonnees.clear();
		lesDonnees.addAll(nouvellesDonnees);
		fireTableDataChanged();
    }

	public Article getArticle(int ligne) {
		return lesDonnees.get(ligne);
	}
}
