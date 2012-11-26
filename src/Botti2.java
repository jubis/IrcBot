import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jibble.pircbot.PircBot;

public class Botti2 extends PircBot implements HelppoBotti {
	private List<BottiKuuntelija> kuuntelijat = new ArrayList<BottiKuuntelija>();
	private KomentoriviUI ui;
	
	public Botti2() {
		this.setVerbose( true );
		
		this.setLogin( "tester" );
		
		this.lisaaKaikkiKuuntelijat();
		
		this.ui = new KomentoriviUI( this );
		this.ui.alustaBotti();
	}
	
	public void asetaNimi( String nimi ) {
		if( ! nimi.trim().equals( "" ) ) {
			this.setName( nimi );
		}
	}
	
	public void liityKanavalle( String kanava ) {
		if( ! kanava.trim().equals( "" ) ) {
			this.joinChannel( kanava );
		}
	}
	
	@Override
	protected void onMessage( String channel, 
	                          String sender, 
	                          String login, 
	                          String hostname, 
	                          String message ) {
		if( ! sender.equals( this.getNick() ) ) {
			this.ilmoitaKuuntelijoilleViestista( message, channel, sender );
		}
	}
	
	@Override
	protected void onJoin(	String channel,
							String sender,
							String login,
							String hostname ) {
		if( sender.equals( this.getNick() ) ) {
			this.ui.liityttiinKanavalle();
		}
		this.ilmoitaKuuntelijoilleLiittymisesta( sender, channel );
	}
	
	@Override
	protected void onPart(	String channel,
							String sender,
							String login,
							String hostname ) {
		this.onLeave( channel, sender );
	}
	
	@Override
	protected void onKick(	String channel,
							String kickerNick,
							String kickerLogin,
							String kickerHostname,
							String recipientNick,
							String reason ) {
		this.ui.halytys( "Saatiin potkut kanavalta " + channel +
		                 ", syy: " + reason );
		this.onLeave( channel, recipientNick );
	}
	
	private void onLeave( String channel,
						  String sender ) {
		if( sender.equals( this.getNick() ) ) {
			this.ui.lahdettiinKanavalta();
		}
		this.ilmoitaKuuntelijoillePoistumisesta( sender, channel );
	}
	
	private void ilmoitaKuuntelijoilleViestista( String viesti,
										String kanava,
										String lahettaja ) {
		for( BottiKuuntelija kuuntelija : this.kuuntelijat ) {
			if( kuuntelija.uusiViesti( viesti, kanava, lahettaja ) ) {
				// jos kuuntelija haluaa olla viimeinen, lopetetaan tähän
				break;
			}
		}
	}
	
	private void ilmoitaKuuntelijoilleLiittymisesta( String nick, String kanava ) {
 		for( BottiKuuntelija kuuntelija : this.kuuntelijat ) {
 			kuuntelija.kanavallaUusiKayttaja( nick, kanava );
 		}
 	}
	
	
	private void ilmoitaKuuntelijoillePoistumisesta( String nick, String kanava ) {
 		for( BottiKuuntelija kuuntelija : this.kuuntelijat ) {
 			kuuntelija.kanavaltaLahtiKayttaja( nick, kanava );
 		}
 	}
	
	private void lisaaKaikkiKuuntelijat() {
		BottiKuuntelija[] kuuntelijat = { new JaarittelijaBotti( this ),
									      new Jtoiminto( this, this ),
									      new MoikkausBotti( this ),
									      new BottiKontrolli( this ) };
		for( BottiKuuntelija kuuntelija : kuuntelijat ) {
			this.kuuntelijat.add( kuuntelija );
		}
		this.jarjestaKuuntelijat();
	}
	
	@Deprecated
	public void lisaaBottiKuuntelija( BottiKuuntelija bk ) {
		this.kuuntelijat.add( bk );
		this.jarjestaKuuntelijat();
	}

	private void jarjestaKuuntelijat() {
		Collections.sort( this.kuuntelijat );
	}
	
	@Override
	public void lahetaViesti( String viesti, String kanava ) {
		this.sendMessage( kanava, viesti );
	}
	
	@Override
	public String annaNick() {
		return this.getNick();
	}
	
	public static void main( String[] args ) {
		Botti2 botti = new Botti2();
		
	}
}
