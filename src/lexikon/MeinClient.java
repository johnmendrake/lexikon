package lexikon;

import java.util.Arrays;
import java.util.Scanner;

public class MeinClient extends Client {

	Scanner sc = new Scanner(System.in);

	/**
	 * Konstruktor fuer einen Client mit der entsprechenden Serveradresse und dem
	 * benoetigten Port.
	 * 
	 * @param pServerIP   Adresse des Servers
	 * @param pServerPort Port des Servers
	 */
	public MeinClient(String pServerIP, int pServerPort) {
		super(pServerIP, pServerPort);
	}

	public static void main(String[] args) {
		// erstelle einen Client fuer den Konkreten Fall
		MeinClient client = new MeinClient("127.0.0.1", 33200);
//		MeinClient client = new MeinClient("10.66.6.42", 33200);

		// zeige Nutzungsanweisungen an
		hilfe();

		// solange aktiv, verarbeite Anweisungen
		while (true) {
			// hole Eingabe des Nutzers
			String input = client.sc.next();
			switch (input) {
			// fordere Begriffsliste
			case "1":
				// sende Anfrage an Server
				client.send("FORDERELISTE");
				break;
			// fordere Definition an
			case "2":
				System.out.print("Suchen nach Definition fuer: ");
				// generiere Suchbefehl und schicke ihn an Server
				client.send(String.format("FORDEREBEGRIFF:%s", client.sc.next()));
				break;
			case "hilfe":
			case "help":
				hilfe();
				break;
			case "exit":
				System.exit(0);
				// zeige an Fehler angepasste Fehlermeldung
			default:
				System.out.printf("Befehl %s konnte nicht gefunden werden.\n", input);
				System.out.println("Bitte wahlen Sie eine der vorgegeben Aktionen. (hilfe zum Anzeigen)");
			}
		}

	}

	@Override
	public void processMessage(String pMessage) {
		// Befehl kommt vor dem Doppelpunkt
		switch (pMessage.split(":")[0]) {
		// Begriffsliste
		case "BEGRIFFE":
			// teile Nachricht nach Doppelpunkt und formatiere die Begriffsliste
			System.out.println(Arrays.toString(pMessage.split(":")[1].split(",")));
			break;
		// Definition fuer speziellen Begriff
		case "DEF":
			// trenne Befehl von Definition
			System.out.println(pMessage.split(":")[1]);
			break;
		// zeige serverseitige Fehler an
		case "ERROR":
			System.out.println(pMessage);
			break;
		// anderweitige Fehler
		default:
			System.out.println("An unknown ERROR occured!");
		}
	}

	private static void hilfe() {
		System.out.println();
		System.out.println("Fragen Sie Definitionen von Begriffen ab!");
		System.out.println("Um eine Liste aller Begriffe abzufragen, waehlen Sie '1'");
		System.out.println("Um nach einem Begriff suchen zu koennen, waehlen Sie '2'");
		System.out.println("Mit der Eingabe von 'exit' wird das Programm beendet.");
		System.out.println();
	}

}
