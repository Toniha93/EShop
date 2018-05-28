package EShop;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class EShop {

	public static void main(String[] args) {
		
		final byte kategory = 0;
		// Produkt-Datenbank erzeugen
		ProductList products = new ProductList();
		final ProductDB productDB = new ProductDB();
		products = productDB.showProducts();
		
		//Benutzer-Datenbank erzeugen
		BenutzerListe users = new BenutzerListe();
		BenutzerDB userDB = new BenutzerDB();
		users = userDB.createUser();
			
		// "Nimbus"-Skin laden, damit wir keinen Augenkrebs bekommen...
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}
		// Hauptfenster erzeugen und anzeigen
		new EShopWindow(products, kategory,users).setVisible(true);
	}
}
