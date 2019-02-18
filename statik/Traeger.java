package informatik2.statik;

public class Traeger {

	private double laenge;
	private double dichte;
	private Querschnitt querschnitt;
		
	public Traeger(double laenge, double dichte, Querschnitt querschnitt)
	{
		this.laenge = laenge;
		this.dichte = dichte;
		this.querschnitt = querschnitt;
	}
	
	public double getLaenge(){return this.laenge;}
	public double getDichte(){return this.dichte;}
	public Querschnitt getQuerschnitt(){return this.querschnitt;}
	
	public double getMasse()
	{
		return (this.querschnitt.getFlaeche() / 100)*this.laenge*10*this.dichte;
	}

}
