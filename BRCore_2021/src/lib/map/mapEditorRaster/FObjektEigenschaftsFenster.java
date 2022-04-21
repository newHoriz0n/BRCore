package lib.map.mapEditorRaster;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import lib.map.MapObjekt;

public class FObjektEigenschaftsFenster extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MapObjekt aktObjekt;

//	private JTable eigenschaften;

	private PMapEditorPanel parent;

	public FObjektEigenschaftsFenster(PMapEditorPanel parent) {
//		this.eigenschaften = new JTable();
		this.parent = parent;
	}

	public void setAktObjekt(MapObjekt o) {
		this.aktObjekt = o;

		createGUI();

	}

	private void createGUI() {

		// OBJEKTTYP

		// TABELLE

		Object rows[][] = new Object[aktObjekt.getEigenschaften().keySet().size() + 1][2];

		rows[0] = new String[] { "Typ", aktObjekt.getTyp() };

		int i = 1;
		for (String s : aktObjekt.getEigenschaften().keySet()) {

			rows[i] = new String[] { s, aktObjekt.getEigenschaften().get(s) };
			i++;
		}

		Object columns[] = { "Eigenschaft", "Wert" };

		@SuppressWarnings("serial")
		JTable table = new JTable(rows, columns) {
			public boolean isCellEditable(int row, int column) {
				if (column == 0 || row == 0) {
					return false;
				}
				return true;
			};

		};

		table.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("tableCellEditor".equals(evt.getPropertyName())) {

					for (int i = 1; i < table.getRowCount(); i++) {
						aktObjekt.setEigenschaft((String) table.getValueAt(i, 0), (String) table.getValueAt(i, 1));
					}

				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);

		getContentPane().add(scrollPane, "Center");

		setVisible(true);
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				parent.closeObjektEigenschaftsFenster();
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

}
