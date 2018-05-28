package EShop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class Warenkorb extends JFrame {

	private JTable tblWarenkorb;

	private JButton btnRemove;
	private JButton btnGoToKasse;
	
	private JLabel lblSumme;
	private double  summe;
	
	
	private WarenkorbList warenkorbList;
	private java.util.List<Product> productsList;
	
	private Kunde kunde;

	public Warenkorb(Kunde kunde,WarenkorbList warenkorbList)
	{
		setVisible(true);
		setTitle("Warenkorb");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		
		this.kunde = kunde;
		this.warenkorbList= warenkorbList;
		System.out.println("Kunde:" +kunde);
		
		
		// add(createControlPanel(), BorderLayout.NORTH);
		add(createWarenkorbTable(), BorderLayout.CENTER);
		add(createButtonsPanel(), BorderLayout.SOUTH);

		// Kontrollelemente aktualisieren
		updateViews();

		// Position und Größe aller Kontrollelemente neu berechnen
		pack();

		// Das Fenster soll sich in der Mitte des Elternfensters öffnen
		setLocationRelativeTo(null);
	}

	protected void updateViews() {
		lblSumme.updateUI();
		productsList = warenkorbList.getProducts();
		System.out.println("Waren:"+productsList);
		//productsList = warenkorbList.getProduct(1);
		// Tabelle neu zeichnen lassen
		((AbstractTableModel) tblWarenkorb.getModel()).fireTableDataChanged();
	}

	private JComponent createButtonsPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);

		btnRemove = new JButton("Aus Warenkorb entfernen");
		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Durch diese Schleife werden ggf. mehrere ausgewählte Produkte entfernt.
				for (int index : tblWarenkorb.getSelectedRows())
					warenkorbList.remove(productsList.get(index));
				
				//Serialsierung
				File sfile = new File("warenkorb.ser");
				ObjectOutputStream oos = null;
				FileOutputStream fos = null;
				Product productRemove = null;
				try {
					productRemove = new Product(null, new RandomAccessFile(
							new File("warenkorb.ser"), "rw"));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					fos = new FileOutputStream(sfile);
					oos = new ObjectOutputStream(fos);
					oos.writeObject(productRemove);
				}catch(IOException ex) {
					System.out.println("Fehler bei der Serialisierung");
				}finally {
					try {if(oos!=null)oos.close();
					}catch(IOException ex) {ex.printStackTrace();}
				}
				warenkorbList.add(productRemove);

				updateViews();
				summe = 0;
				for(int i=0;i<warenkorbList.countProducts();i++)
				{
					summe += warenkorbList.getProducts().get(i).getPreis();
				}
				DecimalFormat df = new DecimalFormat("0.00");
				lblSumme.setText("Summe: "+df.format(summe)); 
			}
		});
		c.gridx = 0;
		c.gridy = 0;
		panel.add(btnRemove, c);

		btnGoToKasse = new JButton("Zur Kasse");
		btnGoToKasse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Kasse kasse = new Kasse(kunde,summe);
				kasse.setVisible(true);
			}
		});

		c.gridx = 1;
		c.gridy = 0;
		panel.add(btnGoToKasse, c);
		for(int i=0;i<warenkorbList.countProducts();i++)
		{
			summe += warenkorbList.getProducts().get(i).getPreis();
		}
		lblSumme= new JLabel();
		DecimalFormat df = new DecimalFormat("0.00");
		lblSumme.setText("Summe: "+df.format(summe));
		c.gridx = 2;
		c.gridy = 0;
		panel.add(lblSumme, c);
		return panel;
	}

	private JComponent createWarenkorbTable() {

		(tblWarenkorb = new JTable(new WarenkorbModel())).addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				// Doppelkltk!
				if (event.getClickCount() == 2) {

				}
			}
		});

		// Der "Löschen"-Button soll nur aktiv sein, wenn auch wirklich ein Termin
		// ausgewählt ist.
		tblWarenkorb.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				// btnRemove.setEnabled(tblAppointments.getSelectedRow() != -1);
			}
		});

		// Relative Spaltenbreiten setzen, damit die ersten Spalten schmaler sind
		final TableColumnModel tableColumnModel = tblWarenkorb.getColumnModel();
		tableColumnModel.getColumn(0).setPreferredWidth(40);
		tableColumnModel.getColumn(1).setPreferredWidth(260);
		tableColumnModel.getColumn(2).setPreferredWidth(260);

		// Die erste und zweite Spalte sollen rechtsbündig formatiert werden.
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tableColumnModel.getColumn(0).setCellRenderer(rightRenderer);
		tableColumnModel.getColumn(1).setCellRenderer(rightRenderer);

		// Die JTable wird in eine JScrollPane verpackt, damit wir scrollen können.
		JScrollPane scrollPane = new JScrollPane(tblWarenkorb);
		scrollPane.setPreferredSize(new Dimension(450, 220));

		return scrollPane;
	}


	private class WarenkorbModel extends AbstractTableModel {
		

		public WarenkorbModel() {
		}

		public int getColumnCount() {
			return 3;
		}

		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "#";

			case 1:
				return "Produkt";

			case 2:
				return "Preis in €";
			}

			return "?";
		}

		public int getRowCount() {
			return 100;
		}

		public Object getValueAt(int row, int column) {

			Product product;
			if (column == 0)
				return row + 1;
			try {
				product = productsList.get(row);
				System.out.println("produkt:" + product);
			} catch (IndexOutOfBoundsException e) {
				return "";
			}
			DecimalFormat df = new DecimalFormat("0.00");
			return (column == 1) ? product.getProductName() : df.format(product.getPreis());
		}
	}
}