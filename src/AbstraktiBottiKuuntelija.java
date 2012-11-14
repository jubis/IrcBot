
public abstract class AbstraktiBottiKuuntelija implements BottiKuuntelija {
	
	public int annaPrioriteetti() {
		return 0;
	}
	
	public boolean uusiViesti( String viesti, String kanava, String lahettaja ) {
		return true;
	}
	public void kanavallaUusiKayttaja( String nick ) {}
	public void kanavaltaLahtiKayttaja( String nick ) {}
}
