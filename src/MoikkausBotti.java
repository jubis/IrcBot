import java.util.Random;


// Botti tervehtii jokaista uutta kayttajaa ja kertoo vitsin
public class MoikkausBotti extends AbstraktiBottiKuuntelija {
	
	private String[] vitsit = {
			"Muut olivat variksia, Janne Kaki", 
			"Muut eivat koskeneet elainlaakareihin, Eevert Saukkokoski", 
			"Muilla on suora, Mikko Latva-Kayra", 
			"Muut olivat vuoria, Tomi Laakso", 
			"Muuto olivat vuoria, Vesa Laakso", 
			"Paniikki: KAPEEE!!!  - Kato googlest!",
			"Muut juoksi, Aleksi Talsi"
			};
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
					" toivotan sut tervetulleeksi kanavalle" + nick + "! ";
		} else if (luku<0.66){
			return nick + " kuka sa oot? Mita sa teet meidan kanavalla? " +
					"No ihan sama! ";
		} else {
			return "Hei " + nick + ", taitaa olla tylsaa, kun tulit "
					+ kanava + "lle? ";
		}
	}
	
	
	public String valitseVitsi(){
		return "Valitse minkalaisen vitsin haluut kuulla kirjoittamalla sen" +
				" komento: !jannekaki, !eevert, !mikkolk, " +
				"!tomi, !vesa, !kape, !aleksi";
	}
	
	// Kun havaitaan uusi kayttaja, tulostetaan siita tiedot ja lahetetaan 
	//viesti, jossa tervetuloa-kommentti ja peraan vitsi
	@Override
	public void kanavallaUusiKayttaja( String nick, String kanava ) {
		System.out.println("Uusi kayttaja " + nick + 
				" liittyi kanavalle " + kanava);
		
		if (nick!=this.botti.annaNick()){
		this.botti.lahetaViesti(this.tervetuloa(nick, kanava) + 
				this.valitseVitsi(), kanava);
		}
	}
	
	@Override
	public boolean uusiViesti( String viesti, String kanava, String lahettaja ) {
		String vastaus = null;
		if (viesti.equals("!jannekaki")){
			vastaus = this.vitsit[0];
		} else if (viesti.equals("!eevert")){
			vastaus = this.vitsit[1];
		} else if (viesti.equals("!mikkolk")){
			vastaus = this.vitsit[2];
		} else if (viesti.equals("!tomi")){
			vastaus = this.vitsit[3];
		} else if (viesti.equals("!vesa")){
			vastaus = this.vitsit[4];
		} else if (viesti.equals("!kape")){
			vastaus = this.vitsit[5];
		} else if (viesti.equals("!aleksi")){
			vastaus = this.vitsit[6];
		}
		if (vastaus!=null && lahettaja!=this.botti.annaNick()){
			this.botti.lahetaViesti(vastaus, kanava);
			return false;
		} else {
			return false;
		}
	}
}
