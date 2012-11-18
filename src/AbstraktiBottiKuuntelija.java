
public abstract class AbstraktiBottiKuuntelija implements BottiKuuntelija {
	protected HelppoBotti botti;
	
	public AbstraktiBottiKuuntelija( HelppoBotti botti ) {
		this.botti = botti;
	}
	
	@Override
	public int annaPrioriteetti() {
		return 0;
	}
	@Override
	public boolean uusiViesti( String viesti, String kanava, String lahettaja ) {
		return false;
	}
	@Override
	public void kanavallaUusiKayttaja( String nick ) {}
	@Override
	public void kanavaltaLahtiKayttaja( String nick ) {}
	
	@Override
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
