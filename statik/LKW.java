package informatik2.statik;

public class LKW extends Fahrzeug {

	private String firma;
	private String fahrer;
	private double achsAbstand;
	
	public LKW(String firma, String fahrer, double achsAbstand, double hoehe, double breite, double gesamtGewicht)
	{
		super(hoehe, breite, gesamtGewicht);		
		this.firma = firma;
		this.fahrer = fahrer;
		this.achsAbstand = achsAbstand;
	}
	
	public void setFirma(String firma){this.firma = firma;}
	public void setFahrer(String fahrer){this.fahrer = fahrer;}
	public void setAchsabstand(double achsAbstand){this.achsAbstand = achsAbstand;}
	
	public String getFirma(){return this.firma;}
	public String getFahrer(){return this.fahrer;}
	public double getAchsAbstand(){return this.achsAbstand;}
	
	public double achsLastVA()
	{
		return this.getGesamtGewicht()*(1.0/3.0)*Bruecke.NEWTON/1000;
	}
	
	public double achsLastHA()
	{		
		return this.getGesamtGewicht()*(2.0/3.0)*Bruecke.NEWTON/1000;
	}	
		
}
