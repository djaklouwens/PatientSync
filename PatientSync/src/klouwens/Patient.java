package klouwens; //13/6/22 23.04 (87)

public class Patient 

{
	
	private String bsn;
	private String postcodeMedicom;
	private String geboortedatum;
	private String woonverband;
	private String mensnummer;
	private String adresMedicom;
	
	private String postcodeSVBZ;
	private String straat;
	private String nummer;
	private String stad;
	
	private boolean medicomOW;
	private boolean svbzOW;
	private String opschorting;
	private String onderzoek;
	private String geheim;


	
	
	// From Medicom
	
	public String getBsn() {
		return bsn;
	}	
	public void setBsn(String bsn) {
		this.bsn = bsn;
	}
	public String getPostcodeMedicom() {
		return postcodeMedicom;
	}
	public void setPostcodeMedicom(String postcodeMedicom) {
		this.postcodeMedicom = postcodeMedicom;
	}
	public String getGeboortedatum() {
		return geboortedatum;
	}
	public void setGeboortedatum(String geboortedatum) {
		this.geboortedatum = geboortedatum;
	}
	public String getWoonverband() {
		return woonverband;
	}
	public void setWoonverband(String woonverband) {
		this.woonverband = woonverband;
	}
	public String getAdresMedicom() {
		return this.adresMedicom;
	}
	 public void setAdresMedicom(String adresMedicom) {
		this.adresMedicom = adresMedicom;
	}
	public String getMensnummer() {
		return this.mensnummer;
	}
	public void setMensnummer(String mensnummer) {
		this.mensnummer = mensnummer;
	}
		
	// From SBV-Z
	
	public String getPostcodeSVBZ() {
		return postcodeSVBZ;
	}
	public void setPostcodeSVBZ(String postcodeSVBZ) {
		this.postcodeSVBZ = postcodeSVBZ;
	}
	public String getStraat() {
		return straat;
	}
	public void setStraat(String straat) {
		this.straat = straat;
	}
	public String getNummer() {
		return nummer;
	}
	public void setNummer(String nummer) {
		this.nummer = nummer;
	}
	public String getStad() {
		return this.stad;
	}
	public void setStad(String stad) {
		this.stad = stad;
	}

	
	
	//Results
	
	public boolean isMedicomOW() {
		return medicomOW;
	}
	public void setMedicomOW(boolean medicomOW) {
		this.medicomOW = medicomOW;	
	}
	public boolean isSvbzOW() {
		return svbzOW;
	}
	public void setSvbzOW(boolean svbzOW) {
		this.svbzOW = svbzOW;
	}
	public String getOpschorting() {
		return opschorting;
	}
	public void setOpschorting(String opschorting) {
		this.opschorting = opschorting;
	}
	public String getOnderzoek() {
		return onderzoek;
	}
	public void setOnderzoek(String onderzoek) {
		this.onderzoek = onderzoek;
	}
	public String getGeheim() {
		return geheim;
	}
	public void setGeheim(String geheim) {
		this.geheim = geheim;
	}
	
}	
	
