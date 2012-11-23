import java.util.Random;


// Botti tervehtii jokaista uutta kayttajaa ja kertoo vitsin
public class MoikkausBotti extends AbstraktiBottiKuuntelija {
	
	private String[] vitsit;
	private Random rand = new Random();

	public MoikkausBotti(HelppoBotti botti) {
		super(botti);
	}

	// Suuri prioriteetti, jotta toteutetaan aina, kun uusi kayttaja tulee
	public int annaPrioriteetti() {
		return 1000;
	}
	
	// Botin heittamat tervetuloa-kommentit
	public String tervetuloa(String nick, String kanava){
		double luku = rand.nextDouble();
		if (luku<0.33){
			return "Kanavan " + kanava + " yleisena fasilitaattorina" +
					" toivotan sinut tervetulleeksi kanavalle" + nick + "!" +
							" Tassa viela vitsi: ";
		} else if (luku<0.66){
			return nick + " kuka olet? Mita teet meidan kanavalla? " +
					"Voit jaada, jos kerrot paremman vitsin kun tama: ";
		} else {
			return "Hei " + nick + ", taitaa olla tylsaa, kun tulit kanavalle"
					+ kanava + "? Tassa sulle vitsi!";
		}
	}
	
	// Arpoo vitsin taulukkoon talletetuista vitseista
	public String annaVitsi(){
		return vitsit[rand.nextInt(vitsit.length)];
	}
	
	// Kun havaitaan uusi kayttaja, tulostetaan siita tiedot ja lahetetaan 
	//viesti, jossa tervetuloa-kommentti ja peraan vitsi
	@Override
	public void kanavallaUusiKayttaja( String nick, String kanava ) {
		System.out.println("Uusi kayttaja " + nick + 
				" liittyi kanavalle " + kanava);
		
		this.botti.lahetaViesti(this.tervetuloa(nick, kanava) + 
				this.annaVitsi(), kanava);
	}
}
