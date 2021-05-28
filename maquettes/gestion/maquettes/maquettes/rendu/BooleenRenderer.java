package maquettes.rendu;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class BooleenRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	private final Icon carte;

	public BooleenRenderer() {
		super();
		carte = new ImageIcon(getClass()
				.getResource("/images/gestion/Pending-Invoice-32.png"));  
	}

	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		Boolean carteFidelite = (Boolean)value;
		setText("");
		if(carteFidelite){
			setIcon(carte);
		} else {
			setIcon(null);
		}
		setHorizontalAlignment(CENTER);
		return this;
	}
}
