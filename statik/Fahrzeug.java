package informatik2.statik;

public class Fahrzeug {
	
	private double hoehe;
	private double breite;
	private double gesamtGewicht;
	
	public Fahrzeug(double hoehe, double breite, double gesamtGewicht)
	{
		this.hoehe = hoehe;
		this.breite = breite;
		this.gesamtGewicht = gesamtGewicht;
	}
	
	public Fahrzeug(){}
	
	public void setHoehe(double hoehe){this.hoehe = hoehe;}
	public void setBreite(double breite){this.breite = breite;}
	public void setGesamtGewicht(double gesamtGewicht){this.gesamtGewicht = gesamtGewicht;}
	
	public double getHoehe(){return this.hoehe;};	
	public double getBreite(){return this.breite;};	
	public double getGesamtGewicht(){return this.gesamtGewicht;};	
	

}
