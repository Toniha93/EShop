package EShop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ProductDB {
	
	//Produkt-Datenbank erzeugen
	final ProductList products = new ProductList();
	
	public ProductDB()
	{

	}

	public ProductList showProducts()
	{
		//Beispiel-Produkt eintragen
		//Serialisierung funktioniert leider nicht gut deswegen null,null
		Product prod1 = new Product(null, null);
		prod1.setProductName("Playstation 4 Pro");
		prod1.setPreis(399.99);
		prod1.setAnzahl(100);
		
		Product prod2 = new Product(null, null);
		prod2.setProductName("XBox One");
		prod2.setPreis(349.99);
		prod2.setAnzahl(100);
		
		Product prod3 = new Product(null, null);
		prod3.setProductName("Horizon");
		prod3.setPreis(39.99);
		prod3.setAnzahl(100);
		
		Product prod4 = new Product(null, null);
		prod4.setProductName("Fifa 18");
		prod4.setPreis(49.99);
		prod4.setAnzahl(100);
		
		Product prod5 = new Product(null, null);
		prod5.setProductName("JBG 3");
		prod5.setPreis(10.99);
		prod5.setAnzahl(100);
		
		Product prod6 = new Product(null, null);
		prod6.setProductName("Wolf of Wall Street");
		prod6.setPreis(12.99);
		prod6.setAnzahl(100);
		
		Product prod7 = new Product(null, null);
		prod7.setProductName("Samsung S8 Galaxy");
		prod7.setPreis(499.99);
		prod7.setAnzahl(40);
		
		Product prod8 = new Product(null, null);
		prod8.setProductName("iPhone 8");
		prod8.setPreis(799.99);
		prod8.setAnzahl(40);
		
		Product prod9 = new Product(null, null);
		prod9.setProductName("Samsung S9 Galaxy");
		prod9.setPreis(899.99);
		prod9.setAnzahl(30);
		
		Product prod10 = new Product(null, null);
		prod10.setProductName("iPhone X");
		prod10.setPreis(1099.99);
		prod10.setAnzahl(50);
		
		Product prod11= new Product(null, null);
		prod11.setProductName("Macbook Pro");
		prod11.setPreis(1599.99);
		prod11.setAnzahl(50);
		
		Product prod12 = new Product(null, null);
		prod12.setProductName("Surface Pro");
		prod12.setPreis(1299.99);
		prod12.setAnzahl(50);
		
		Product prod13 = new Product(null, null);
		prod13.setProductName("Surface Laptop");
		prod13.setPreis(1099.99);
		prod13.setAnzahl(50);
		
		Product prod14 = new Product(null, null);
		prod14.setProductName("Sony 4K TV 49 Zoll");
		prod14.setPreis(1099.99);
		prod14.setAnzahl(50);
		
		Product prod15 = new Product(null, null);
		prod15.setProductName("Samsung 4K TV 55 Zoll");
		prod15.setPreis(1099.99);
		prod15.setAnzahl(50);
		
		Product prod16 = new Product(null, null);
		prod16.setProductName("ipad Pro");
		prod16.setPreis(799.99);
		prod16.setAnzahl(20);
		
		Product prod17 = new Product(null, null);
		prod17.setProductName("Samsung Galaxy Tab S3");
		prod17.setPreis(699.99);
		prod17.setAnzahl(20);
		
		Product prod18 = new Product(null, null);
		prod18.setProductName("Canon EOS 750D");
		prod18.setPreis(799.99);
		prod18.setAnzahl(20);
		
		Product prod19 = new Product(null, null);
		prod19.setProductName("Bose Kopfhörer");
		prod19.setPreis(299.99);
		prod19.setAnzahl(20);
		
		Product prod20 = new Product(null, null);
		prod20.setProductName("Beats by Dr. Dre Solo 3");
		prod20.setPreis(299.99);
		prod20.setAnzahl(20);
		
		//Deserialisierung
		File sfile2 = new File("product.ser");
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		Product save = null;
		try {
			fis = new FileInputStream(sfile2);
			ois = new ObjectInputStream(fis);
			save = (Product)ois.readObject();
		}catch(IOException e) {
			System.out.println("Fehler bei der Deserialisierung");
		}catch(ClassNotFoundException e) {
			//TODO
		}finally {
			try {
				if(ois!=null)ois.close();
			}catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("Save: "+save);
		products.addProduct("Konsolen",prod1);
		products.addProduct("Konsolen",prod2);
		products.addProduct("Games",prod3);
		products.addProduct("Games",prod4);
		products.addProduct("Musik", prod5);
		products.addProduct("Filme", prod6);
		products.addProduct("SmartPhones", prod7);
		products.addProduct("SmartPhones", prod8);
		products.addProduct("SmartPhones", prod9);
		products.addProduct("SmartPhones", prod10);
		products.addProduct("Computer", prod11);
		products.addProduct("Computer", prod12);
		products.addProduct("Computer", prod13);
		products.addProduct("TV", prod14);
		products.addProduct("TV", prod15);
		products.addProduct("Tablets", prod16);
		products.addProduct("Tablets", prod17);
		products.addProduct("Kameras", prod18);
		products.addProduct("Audio", prod19);
		products.addProduct("Audio", prod20);
		products.addProduct("Konsolen",save);
		return products;
	}
}
