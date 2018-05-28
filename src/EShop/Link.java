package EShop;

public class Link<T extends Product>
{
	public T product;
	public Link<T> next;

	public Link(T prod, Link<T> next)
	{
		assert(prod!=null);

		this.product = prod;
		this.next = next;
	}
}