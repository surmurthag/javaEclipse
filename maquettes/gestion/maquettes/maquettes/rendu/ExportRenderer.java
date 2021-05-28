package maquettes.rendu;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

import controle.etat.FormatExport;

public class ExportRenderer extends DefaultListCellRenderer {	
	private static final long serialVersionUID = 1L;

	public ExportRenderer() {
		super();
	}

	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		FormatExport type = (FormatExport)value;
		String txt = type.getDescription();
		ImageIcon icon = type.getIcone();
				
		setIcon(icon);
		setText(txt);
		
		return this;
	}
}