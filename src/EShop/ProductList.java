package EShop;

import java.util.ArrayList;
import java.util.List;

public class ProductList {

	private Link<Product>[][] buckets;
	private int anzProduct = 0;
	public static final String[] KATEGORYS = new String[] { "Konsolen", "Games", "Filme", "Musik", "Computer",
			"Tablets", "TV", "Kameras", "SmartPhones", "Audio" };




	@SuppressWarnings("unchecked")
	public ProductList() {
		buckets = (Link<Product>[][]) new Link[KATEGORYS.length][20];	
	}

	public boolean addProduct(String kategory, Product prod) {
		assert (prod != null);
		final int kategoryBucket = chooseKategory(kategory);
		final int productBucket = calculateProduct(kategory);
		buckets[kategoryBucket][productBucket] = new Link<Product>(prod, buckets[kategoryBucket][productBucket]);
		return true;
	}

	public boolean remove(String kategory, Product victim) {
		// Die Produkt-Referent app darf nicht null sein!
		// Dies kann im normalen Betrieb auch niemals vorkommen, daher wird mit assert()
		// geprüft.
		assert (victim != null);

		final int kategoryBucket = chooseKategory(kategory);
		final int productBucket = calculateProduct(kategory);

		Link<Product> zeiger = buckets[kategoryBucket][productBucket];

		/*
		 * Diese Variable dient dazu, uns das Vorgängerelement zu merken. Wir werden es
		 * später brauchen, wenn wir ein Element aus der Mitte der Liste entfernen.
		 * Zudem können wir es nutzen, um zu erkennen, ob das zu entfernende Element am
		 * Anfang der Liste steht - dann ist diese Variable noch mit dem Wert null
		 * belegt. Dies ist für die Sonderbehandlung des ersten Element notwendig, da es
		 * dann keinen Vorgänger gibt.
		 */
		Link<Product> vorgaenger = null;

		while (zeiger != null) {
			/*
			 * Prüfe, ob list.product unser gesuchter Produkt ist. Um den Code zum
			 * Vergleichen wiederverwenden zu können, haben wir ihn in eine eigene Methode
			 * ausgelagert.
			 *
			 * Normalerweise würde man diesen Test in der equals-Methode von Product
			 * implementieren. Wir haben hier darauf verzichtet, weil equals einige weitere
			 * Bedingungen stellt, deren Umsetzung für diese Lösung zu komplex werden würde.
			 */
			if (zeiger.product == victim) {
				// Sonderbehandlung für das erste Listenelement.
				if (vorgaenger == null) {
					// Das erste Element wird aus der Liste entfernt. Das geht besonders einfach.
					buckets[kategoryBucket][productBucket] = zeiger.next;
				} else {
					// Wir entfernen nun das Element aus der Liste durch Umbiegen des
					// Vorgänger-Zeigers.
					vorgaenger.next = zeiger.next;
				}

				// Fertig!
				return true;
			}

			// Wir merken uns das aktuelle Element nun als vorheriges Element.
			vorgaenger = zeiger;

			// Setze zeiger auf das nächste Element (ggf. auch null).
			zeiger = zeiger.next;
		}

		return false;
	}

	private int calculateProduct(String kategory) {
		return 10;
	}

	private int chooseKategory(String kategory) {

		int sorte = 0;
		for (int i = 0; i < KATEGORYS.length; i++) {
			if (kategory.equals(KATEGORYS[i])) {
				sorte = i;
			}
		}
		return sorte;
	}

	public int countProducts(String kategory) {
		final int kategoryBucket = chooseKategory(kategory);
		final int productBucket = calculateProduct(kategory);
		int count = 0;

		// Der Bucket wird vollständig iteriert, um die Elemente zu zählen
		Link<Product> zeiger = buckets[kategoryBucket][productBucket];

		while (zeiger != null) {
			count++;
			zeiger = zeiger.next;
		}

		return count;
	}

	public List<Product> getProducts(String kategory) {
		List<Product> list = new ArrayList<>(countProducts(kategory));

		final int kategoryBucket = chooseKategory(kategory);
		final int productBucket = calculateProduct(kategory);

		Link<Product> zeiger = buckets[kategoryBucket][productBucket];
		while (zeiger != null) {
			list.add(zeiger.product);
			zeiger = zeiger.next;
		}

		return list;
	}

	public int getAnzProduct() {
		return anzProduct;
	}
}
