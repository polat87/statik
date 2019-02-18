package informatik2.statikdemo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

import informatik2.statik.*;

public class StatikApp {
	
	public static Scanner scanner = new Scanner(System.in);
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
	
	public static double readInput(String message)
	{
		double value = 0;
		String input;
		boolean ok = false;
		
		System.out.print(message);

		while(!ok)
		{			
			try
			{
				input = scanner.nextLine();
				value = Double.parseDouble(input);
				ok = true;
			}catch(NumberFormatException format)
			{
				System.out.print("Ungültige Eingabe!\n");
				System.out.print(message);
			}
		}

		return value;		
	}
	
	
	public static boolean parseDoubles(double arr[], String str)
	{
		String strings[] =  str.split(" ", 4);
		
		if(strings.length != 4)
			return false;

		for(int i=0; i < strings.length; i++)
		{
			try{
			arr[i] = Double.parseDouble(strings[i]);
			}catch(IllegalArgumentException ex)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public static double[] readString(String message)
	{
		double arr[] = new double[4];
		String input;
		boolean ok = false;
		
		System.out.print(message);

		while(!ok)
		{
			input = scanner.nextLine();				
			ok = parseDoubles(arr, input);	
			
			if(!ok)
			{
				System.out.print("Ungültige Eingabe!\n");
				System.out.print(message);
			}
		}
							
		return arr;		
	}
	
	
	public static void simuliereUeberfahrt(Bruecke bruecke, LKW lkw)
	{
		bruecke.calcMomenteGleichlast();
		bruecke.calcMomenteLKW(lkw);
		bruecke.calcBiegeSpannung();
		
		System.out.format("MaxMomente= %.2fkNm ", bruecke.getMaxMoment());
		System.out.format("bei Brückenposition= %.2fm", bruecke.getMaxMomentPos());
		System.out.format("(LKW-VA Position=%.2fm)\n", bruecke.getMaxVAPos());
		
		System.out.format("Max. Biegespannung ist: %.2fN/mm^2\n", bruecke.getMaxSigma());
		
		if(bruecke.getMaxSigma() < Bruecke.SIGMA_D)
			System.out.print("LKW darf Brücke befahren!!\n");
		else
			System.out.print("LKW darf Brücke NICHT befahren!!\n");
	}
	
	public static void main(String[] args) throws IOException {
		double gesamtGewicht = 0;
		double achsAbstand = 0;
		double laengeBruecke = 0;
		double b, h, s, t;
		double lkw_hoehe = 3.8, lkw_breite = 2.6; // für Berechnungen irrelevant
		double arr[];
		boolean run = true;

		while(run)
		{
			System.out.print("----------Statikberechnung für:\n\n");
	
			gesamtGewicht = 1000*readInput("Geben Sie das Gesamtgewicht des Zweiachsers ein [5 - 10 t]:");
			achsAbstand = readInput("Geben Sie den Achsabstand des Zweiachsers ein [4 - 10 m]:");
			laengeBruecke = readInput("Geben Sie die Länge der Brücke ein [5 - 20 m]:"); 
			arr = readString("Geben Sie die Querschnittsmaße des Trägers ein [b h s t (in cm)]:"); 		
			b = arr[0];	h = arr[1]; s = arr[2];	t = arr[3];
	
			Querschnitt q1 = new Querschnitt(b,h,s,t);		
			Traeger t1 = new Traeger(laengeBruecke, 7.85, q1);		
			LKW lkw = new LKW("BauInc", "Max Mustermann", achsAbstand, lkw_hoehe, lkw_breite, gesamtGewicht);
			Bruecke bruecke = new Bruecke(3, laengeBruecke, t1);
	
			System.out.print("----------Statikberechnung für:\n");
			System.out.format("LKW: LKW --> Achsabstand: %.1fm, Firma: %s, Fahrer: %s, Last-Vorderachse: %.2fkN, Last-Hinterachse: %.2fkN.\n", 
					lkw.getAchsAbstand(), lkw.getFirma(), lkw.getFahrer(), lkw.achsLastVA(), lkw.achsLastHA());		
			System.out.format("Fahrzeug --> Höhe: %.1fm, Breite: %.1fm, Gesamtgewicht: %.2fkg.\n", lkw.getHoehe(), lkw.getBreite(), lkw.getGesamtGewicht());
			System.out.format("auf Brücke: Bruecke --> konst. Fahrbahndeckenlast: %.2fkN/m, Länge: %.1fm\n", bruecke.getFahrbahndeckenlast(), bruecke.getLaenge());
			System.out.format("Gesamtgleichlast: %.2fkN/m\n",bruecke.getGesamtLast());
			System.out.format("Trägerpaar: Träger --> Länge: %.1fm, Dichte: %.2fkg/dm^3, Masse: %fkg.\n", t1.getLaenge(), t1.getDichte(), t1.getMasse());
			System.out.format("Querschnitt --> b: %.1fcm, h: %.1fcm, s: %.1fcm, t: %.1fcm\n", q1.getB(), q1.getH(), q1.getS(), q1.getT());
			System.out.format("Querschnittsfläche: %.2fcm^2, Flächenträgheitsmoment: %.2fcm^4.\n\n", q1.getFlaeche(), q1.getIy());
			
			simuliereUeberfahrt(bruecke, lkw);		
			
			System.out.print("\n\nContinue? [y/n]: ");

			if(br.read() == 'n')
				break;				
			
		}
		
		scanner.close();
		br.close();
	}

}
