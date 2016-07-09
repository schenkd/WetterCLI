package de.davidschenk.wetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main { 	// Klassenname 

	public static void main(String[] args) {
	
		StringBuilder input = new StringBuilder();
		BufferedReader cli;
		
		System.out.println("####################################");
		System.out.println("#                                  #");
		System.out.println("#                                  #");
		System.out.println("#       Willkommen zur CLI         #");
		System.out.println("#          WetterStation           #");
		System.out.println("#                                  #");
		System.out.println("#                                  #");
		System.out.println("#                                  #");
		System.out.println("#                                  #");
		System.out.println("#      dev by David Schenk         #");
		System.out.println("#                                  #");
		System.out.println("####################################");
		System.out.println("\n\n");
		
		// While-loop für Ortschaften
		while (!input.equals("exit")) {
			
			try{
				
				cli = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("\nOrt eingeben: ");
				input = new StringBuilder(cli.readLine());
				
				while( input.indexOf(" ") != -1) {
					input.replace(input.indexOf(" "), input.indexOf(" ")+1, "%20");
				}
				
			} catch (IOException ex) {
				
				System.err.println("CLI Syntax Error");
				
			} finally {
			
				if (input.equals("exit")) {
					System.exit(0);
				} else {
					String json = Temperatur.anfrage(input);
					if (json.equals("fail")) {
						continue;
					} else {
						Temperatur.ausgabe(json);
					}
					
				}
			}
			
		}
	}
	
}
