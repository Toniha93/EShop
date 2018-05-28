package EShop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Verwaltung extends JPanel {
	// Produkt
	private Product product;

	// UI-Elemente
	private JTextField txtName;
	private JTextField txtPreis;
	private JTextField txtAnzahl;
	
	public Verwaltung(Product product) {
		
		//Parameter speichern
		assert(product!=null);
		this.product = product;
		
		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(4, 4, 4, 4);
		
		add(new JLabel("Produktname:"),c);
		
		c.gridx = 1;
		add(txtName = new JTextField(15), c);
		txtName.setText(product.getProductName());
		
		c.gridx = 0;
		c.gridy = 1;
		add(new JLabel("Preis:"), c);
		
		c.gridx = 1;
		add(txtPreis=new JTextField(), c);
		txtPreis.setText(String.valueOf(product.getPreis()));
		
		c.gridx = 0;
		c.gridy = 2;
		add(new JLabel("Anzahl:"), c);

		c.gridx = 1;
		add(txtAnzahl=new JTextField(), c);
		txtAnzahl.setText(String.valueOf(product.getAnzahl()));
	}
	
	public Product updateProduct()
	{
		product.setProductName(txtName.getText());
		double preis = Double.parseDouble(txtPreis.getText());
		product.setPreis(preis);
		int anzahl = Integer.parseInt(txtAnzahl.getText());
		product.setAnzahl(anzahl);
		return product;
	}
}
