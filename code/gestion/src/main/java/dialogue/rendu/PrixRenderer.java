package dialogue.rendu;

import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import dialogue.Messages;

public class PrixRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object
		value, boolean isSelected,  boolean hasFocus,  int row,   int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		DecimalFormat format = new DecimalFormat(
				Messages.getString("FenCommande.72")); //$NON-NLS-1$

		String texte = format.format(value);

		this.setText(texte);
	    this.setHorizontalAlignment(CENTER);
	    return this;
	}
}
