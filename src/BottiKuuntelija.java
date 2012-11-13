
public interface BottiKuuntelija {
	public boolean uusiViesti( String viesti, String kanava, String lahettaja );
	public void kanavallaUusiKayttaja( String nick );
	public void kanavaltaLahtiKayttaja( String nick );
}
