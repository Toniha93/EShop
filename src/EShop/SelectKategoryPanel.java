package EShop;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SelectKategoryPanel extends JPanel 
{
	private JTextField txtKategoryNr;
	private JTextField txtKategoryName;
	
	public SelectKategoryPanel(byte kategory)
	{
		super(new GridLayout(2,2));
		
		//Kontrolelemente erzeugen und einfügen
		add(new JLabel("KategorieNr."));
		add(txtKategoryNr= new JTextField());
		add(new JLabel("Kategorie:"));
		add(txtKategoryName= new JTextField(""));
	}
	
	public String getKategoryName()
	{
		assert(txtKategoryName !=null);
		try {
			for (byte i = 0; i < ProductList.KATEGORYS.length; i++) 
			{
				if (txtKategoryName.getText().equals(ProductList.KATEGORYS[i]))
				{
					return txtKategoryName.getText();
				}
			}
		}
		catch(Exception e)
		{
			return "Nicht gefunden";
		}
		return null;
	}
	
	public byte getKategoryNr()
	{
		try{
			return (byte)(Byte.parseByte(txtKategoryNr.getText())-1);
		}
		catch(NumberFormatException e)
		{
			return -1;
		}
	}
	
	
}
