
public class BottiKontrolli extends AbstraktiBottiKuuntelija {
	private Botti2 botti;
	
	public BottiKontrolli( Botti2 botti ) {
		super( botti );
		this.botti = botti;
	}
	
	@Override
	public boolean uusiViesti( String viesti, 
	                           String kanava, 
	                           String lahettaja ) {
		if( !viesti.trim().equals( "" ) && 
			viesti.startsWith( this.botti.getNick() + ":" ) ) {
			this.tulkkaa( viesti, kanava );
		}
		return true;
	}
	
	private void tulkkaa( String komento, String kanava ) {
		String[] osat = komento.split( "\\s+" );
		
		if( osat.length > 1 ) {
			switch( osat[1] ) {
				case "!joinchannel":
					if( osat.length > 2 )
						this.botti.joinChannel( osat[2] );
					break;
				case "!partchannel":
					if( osat.length > 2 )
						this.botti.partChannel( osat[2] );
					break;
				case "!channels":
					this.kerroKanavat( kanava );
					break;
				case "!say":
					if( osat.length > 3 )
						this.sano( komento );
					break;
				case "!help":
					this.kerroOhjeet( kanava );
					break;
				default:
					this.kerroOhjeet( kanava );
			}
		} else {
			this.kerroKanavat( kanava );
		}
	}

	private void sano( String komento ) {
		String[] osat = komento.split( "\"" );
		if( osat.length < 3 ) {
			return;
		}
		this.botti.sendMessage( osat[2].trim(), osat[1] );
	}
	
	private void kerroKanavat( String komennonKanava ) {
		String viesti = "";
		if( this.botti.getChannels().length == 0 ) {
			viesti += "Ei yhtään kanavaa";
		} else {
			viesti += "Kanavat:";
			for( String kanava : this.botti.getChannels() ) {
				viesti += " " + kanava;
			}
		}
		this.botti.sendMessage( komennonKanava, viesti );
	}
	
	private void kerroOhjeet( String komennonKanava ) {
		String viesti = "";
		viesti += "!joinchannel <kanava> - liity kanavalle | ";
		viesti += "!partchannel <kanava> - poisto kanavalta | ";
		viesti += "!channels - tulostaa kanavat | ";
		viesti += "!say \"<viesti>\" <kanava> - lähettää viestin | ";
		viesti += "!help - tulostaa ohjeet";
		this.botti.sendMessage( komennonKanava, viesti );
	}

	@Override
	public int annaPrioriteetti() {
		return Integer.MAX_VALUE;
	}
}
