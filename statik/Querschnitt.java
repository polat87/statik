package informatik2.statik;

public class Querschnitt {

	private double b;
	private double h;
	private double s;
	private double t;
	
	public Querschnitt(double b, double h, double s, double t)
	{
		this.b = b;
		this.h = h;
		this.s = s;
		this.t = t;		
	}
	
	public double getB(){return this.b;}
	public double getH(){return this.h;}
	public double getS(){return this.s;}
	public double getT(){return this.t;}
	
	public double getFlaeche()
	{	
		return b*h - (b-s) * (h-2*t);	
	}
	
	public double getIy()
	{
		return   ((1.0/12.0) * (b*Math.pow(h,3) - (b-s)*Math.pow(h-2*t,3)));		

	}

}
