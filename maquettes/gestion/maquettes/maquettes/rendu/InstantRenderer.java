package maquettes.rendu;

import java.awt.Component;
import java.time.Instant;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import controle.utilitaires.GestionDates;

public class InstantRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object
		value, boolean isSelected,  boolean hasFocus,  int row,   int column) {
		
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		Instant instant = (Instant) value;
		String texte = GestionDates.dateEnChaineFR(instant);

		this.setText(texte);
	    this.setHorizontalAlignment(CENTER);
	    return this;
	}
}
