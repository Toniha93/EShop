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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;






@SuppressWarnings("serial")
public class EShopWindow extends JFrame {
	private byte kategory;

	// Produkt-Datenbank
	protected final ProductList products;
	private java.util.List<Product> productsList;

	// Benutzer-Datenbank
	protected final BenutzerListe users;
	
	private Kunde kunde;

	// UI-Elemente
	private JButton btnPreviousKategory;
	private JButton btnNextKategory;
	private JLabel lblcurrentKategory;
	private JTable tblProducts;
	private JButton btnLogin;
	private JButton btnSelectKategory;
	private JButton btnLogout;

	// Button for Admins
	private JButton btnRemove;
	private JButton btnAdd;

	// Button for Kunde
	private JButton btnAddToWarenkorb;
	private JButton btnGoToWarenkorb;
	
	private WarenkorbList warenkorbList = new WarenkorbList();

	public EShopWindow(ProductList products, byte kategory, BenutzerListe users) {
		setTitle("EShop");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 1000);

		// Parameter speicherm
		assert (products != null);

		this.products = products;
		this.kategory = kategory;
		this.users = users;
		System.out.println(users.toString());

		// Kontrollelemente erzeugen und einfügen
		add(createControls(), BorderLayout.NORTH);
		add(createProductsTable(), BorderLayout.CENTER);
		add(createTasks(), BorderLayout.SOUTH);

		// Kontrollelemente aktualisieren
		updateViews();

		// Position und Größe aller Kontrollelemente neu berechnen
		pack();

		// Das Fenster soll sich in der Mitte des Bildschirms befinden
		setLocationRelativeTo(null);
	}

	protected void updateViews() {
		productsList = products.getProducts(ProductList.KATEGORYS[kategory]);
		// Tabelle neu zeichnen lassen
		((AbstractTableModel) tblProducts.getModel()).fireTableDataChanged();

		// Label-Text

		try {
			lblcurrentKategory.setText(ProductList.KATEGORYS[kategory]);
		} catch (ArrayIndexOutOfBoundsException a) {
			lblcurrentKategory.setText(ProductList.KATEGORYS[1]);
		}

	}

	// Kontrollelemente am oberen Fensterrand erzeugen
	private JComponent createControls() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);

		// Button zum vorherigen Produkt
		btnPreviousKategory = new JButton("<");
		btnPreviousKategory.setEnabled(false);
		btnPreviousKategory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				btnNextKategory.setEnabled(true);
				if (--kategory <= 0) {
					// kategory = 9;
					btnPreviousKategory.setEnabled(false);
				}
				updateViews();
			}
		});

		c.gridx = 0;
		c.gridy = 0;
		panel.add(btnPreviousKategory, c);

		// Label mit demaktuellen Produkt
		lblcurrentKategory = new JLabel();
		c.gridx = 1;
		panel.add(lblcurrentKategory, c);

		// Button zum nächsten Produkt
		btnNextKategory = new JButton(">");
		btnNextKategory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				btnPreviousKategory.setEnabled(true);
				if (++kategory >= 9) {
					btnNextKategory.setEnabled(false);
				}

				updateViews();

			}
		});
		c.gridx = 2;
		panel.add(btnNextKategory, c);

		// Button für den Auswahldialog
		btnSelectKategory = new JButton("Andere Kategorie...");
		btnSelectKategory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SelectKategoryPanel panel = new SelectKategoryPanel(kategory);

				if (JOptionPane.showConfirmDialog(EShopWindow.this, panel, "Andere Kategorie",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {

					try {
						kategory = panel.getKategoryNr();

						try {
							for (byte i = 0; i < ProductList.KATEGORYS.length; i++) {
								if (panel.getKategoryName().equals(ProductList.KATEGORYS[i])) {
									kategory = i;
								}
							}
						} catch (NullPointerException np) {

						}
						updateViews();
					} catch (IllegalStateException ex) {
						JOptionPane.showMessageDialog(EShopWindow.this, ex.getMessage(), "Ungültige Kategorie",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		c.gridx = 0;
		c.gridy = 1;
		panel.add(btnSelectKategory, c);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Login log = new Login();
				if (JOptionPane.showConfirmDialog(EShopWindow.this, log, "Login", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {

					String username = log.getUserName();
					String password = log.getPassword();
					System.out.println("User:" + username);
					System.out.println("Passwort:" + password);

					for (int i = 0; i < users.getAnzBenutzer(); i++) {
						int position;
						if (users.getBenutzer(i).getUsername().equals(username)) {
							position = i;
							if (users.getBenutzer(position).getPassword().equals(password)) {
								if (users.getBenutzer(position).isRechte()) {
									btnRemove.setVisible(true);
									btnAdd.setVisible(true);
									btnGoToWarenkorb.setVisible(false);
									btnAddToWarenkorb.setVisible(false);
								} else {
									kunde = (Kunde) users.getBenutzer(position);
									btnRemove.setVisible(false);
									btnAdd.setVisible(false);
									btnGoToWarenkorb.setVisible(true);
									btnAddToWarenkorb.setVisible(true);
								}
								JOptionPane.showMessageDialog(EShopWindow.this,"Login erfolgreich","Login erfolgreich",JOptionPane.INFORMATION_MESSAGE);
							} else {
								btnRemove.setVisible(false);
								btnAdd.setVisible(false);
								btnGoToWarenkorb.setVisible(false);
								btnAddToWarenkorb.setVisible(false);
								JOptionPane.showMessageDialog(EShopWindow.this,"Login nicht erfolgreich","Login nicht erfolgreich",JOptionPane.ERROR_MESSAGE);

							}
							if(users.getBenutzer(position).getUsername().equals(username) && users.getBenutzer(position).getPassword().equals(password))
								btnLogin.setVisible(false);
							btnLogout = new JButton("Logout");
							btnLogout.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent arg0) {
									btnLogin.setVisible(true);
									btnLogout.setVisible(false);
									btnAdd.setVisible(false);
									btnRemove.setVisible(false);
									btnAddToWarenkorb.setVisible(false);
									btnGoToWarenkorb.setVisible(false);
								}
							});
							c.gridx = 2;
							c.gridy = 1;
							panel.add(btnLogout, c);

						}
					}

				}
			}

		});
		c.gridx = 2;
		c.gridy = 1;
		panel.add(btnLogin, c);

		return panel;
	}

	private JComponent createTasks() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);

		// Button zum Löschen von Produkten
		btnRemove = new JButton("Produkt Löschen");
		btnRemove.setVisible(false);
		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Durch diese Schleife werden ggf. mehrere ausgewählte Produkte entfernt.
				for (int index : tblProducts.getSelectedRows())
					products.remove(ProductList.KATEGORYS[kategory], productsList.get(index));
				updateViews();
			}
		});

		c.gridx = 0;
		c.gridy = 0;
		panel.add(btnRemove, c);

		// Button Zum Warenkorb
		btnGoToWarenkorb = new JButton("Zum Warenkorb");
		btnGoToWarenkorb.setVisible(false);
		btnGoToWarenkorb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Deserialisierung
				File sfile2 = new File("warenkorb.ser");
				ObjectInputStream ois = null;
				FileInputStream fis = null;
				Product prod2 = null;
			
				try {
					fis = new FileInputStream(sfile2);
					ois = new ObjectInputStream(fis);
					prod2 = (Product)ois.readObject();
				}catch(IOException ex) {
					System.out.println("Fehler bei der Deserialisierung");
				}catch(ClassNotFoundException ex) {
					//TODO
				}finally {
					try {
						if(ois!=null)ois.close();
					}catch(IOException ex)
					{
						ex.printStackTrace();
					}
				}
				warenkorbList.add(prod2);
				Warenkorb korb = new Warenkorb(kunde,warenkorbList);
				korb.setVisible(true);
			}
		});

		c.gridx = 0;
		c.gridy = 0;
		panel.add(btnGoToWarenkorb, c);

		// Button zum Hinzufügen von Produkten
		btnAdd = new JButton("Produkt hinzufügen");

		btnAdd.setVisible(false);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					editProduct(null);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		c.gridx = 2;
		panel.add(btnAdd, c);

		// Button In den Warenkorb
		btnAddToWarenkorb = new JButton("In den Warenkorb hinzufügen");
		btnAddToWarenkorb.setVisible(false);
		btnAddToWarenkorb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final int index = tblProducts.getSelectedRow();
				if (index != -1) {
					Product prod;
					try {
						prod = new Product(null,new RandomAccessFile(
								new File("warenkorb.ser"), "rw"));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					prod = productsList.get(index);
					System.out.println("Test: "+productsList.get(index));
					//Serialsierung
					File sfile = new File("warenkorb.ser");
					ObjectOutputStream oos = null;
					FileOutputStream fos = null;
					
					try {
						fos = new FileOutputStream(sfile);
						oos = new ObjectOutputStream(fos);
						oos.writeObject(prod);
					}catch(IOException ex) {
						System.out.println("Fehler bei der Serialisierung");
					}finally {
						try {if(oos!=null)oos.close();
						}catch(IOException ex) {ex.printStackTrace();}
					}
					
					warenkorbList.add(prod);
				}
			}
		});
		c.gridx = 2;
		panel.add(btnAddToWarenkorb, c);
		return panel;
	}

	// Product editieren
	protected void editProduct(Product product) throws FileNotFoundException {
		Product productCopy = new Product(product, new RandomAccessFile(
				new File("product.ser"), "rw"));
		
	
				
		

		Verwaltung panel = new Verwaltung(productCopy);
		if (JOptionPane.showConfirmDialog(EShopWindow.this, panel, "Produkt bearbeiten", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
			try {
				panel.updateProduct();

			
				if (product != null)
					products.remove(ProductList.KATEGORYS[kategory], product);

				//Serialsierung
				File sfile = new File("product.ser");
				ObjectOutputStream oos = null;
				FileOutputStream fos = null;
				
				try {
					fos = new FileOutputStream(sfile);
					oos = new ObjectOutputStream(fos);
					oos.writeObject(productCopy);
				}catch(IOException e) {
					System.out.println("Fehler bei der Serialisierung");
				}finally {
					try {if(oos!=null)oos.close();
					}catch(IOException e) {e.printStackTrace();}
				}
				products.addProduct(ProductList.KATEGORYS[kategory], productCopy);

				updateViews();
			} catch (IllegalArgumentException | IllegalStateException e) {
				JOptionPane.showMessageDialog(EShopWindow.this, e.getMessage(), "Ungültiger Termin",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// Produkt-Tabelle erzeugen
	private JComponent createProductsTable() {
		tblProducts = new JTable(new KategoryViewTableModel());
		
		tblProducts.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent event)
			{
				// Doppelkltk!
				if (event.getClickCount() == 2)
				{
					final int index = tblProducts.getSelectedRow();
					if (index != -1)
						try {
							editProduct(productsList.get(index));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					//	System.out.println("Test: "+productsList.get(index));
				}
			}
		});

		// Relative Spaltenbreiten setzen, damit die erste Spalte schmaler ist
		final TableColumnModel tableColumnModel = tblProducts.getColumnModel();
		tableColumnModel.getColumn(0).setPreferredWidth(40);
		tableColumnModel.getColumn(1).setPreferredWidth(260);
		tableColumnModel.getColumn(2).setPreferredWidth(260);

		// Die erste Spalte soll rechtsbündig formatiert werden
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tableColumnModel.getColumn(0).setCellRenderer(rightRenderer);

		// Die JTable wird in eine JScrollPane verpackt, damit wir scrollen
		// können
		JScrollPane scrollPane = new JScrollPane(tblProducts);
		scrollPane.setPreferredSize(new Dimension(500, 525));

		return scrollPane;
	}

	private class KategoryViewTableModel extends AbstractTableModel {

		@Override
		public int getColumnCount() {

			return 3;
		}

		public String getColumnName(int column) {
			return (column == 0) ? "Nr." : (column == 1) ? "Produkt" : "Preis in €";
		}

		@Override
		public int getRowCount() {

			return 100;
		}

		@Override
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
