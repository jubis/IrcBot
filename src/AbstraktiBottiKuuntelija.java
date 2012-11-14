
public abstract class AbstraktiBottiKuuntelija implements BottiKuuntelija {
	protected Botti2 botti;
	
	public AbstraktiBottiKuuntelija( Botti2 botti ) {
		this.botti = botti;
	}
	
	public int annaPrioriteetti() {
		return 0;
	}
	public boolean uusiViesti( String viesti, String kanava, String lahettaja ) {
		return false;
	}
	public void kanavallaUusiKayttaja( String nick ) {}
	public void kanavaltaLahtiKayttaja( String nick ) {}
	
	public int compareTo( BottiKuuntelija vertailtava ) {
		int omaPrioriteetti = this.annaPrioriteetti();
		int vertailtavanPrioriteetti = vertailtava.annaPrioriteetti();
		
		if( omaPrioriteetti < vertailtavanPrioriteetti ) {
			return -1;
		} else if( omaPrioriteetti > vertailtavanPrioriteetti ) {
			return 1;
		} else {
			return 0;
		}
	}
}
