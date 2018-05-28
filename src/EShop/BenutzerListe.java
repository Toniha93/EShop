package EShop;


public class BenutzerListe {
	
	private Benutzer[] dieBenutzer;
	private  int anzBenutzer = 0;
	
	public int getAnzBenutzer() {
		return anzBenutzer;
	}

	public BenutzerListe()
	{
		dieBenutzer = new Benutzer[100];
	}
	
	public boolean addBenutzer(Benutzer user)
	{
		boolean eingefuegt;
		
		if(anzBenutzer < dieBenutzer.length)
		{
			dieBenutzer[anzBenutzer] = user;
			anzBenutzer++;
			eingefuegt = true;
		}
		else
		{
			eingefuegt = false;
		}
		return eingefuegt;
	}
	
	public Benutzer getBenutzer(int pos)
	{
		Benutzer erg;
		if(pos >=0 && pos < anzBenutzer)
		{
			erg = dieBenutzer[pos];
		}
		else {
			erg = null;
		}
		return erg;
	}

	@Override
	public String toString() {
		String users = "";
		for(int i=0;i<anzBenutzer;i++)
		{
			users +=dieBenutzer[i]+"\n";
		}
		return users;
	}
}
