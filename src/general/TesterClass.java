package general;

import java.io.IOException;

import org.json.simple.parser.ParseException;

public class TesterClass {

	public static void main(String[] args) throws ParseException, IOException {
			Quotely q = new Quotely();
			q.getQuote();
	}
}
