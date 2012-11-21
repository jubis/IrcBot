import org.jibble.pircbot.PircBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Jtoiminto implements BottiKuuntelija {

	/*
	 * http://en.wikipedia.org/w/api.php
	 * prop=extracts (ex) *
	 * Returns plain-text or limited HTML extracts of the given page(s)
	 */
	private static final String apiurl = "http://en.wikipedia.org/w/api.php?action=query&format=txt&prop=extracts&exchars=500&titles=";	

	private static BufferedReader lukija = new BufferedReader(new 
			InputStreamReader(System.in));

	public Jtoiminto(PircBot pircbot) {
	}

	private URL teeUrl(String raakaurl) {
		try {
			return new java.net.URL(raakaurl);
		} catch (MalformedURLException e) {
			System.out.println("paha url");
			e.printStackTrace();
			return null;
		}

	}

	private boolean logiikka(String viesti) {
		String[] menotaulu = viesti.split("\\s+");

		String sana = null;
		try {sana = menotaulu[1];
		}	catch(ArrayIndexOutOfBoundsException ex) {
			return false;
		}

		//teipataan url kasaan
		URL url = teeUrl(apiurl + sana);

		//tehdaan inputstream, jolla haetaan tavaraa netista
		InputStream virta = null;
		try {
			virta = url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
		//teetekstia() hakee streamin avulla tekstin
		String teksti = teeTekstia(virta);


		//jaetaan teksti osiin [extract]-tagin ja "...":n mukaan
		String[] paluutaulu = teksti.split("extract]|(\\.\\.\\.)");

		//poistetaan tuloksesta <p>:t, </b>:t yms jne.
		String tulos = null;
		try{
			tulos = paluutaulu[1].replaceAll("<.>","");
			tulos = tulos.replaceAll("<..>","");
			tulos = tulos.replaceAll("<...>","");
			tulos = tulos.replaceAll("<....>","");
		} catch(RuntimeException Re) {
			System.out.println("virhe");
		}
		//null-tarkastus
		if(tulos == null) {
			return false;
		}
		if(tulos.contains("REDIRECT")){
			System.out.println("redirect-sivu");
			return false;
		}

		//tyyliseikkana teipataan pisteet takaisin.
		tulos += "...";

		//printataan tulos
		System.out.println(tulos);


		//yritetään vielä sulkea virta
		try {
			virta.close();
		} catch (IOException e) {}

		return true;
	}



	public boolean uusiViesti(String viesti, String kanava, String lahettaja) {

		//toimii kun/jos viesti muotoa "!wiki <aihe>":
		//ymmartaa vain yksiosaisia hakusanoja.

		if (viesti.contains("!wiki")) {	
			return logiikka(viesti);
		}
		else return false;
	}

	private static String teeTekstia(InputStream virta) {
		java.util.Scanner scan = new java.util.Scanner(virta).useDelimiter("//A");
		return scan.hasNext() ? scan.next() : "";	
	}
	public int annaPrioriteetti() {
		return 0;		
	}

	public int compareTo(BottiKuuntelija arg0) {	
		return 0;
	}

	public void kanavallaUusiKayttaja(String nick) {

	}

	public void kanavaltaLahtiKayttaja(String nick) {

	}

	private static String kysy() {
		try {
			return lukija.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		Jtoiminto J = new Jtoiminto(null);

		System.out.println("mita haluat tietaa? (muista !wiki):");
		while(true){
			J.uusiViesti(kysy(), "", "");
		}
	}
}
