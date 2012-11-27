import java.util.Random;


public class JaarittelijaBotti extends AbstraktiBottiKuuntelija {

	Random rand = new Random();
	
	public JaarittelijaBotti(HelppoBotti botti){
		super(botti);
	}
	
	public String mitaKuuluu() {
	
		String [] kuuluminen = {"hyvaa kuuluu, ", "ihan ok, ", "paskaa, hajoon javaan... ", 
		"mitas tassa, kaamee kapula, "};

		String [] vastaus = {"ent sulle?", "mitas sa?", "enta ite?", "mita sulle?"};
	
		int kuulumisindeksi = rand.nextInt(kuuluminen.length);
		int vastaamisindeksi = rand.nextInt(vastaus.length);
		String kuuluu = kuuluminen[kuulumisindeksi];
		String vastaa = vastaus[vastaamisindeksi];
		
		return kuuluu+vastaa;
	}
	
	public String noJustSita(){
		String [] mitaVastaukset = {"emt", "kuinni", "trolololol", "kato vittu googlest", 
				"mitenni", "no just sita"};
		
		String lyhytVastaus = mitaVastaukset[rand.nextInt(mitaVastaukset.length)];
		
		return lyhytVastaus;
	}
	
	public String noJustSiksi(){
		String [] miksiVastaukset = {"no just siks", "emt, kato googlest", 
				"miks sa multa kysyt", "koska nain nyt on aina vaan ollut", 
				"sehan on itsestaanselvaa...", "koska ma sanon nii!!"};
		
		String vastaus = miksiVastaukset[rand.nextInt(miksiVastaukset.length)];
		
		return vastaus;
	}
	
	public String vastaaEiKyssariin(){
		String [] vastauksetEiKyssariin = {"ookoo...", "jepjep", "hmm..", "nojuu no jaa",
				"mitas vittua sita nyt tekis", "oo hiljaa", "sori en jaksa kuunnella",
				"kiinnostaa ku kilo paskaa", "rauhotu nyt!", "asdff",
				"mutsis on", "samaa mielta", "tuskinpa"};
		String eiKyssariin = vastauksetEiKyssariin[rand.nextInt(vastauksetEiKyssariin.length)];
		return eiKyssariin;
	}
	
	
	public String worstCaseScenario(){
		String [] syytLahtea = {"Pitas varmaa menna nukkuu.. moro", "Viddu, pakko menna" +
				" lukee tenttii, later!", "akku loppuu, tuun koht back",
				"hirvee nalka, pakko kayda hakee safkaa..", "venatkaa hetki, pitaa tsekkaa" + 
				" yks juttu..", "ei vittu, luento alkaa, moro", "kayn hakee bissen, koht takas"};
		
		String syyLahtea = syytLahtea[rand.nextInt(syytLahtea.length)];
		return syyLahtea;
	
	}
	
	
	@Override
	public boolean uusiViesti( String viesti, String kanava, String lahettaja) {
		String vastaus = null;
		
		if ((viesti.startsWith("Mita kuuluu") || 
			(viesti.startsWith("Miten menee")) ||
			(viesti.startsWith("Mitas s")))){
			vastaus = this.mitaKuuluu();
			
		} 
		else if (viesti.startsWith("Mita")){
			vastaus =this.noJustSita();
		}
		else if (viesti.startsWith("Miksi")){
			vastaus = this.noJustSiksi();
		}
		else if (!(viesti.endsWith("?"))){
			vastaus = this.vastaaEiKyssariin();
		}
		else{
			vastaus = this.worstCaseScenario();
		}
		
		this.botti.lahetaViesti(vastaus, kanava);
		
		return false;
	}
	
	@Override
	public int annaPrioriteetti() {
		return 1;
	}
}
