package EShop;

public class Kunde extends Benutzer {

	private int kundenNr;
	private String vorname;
	private String nachname;
	private String userName;
	private String password;
	private String gebdat;
	private double kontostand;
	
	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}


	@Override
	public String getUsername() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGebdat() {
		return gebdat;
	}

	public void setGebdat(String gebdat) {
		this.gebdat = gebdat;
	}

	public double getKontostand() {
		return kontostand;
	}

	public void setKontostand(double kontostand) {
		this.kontostand = kontostand;
	}

	public Kunde(boolean rechte, String vorname, String nachname, String userName, String password,int kundenNr,
			String gebdat, double kontostand) {
		super(rechte);
		
		this.vorname = vorname;
		this.nachname = nachname;
		this.userName = userName;
		this.password = password;
		this.kundenNr = kundenNr;
		this.gebdat = gebdat;
		this.kontostand = kontostand;
	}

	@Override
	public String toString() {
		return "Kunde [kundenNr=" + kundenNr + ", vorname=" + vorname + ", nachname=" + nachname + ", userName="
				+ userName + ", password=" + password + ", gebdat=" + gebdat + ", kontostand=" + kontostand + "]";
	}

	public int getKundenNr() {
		return kundenNr;
	}

	public void setKundenNr(int kundenNr) {
		this.kundenNr = kundenNr;
	}


}
