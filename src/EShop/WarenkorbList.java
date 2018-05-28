package EShop;

import java.util.ArrayList;
import java.util.List;

public class WarenkorbList {

	private Link<Product>[] buckets;

	@SuppressWarnings("unchecked")
	public WarenkorbList() {
		buckets = (Link<Product>[]) new Link[100];
	}

	private int calculateProduct() {
		return 10;
	}

	public Link<Product> getProduct(int pos) {
		Link<Product> erg;
		if (pos >= 0 && pos < countProducts())
			erg = buckets[pos];
		else
			erg = null;
		return erg;
	}

	public int countProducts() {
		final int productBucket = calculateProduct();
		int count = 0;

		// Der Bucket wird vollständig iteriert, um die Elemente zu zählen
		Link<Product> zeiger = buckets[productBucket];

		while (zeiger != null) {
			count++;
			zeiger = zeiger.next;
		}
		return count;
	}

	public List<Product> getProducts() {
		List<Product> list = new ArrayList<>(countProducts());
		final int productBucket = calculateProduct();
		Link<Product> zeiger = buckets[productBucket];
		while (zeiger != null) {
			list.add(zeiger.product);
			zeiger = zeiger.next;
		}
		return list;
	}

	public boolean add(Product ap) {
		final int productBucket = calculateProduct();
		buckets[productBucket] = new Link<Product>(ap, buckets[productBucket]);
		return true;
	}
	
	public boolean remove(Product victim)
	{
		final int productBucket = calculateProduct();

		Link<Product> zeiger = buckets[productBucket];

		Link<Product> vorgaenger = null;

		while (zeiger != null)
		{
			if (zeiger.product == victim)
			{
				// Sonderbehandlung für das erste Listenelement.
				if (vorgaenger == null)
				{
					// Das erste Element wird aus der Liste entfernt. Das geht besonders einfach.
					buckets[productBucket]= zeiger.next;
				}
				else
				{
					// Wir entfernen nun das Element aus der Liste durch Umbiegen des Vorgänger-Zeigers.
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
}
