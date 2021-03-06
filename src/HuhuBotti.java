import java.util.Random;



public class HuhuBotti extends AbstraktiBottiKuuntelija {
	private String[] huhut = new String[11];
	private Random rand = new Random();
	private String[] phuksiTytot = {"Emmi", "Noora", "Saara", "Meri", "Joanna",
			"Matilda", "Katriina", "Reetta", "Henni", "Nelli", "Eeva", "Hanna",
			"Viivi", "Laura", "Auli", "Lotta", "Ilona", "Inna", "Annukka",
			"Kaisa"};
	private String[] phuksiJatkat = {"Aleksi", "Jaakko", "Toomas", "Matias",
			"Johannes", "Vesa", "Miikka", "Nico", "Pauli", "Timo", "Joel",
			"Ossi", "Masa", "Lauri", "Olli", "Topi", "Justus", "Otso", "Atro",
			"Tuomo", "Mikko", "Fredrik", "Ilja"};

	/**
	 * Huhutaulukon alustus.
	 */
	public HuhuBotti( HelppoBotti botti ){
		super(botti);
		this.huhut[0] = "Tiesikkö, että Justus on sammunu Saaralle";
		this.huhut[1] = "Hei kuulitko, et Saaran lattialle sammu kerran " +
				"ranskalainen vaihtari";
		this.huhut[2] = "Kelaa, phuksien juhlasitseillä oliver kulki infon" +
				"phuksityttöjen perässä vesilasi kädessä!";
		this.huhut[3] = "Kuulin vähän juttuu et heppatyttä ysikaks ei oo tyttä" +
				" eikä ysikaks..!";
		this.huhut[4] = "Liikkuu vähän sellast juttuu et " + 
				phuksiTytot[rand.nextInt(phuksiTytot.length)] + "on raskaana!!";
		this.huhut[5] = phuksiJatkat[rand.nextInt(phuksiJatkat.length)] + " ei" +
				" tainnu olla " + phuksiTytot[rand.nextInt(phuksiTytot.length)]
						+ "n avec vaan viime sitseilla, kuulin ma...";
		this.huhut[6] = "Mieti, HV:l on joskus ollu musta tukka ja se soitti " +
				"bändis!";
		this.huhut[7] = "Arvaa, Emmi ja Janne Käki oli samas hotellihuoneessa" +
				" Virossa";
		this.huhut[8] = phuksiJatkat[rand.nextInt(phuksiJatkat.length)] +
				" sammu kuulemma tanssilattialle kesken viime bileiden!";
		this.huhut[9] = "Tiesitkä et Merin haalarit on värjätty siiderillä!";
		this.huhut[10] = "Hei joku kerto et " + 
				phuksiJatkat[rand.nextInt(phuksiJatkat.length)] + " tykkää " + 
				phuksiTytot[rand.nextInt(phuksiTytot.length)] + "sta :)";

	}
	/**
	 * Arpoo huhut taulukossa randomilla huhun ja palauttaa sen.
	 * 
	 * @param huhut
	 * @return
	 */

	public String annaHuhu(String[] huhut){
		if(true){
			int i = this.rand.nextInt(this.huhut.length);
			return huhut[i];
		}
		return "";
	}

	@Override
	public boolean uusiViesti( String viesti, String kanava, String lahettaja ) {
		viesti = this.annaHuhu(huhut);
		Random rand = new Random();
		if( rand.nextDouble() < 0.2 ) {
			this.botti.lahetaViesti( viesti, kanava );
			return true;
		} else {
			return false;
		}
	}
	
	public void lahetaViesti(String viesti, String kanava){
	}
	
	@Override
	public int annaPrioriteetti() {
		return 10;
	}
}