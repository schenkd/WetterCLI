package de.davidschenk.wetter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Class: HTTPClientConnection.java
 * @author Blauel
 *
 * Verbindet sich mit dem Server und stellt nach Aufforderung HTTP-Anfragen.
 * Die Serverantwort wird eingelesen und gespeichert
 */
public class HTTPClientConnection {

	private URL url = null;					// URL der Serververbindung
	private URLConnection con = null;		// Verbindungsobjekt zum Server
	private Scanner scan = null;			// Scanner-Objekt zum Einlesen der Server-Antworten

	/**
	 * Konstruktor mit Uebergabe der Serveradresse
	 */
//	public HTTPClientConnection(String hostname)
//	{
//		this.hostname = hostname;
//	}
	
	/**
	 * Vorbereitung der  Anfrage an den Server.
	 * Gibt Antwort des Servers als String zurueck
	 * @param cmd
	 * @return
	 */
	public synchronized String sendRequest(String cmd) {
		
		String retV = "";
		
		try {
			url = new URL(cmd);	// URL mit Anfrage-Zeichenkette anlegen
			
//			System.out.println("url="+url);
			
			retV = nextServerRequest(cmd);	// Anfrage-Methode aufrufen
		}
		catch (MalformedURLException e) {
			
			System.err.println("\nFehler in der Formatierung der URL!\n");
			//e.printStackTrace(); 
			} 
		
		return retV;	// Rueckgabe der Serverantwort
	}
	
	/**
	 * Sendet Anfrage an den Server und gibt Antwort aus String zurueck
	 * @param cmd
	 * @return
	 */
	private synchronized String nextServerRequest(String cmd)
	{
		StringBuffer str = new StringBuffer();	// Speichert die Serverantwort
		
		// Serververbindung herstellen & InputStream initialisieren
		try {
			con = url.openConnection();
						
			scan = new Scanner(con.getInputStream());
			
		} catch (IOException e) {
			
			System.err.println("\nUuups! Da ist was schief gelaufen!\n");
			//e.printStackTrace();
			return "fail";
		}
		
		if(scan!=null)	// Fehlermeldung durch Unterbrechung des Servers abfangen
		{
			while(scan.hasNext())
			{
				str.append(scan.nextLine());
			}
			
			scan.close();	// InputStream schliessen
		}
		
		con = null;			// Verbindungsobjekt auf null setzen

		return str.toString();	// Rueckgabe der Serverantwort
	}
}
