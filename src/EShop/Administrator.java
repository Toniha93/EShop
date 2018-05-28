package EShop;

public class Administrator extends Benutzer {

	private int adminNr;
	private String vorname;
	private String nachname;
	private String username;
	private String password;

	public Administrator(boolean rechte, int adminNr, String vorname, String nachname, String username,
			String password) {
		super(rechte);
		this.adminNr = adminNr;
		this.vorname = vorname;
		this.nachname = nachname;
		this.username = username;
		this.password = password;
	}

	public String getVorname() {
		return vorname;
	}

	
	@Override
	public String toString() {
		return "Administrator [adminNr=" + adminNr + ", vorname=" + vorname + ", nachname=" + nachname + ", username="
				+ username + ", password=" + password + "]";
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAdminNr() {
		return adminNr;
	}

	public void setAdminNr(int adminNr) {
		this.adminNr = adminNr;
	}
}
