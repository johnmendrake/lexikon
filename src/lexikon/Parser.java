package lexikon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
	private File datafile;

	public Parser(String datafile) {
		super();
		this.datafile = new File(datafile);
	}
	
	public List<Begriff> readData() {
		List<Begriff> output = new List<Begriff>();
		try {
			Scanner sc = new Scanner(datafile);
			while (sc.hasNext()) {
				String curline = sc.next();
				Scanner lineSc = new Scanner(curline);
				lineSc.useDelimiter(":");
				output.append(new Begriff(lineSc.next(), lineSc.next()));
				lineSc.close();
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("filepath invalid");
		}
		return output;
	}
}
