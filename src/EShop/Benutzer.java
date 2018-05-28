package EShop;

public abstract class Benutzer 
{
	private boolean rechte;

	public Benutzer(boolean rechte) {
		this.rechte = rechte;
	}

	public boolean isRechte() {
		return rechte;
	}
	
	public abstract String getUsername();

	public void setRechte(boolean rechte) {
		this.rechte = rechte;
	}
	
	public abstract String getPassword();
}
