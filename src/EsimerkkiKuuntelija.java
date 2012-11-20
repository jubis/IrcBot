/**
 * Tämä botti toistaa aina kaikki viestit isoilla kirjaimilla.
 * Se myös tervehtii aina uusia käyttäjiä kanavalla.
 * 
 * @author matia_000
 */
public class EsimerkkiKuuntelija extends AbstraktiBottiKuuntelija {

	/**
	 * Tässä ei tapahdu mitään ihmeellistä. 
	 * Pitää vain muistaa välittää yläluokalle parametri botti. 
	 */
	public EsimerkkiKuuntelija( HelppoBotti botti ) {
		super( botti );
	}

	/**
	 * Koska yläluokassa (AbstraktiBottiKuuntelija) metodi uusiViesti on
	 * tyhjä, eikä tee mitään, ylikirjoitetaan tämä metodi.
	 * 
	 * Botti kutsuu tätä metodia, kun se havaitsee uuden viestin kanavalla.
	 */
	@Override
	public boolean uusiViesti( String viesti, String kanava, String lahettaja ) {
		//tulostetaan saatu viesti, ja kerrotaan, miltä kanavalta se tuli 
		System.out.println( "Uusi viesti, kanavalla " + kanava +
		                    ", viestin sisältö: " + viesti +
		                    ", viestin lähetti " + lahettaja );
		
		//muutetaan annettu viesti isoiksi kirjaimiksi itse tehdyllä metodilla
		String viestiIsolla = muutaIsoiksiKirjaimiksi( viesti );
		
		//-- lähetetään kanavalle takaisin viesti --
		//this.botti on yläluokalta peritty attribuutti, joka viittaa
		//itse "ytimenä" olevaan irc-bottiin
		//
		//botilla on metodi lahetaViesti, jota käytetään
		//
		//huomaa, että käytetään kanavana sitä kanavaa, josta alkuperäinen
		//viesti tuli (kanava on tämän metodin parametri)
		this.botti.lahetaViesti( viestiIsolla, kanava );
		
		//palautetaan false, jotta tämän kuuntelijan jälkeen
		//seuraavatkin kuuntelijat saisivat vuoron
		return false;
		//jos palautus olisi true,
		//botti jättäisi tämän kuuntelijan viimeiseksi kuuntelijaksi,
		//joka saa tehdä mitään
	}
	
	/**
	 * Muuttaa annetun tekstin isoiksi kirjaimiksi
	 * 
	 * @param muutettava Teksti, joka muutetaan
	 * @return Annettu teksti muutettuna isoiksi kirjaimiksi
	 */
	private static String muutaIsoiksiKirjaimiksi( String muutettava ) {
		//käytetään String-olion valmista metodia toUpperCase
		return muutettava.toUpperCase();
	}
	
	/**
	 * Yläluokassa tämä metodi palauttaa aina oletusprioriteetin (0).
	 * Ylikirjoitetaan yläluokan metodia, annetaan parempi prioriteetti.
	 * 
	 * Kun botti antaa kuuntelijoille "vuoronumerot", joiden mukaisessa
	 * järjestyksessä ne saa toimia, 
	 * se muodostaa järjestyksen prioriteetin avulla.
	 * 
	 * Prioriteetin botti saa kutsumalla tätä metodia.
	 * 
	 * (mitä suurempi prioriteetti, sitä aikaisemmin kuuntelija saa vuoron)
	 */
	@Override
	public int annaPrioriteetti() {
		//annetaan iso prioriteetti, jotta tämän kuuntelija
		//vuoro tulisi mahdollisimman aikaisin
		return 100000;
	}
	
	/**
	 * Tämäkään metodi ei yläluokassa tee mitään. Ylikirjoitataan se, ja
	 * laitetaan se tekemään jotain hauskaa.
	 * 
	 * Botti kutsuu tätä metodia aina, kun sen kanssa samalle kanavalle tulee
	 * uusi käyttäjä. (Tätä kutsutaan myös, jos tämä käyttäjä on botti itse.)
	 */
	@Override
	public void kanavallaUusiKayttaja( String nick, String kanava ) {
		//tervehditään uutta käyttäjää
		//
		//huomaa, että tervehdys lähetetään juuri sille kanavalle,
		//johon käyttäjän on ilmoitettu liittyneen
		//(eli sille kanavalle, joka on annettu tälle metodille parametriksi)
		this.botti.lahetaViesti( "Moi " + nick + "!", kanava );
	}
}
