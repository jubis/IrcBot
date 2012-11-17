import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jibble.pircbot.PircBot;

public class Botti2 extends PircBot {
	private List<BottiKuuntelija> kuuntelijat = new ArrayList<BottiKuuntelija>();
	
	public Botti2() {
	}
	
	@Override
	protected void onMessage( String channel, 
	                          String sender, 
	                          String login, 
	                          String hostname, 
	                          String message ) {
		this.ilmoitaKuuntelijoille( message, channel, sender );
	}

	private void ilmoitaKuuntelijoille( String viesti,
										String kanava,
										String lahettaja ) {
		for( BottiKuuntelija kuuntelija : this.kuuntelijat ) {
			kuuntelija.uusiViesti( viesti, kanava, lahettaja );
		}
		
	}
	
	public void lisaaBottiKuuntelija( BottiKuuntelija bk ) {
		this.kuuntelijat.add( bk );
		this.jarjestaKuuntelijat();
	}

	private void jarjestaKuuntelijat() {
		Collections.sort( this.kuuntelijat );
	}
	
	public void lahetaViesti( String viesti, String kanava ) {
		this.sendMessage( kanava, viesti );
	}
}
