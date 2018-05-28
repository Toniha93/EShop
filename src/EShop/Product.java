package EShop;

import java.io.RandomAccessFile;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Product implements Serializable
{
	private String productName;
	private double preis;
	private int anzahl;

	@SuppressWarnings("unused")
	private transient RandomAccessFile raf;
	
	public Product(Product p,RandomAccessFile raf)
	{
		if(p!=null)
		{
			//Einen Klon eines anderen Produkt-Objekts erstellen
			this.productName = p.productName;
			this.anzahl = p.anzahl;
			this.raf = raf;
		}
	}
	
	public Product(String productName,double preis,int anzahl)
	{
		this.productName = productName;
		this.preis = preis;
		this.anzahl = anzahl;
	}

	public String getProductName() {
		return productName;
	}

	@Override
	public String toString() {
		return "Product [productName=" + productName + ", anzahl=" + anzahl + "]";
	}

	public int getAnzahl() {
		return anzahl;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}
}
