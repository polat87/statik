package informatik2.statik;

public class Bruecke {
	private double laenge;
	private double Fahrbahndeckenlast;
	private double momente_GleichLast[];
	private Traeger traeger;
	static final double DECKENLAST = 3.0; 
	static final double NEWTON = 9.81; //9.80665002864
	private static final double STEP_LEN = 0.1;
	private double momVA[][];
	private double momHA[][];
	private double momSum[][];
	double maxSigma = 0;
	double maxMomentPos = 0;
	double maxVAPos = 0;
	public static final double SIGMA_D = 300.0;
	
	public Bruecke(double fahrbahndeckenlast, double laenge, Traeger traeger)
	{
		this.laenge = laenge;
		this.Fahrbahndeckenlast = fahrbahndeckenlast;
		this.traeger = traeger;
	}
	
	public double getMaxVAPos()
	{
		return maxVAPos;
	}
	
	public double getMaxSigma()
	{
		return maxSigma;
	}
	
	public double getMaxMomentPos()
	{
		return maxMomentPos;
	}
	
	public double getFahrbahndeckenlast()
	{
		return this.Fahrbahndeckenlast;
	}
	
	public double getLaenge()
	{
		return this.laenge;
	}
	
	public Traeger getTraeger()
	{
		return this.traeger;
	}
	
	public double getGesamtLast()
	{
		double last = ((this.traeger.getMasse() * NEWTON) / this.traeger.getLaenge()) / 1000;
		return (last + DECKENLAST)*1.5;		
	}

	public void calcMomenteGleichlast()
	{
		double xi, xi_;
		double l = this.laenge;
		double q = this.getGesamtLast();
		double x = 0;
		int schritte = (int)(l / STEP_LEN);
		this.momente_GleichLast = new double[schritte+1];
		
		for(int i= 0; i < schritte; i++ )
		{
			xi = x / l;
			xi_ = (l-x) / l;
			this.momente_GleichLast[i] = ((xi*xi_)/2)*q*l*l;
			x+= STEP_LEN;
		}

	}

	public double[] calcAchsenbelastung(double pos, double achslast)
	{
		double l = this.laenge;
		int steps = (int) (l / STEP_LEN) + 1;
		double[] momente = new double[steps];
		
		if ( pos > 0  &&  pos <= l )
		{
			double x = 0;			
			for(int m=0; m < steps-1; m++)
			{		
				if( x > pos)
					momente[m] = ((l - x) / l)*pos*achslast;
				else
					momente[m] = x / l * (l-pos) * achslast;
				
				x+=STEP_LEN;
			}
		}
		
		return momente;
	}	
	
	public double getMaxMoment() {
		double max = 0.0;		
		for(int i=0; i < momSum.length; i++)
			for(int j=0; j < momSum[i].length; j++)
			{
				if(momSum[i][j] > max){
					max = momSum[i][j];
					maxMomentPos = j* STEP_LEN;
					maxVAPos = i * STEP_LEN;
				}
			}		
		
		return max;
	}	
	
	
	public void calcBiegeSpannung() {
		double maxMoment = getMaxMoment();
		maxSigma = ((maxMoment*100) / this.traeger.getQuerschnitt().getIy()) * (this.traeger.getQuerschnitt().getH() / 2);
		maxSigma = maxSigma * 1000; // --> kN/cm2
		maxSigma = maxSigma / 100; // --> N / mm2
	}
	
	public void calcMomenteLKW(LKW lkw)
	{
		int pos = (int)((lkw.getAchsAbstand() + this.laenge) / STEP_LEN) +1;
		int measurePoints = 1 + (int)(this.laenge / STEP_LEN);
		double currentPos = 0;
		this.momVA = new double[pos][];
		this.momHA = new double[pos][];
		this.momSum = new double[pos][measurePoints];
		
		for ( int p = 0; p < pos; p++ ) {
			this.momVA[p] = calcAchsenbelastung(currentPos, lkw.achsLastVA());
			this.momHA[p] = calcAchsenbelastung(currentPos - lkw.getAchsAbstand(), lkw.achsLastHA());
			
			for(int m=0; m < measurePoints; m++)
			{
				this.momSum[p][m] = momente_GleichLast[m] + momVA[p][m] + momHA[p][m];				
			}
	
			currentPos+= STEP_LEN;
		}		
				
	}
	
}
