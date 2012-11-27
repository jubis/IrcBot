import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.UnknownHostException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;


public class KomentoriviUI {
	private BufferedReader br;
	private final Botti2 botti;						  
	
	public KomentoriviUI( Botti2 botti ) {
		this.botti = botti;
		br = new BufferedReader( new InputStreamReader( System.in ) );
	}
		
	public String lueKomento( String kehote ) {
		System.out.print( kehote + " " );
		try {
			return br.readLine();
		} catch ( IOException e ) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void halytys( String halytys ) {
		System.out.println( "*** " + halytys + " ***" );
	}
	
	public void alustaBotti() {
		this.botti.asetaNimi( this.hankiTieto( "nick", "olo2botti" ) );
		String palvelin = this.hankiTieto( "palvelin", "irc.cs.hut.fi" );
		
		while( ! botti.isConnected() ) {
			try {
				System.out.println( "Yhdistetään (" + palvelin + ")..." );
				this.botti.connect( palvelin );
			} catch ( NickAlreadyInUseException e ) {
				this.botti.asetaNimi( this.lueKomento( "Nick on jo käytössä, anna uusi:" ) ); 
			} catch ( UnknownHostException|ConnectException e ) {
				palvelin = this.lueKomento( "Antamaasi palvelin on virheellinen, " +
													 "anna uusi osoite:" );
			} catch ( IrcException e ) {
				e.printStackTrace();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		
		System.out.println( "Yhteys muodostettu palvelimelle " + botti.getServer() );
		
		this.liityKanavalle();
	}
	
	private String hankiTieto( String tiedonNimi, String oletus ) {
		String tieto = this.lueKomento( "Anna " + tiedonNimi + ":" );
		if( tieto.trim().equals( "" ) ) {
			return oletus;
		} else {
			return tieto;
		}
	}
	
	private void liityKanavalle() {
		do {
			this.botti.liityKanavalle( this.lueKomento( "Liity kanavalle:" ) );
			try {
				Thread.sleep( 1000 );
			} catch ( InterruptedException e ) {}
		} while( this.botti.getChannels().length == 0 );
	}
	
	private void kerroKanavat() {
		if( this.botti.getChannels().length == 0 ) {
			System.out.println( "Ei yhtään kanavaa" );
			this.liityKanavalle();
		} else {
			System.out.println( "Kanavat:" );
			for( String kanava : this.botti.getChannels() ) {
				System.out.println( "    " + kanava );
			}
		}
	}

	public void liityttiinKanavalle() {
		System.out.println( "Liityttiin kanavalle" );
		this.kerroKanavat();
		
	}

	public void lahdettiinKanavalta() {
		System.out.println( "Poistuttiin kanavalta" );
		this.kerroKanavat();
	}
}
