import org.jibble.pircbot.PircBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

//TODO : miten lähetysmetodia pitää kutsua? nyt toteutettu kuten EsimerkkiKuuntelija. PircBot-attribuutit kommenteissa.

/**
 * Jtoiminto tottelee komentoja 
 * 
 * "!wiki <sana>"
 * 			tulostaa wikipediasta sanan tuottaman 
 * 			artikkelin alusta < merkkimaara-attribuutin verran merkkeja.
 * "!pituus <luku>"
 * 			muuttaa attribuuttia merkkimaara
 * 			
 * @author Johannes
 * @attribute merkkimaara	netista haettavan tekstin merkkien maara 
 * @attribute pircbot 		Toiminnon tiedossa oleva PircBot
 * 
 * Lopullinen tuloste voi olla paljonkin lyhempi kuin merkkimaara, 
 * silla haetusta tekstista poistetaan kaikki <p>:t, </b>:t yms.
 * 
 * Heitin prioriteetiksi 10.
 */

public class Jtoiminto implements BottiKuuntelija {
	
	/*
	 * http://en.wikipedia.org/w/api.php
	 * prop=extracts (ex)
	 * Returns plain-text or limited HTML extracts of the given page(s)
	 */
	private int merkkimaara = 500;	
//	private PircBot pircbot;	
	private HelppoBotti botti;
	private BufferedReader lukija = new BufferedReader(new 
			InputStreamReader(System.in));

	
	public Jtoiminto(PircBot pircbot, HelppoBotti botti) {
	//this.pircbot = pircbot;
	this.botti = botti;
	}
	
	private String annaApiurl() {
		return "http://en.wikipedia.org/w/api.php?action=query&format=txt&prop=extracts&exchars="+annaArtikkelinPituus()+"&titles=";
	}
	
	private void asetaArtikkelinPituus(int merkit) {
		merkkimaara = merkit;
	}
	
	private String annaArtikkelinPituus() {
		return ""+merkkimaara;
	}
	
	

	public boolean uusiViesti(String viesti, String kanava, String lahettaja) {
		
		//toimii kun/jos viesti muotoa "!wiki <aihe>":
		//ymmartaa vain yksiosaisia hakusanoja.
		
		if (viesti.contains("!wiki")) {	
			return logiikka(viesti, kanava);
		}
		
		//artikkelin pituutta voidaan saataa "!pituus <luku>"
		else if (viesti.contains("!pituus")) {
			return kutsuAsetus(viesti, kanava);
		}
		else return false;
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

	private InputStream avaaVirta(URL url) {
		InputStream virta = null;
		try {
			virta = url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
		return virta;
	}
	
	private boolean logiikka(String viesti, String kanava) {
		String[] viestiosina = viesti.split("\\s+");
		String hakusana = null;
		
		if (viestiosina.length == 2) {
			hakusana = viestiosina[1];
			
		}	else return false;
		
		//teipataan url kasaan
		URL url = teeUrl(annaApiurl() + hakusana);

		//tehdaan inputstream, jolla haetaan tavaraa netista
		InputStream virta = avaaVirta(url);
	
		if (virta == null) {
			return false;
		}
		
		//teetekstia() hakee streamin avulla tekstin
		String teksti = teeTekstia(virta);
		
		//suljetaan virta
		try {
			virta.close();
		} catch (IOException e) {}

		//jaetaan teksti osiin [extract]-tagin ja "...":n mukaan
		String[] paluutaulu = teksti.split("extract]|(\\.\\.\\.)");

		//poistetaan tuloksesta <p>:t, </b>:t yms jne.
		String tulos = null;
		if (paluutaulu.length > 1) {
			tulos = paluutaulu[1].replaceAll("<.>","");
			tulos = tulos.replaceAll("<..>","");
			tulos = tulos.replaceAll("<...>","");
			tulos = tulos.replaceAll("<....>","");
		} else {
			System.out.println("virhe");
			return false;
		}
		//null-tarkastus
		if(tulos == null) {
			return false;
		}
		//jos tuloksessa on sana REDIRECT, haku on palauttanut redirect-sivun
		if(tulos.contains("REDIRECT")){
			System.out.println("palautti redirect-sivun, yrita muotoilla uudestaan");
			return false;
		}

		//liitetaan pisteet takaisin, jotka poistettiin paluutaulun splitissa
		tulos += "...";

		//printataan / lahetetaan tulos
		
		//System.out.println(tulos);
		this.botti.lahetaViesti(tulos, kanava);
		return true;
	}

	private boolean kutsuAsetus(String viesti, String kanava) {
		String[] viestiosina = viesti.split("\\s+");
		int merkit = 0;
		
		if (viestiosina.length == 2) {
			
			merkit = Integer.parseInt(viestiosina[1]);
			
			asetaArtikkelinPituus(merkit);
		
			//System.out.println("pituudeksi asetettu "+merkit+" merkkia.", kanava);
			this.botti.lahetaViesti("pituudeksi asetettu "+merkit+" merkkia.", kanava);
		return true;
		}	
		else return false;
	}


	private static String teeTekstia(InputStream virta) {
		java.util.Scanner scan = new java.util.Scanner(virta).useDelimiter("//A");
		return scan.hasNext() ? scan.next() : "";	
	}
	
	
	public int annaPrioriteetti() {
		//heitin jotain.
		return 10;		
	}

	public int compareTo(BottiKuuntelija arg0) {	
		return 0;
	}

	private String kysy() {
		try {
			return this.lukija.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void kanavallaUusiKayttaja(String nick, String kanava) {
				
	}

	public void kanavaltaLahtiKayttaja(String nick, String kanava) {
	
	}

	public static void main(String[] args) {
		
		Jtoiminto J = new Jtoiminto(null, null);
		System.out.println("mita haluat tehda? (!pituus / !wiki):");
		while(true){
			J.uusiViesti(J.kysy(), "", "");
		}
	}
	

}
