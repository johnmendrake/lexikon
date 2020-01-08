package lexikon;

public class Begriff {
	
	private String titel;
	private String beschreibung;
	
	public Begriff(String titel, String beschreibung) {
		super();
		this.titel = titel;
		this.beschreibung = beschreibung;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

}
