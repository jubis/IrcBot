
public interface BottiKuuntelija extends Comparable<BottiKuuntelija> {
	
	/**
	 * Kuuntelijan prioriteetti määrittää, missä järjestyksessä se saa
	 * vuoron toimia. Korkein prioriteetti (suurin luku) toimii ensin.
	 * 
	 * @return Prioriteetin arvo
	 */
	public int annaPrioriteetti();
	
	/**
	 * Kutsutaan, kun botti havaitsee, että sen kanssa samalle kanavalle
	 * lähetetään viesti. Tätä ei kuitenkaan kutsuta, jos viestin lähettäjä
	 * on botti itse.
	 * 
	 * Palautusarvo määrää, jääkö tämä kuutelija viimeiseksi
	 * kutsutuksi kuuntelijaksi.
	 * 
	 * @param viesti Kanavalle lähetetty viesti muuttumattomana
	 * @param kanava Kanava, jolle viesti on lähetetty
	 * @param lahettaja Viestin lähettäjän nick
	 * 
	 * @return true, jos tämä kuuntelija on viimeinen, jota kutsutaan
	 * 		   false, jos myös pienemmän prioriteetin kuuntelijat saavat vuoron
	 */
	public boolean uusiViesti( String viesti, String kanava, String lahettaja );
	
	/**
	 * Kutsutaan, kun kanavalle on tullut uusi käyttäjä. 
	 * Kutsutaan myös, vaikka käyttäjä olisi botti itse. 
	 * 
	 * @param nick Uuden käyttäjän nick
	 * @param kanava Kertoo, millä kanavalla tämä on tapahtunut
	 */
	public void kanavallaUusiKayttaja( String nick, String kanava );
	
	/**
	 * Kutsutaan, kun kanavalta on poistunut käyttäjä.
	 * Kutsutaan myös, vaikka käyttäjä olisi botti itse. 
	 * 
	 * Huom! Kutsuhetkellä käyttäjä on jo poistunut
	 * 
	 * @param nick Poistuneen käyttäjän nick
	 * @param kanava Kertoo, millä kanavalla tämä on tapahtunut
	 */
	public void kanavaltaLahtiKayttaja( String nick, String kanava );
}
