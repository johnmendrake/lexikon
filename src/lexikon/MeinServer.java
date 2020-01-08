package lexikon;

public class MeinServer extends Server {

	// hier werden die Begriffe gespeichert
	private static List<Begriff> begriffe = new List<Begriff>();

	// Konstruktor fuer MeinServer, ruft den Konstruktor von Server mit dem
	// uebergebenen Port auf
	public MeinServer(int pPort) {
		super(pPort);
	}

	public static void main(String[] args) {
		// erstellt den Server
		// 33200 fuer mich vorgegebener Port
		new MeinServer(33200);

		// fuegt zwei Begriffe ein
		begriffe.append(new Begriff("foo", "means just foo"));
		begriffe.append(new Begriff("bar", "an popular extention to foo"));
		
//		Parser p = new Parser("src/lexikon/data");
//		begriffe = p.readData();

	}

	@Override
	public void processNewConnection(String pClientIP, int pClientPort) {
		// TODO Auto-generated method stub
		// fuer den vorliegenden Server wird keine Behandlung von neuen Verbindungen
		// benoetigt
	}

	@Override
	public void processMessage(String pClientIP, int pClientPort, String pMessage) {
		// alle empfangenen Nachrichten beginnen mit dem Befehl, gefolgt von einem
		// Doppelpunkt
		// switch fuer Befehle
		switch (pMessage.split(":")[0]) {
		// eine Liste der gespeicherten Begriffe wird vom Client gefordert
		case "FORDERELISTE":
			send(pClientIP, pClientPort, "BEGRIFFE:" + getBegriffe());
			break;
		// die Definition eines Begriffs wird vom Client gefordert
		case "FORDEREBEGRIFF":
			try {
				this.send(pClientIP, pClientPort, "DEF:" + getDefinition(pMessage.split(":")[1]));
			} catch (Exception e) {
				// wird zurueck gegeben, falls Begriff nicht gefunden werden kann
				this.send(pClientIP, pClientPort, "ERROR:kein Begriff uebergeben");
			}
			break;
		default:
			// in jedem anderen Fall ist der Befehl ungueltig
			send(pClientIP, pClientPort, "ERROR:unbekannter Befehl");
		}

	}

	/**
	 * Gibt die Definition fuer den Begriff zurueck.
	 * 
	 * @param pBegriff Begriff, dessen Definition gesucht ist
	 * @return Definition des Begriffs oder Fehlermeldung
	 */
	private static String getDefinition(String pBegriff) {
		begriffe.toFirst();
		while (begriffe.hasAccess()) {
			// wenn der Begriff gefunden wurde, wird die Beschreibung zurueckgegeben und die
			// Suche abgebrochen
			if (begriffe.getContent().getTitel().equals(pBegriff)) {
				return begriffe.getContent().getBeschreibung();
			}
			begriffe.next();
		}
		return String.format("Die Definition für %s konnte leider nicht gefunden werden.", pBegriff);
	}

	/**
	 * Gibt eine mit Kommata getrennte Liste aller gespeicherten Begriffe zurueck.
	 * 
	 * @return Liste der gespeicherten Begriffe
	 */
	private static String getBegriffe() {
		String output = "";
		begriffe.toFirst();
		// durchlaufe alle Begriffe
		while (begriffe.hasAccess()) {
			// haenge Begriff und Komma an Ausgabe an
			output += begriffe.getContent().getTitel();
			output += ",";
			begriffe.next();
		}
		// ist die Ausgabe gleich null, so existieren keine Kommata
		if (output.length() > 0)
			// entferne das letzte Komma
			output = output.substring(0, output.length() - 1);
		return output;
	}

	@Override
	public void processClosingConnection(String pClientIP, int pClientPort) {
		// TODO Auto-generated method stub
		// fuer den vorliegenden Server wird keine Behandlung von neuen Verbindungen
		// benoetigt
	}

}
