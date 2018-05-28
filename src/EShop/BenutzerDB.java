package EShop;

public class BenutzerDB {
	
	private BenutzerListe users = new BenutzerListe();
	
	public BenutzerListe createUser()
	{
		@SuppressWarnings("unused")
		boolean rechteBenutzer = false;
		boolean rechteKunde = false;
		boolean rechteAdmin = true;
		
		//Admin
		Administrator admin1 = new Administrator(rechteAdmin,1,"Toni", "Kroos", "Toni", "Real");
		Administrator admin2 = new Administrator(rechteAdmin,1,"Thomas", "Müller", "Thomas", "Bayern");
		
		//Kunde
		Kunde k1 = new Kunde(rechteKunde,"Robert","Lewandowski","Robert","Bayern",1,"23.11.1989",34000000.00);
		Kunde k2 = new Kunde(rechteKunde,"Cristiano","Ronaldo","Cristiano","Real",1,"5.2.1985",94000000.00);
		Kunde k3 = new Kunde(rechteKunde,"Armer","Mann","Pleite","arm",1,"5.2.1985",10.00);
		
		users.addBenutzer(admin1);
		users.addBenutzer(admin2);
		users.addBenutzer(k1);
		users.addBenutzer(k2);
		users.addBenutzer(k3);
		return users;
	}
	
}
