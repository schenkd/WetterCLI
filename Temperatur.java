package de.davidschenk.wetter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;


public class Temperatur {
	
	private final static HTTPClientConnection connect() {
		// Erzeugen eines neuen Connection Objekt
		HTTPClientConnection conn = new HTTPClientConnection();
		return conn;
	}
	
	protected static String anfrage(StringBuilder stadt) {
		// Bei jeder Anfrage eine neue Connection
		HTTPClientConnection conn = Temperatur.connect();
		
		// String mit Request
		String anfrage = "http://api.openweathermap.org/data/2.5/weather?q=" + stadt
				+ ",de&units=metric&appid=5ef0ae4f0a4d2430d7172f1ad38ab60e";
		
		// Rückgabe in JSON-Format
		String result = conn.sendRequest(anfrage);
		
		return result;
	}
	
	protected static void ausgabe(String result) {
		JSONObject obj = new JSONObject(result);
		
		// Main Object: temp, pressure, humidity, temp_min, temp_max, sea_level, grnd_level
		JSONObject main = obj.getJSONObject("main");
		
		// Wind Object: speed, deg
		JSONObject wind = obj.getJSONObject("wind");
		
		// Wind Object: speed, deg
		JSONArray weather = new JSONArray(obj.getJSONArray("weather").toString());
		JSONObject wetter = new JSONObject(weather.get(0).toString());
		
		// Sys Object: message, country, sunrise, sunset
		JSONObject sys = obj.getJSONObject("sys");
		long sunset = sys.getLong("sunset");
		long sunrise = sys.getLong("sunrise");
		
		// Coord Object: lon, lat
		JSONObject coord = obj.getJSONObject("coord");
		
		System.out.println("Aktuelle Wetterdaten aus " + obj.get("name") + " - " + sys.get("country"));
		System.out.println("Temp: " + main.get("temp") + " °C"); 
		System.out.println("Wetter: " + wetter.get("main") + " - " + wetter.get("description"));
		System.out.println("Luftdruck: " + main.get("pressure") + " hPa");
		System.out.println("Windgeschwindigkeit: " + wind.get("speed") + " km/h");
		System.out.println("Koordinaten: " + coord.get("lon") + " " + coord.get("lat"));
		System.out.println("Sonnenaufgang um " + Temperatur.unixTimeConvert(sunrise) );
		System.out.println("Sonnenuntergang um " + Temperatur.unixTimeConvert(sunset) );
	}
	
	protected static String unixTimeConvert(long unixTimestamp) {
		Date date = new Date(unixTimestamp*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC+1"));
		
		String datum = sdf.format(date);
		return datum;
		
	}
}
